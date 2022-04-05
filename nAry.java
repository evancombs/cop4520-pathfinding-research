import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TreeNode
{
	public int data;
	public List <TreeNode> children;
	public boolean visited;

	public TreeNode(int data)
	{
		this.data = data;
		this.children = new LinkedList <TreeNode> ();
		this.visited = false;
	}

	public void addChild(int data)
	{
		TreeNode newChild = new TreeNode(data);
		this.children.add(newChild);
	}

	public int getData()
	{
		return this.data;
	}
}

public class sequentialnAry
{
	public static Lock lock = new ReentrantLock();
	public static ArrayList<ArrayList<Integer>> allNodes = new ArrayList<ArrayList<Integer>>();
	public TreeNode root;

	public nAry()
	{
		this.root = null;
	}

	public static class Threading extends Thread
	{
		TreeNode startNode;
		int n;

		ArrayList<Integer> nodes = new ArrayList<Integer>();

		public Threading (TreeNode startNode)
		{
			this.startNode = startNode;
			this.n = n;
		}

	 	@Override
	 	public void run ()
	 	{
	 		dfsThread(startNode);

	 		lock.lock();

	 		try
	 		{
	 			allNodes.add(nodes);
	 		} catch(Exception e){

	 		} finally{
	 			lock.unlock();
	 		}
	 	}

	 	public void dfsThread(TreeNode current)
	 	{
	 		current.visited = true;
	 		this.nodes.add(current.data);

	 		int size = current.children.size();

	 		for (int i = 0; i < size; i++)
	 		{
	 			if (current.children.get(i).visited == false)
	 				dfsThread(current.children.get(i));
	 		}
	 	}
	 }

	public static void DFS (nAry tree, int[] nodesInEachChild)
	{
		int numThreads = tree.root.children.size();

		ArrayList<Threading> threads = new ArrayList<Threading>();

		for (int i = 0; i < numThreads; i++)
		{
			Threading thread = new Threading(tree.root.children.get(i));
			threads.add(thread);
			thread.start();
		}

		for (Threading thread : threads)
		{
			try
			{
				thread.join();
			}
			catch(Exception e)
			{

			}

		}
	}

	public static void main(String[] args)
	{
		nAry tree = new nAry();

		/*
		           			  1
		           	   /   |     |    \
		           	 2     3     4     5
		           / | \  / \   / \   / | \
				  9  7 10 6 -1 -2 -3 15 20 13
		*/

		tree.root = new TreeNode(1);

		// Adding 4 children to the root
		tree.root.addChild(2);
		tree.root.addChild(3);
		tree.root.addChild(4);
		tree.root.addChild(5);

		// Adding 3 children to child 2 of root
		tree.root.children.get(0).addChild(9);
		tree.root.children.get(0).addChild(7);
		tree.root.children.get(0).addChild(10);

		// Adding 2 children to child 3 of root
		tree.root.children.get(1).addChild(6);
		tree.root.children.get(1).addChild(-1);

		// Adding 2 children to child 3 of root
		tree.root.children.get(2).addChild(-2);
		tree.root.children.get(2).addChild(-3);

		// Adding 3 children to child 5 of root
		tree.root.children.get(3).addChild(15);
		tree.root.children.get(3).addChild(20);
		tree.root.children.get(3).addChild(13);

		ArrayList<Integer> theRoot = new ArrayList<Integer>();
		theRoot.add(tree.root.data);
		allNodes.add(theRoot);

		DFS(tree, nodesInEachChild);
		for (int i = 0; i < allNodes.size(); i++)
			for (int j = 0; j < allNodes.get(i).size(); j++)
				System.out.println(allNodes.get(i).get(j));	
	}
}