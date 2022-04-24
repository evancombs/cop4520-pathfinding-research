// Runs implemented concurrent algorithms against eachother on a  layout
// several times, recording the results and taking the average execution time
// Layout5 is a very large layout with no solution.
// Exports to output0.txt with results of execution.
// Records only the time taken to complete execution.
// Only compares concurrent implementation (implementations of Pathfinder)
import java.io.*;
import java.util.*;
import java.lang.System;

public class TestCase4
{
  public static void main(String [] args)
  {
    // int i = 0;
    double[][] times;
    double start, end;
    ArrayList<Pathfinder> algorithms = new ArrayList<>();

    Layout testLayout = new Layout("tests/layout5.txt");

    int numTestIterations = 32;

    algorithms.add(new BFS());
    algorithms.add(new AParallel());

    // times = new double[algorithms.size()];
    times = new double[algorithms.size()][numTestIterations];
    for (int i = 0; i < algorithms.size(); i++)
    {
      for (int j = 0; j < numTestIterations; j++)
      {
        start = System.currentTimeMillis();
        algorithms.get(i).findPaths(testLayout);
        end = System.currentTimeMillis();
        times[i][j] = (end - start);
      }
    }

    double sum = 0;
    double[] avgTimes = new double[algorithms.size()];
    for (int i = 0; i < algorithms.size(); i++)
    {
      for (int j = 0; j < numTestIterations; j++)
      {
        sum += times[i][j];
      }
      avgTimes[i] = sum / numTestIterations;
      sum = 0;
    }


    try
    {
      PrintWriter out = new PrintWriter("output4.txt");

      for (int i = 0; i < algorithms.size(); i++)
      {
        for (int j = 0; j < numTestIterations; j++)
        {
          out.println("" + algorithms.get(i).getClass().getName() + "  " + j + ": " + times[i][j] + "ms");
        }

      }


      for (int i = 0; i < algorithms.size(); i++)
      {
        out.println("" + algorithms.get(i).getClass().getName() + "  Average: " + avgTimes[i] + "ms");
      }

      //for(i = 0; i < algorithms.size(); i++)
        //out.println("" + algorithms.get(i).getClass().getName() + " " + times[i] + "ms");

      out.close();
    }
    catch(Exception e)
    {
      System.out.println("Error occured opening file!" + e);

    }


  }

}
