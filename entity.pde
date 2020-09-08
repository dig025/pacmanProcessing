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

  void move(Maze maze) {
    //Just a defenition
  }

  void display() {
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

  void changeDir(int dir) {
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
