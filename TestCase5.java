// Runs sequential versions of algorithms against eachother on a simple 4x4 layout.
// Exports to output1.txt with results of execution.
// Records only the time taken to complete execution.
import java.io.*;
import java.util.*;
import java.lang.System;

public class TestCase5
{
  public static void main(String [] args)
  {
    // int i = 0;
    // double[] times;
    double start, end;
    // BArrayList<Pathfinder> algorithms = new ArrayList<>();

    Layout testLayout = new Layout("tests/layout1.txt");

    int numTestIterations = 32;
    //algorithms.add(new BFS());
    // algorithms.add(new AParallel());
    BFS bfs = new BFS();
    Pathfinder astar = new AStar();
    Pathfinder aParallel = new AParallel();

    double[] sBFSTimes = new double[numTestIterations];
    double[] cBFSTimes = new double[numTestIterations];
    double[] sAstarTimes = new double[numTestIterations];
    double[] cAstarTimes = new double[numTestIterations];

    for (int i = 0; i < numTestIterations; i++)
    {
      start = System.currentTimeMillis();
      bfs.findPathsSequential(testLayout);
      end = System.currentTimeMillis();
      sBFSTimes[i] = (end - start);
    }

    for (int i = 0; i < numTestIterations; i++)
    {
      start = System.currentTimeMillis();
      bfs.findPaths(testLayout);
      end = System.currentTimeMillis();
      cBFSTimes[i] = (end - start);
    }

    for (int i = 0; i < numTestIterations; i++)
    {
      start = System.currentTimeMillis();
      astar.findPaths(testLayout);
      end = System.currentTimeMillis();
      sAstarTimes[i] = (end - start);
    }

    for (int i = 0; i < numTestIterations; i++)
    {
      start = System.currentTimeMillis();
      aParallel.findPaths(testLayout);
      end = System.currentTimeMillis();
      cAstarTimes[i] = (end - start);
    }

    double sBFSavg = Average(sBFSTimes);
    double cBFSavg = Average(cBFSTimes);
    double sAstaravg = Average(sAstarTimes);
    double cAstaravg = Average(cAstarTimes);

    try
    {
      PrintWriter out = new PrintWriter("output5.txt");

      out.println("Sequential BFS times: ");
      for (int i = 0; i < numTestIterations; i++)
      {
        out.println("   Sequential BFS " + i + ": " + sBFSTimes[i]);
      }

      out.println("Concurrent BFS times: ");
      for (int i = 0; i < numTestIterations; i++)
      {
        out.println("   Concurrent BFS " + i + ": " + cBFSTimes[i]);
      }

      out.println("Sequential A* times: ");
      for (int i = 0; i < numTestIterations; i++)
      {
        out.println("   Sequential A* " + i + ": " + sAstarTimes[i]);
      }

      out.println("Concurrent A* times: ");
      for (int i = 0; i < numTestIterations; i++)
      {
        out.println("   Concurrent A* " + i + ": " + cAstarTimes[i]);
      }

      out.println();
      out.println("Sequential BFS Average: " + sBFSavg);
      out.println("Concurrent BFS Average: " + cBFSavg);
      out.println("Sequential A* Average: " + sAstaravg);
      out.println("Concurrent A* Average: " + cAstaravg);


      out.close();
    }
    catch(Exception e)
    {
      System.out.println("Error occured opening file!" + e);

    }
  }

  public static double Average(double[] arr)
  {
    double sum = 0;
    for (int i = 0; i < arr.length; i++)
    {
      sum += arr[i];
    }
    return sum / arr.length;
  }
}
