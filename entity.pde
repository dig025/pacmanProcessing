class Entity {
  PVector gridPos, gridOff;  //The entity's position on the grid and the offset
  PVector pos, vel; //The entity's position and velocity in pixels
  PVector nextDir;
  int tile, size;   //The size of a tile and the size of the Entity

  Entity() {
    tile = 20;
    size = 15;
  }

  void move(Maze maze) {
    //filler
  }

  void display() {
    //filler
  }
  
  /**
   * Gets the players current grid position and returns it as a PVector
   **/
  PVector getPos() {
    PVector p = gridPos.copy();
    return p;
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
