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

PImage bg;
int tileSize;
Player player;
Maze maze;

int img_w = 560;
int img_h = 620;

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

  bg = loadImage("maze.png");  //560x620 (the grid is 28 x 31)
  f1 = createFont("Arial Bold", 18);
  tileSize = 20;
  player = new Player();
  maze = new Maze("walls.txt");
  speed = 2;

}

public void draw() {
  //Draw the background and maze first
  background(0);
  image(bg, 25, 25);

  //Show the highscore current score and
  textFont(f1);
  fill(255);
  text("HIGH SCORE: " + highscore, 100, 17);
  text("CURRENT SCORE: " + score, 350, 17);

  //This is just to help visualize the grid
  stroke(255);
  fill(255);
  for(int i = start_w; i <= img_w + start_w; i+=tileSize) {
    line(i, start_h, i, img_h + start_h);
  }
  for(int j = start_h; j <= img_h + start_h; j += tileSize) {
    line(start_w, j, img_w + start_w, j);
  }

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
  PVector gridPos, gridOff;  //The player's position on the grid and the offset
  PVector pos, vel; //The player's position and velocity in pixels
  PVector nextDir;
  int tile, size;   //The size of a tile and the size of the player

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
    if(gridPos.x % 1 == 0 && gridPos.y % 1 == 0) {
      if(nextDir.x != vel.x || nextDir.y != vel.y) {
        if(maze.wall(PApplet.parseInt(gridPos.x + nextDir.x), PApplet.parseInt(gridPos.y + nextDir.y))) {
          //Do nothing, cant turn into a wall
        } else {
          vel = nextDir.copy();
        }
      }
      if(maze.wall(PApplet.parseInt(gridPos.x + vel.x), PApplet.parseInt(gridPos.y + vel.y))) {
        vel.mult(0);
      }
    }

    pos.add(vel);
    gridPos = PVector.sub(pos, gridOff);
    gridPos.div(tile);
  }

  public void display() {
    fill(255, 255, 0);
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

  Maze(String maze) {
    lines = loadStrings(maze);
    cols = 28;
    rows = 31;
    grid = new char[cols][rows];

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
  public boolean wall(int i, int j) {
    if(grid[i][j] == '1') {
      return true;
    }

    return false;
  }
}
class Player extends Entity {
  Player() {
    super();
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
