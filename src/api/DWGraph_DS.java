package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
/**
 * this class represents a directional weighted graph.
 * @author yuval Yahod
 *
 */

public class DWGraph_DS implements directed_weighted_graph{
	private HashMap<Integer ,ArrayList<edge_data> > Edges;
	/**
	 * to save the edges in this graph
	 * key of Edges is the key of the node, and each key has value that arraylist of edges
	 * that each edge of this node , src = this key
	 */

	private ArrayList<node_data> Nodes;//to savr the nodes in this graph
	private int node_size , edge_size , MC;

	public DWGraph_DS() {
		this.Nodes= new ArrayList<>();
		this.Edges= new HashMap<>();
		node_size=0;
		edge_size=0;
		MC=0;
		if(Nodes.size()==0) {
			Node.uptCount();//to update the key of the nodes
		}
	}

	@Override
	public node_data getNode(int key) {
		if(key<Nodes.size()) return Nodes.get(key);
		return null;
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if(src<Edges.size() && dest<Edges.size()) {
			if (Edges.get(src) != null && dest < Edges.get(src).size()) return Edges.get(src).get(dest);
		}
		return null;
	}
	/**
	 * add node to the graph
	 */
	@Override
	public void addNode(node_data n) {
		int key = n.getKey();

		if(n.getKey() < Nodes.size()) { 
			if(getNode(n.getKey())==null) {// null --> this node isnt in the graph
				Nodes.set(n.getKey(), n);
				ArrayList<edge_data> toNi = new ArrayList<>();//resat array list on the edges
				Edges.put(n.getKey(), toNi);
				node_size++;
				MC++;
			}
		}
		else if(key>=Nodes.size()) {// add null to the end  for the list and less add the new node
			int size=Nodes.size();
			while(key>size) {
				Nodes.add(null);
				size++;
			}
			Nodes.add(n);//less add node
			ArrayList<edge_data> toNi = new ArrayList<>();
			Edges.put(n.getKey(), toNi);
			//add size and MC
			node_size++;
			MC++;
		}

	}

	/**
	 * connect src to dest with weight
	 */
	@Override
	public void connect(int src, int dest, double w) {
		//if there is such node that not null
		if(w>=0 && src != dest && src < Nodes.size() && dest < Nodes.size() && Nodes.get(src) != null && Nodes.get(dest) != null) {
			edge_data newEdge= new Edge(Nodes.get(src) , Nodes.get(dest) ,w);
			//to add to the array list of the edges we do algorithm very look like the addNode algorithm
			if(dest < Edges.get(src).size()) { 
				if(Edges.get(src).get(dest)==null) {
					int k = Nodes.get(src).getTag();
					Nodes.get(src).setTag(k++);
					Edges.get(src).set(dest, newEdge);
					//add to count size and MC
					edge_size++;
					MC++;
				}
			}
			else if(dest >= Edges.get(src).size()) {
				int size=Edges.get(src).size();
				while(dest>size) {
					Edges.get(src).add(null);
					size++;
				}
				Edges.get(src).add(newEdge);
				int k = Nodes.get(src).getTag()+1;
				Nodes.get(src).setTag(k);
				MC++;
				edge_size++;

			}
		}
	}

	@Override
	public Collection<node_data> getV() {
		return Nodes;
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return Edges.get(node_id);
	}

	/**
	 * this method remove node from the graph with the given key
	 * we need to delete all the edges that send to this node with the given key
	 */
	@Override
	public node_data removeNode(int key) {
		node_data nd=null;
		if(key < Nodes.size() && Nodes.get(key) != null) {//check if there is such node in the graph
			edge_size=edge_size-Nodes.get(key).getTag();
			nd = Nodes.get(key);
			Nodes.set(key, null);
			Edges.remove(key);
			node_size--;
			MC++;
			//moving on all the nodes in the graph and if there is edge from other node to this node we delete so we remove him
			for (int i = 0; i < Nodes.size(); i++) {
				if( i != key && Nodes.get(i)!=null && Edges.get(i).size() > key && Edges.get(i).get(key) != null) {
					Edges.get(i).set(key , null);
					edge_size--;
					MC++;
				}
			}
		}
		return nd;
	}

	/**
	 * remove edge in the graph src-->dest
	 * @return edge_data null if this edge doesn't exist
	 */
	@Override
	public edge_data removeEdge(int src, int dest) {
		edge_data retu = this.getEdge(src, dest);
		if(retu != null ) {
			Edges.get(src).set(dest, null);
			edge_size--;
			MC++;
		}
		return null;
	}

	/**
	 * @return the size of the node
	 */
	@Override
	public int nodeSize() {
		return node_size;
	}

	/**
	 * @return the size of the edges
	 */
	@Override
	public int edgeSize() {
		return edge_size;
	}

	/**
	 * @return the number of changes
	 */

	@Override
	public int getMC() {
		return MC;
	}

}
