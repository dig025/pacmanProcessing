class Ghost extends Entity {
  Player player;
  PVector target;
  boolean dead, active;

  Ghost(Player p) {
    super();

    player = p;
    target = p.getPos();

    gridPos = new PVector(14, 14);
    gridOff = new PVector(25 + tile / 2, 25 + tile / 2);
    pos = PVector.mult(gridPos, tile);  //To get pixel pos, we multiply the gridpos by the tilesize
    pos.add(gridOff);                   //Then we offset it by 25 + half a tile

    vel = new PVector(0, 0);
    nextDir = new PVector(0, 0);

    active = false;
    dead = false;
  }

  void activate() {
    active = true;
  }

  void move() {
    if(active) {

    }

    if(dead) {

    }

  }

  void display() {
    if(active) {

    }

    if(dead) {

    }

  }

}
