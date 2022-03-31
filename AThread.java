import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class AThread implements Runnable {

  Node node;
  Node parent;
  double cost;
  AParallel path;

  public AThread(Node node, Node parent, double cost, AParallel path) {
    this.node = node;
    this.parent = parent;
    this.path = path;
    this.cost = cost;
  }

  public void run() {
    double totalCost = parent.g + cost;
    if (!path.openList.contains(node) && !path.closedList.contains(node)){
        node.parent = parent;
        node.g = totalCost;
        node.f = node.g + node.calculateHeuristic(path.target);
        path.openList.add(node);
    } else {
        if(totalCost < node.g){
            node.parent = parent;
            node.g = totalCost;
            node.f = node.g + node.calculateHeuristic(path.target);
            if(path.closedList.contains(node)){
                path.closedList.remove(node);
                path.openList.add(node);
            }
        }
    }
  }
}
