import java.awt.Point;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

// Layout is a wrapper class that holds an arrangement of traversable and
// non-traversable positions, represented as an integer array.
// A value of -1 represents an unaccesible positions.
public class Layout
{
  int width, height;
  int[][] positions;

  Point start, end;

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

  // Loads a layout from a txt file
  public Layout(String filename)
  {
    this(new File(filename));
  }

  public Layout(File file)
  {
    Scanner in;
    try
    {
      in = new Scanner(file);
    }
    catch(Exception e)
    {
      System.out.println("File not found " + e);
      return;
    }
    this.width = in.nextInt();
    this.height = in.nextInt();
    this.positions = new int[width][height];

    for(int x = 0; x < width; x++)
    {
      for(int y = 0; y < height; y++)
      {
        String temp = in.next();
        if (temp.charAt(0) == 'S')
          this.start = new Point(x,y);
        else if (temp.charAt(0) == 'E')
          this.end = new Point(x,y);
        else
          positions[x][y] = Integer.parseInt(temp);
      }
    }
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

  public Point GetStart()
  {
    return start;
  }

  public Point GetEnd()
  {
    return end;
  }

  // WIP conversions to convetional graph representation
  public static void LayoutToAdjacencyList()
  {

  }

  public static void LayoutToAdjacencyMatrix()
  {

  }

  public ArrayList<Point> GetAdjacentPoints(Point point)
  {
    ArrayList<Point> validPoints = new ArrayList<>();
    if (point.x - 1 >= 0 && point.x - 1 < width && positions[point.x - 1][point.y] != -1)
      validPoints.add(new Point(point.x - 1, point.y));
    if (point.x + 1 >= 0 && point.x + 1 < width && positions[point.x + 1][point.y] != -1)
      validPoints.add(new Point(point.x + 1, point.y));
    if (point.y - 1 >= 0 && point.y - 1 < width && positions[point.x][point.y - 1] != -1)
      validPoints.add(new Point(point.x, point.y - 1));
    if (point.y + 1 >= 0 && point.y + 1 < width && positions[point.x][point.y + 1] != -1)
      validPoints.add(new Point(point.x, point.y + 1));

    return validPoints;
  }



}
