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
  void move(Maze maze) {
    if(gridPos.x % 1 == 0 && gridPos.y % 1 == 0) {
      char nextCell;

      if(nextDir.x != vel.x || nextDir.y != vel.y) {
        nextCell = maze.getCell(int(gridPos.x + nextDir.x), int(gridPos.y + nextDir.y));
        if(nextCell == '1') {
          //Do nothing, cant turn into a wall
        } else {
          vel = nextDir.copy();
        }
      }

      nextCell = maze.getCell(int(gridPos.x + vel.x), int(gridPos.y + vel.y));
      if(nextCell == '1') {
        vel.mult(0);
      } else if(nextCell == 'f') {
        score += 10;
        maze.setCell(int(gridPos.x + vel.x), int(gridPos.y + vel.y),'0');
      } else if(nextCell == 'p') {
        score += 50;
        power = true;
        maze.setCell(int(gridPos.x + vel.x), int(gridPos.y + vel.y),'0');
      }
    }

    pos.add(vel);
    gridPos = PVector.sub(pos, gridOff);
    gridPos.div(tile);
  }

  void display() {
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
