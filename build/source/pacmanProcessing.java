import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class pacmanProcessing extends PApplet {

int tileSize;
Player player;
Maze maze;

int maze_w = 560;
int maze_h = 620;

int start_w = 25;
int start_h = 25;

PFont f1;
int highscore;
int score;
int speed;

public void setup() {
  /** We use an image of the pacman maze and we split it into tiles
   * eventually each tile will be food, a wall, the player, or a ghost
  **/
  
  frameRate(60);

  f1 = createFont("Arial Bold", 18);
  tileSize = 20;
  player = new Player();
  maze = new Maze("walls.txt");
  speed = 2;

}

public void draw() {
  //Draw the background and maze first
  background(0);
  maze.display();

  //Show the highscore current score and
  textFont(f1);
  fill(255);
  text("HIGH SCORE: " + 0, 100, 17);    //not implemented yet
  text("CURRENT SCORE: " + player.score, 350, 17);

  //This is just to help visualize the grid
  /*stroke(255);
  fill(255);
  for(int i = start_w; i <= maze_w + start_w; i+=tileSize) {
    line(i, start_h, i, maze_h + start_h);
  }
  for(int j = start_h; j <= maze_h + start_h; j += tileSize) {
    line(start_w, j, maze_w + start_w, j);
  }
  */

  for(int i = 0; i < speed; ++i) {
    player.display();
    player.move(maze);
  }

}

public void keyPressed() {
  if(key == 'w') {
    player.changeDir(0);
  }
  if(key == 'a') {
    player.changeDir(2);
  }
  if(key == 's') {
    player.changeDir(1);
  }
  if(key == 'd') {
    player.changeDir(3);
  }
}
class Entity {
  PVector gridPos, gridOff;  //The entity's position on the grid and the offset
  PVector pos, vel; //The entity's position and velocity in pixels
  PVector nextDir;
  int tile, size;   //The size of a tile and the size of the Entity

  Entity() {
    tile = 20;
    size = 15;
    gridPos = new PVector(14, 23);
    gridOff = new PVector(25 + tile / 2, 25 + tile / 2);
    pos = PVector.mult(gridPos, tile);  //To get pixel pos, we multiply the gridpos by the tilesize
    pos.add(gridOff);                   //Then we offset it by 25 + half a tile
    vel = new PVector(0, 0);
    nextDir = new PVector(0, 0);

  }

  public void move(Maze maze) {
    //filler
  }

  public void display() {
    //filler
  }

  public void changeDir(int dir) {
    int hor, ver;
    hor = 0;
    ver = 0;

    switch(dir) {
      case 0:
        ver = -1;
        hor = 0;
        break;
      case 1:
        ver = 1;
        hor = 0;
        break;
      case 2:
        ver = 0;
        hor = -1;
        break;
      case 3:
        ver = 0;
        hor = 1;
        break;
    }

    nextDir = new PVector(hor, ver);
  }

}
/**
 * A maze is just a matrix of 1's and 0's created from a text file
 * if the index contains a 1, it is a wall
 * on the other hand if it is a 0, it is an open space
 * since it is a character matrix we can use more than just 1's
 * and 0's, maybe f for food and g if a ghost is in the cell or p for player
 **/
class Maze {
  String[] lines;
  char[][] grid;
  int cols, rows;
  int tile;
  int offset;
  int foodSize, pelletSize;

  Maze(String maze) {
    lines = loadStrings(maze);
    cols = 28;
    rows = 31;
    grid = new char[cols][rows];
    tile = 20;
    offset = 25;   //Because the grid is centered, we have to account for the offset
    foodSize = 5;
    pelletSize = 8;

    for(int i = 0; i < cols; ++i) {
      for(int j = 0; j < rows; ++j) {
        grid[i][j] = lines[j].charAt(i);
      }
    }
  }

/*
 * This returns true if the cell at i j is a 1 (a wall)
 * false otherwise
 */
  public char getCell(int i, int j) {
    return grid[i][j];
  }

  /*
   * Allows the user to set a specific cell in the grid to any char
   * ideally would be 'f' or '0' (when the player eats food 'f', set to empty '0' )
   */
  public void setCell(int i, int j, char c) {
    grid[i][j] = c;
  }

  /*
   * This will display the maze using plain rectangles
   * can also be used to display food for player...
   * the problem is we have to loop through the whole matrix linearly :(
   */
   public void display() {
     for(int i = 0; i < cols; ++i) {
       for(int j = 0; j < rows; ++j) {
         int x = i * tile + offset;
         int y = j * tile + offset;
         if(grid[i][j] == '1') {
           noStroke();
           fill(0, 0, 255);
           rectMode(CORNER);
           rect(x, y, tile, tile);
         } else if(grid[i][j] == 'f') {
           fill(255);
           rectMode(CENTER);
           rect(x + tile / 2, y + tile / 2, foodSize, foodSize);
         } else if(grid[i][j] == 'p') {
           fill(255);
           ellipse(x + tile / 2, y + tile / 2, pelletSize, pelletSize);
         }
       }
     }

   }
}
class Player extends Entity {
  int score;
  boolean power;

  Player() {
    super();
    score = 0;
    power = false;
  }

  /**
   * moves the player one pixel at a time in whatever direction the vel is
   * will collide with walls, if the player enters a cell with food in it,
   * it will consume the food and score will go up
   **/
  public void move(Maze maze) {
    if(gridPos.x % 1 == 0 && gridPos.y % 1 == 0) {
      char nextCell;

      if(nextDir.x != vel.x || nextDir.y != vel.y) {
        nextCell = maze.getCell(PApplet.parseInt(gridPos.x + nextDir.x), PApplet.parseInt(gridPos.y + nextDir.y));
        if(nextCell == '1') {
          //Do nothing, cant turn into a wall
        } else {
          vel = nextDir.copy();
        }
      }

      nextCell = maze.getCell(PApplet.parseInt(gridPos.x + vel.x), PApplet.parseInt(gridPos.y + vel.y));
      if(nextCell == '1') {
        vel.mult(0);
      } else if(nextCell == 'f') {    //**********Need to try and make coin eating seem more smooth (eating coin too early)**********//
        score += 10;
        maze.setCell(PApplet.parseInt(gridPos.x + vel.x), PApplet.parseInt(gridPos.y + vel.y),'0');
      } else if(nextCell == 'p') {
        score += 50;
        power = true;
        maze.setCell(PApplet.parseInt(gridPos.x + vel.x), PApplet.parseInt(gridPos.y + vel.y),'0');
      } else if(nextCell == 'r') {
        gridPos = new PVector(4, 14);
        pos = PVector.mult(gridPos, tile);  //To get pixel pos, we multiply the gridpos by the tilesize
        pos.add(gridOff);                   //Then we offset it by 25 + half a tile
      } else if(nextCell == 'l') {
        gridPos = new PVector(23, 14);
        pos = PVector.mult(gridPos, tile);  //To get pixel pos, we multiply the gridpos by the tilesize
        pos.add(gridOff);                   //Then we offset it by 25 + half a tile
      }
    }

    pos.add(vel);
    gridPos = PVector.sub(pos, gridOff);
    gridPos.div(tile);
  }

  public void display() {
    if(power){
      fill(255, 0, 0);
    } else {
      fill(255, 255, 0);
    }

    noStroke();
    ellipse(pos.x, pos.y, size, size);

    stroke(255, 0, 0);
    noFill();
    int x, y;
    x = round(gridPos.x);
    x *= tile;
    x += gridOff.x - tile / 2;
    y = round(gridPos.y);
    y *= tile;
    y += gridOff.y - tile / 2;
    rect(x, y, tile, tile);     //this just helps visualize the players pos on the grid
  }

}
  public void settings() {  size(610, 670); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "pacmanProcessing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
