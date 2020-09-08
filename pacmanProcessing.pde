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

void setup() {
  /** We use an image of the pacman maze and we split it into tiles
   * eventually each tile will be food, a wall, the player, or a ghost
  **/
  size(610, 670);
  frameRate(60);

  bg = loadImage("maze.png");  //560x620 (the grid is 28 x 31)
  f1 = createFont("Arial Bold", 18);
  tileSize = 20;
  player = new Player();
  maze = new Maze("walls.txt");
  speed = 2;

}

void draw() {
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

void keyPressed() {
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
