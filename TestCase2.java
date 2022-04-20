// Runs implemented concurrent algorithms against eachother on a large layout with
// a path
// Exports to output2.txt with results of execution.
// Records only the time taken to complete execution.
// Only compares concurrent implementation (implementations of Pathfinder)
import java.io.*;
import java.util.*;
import java.lang.System;

public class TestCase2
{
  public static void main(String [] args)
  {
    int i = 0;
    double[] times;
    double start, end;
    ArrayList<Pathfinder> algorithms = new ArrayList<>();

    Layout testLayout = new Layout("tests/layout4.txt");

    algorithms.add(new BFS());
    algorithms.add(new AParallel());

    times = new double[algorithms.size()];

    for (Pathfinder alg : algorithms)
    {
      start = System.currentTimeMillis();
      alg.findPaths(testLayout);
      end = System.currentTimeMillis();
      times[i++] = (end - start);
    }

    try
    {
      PrintWriter out = new PrintWriter("output2.txt");

      for(i = 0; i < algorithms.size(); i++)
        out.println("" + algorithms.get(i).getClass().getName() + " " + times[i] + "ms");

      out.close();
    }
    catch(Exception e)
    {
      System.out.println("Error occured opening file!" + e);

    }


  }

}
