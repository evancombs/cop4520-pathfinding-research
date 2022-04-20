// Runs sequential versions of algorithms against eachother on a simple 4x4 layout.
// Exports to output1.txt with results of execution.
// Records only the time taken to complete execution.
import java.io.*;
import java.util.*;
import java.lang.System;

public class TestCase1
{
  public static void main(String [] args)
  {
    int i = 0;
    double[] times;
    double start, end;
    ArrayList<Pathfinder> algorithms = new ArrayList<>();

    Layout testLayout = new Layout("tests/layout1.txt");

    //algorithms.add(new BFS());
    // algorithms.add(new AParallel());
    BFS bfs = new BFS();
    Pathfinder astar = new AStar();
    times = new double[2];

/*
    for (Pathfinder alg : algorithms)
    {
      start = System.currentTimeMillis();
      alg.findPaths(testLayout);
      end = System.currentTimeMillis();
      times[i++] = (end - start);
    }*/
    start = System.currentTimeMillis();
    bfs.findPathsSequential(testLayout);
    end = System.currentTimeMillis();
    times[0] = (end - start);

    start = System.currentTimeMillis();
    astar.findPaths(testLayout);
    end = System.currentTimeMillis();
    times[1] = (end - start);

    try
    {
      PrintWriter out = new PrintWriter("output1.txt");

      out.println("BFS " + times[0] + "ms");
      out.println("AStar " + times[0] + "ms");
      
      out.close();
    }
    catch(Exception e)
    {
      System.out.println("Error occured opening file!" + e);

    }


  }

}
