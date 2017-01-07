import java.util.*;
public class NodeSet
{
	ArrayList<Node> NodeSet = new ArrayList<Node>();
	
	public void Add(Node node)
	{
		NodeSet.add(node);
	}
	
	public void Remove(Node node)
	{
		NodeSet.remove(node);
	}
	
	public boolean Find(Node node)
	{
		if(NodeSet.contains(node))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Node Get(Node node)
	{
		if(Find(node))
		{
			for(int i = 0; i<NodeSet.size(); i++)
			{
				Node test = NodeSet.get(i);
				if(test.equals(node))
				{
					return NodeSet.get(i);
				}
			}
		}
		return null;
	}
	
	public void Describe()
	{
		System.out.println("=============================\n\tList of nodes\n=============================");
		System.out.print("[");
		for(int i = 0; i<NodeSet.size(); i++)
		{
			Node temp = NodeSet.get(i);
			if(i==NodeSet.size() - 1)
			{
				System.out.println(temp.getNode()+"]\n");
				break;
			}
			System.out.print(temp.getNode()+", ");
		}
	}
}