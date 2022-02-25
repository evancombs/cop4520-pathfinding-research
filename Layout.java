
// Layout is a wrapper class that holds an arrangement of traversable and
// non-traversable positions, represented as an integer array.
// A value of -1 represents an unaccesible positions.
public class Layout
{
  int width, height;
  int[][] positions;

  // Creates an empty Layout of width x height
  public Layout(int width, int height)
  {
    this.width = width;
    this.height = height;
    positions = new int[width][height];
  }

  // Creates a Layout from a given position array
  public Layout(int[][] positions)
  {
    this.width = positions.length;
    this.height = positions[0].length;
    this.positions = new int[width][height];

    for (int i = 0; i < width; i++)
      for (int j = 0; j < height; j++)
        this.positions[i][j] = positions[i][j];
  }


  // Getters
  public int GetHeight()
  {
    return height;
  }
  public int GetWidth()
  {
    return width;
  }

  // WIP conversions to convetional graph representation
  public static void LayoutToAdjacencyList()
  {

  }

  public static void LayoutToAdjacencyMatrix()
  {

  }
}
