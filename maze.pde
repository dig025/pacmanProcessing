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
  boolean wall(int i, int j) {
    if(grid[i][j] == '1') {
      return true;
    }

    return false;
  }
}
