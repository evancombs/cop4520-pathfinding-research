import java.util.*;

public class AThread extends Thread {

  Node node;
  Node parent;
  Node target;
  double cost;

  PriorityQueue<Node> openList;
  PriorityQueue<Node> closedList;

  public AThread(Node node, Node parent, Node target, double cost, PriorityQueue<Node> openList, PriorityQueue<Node> closedList) {
    this.node = node;
    this.parent = parent;
    this.target = target;
    this.cost = cost;
    this.openList = openList;
    this.closedList = closedList;
  }

  public void run() {
    System.out.println(node.x + " " + node.y + " " + cost);
    double totalCost = parent.g + cost;
    if(!openList.contains(node) && !closedList.contains(node)){
        node.parent = parent;
        node.g = totalCost;
        node.f = node.g + node.calculateHeuristic(target);
        openList.add(node);
    } else {
        if(totalCost < node.g){
            node.parent = parent;
            node.g = totalCost;
            node.f = node.g + node.calculateHeuristic(target);
            if(closedList.contains(node)){
                closedList.remove(node);
                openList.add(node);
            }
        }
    }
  }


}
