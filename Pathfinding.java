
// Pathfinding is the driver class that different algorithms are evaluated on.
// Individual algorithms should be implemented as respective classes that implement
// concurrency as required.

public class Pathfinding
{
  public Layout commonLayout;

  public static void main(String[] args)
  {
    int[][] testArr =
    {{1, -1, 1, 1},
     {1, -1, 1, 1},
     {1, 1, 1, 1},
     {1, -1, 1, 1}};

    Layout test = new Layout(testArr);
    test.printLayout();

    Pathfinder example = new ExampleAlgorithm();

    example.findPaths(test);
  }
}
