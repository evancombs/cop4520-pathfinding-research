// Randomwalker is a VERY simple implementation that spawns multiple threads
// that simply take random steps at each point; very much a "monkeys with a
// typewriter" situation
public class Randomwalker extends Pathfinder
{
  int numThreads;

  public Randomwalker()
  {
    this(1);
  }

  public Randomwalker(int numThreads)
  {
    this.numThreads = numThreads;
  }

  @Override
  public void solve(Layout layout)
  {
    Solver solver = new Solver();

    ArrayList<Thread> threads = new ArrayList<>();
    for (int i = 0; i < numThreads; i++)
    {
      Thread thread = new Thread();
      solver.add(thread);
    }

    for (Thread thread : threads)
        thread.start();
  }
}

private class Solver implements Runnable
{
  private Layout layout;

  public Solver(Layout layout)
  {
    this.layout = layout;
  }

  public void run()
  {

  }

}
