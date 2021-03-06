import java.util.*;
import java.awt.Point;

public class AStar extends Pathfinder {

  public static void main(String[] args) {
    //Basic test case
    int[][] testArr =
    {{1, 1, 1, 1},
     {1, -1, -1, -1},
     {1, -1, 1, 1},
     {1, -1, 1, 1},
     {1, 1, 1, 1}};

     Point start = new Point(0,0);
     Point end = new Point(3,4);

     // Test case using the layout system
     AStar aStar = new AStar();
     aStar.findPaths(new Layout("./tests/layout1.txt"));
  }

  @Override
  public void findPaths(Layout layout) {
    Node head = new Node(layout.start.x, layout.start.y, false);
    Node target = new Node(layout.end.x, layout.end.y, false);

    Node[][] map = new Node[layout.height][layout.width];
    for (int i = 0; i < layout.height; i++)
      for (int j = 0; j < layout.width; j++)
        map[i][j] = new Node(i, j, layout.positions[i][j] == -1);

    head.g = 0;
    Node res = aStar(head, target, map);
    int n = 100;
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

  public static Node aStar(Node start, Node target, Node[][] map){
      PriorityQueue<Node> closedList = new PriorityQueue<>();
      PriorityQueue<Node> openList = new PriorityQueue<>();

      // Left, down-left, down, down-right, right, up-right, up, up-left
      int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
      int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
      double[] moveCost = {1, 1.4, 1, 1.4, 1, 1.4, 1, 1.4};

      start.f = start.g + start.calculateHeuristic(target);
      openList.add(start);

      while(!openList.isEmpty()){
          Node n = openList.peek();
          if(n.x == target.x && n.y == target.y){
            return n;
          }
          for(int i = 0; i < dx.length; i++){
            if (n.x + dx[i] < 0 || n.x + dx[i] >= map.length) continue;
            if (n.y + dy[i] < 0 || n.y + dy[i] >= map[n.x].length) continue;
            Node m = map[n.x + dx[i]][n.y + dy[i]];
            if (m.blocked) continue;
            double totalCost = n.g + moveCost[i];
            if(!openList.contains(m) && !closedList.contains(m)){
              m.parent = n;
              m.g = totalCost;
              m.f = m.g + m.calculateHeuristic(target);
              openList.add(m);
            } else {
              if(totalCost < m.g){
                m.parent = n;
                m.g = totalCost;
                m.f = m.g + m.calculateHeuristic(target);

                if(closedList.contains(m)){
                  closedList.remove(m);
                  openList.add(m);
                }
              }
            }
          }
          openList.remove(n);
          closedList.add(n);
          // try{
          //   Thread.sleep(2000);
          // }
          // catch(Exception e) {
          // }
          // System.out.println("Node: " + "(" + n.x + ", " + n.y + ") " + n.f + " " + n.calculateHeuristic(target));
          // for (Node e : Arrays.copyOf(openList.toArray(), openList.toArray().length, Node[].class)) {
          //   System.out.print("(" + e.x + ", " + e.y + ", " + e.f + ")");
          // }
          // System.out.println();
      }
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
    int D = 1;
    int D2 = 2;
    int dx = Math.abs(x - target.x);
    int dy = Math.abs(y - target.y);
    return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
  }
}
