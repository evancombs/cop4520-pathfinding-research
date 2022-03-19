public class BFS extends Pathfinder
{


  ArrayList<Thread> guests = new ArrayList<>();

  // Create a new labyrinth for the guests to enter
  Labyrinth labyrinth = new Labyrinth(numGuests);
  for (int i = 0; i < numGuests; i++)
  {
    // We can name the guests and elect guest 0 as the leader.
    Thread guest = new Thread(labyrinth, String.valueOf(i));
    guests.add(guest);
  }

  for (Thread guest : guests)
      guest.start();
}


class ThreadRunner implements Runnable
{
  Layout layout;
  public ThreadRunner(Layout layout)
  {
    this.layout = layout;
  }

  // Idea: spawn multiple threads
  public void run()
  {

  }
}
