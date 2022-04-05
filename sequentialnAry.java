import java.util.*;

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
	public TreeNode root;

	public static ArrayList<Integer> nodes = new ArrayList<Integer>();

	public static void dfsSequential(TreeNode current)
	{
		current.visited = true;
 		nodes.add(current.data);

 		int size = current.children.size();

 		for (int i = 0; i < size; i++)
 		{
 			if (current.children.get(i).visited == false)
 				dfsSequential(current.children.get(i));
 		}
	}

	public static void main(String[] args)
	{
		sequentialnAry tree = new sequentialnAry();
		
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

		dfsSequential(tree.root);
		for (int i = 0; i < nodes.size(); i++)
				System.out.println(nodes.get(i));	
	}
}