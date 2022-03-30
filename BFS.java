import java.awt.Point;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BFS extends Pathfinder
{
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

        try
        {
          for (Thread thread : threads)
            thread.join();
        }
        catch (Exception e)
        {
          System.out.println("Failed joining threads!");
        }
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
        while(temp.GetParent() != null)
        {
          System.out.println(temp.GetData().toString());
          temp = temp.GetParent();
        }
        return;
      }
      // there are at most four possible edges
      // PathNode adjacent = new PathNode(new Point(0,1))
      ArrayList<Point> adjacentNodes = layout.GetAdjacentPoints(temp.GetData());

      for (Point adjacentNode : adjacentNodes)
      {
        if (!visited[adjacentNode.x][adjacentNode.y])
        {
          visited[adjacentNode.x][adjacentNode.y] = true;
          queue.add(new PathNode(adjacentNode, temp));
        }
      }
    }
    System.out.println("No path found from start to end!");
  }
}

// PathNode is a wrapper / linked list to keep track
// of the path we took through the layout / graph
class PathNode
{
  Point data;
  PathNode parent;
  public PathNode(Point data, PathNode parent)
  {
    this.data = data;
    this.parent = parent;
  }
  public PathNode(int x, int y, PathNode parent)
  {
    this.data = new Point(x,y);
    this.parent = parent;
  }

  public Point GetData()
  {
    return data;
  }

  public PathNode GetParent()
  {
    return parent;
  }
}


class ThreadRunner implements Runnable
{
  Layout layout;
  int numThreads;
  ArrayList<Point> targets;

  AtomicBoolean[][] visited;
  ConcurrentLinkedQueue<PathNode> queue;
  Point start;
  Point end;

  AtomicInteger nodesLeftInLevel = new AtomicInteger();

  AtomicBoolean endflag;

  public ThreadRunner(Layout layout, int numThreads)
  {
    this.layout = layout;
    this.numThreads = numThreads;

    start = layout.GetStart();
    end = layout.GetEnd();

    endflag = new AtomicBoolean(true);

    visited = new AtomicBoolean[layout.GetWidth()][layout.GetHeight()];

    // Initialize visited array
    for (int i = 0; i < layout.GetWidth(); i++)
    {
      for (int j = 0; j < layout.GetHeight(); j++)
      {
        visited[i][j] = new AtomicBoolean(false);
      }
    }
    queue = new ConcurrentLinkedQueue<>();

    queue.add(new PathNode(start, null));
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

  // Parallel idea: Using a ConcurrentLinkedQueue, each thread pops nodes that
  // need to be explored / checked as they are able to.

  // Notable issue: Breadth-first search requires all nodes of a specified
  // distance (aka depth or level) are processed before the next distance is.
  // Therefore, we need to prevent
  public void run()
  {
    while(endflag.get() && !queue.isEmpty())
    {
      // if (queue.isEmpty())
        // continue;
      while (nodesLeftInLevel.get() > 0)
        ; // Wait until level is complete
      PathNode temp = queue.poll();

      if (temp == null)
        continue;
      if (temp.GetData().equals(end))
      {
        // We found the goal! We need to alert all the other threads!
        endflag.set(false);

        while(temp.GetParent() != null)
        {
          System.out.println(temp.GetData().toString());
          temp = temp.GetParent();
        }
        return;
      }
      ArrayList<Point> adjacentNodes = layout.GetAdjacentPoints(temp.GetData());

      // We must ensure that adjacent nodes are updated atomically and not added
      // multiple times.
      synchronized(this)
      {
        for (Point adjacentNode : adjacentNodes)
        {
          nodesLeftInLevel.getAndDecrement();
          // nodesLeftInLevel.getAndAdd(adjacentNodes.size());
          if (!visited[adjacentNode.x][adjacentNode.y].get())
          {
            nodesLeftInLevel.getAndIncrement();
            visited[adjacentNode.x][adjacentNode.y].set(true);
            queue.add(new PathNode(adjacentNode, temp));
          }
        }
      }
    }
  }
}
