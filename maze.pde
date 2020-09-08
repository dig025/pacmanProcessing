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
  char getCell(int i, int j) {
    return grid[i][j];
  }

  /*
   * Allows the user to set a specific cell in the grid to any char
   * ideally would be 'f' or '0' (when the player eats food 'f', set to empty '0' )
   */
  void setCell(int i, int j, char c) {
    grid[i][j] = c;
  }

  /*
   * This will display the maze using plain rectangles
   * can also be used to display food for player...
   * the problem is we have to loop through the whole matrix linearly :(
   */
   void display() {
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
