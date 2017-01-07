import java.util.*;

public class EdgeSet
{
	Map<String, Edge> EdgeSet = new HashMap<String, Edge>();
	
	public void Add(String name, Edge e)
	{
		EdgeSet.put(name, e);
	}
	
	public void Remove(String name)
	{
		EdgeSet.remove(name);
	}
	
	public Edge Find(String key)
	{
		if(EdgeSet.containsKey(key))
		{
			return EdgeSet.get(key);
		}
		else
		{
			return null;
		}
	}
	
	public String Get(Edge e)
	{
		return null;
	}
	
	public void Describe()
	{
		System.out.println("=============================\n\tList of edges\n=============================");
		for(int i = 1; i<=EdgeSet.size(); i++)
		{
			Edge e = Find(Integer.toString(i));
			System.out.print("{"+i+", "+e.getStart()+"->"+e.getEnd()+"} ");
		}
		System.out.println("\n");
	}
}