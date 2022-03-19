import java.awt.Point;
// Layout is a wrapper class that holds an arrangement of traversable and
// non-traversable positions, represented as an integer array.
// A value of -1 represents an unaccesible positions.
public class Layout
{
  int width, height;
  int[][] positions;

  public Point start, end;

  // Creates an empty Layout of width x height
  public Layout(int width, int height, Point start, Point end)
  {
    this.width = width;
    this.height = height;

    this.start = start;
    this.end = end;

    positions = new int[width][height];
  }

  // Creates a Layout from a given position array
  public Layout(int[][] positions, Point start, Point end)
  {
    this.width = positions.length;
    this.height = positions[0].length;
    this.positions = new int[width][height];

    this.start = start;
    this.end = end;

    for (int i = 0; i < width; i++)
      for (int j = 0; j < height; j++)
        this.positions[i][j] = positions[i][j];
  }

  // Prints to console a representation of this Layout
  public void printLayout()
  {
    // Usually use row major; using column major here just for printing purposes
    for (int j = 0; j < width; j++)
      System.out.print(" _");
    System.out.println();

    for (int i = 0; i < height; i++)
    {
      System.out.print('|');
      for (int j = 0; j < width; j++)
      {
        System.out.print(positions[i][j] == -1 ? "X " : "  ");
      }
      System.out.print('|');
      System.out.println();
    }

    for (int j = 0; j < width; j++)
      System.out.print(" _");
    System.out.println();
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
