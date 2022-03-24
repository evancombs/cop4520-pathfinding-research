import java.util.*;
import java.awt.Point;

public class A extends Pathfinder {

  public static void main(String[] args) {
    int[][] testArr =
    {{1, 1, 1, 1},
     {1, -1, -1, -1},
     {1, -1, 1, 1},
     {1, -1, 1, 1},
     {1, 1, 1, 1}};

     Point start = new Point(0,0);
     Point end = new Point(3,4);

     A aStar = new A();
     aStar.findPaths(new Layout(testArr, start, end));
  }

  @Override
  public void findPaths(Layout layout) {
    Node head = new Node(layout.start.x, layout.start.y, 1, false);
    Node target = new Node(layout.end.x, layout.end.y, 1, false);
    Node[][] map = new Node[layout.width][layout.height];
    for (int i = 0; i < layout.width; i++) {
      for (int j = 0; j < layout.height; j++) {
        map[i][j] = new Node(j, i, 1, layout.positions[i][j] == -1);
        // System.out.print(layout.positions[i][j] + " ");
      }
      // System.out.println();
    }
    head.g = 0;

    Node res = aStar(head, target, map);
    printPath(res);
  }

  public static Node aStar(Node start, Node target, Node[][] map){
      PriorityQueue<Node> closedList = new PriorityQueue<>();
      PriorityQueue<Node> openList = new PriorityQueue<>();

      // Left, down-left, down, down-right, right, up-right, up, up-left
      int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
      int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
      int[] moveCost = {1, 2, 1, 2, 1, 2, 1, 2};

      start.f = start.g + start.calculateHeuristic(target);
      openList.add(start);

      while(!openList.isEmpty()){
          Node n = openList.peek();
          if(n.x == target.x && n.y == target.y){
              return n;
          }

          for(int i = 0; i < dx.length; i++){
              if (n.y + dy[i] < 0 || n.y + dy[i] >= map.length) continue;
              if (n.x + dx[i] < 0 || n.x + dx[i] >= map[n.x].length) continue;
              Node m = map[n.y + dy[i]][n.x + dx[i]];
              // System.out.println("(" + m.x + ", " + m.y + ") ");
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

  Node(int x, int y, double h, boolean blocked){
    this.x = x;
    this.y = y;
    this.h = h;
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
