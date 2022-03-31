import java.util.*;
import java.awt.Point;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AParallel extends Pathfinder {

  public static PriorityBlockingQueue<Node> openList;
  public static PriorityBlockingQueue<Node> closedList;
  public static Node target;

  public static void main(String[] args) {
    // Basic test case
    int[][] testArr =
    {{1, 1, 1, 1},
     {1, -1, -1, -1},
     {1, -1, 1, 1},
     {1, -1, 1, 1},
     {1, 1, 1, 1}};

     Point start = new Point(0,0);
     Point end = new Point(3,4);

     AParallel aStar = new AParallel();
     aStar.findPaths(new Layout("./tests/layout5.txt"));
  }

  @Override
  public void findPaths(Layout layout) {
    Node head = new Node(layout.start.x, layout.start.y, false);
    target = new Node(layout.end.x, layout.end.y, false);

    Node[][] map = new Node[layout.height][layout.width];
    for (int i = 0; i < layout.height; i++)
      for (int j = 0; j < layout.width; j++)
        map[i][j] = new Node(i, j, layout.positions[i][j] == -1);

    head.g = 0;

    Node res = aStar(head, target, map);
    int n = 1;
    long startTime, endTime, duration = 0;
    for (int i = 0 ; i < n; i++) {
      startTime = System.nanoTime();
      res = aStar(head, target, map);
      endTime = System.nanoTime();
      duration += (endTime - startTime);
    }
    printPath(res);
    double avg = (double)duration / 1000000 / n;
    System.out.println(avg);
  }

  public Node aStar(Node start, Node target, Node[][] map){
      ExecutorService executor = Executors.newFixedThreadPool(8);
      List<Future<?>> futures = new ArrayList<Future<?>>();

      // Left, down-left, down, down-right, right, up-right, up, up-left
      int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
      int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
      double[] moveCost = {1, 1.4, 1, 1.4, 1, 1.4, 1, 1.4};

      openList = new PriorityBlockingQueue<>();
      closedList = new PriorityBlockingQueue<>();

      start.f = start.g + start.calculateHeuristic(target);
      openList.add(start);

      while(!openList.isEmpty()){
          Node n = openList.peek();
          if(n.x == target.x && n.y == target.y){
              executor.shutdown();
              return n;
          }
          // System.out.println("Start threads for " + n.x + " " + n.y);
          // Loop through all the neighbouring nodes
          for(int i = 0; i < dx.length; i++){
              // Check if the node is in the bounds of the map
              if (n.x + dx[i] < 0 || n.x + dx[i] >= map.length) continue;
              if (n.y + dy[i] < 0 || n.y + dy[i] >= map[n.x].length) continue;

              Node m = map[n.x + dx[i]][n.y + dy[i]];
              if (m.blocked) continue;
              // System.out.println(m.x + " " + m.y);
              Runnable task = new AThread(m, n, moveCost[i], this);
              Future<?> f = executor.submit(task);
              futures.add(f);
          }
          try{
            for(Future<?> future : futures){
              future.get();
            }
          }
          catch(Exception e) {
          }
          openList.remove(n);
          closedList.add(n);
      }
      executor.shutdown();
      return null;
  }

  public static void printPath(Node target){
      Node n = target;
      if (n == null) {
        System.out.println("No valid path");
        return;
      }
      List<Node> nodes = new ArrayList<>();
      while(n.parent != null){
          nodes.add(n);
          n = n.parent;
      }
      nodes.add(n);
      Collections.reverse(nodes);
      for(Node node : nodes){
          System.out.print("(" + node.x + ", " + node.y + ") ");
      }
      System.out.println("");
  }

}

class Node implements Comparable<Node> {
  public int x;
  public int y;
  public boolean blocked;
  public Node parent = null;

  public double f = Double.MAX_VALUE;
  public double g = Double.MAX_VALUE;
  public double h;

  Node(int x, int y, boolean blocked){
    this.x = x;
    this.y = y;
    this.blocked = blocked;
  }

  @Override
  public int compareTo(Node n) {
        return Double.compare(this.f, n.f);
  }

  public double calculateHeuristic(Node target){
        return this.h;
  }
}
