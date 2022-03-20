import java.awt.Point;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class BFS extends Pathfinder
{

  private class PathNode
  {
    Point data, parent;
    public PathNode(Point data, Point parent)
    {
      this.data = data;
      this.parent = parent;
    }
    public PathNode(int x, int y, Point parent)
    {
      this.data = new Point(x,y);
      this.parent = parent;
    }

    public Point GetData()
    {
      return data;
    }

    public Point GetParent()
    {
      return parent;
    }
  }

  public void findPaths(Layout layout)
  {
    int numThreads = 4;
    ArrayList<Thread> threads = new ArrayList<>();

    // Create a new labyrinth for the guests to enter
    ThreadRunner threadRunner = new ThreadRunner(layout, numThreads);
    for (int i = 0; i < numThreads; i++)
    {
      // We can name the guests and elect guest 0 as the leader.
      Thread thread = new Thread(threadRunner, String.valueOf(i));
      threads.add(thread);
    }

    for (Thread thread : threads)
        thread.start();
  }


  public void findPathsSequential(Layout layout)
  {
    boolean[][] visited = new boolean[layout.GetWidth()][layout.GetHeight()];

    Queue<PathNode> queue = new LinkedList<>();

    Point start = layout.GetStart();
    Point end = layout.GetEnd();

    visited[start.x][start.y] = true;
    queue.add(new PathNode(start, null));
    while(!queue.isEmpty())
    {
      PathNode temp = queue.remove();
      if (temp.GetData().equals(end))
      {
        // WE FOUND THE GOAL
      }
      // there are at most four possible edges
      // PathNode adjacent = new PathNode(new Point(0,1))
      ArrayList<Point> adjacentNodes = layout.GetAdjacentPoints(temp.GetData());
    }
  }
}



class ThreadRunner implements Runnable
{
  Layout layout;
  int numThreads;
  ArrayList<Point> targets;
  public ThreadRunner(Layout layout, int numThreads)
  {
    this.layout = layout;
    this.numThreads = numThreads;

    FindNodeTargets();

    for (Point point : targets)// debug
      System.out.println(point.toString());
  }

  // FindNodeTargets() draws a line between the start and end, and uses Bresenham's
  // algorithm to find the positions along that line.
  private ArrayList<Point> FindNodeTargets()
  {
    // ArrayList<Point> targets = new ArrayList<Point>();
    targets = new ArrayList<Point>();

    Point start = layout.GetStart();
    Point end = layout.GetEnd();

    // An implementation of Bresenham's algorithm to find node targets from start to end.
    int dx = end.x - start.x;
    int dy = end.y - start.y;

    int D = 2 * dy - dx;
    int y = start.y;

    for (int x = start.x; x <= end.x; x++)
    {
      targets.add(new Point(x, y));
      if (D > 0)
      {
        y = y + 1;
        D = D - 2 * dx;
      }
      D = D + 2 * dy;
    }
    return targets;
  }

  // Parallel idea: Divide the distance between the start and end point
  // up among the threads, and BFS between each once.
  // Thread 0 searches from start to target 0, thread 1 searches from target 0
  // to target 1, and so on.
  public void run()
  {
    boolean[][] visted = new boolean[layout.GetWidth()][layout.GetHeight()];

    int id = Integer.parseInt(Thread.currentThread().getName());
    // To divide the targets up effectively,

    Point threadStart;
    Point threadEnd;
  }
}
