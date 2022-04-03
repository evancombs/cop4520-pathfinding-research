class ParallelDijkstras
{
	static int[][] graph;
	int v;
	static double[] avereges;
	
	ParallelDijkstras (int[][] g, int v)
	{
		this.v = v;
		this.graph = g;
		this.avereges = new double[v];

		System.out.println("Using Multi-Threading and Dijkstras to find the most optimal starting vertex for graph traversal");
		System.out.println("The list of the averege distances for every staring vertex:");
		
		ThreadDijkstras[] t = new ThreadDijkstras[v];

		for (int i = 0; i < v; i++)
			t[i] = new ThreadDijkstras(i, v);

		for (int i = 0; i < v; i++)
			t[i].start();

		for (int i = 0; i < v; i++)
		{
			try
			{
				t[i].join();
			}
			catch (Exception e)
			{      
			}
		}
		
		for (int i = 0; i < v; i++)
		{
			System.out.print("vertex " + i + ": ");
			System.out.format("%.3f", avereges[i]);
			System.out.println();
		}
		System.out.println();
		System.out.println("The most optimal staring vertex is vertex: " + indexOfSmallest());
		System.out.println();
		
	}
	
	static class ThreadDijkstras extends Thread
	{
		int ver;
		int v;

		ThreadDijkstras (int ver, int v)
		{
			this.ver = ver;
			this.v = v;
		}

		@Override
		public void run()
		{
			dijkstras(graph, ver, v);
		}
	}

	static void dijkstras(int graph[][], int src, int v)
	{
		int dist[] = new int[v]; 

		Boolean sptSet[] = new Boolean[v];

		for (int i = 0; i < v; i++) {
			dist[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}

		dist[src] = 0;

		for (int count = 0; count < v - 1; count++) {
			int u = minDistance(dist, sptSet, v);

			sptSet[u] = true;

			for (int V = 0; V < v; V++)
				if (!sptSet[V] && graph[u][V] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][V] < dist[V])
					dist[V] = dist[u] + graph[u][V];
		}

		calcAvg(dist, v, src);
	}

	static void calcAvg(int dist[], int V, int src)
	{
		double total = 0;
		
		for (int i = 0; i < dist.length; i++)
		{
			total = total + dist[i];
		}

		double average = total / dist.length;
		avereges[src] = average;
	}

	static int minDistance(int dist[], Boolean sptSet[], int v)
	{
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int V = 0; V < v; V++)
			if (sptSet[V] == false && dist[V] <= min) {
				min = dist[V];
				min_index = V;
			}

		return min_index;
	}

	static int indexOfSmallest()
	{

    if (avereges.length == 0)
			return -1;

    int index = 0;
    double min = avereges[index];

    for (int i = 1; i < avereges.length; i++)
		{
			if (avereges[i] <= min)
			{
				min = avereges[i];
				index = i;
			}
    }
    return index;
	}
}

