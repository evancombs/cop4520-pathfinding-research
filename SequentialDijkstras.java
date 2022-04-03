class SequentialDijkstras
{
	int[][] graph;
	int v;
	double[] avereges;
	
	SequentialDijkstras (int[][] graph, int v)
	{
		this.v = v;
		this.graph = graph;
		this.avereges = new double[v];

		System.out.println("The list of the averege distances for every staring vertex:");
		for (int i = 0; i < v; i++)
		{
			dijkstras(graph, i, v);
			System.out.print("vertex " + i + ": ");
			System.out.format("%.3f", this.avereges[i]);
			System.out.println();
		}

		System.out.println();
		System.out.println("The most optimal staring vertex is vertex: " + indexOfSmallest());
	}

	void dijkstras(int graph[][], int src, int v)
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

	void calcAvg(int dist[], int V, int src)
	{
		double total = 0;
		
		for (int i = 0; i < dist.length; i++)
		{
			total = total + dist[i];
		}

		double average = total / dist.length;
		this.avereges[src] = average;
	}

	int minDistance(int dist[], Boolean sptSet[], int v)
	{
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int V = 0; V < v; V++)
			if (sptSet[V] == false && dist[V] <= min) {
				min = dist[V];
				min_index = V;
			}

		return min_index;
	}

	int indexOfSmallest(){

    if (this.avereges.length == 0)
        return -1;

    int index = 0;
    double min = this.avereges[index];

    for (int i = 1; i < this.avereges.length; i++){
        if (this.avereges[i] <= min){
        min = this.avereges[i];
        index = i;
        }
    }
    return index;
}
}