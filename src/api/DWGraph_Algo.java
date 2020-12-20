package api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * this class represents a Directed (positive) Weighted Graph Theory Algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected(); // strongly (all ordered pais connected)
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file); // JSON file
 * 6. Load(file); // JSON file
 * @author yuval 
 *
 */

public class DWGraph_Algo implements dw_graph_algorithms {
	private directed_weighted_graph algo;
	
	public DWGraph_Algo() {
		this.algo= new DWGraph_DS();
	}
	//init the graph
	@Override
	public void init(directed_weighted_graph g) {
		this.algo=g;

	}
	
	@Override
	public directed_weighted_graph getGraph() {
		return algo;
	}
	/**
	 * copy the graph that algo point on to be his own graph
	 * @return graph
	 */
	@Override
	public directed_weighted_graph copy() {

		directed_weighted_graph temp = new DWGraph_DS();
		Iterator<node_data> it=  algo.getV().iterator();
		while(temp.getV().size()<algo.getV().size()) {
			node_data n= it.next();
			if(n != null) {
				temp.addNode(n);//add the elements to temp
			}
		}
		//connect edge to temp
		it=  algo.getV().iterator();
		for (int i = 0; i < algo.getV().size(); i++) {
			for (int j = 0; j < algo.getV().size(); j++) {
				if(i!=j && algo.getEdge(i, j)!=null ) {
					temp.connect(i, j, algo.getEdge(i, j).getWeight());
				}

			}
		}
		//init this graph
		algo=new DWGraph_DS();
		Iterator<node_data> it2=  temp.getV().iterator();
		while(temp.getV().size()>algo.getV().size()) {
			node_data n= it2.next();
			if(n != null) {
				algo.addNode(n);
			}
		}
		it =  temp.getV().iterator();
		for (int i = 0; i < temp.getV().size(); i++) {
			for (int j = 0; j < temp.getV().size(); j++) {
				if(i!=j && temp.getEdge(i, j) != null) {
					algo.connect(i, j, temp.getEdge(i, j).getWeight());
				}
			}
		}
		return algo;
	}
	
	/**
	 * Check if this graph is strongly connected
	 * using an bfs algorithm 
	 * move on all the nodes in the graph and check if this node cold go to 
	 * every other node in the graph
	 * @return true if this graph is strongly connected , if non so return false
	 */
	@Override
	public boolean isConnected() {
		int vertex = algo.getV().size();
		Iterator<node_data> itr=algo.getV().iterator(); 
		boolean ans= true;
		while(itr.hasNext()) {
			node_data node = itr.next();
			if(node != null) {
				ArrayList<Boolean> visited=new ArrayList<Boolean>(); 
				for(int i=0;i<vertex;i++) { 
					visited.add(i,false); 
				} 
				int sum =0;
				int bfs =bfshelp(node.getKey(),visited ,sum); 
				if(bfs != algo.nodeSize()) {
					ans= false;
					break;
				}
			}
		}
		return ans;
	} 
	/**
	 * 
	 * @param s
	 * @param visited
	 * @param sum
	 * @return the number that there is  path between s to other node
	 */
	private int bfshelp(int s , ArrayList<Boolean> visited, int sum ) { 
		node_data snew = algo.getNode(s);
		LinkedList<node_data> q=new LinkedList<>(); 

		q.add(snew);
		sum++;
		visited.set(s,true); 

		while(!q.isEmpty()) { 
			node_data f=q.poll(); 

			Iterator<edge_data> i=algo.getE(f.getKey()).iterator(); 

			while(i.hasNext()) { 
				edge_data n=i.next();

				if(n != null && !visited.get(n.getDest())) { 
					visited.set(n.getDest(),true); 
					q.add(algo.getNode(n.getDest())); 
					sum++;
				} 
			} 
		} 
		return sum;
	} 

	/**
	 * Count the smallest distance from stc to dest
	 * arr help us to save the distance in each index.
	 * paths save string with the shortest path to dest
	 * after we find shortest path we set the info of src to the string on his shortest path 
	 * @return double distance
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		if(algo.getNode(src)!=null && algo.getNode(dest)!=null) {
			String pathS[]=new String[algo.getV().size()];
			ArrayBlockingQueue<node_data> q= new ArrayBlockingQueue<>(algo.getV().size());
			java.util.Iterator<node_data> it=  algo.getV().iterator();//// max to do get v of nodeinfo
			double arr[]=new double[algo.getV().size()];
			while(it.hasNext()) {
				node_data n =it.next();
				if(n != null ) {			
					n.setInfo("");
					if(n.getKey() == src) {
						try {
							n.setInfo(" ,"+n.getKey());
							pathS[n.getKey()]=" ,"+n.getKey();
							q.put(n);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			boolean ans=false;
			while(!q.isEmpty()) {
				node_data n = q.poll();
				n.setInfo("was here!");
				Iterator<edge_data> ni=  algo.getE(n.getKey()).iterator();
				while(ni.hasNext()) {
					edge_data test =ni.next();
					node_data  nodeni;//= algo.getNode(ni.next().getDest());
					if(test != null ) {
						nodeni = algo.getNode(test.getDest());
						if(algo.getNode(nodeni.getKey()).getInfo() == "") {
							double kt=arr[nodeni.getKey()] + algo.getEdge(n.getKey(), nodeni.getKey()).getWeight(); ///
							if(arr[nodeni.getKey()]==0) {
								arr[nodeni.getKey()]=arr[n.getKey()]+algo.getEdge(n.getKey(), nodeni.getKey()).getWeight();//=sum;
								pathS[nodeni.getKey()]=pathS[n.getKey()]+" ,"+nodeni.getKey();
								try {
									q.put(nodeni);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							else {

								if(arr[n.getKey()]+algo.getEdge(n.getKey(), nodeni.getKey()).getWeight()<arr[nodeni.getKey()]) {
									arr[nodeni.getKey()]=arr[n.getKey()]+algo.getEdge(n.getKey(), nodeni.getKey()).getWeight();
									pathS[nodeni.getKey()]=pathS[n.getKey()]+" ,"+nodeni.getKey();

								}
								else if(kt < arr[n.getKey()]) {
									arr[n.getKey()]=kt;
									pathS[n.getKey()]=pathS[nodeni.getKey()]+" ,"+n.getKey();

								}
							}
						}
					}
				}
				if(n.getKey()==dest) {
					algo.getNode(src).setInfo(pathS[dest]);
					ans=true;
					break;
				}
			}

			if(ans==true) return arr[dest];
			else return -1;
		}
		else return -1;
	}
	
	/**
	 * make list to show the path of the smallest dist.
	 * arr help us to save the distance from src to each index ,
	 * paths help us to save string wits the path from src to each index
	 * @return ArrayList 
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		ArrayList<node_data> shortest = new ArrayList<>();
		//check if there is path between src to dest and set the the info of src to the shortest path we find in shortestPathDist
		if(shortestPathDist(src ,  dest)>=0) { 
			node_data nodesrc = algo.getNode(src);
			//convert this string to node list
			for (int i = 2; i < nodesrc.getInfo().length(); i+=3) {
				int temp=0;
				int num=nodesrc.getInfo().charAt(i)-'0';
				boolean flag=false;
				while(i+1<nodesrc.getInfo().length() && nodesrc.getInfo().charAt(i+1) !=' ') {
					num=nodesrc.getInfo().charAt(i)-'0';
					temp=temp+num;
					if(i+1<nodesrc.getInfo().length() && nodesrc.getInfo().charAt(i+1) !=' ') temp=temp*10;
					i++;
					if(i+1<nodesrc.getInfo().length() && nodesrc.getInfo().charAt(i+1)==' ')temp=temp+nodesrc.getInfo().charAt(i)-'0';
					if(i+1==nodesrc.getInfo().length()) flag=true;
				}
				if(flag==true) temp= temp+nodesrc.getInfo().charAt(i)-'0';
				if(temp!=0) num = temp;
				node_data node=algo.getNode(num);
				shortest.add(node);
			}
		}
		return shortest;
	}

	/**
	 * save this graph to json file
	 * with Gson
	 * @return true if we succeed to save
	 */
	@Override
	public boolean save(String file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json=gson.toJson(this);
		try 
		{
			PrintWriter pw = new PrintWriter(new File(file));
			pw.write(json);
			pw.close();
			return true;
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/**
	 * load this graph from json string
	 * inspiration of yael's example
	 * @return true if we succeed to load the graph
	 */
	@Override
	public boolean load(String file) {
		try 
		{
			GsonBuilder builder = new GsonBuilder();
			DWGraph_AlgoJsonDeserializer n = new DWGraph_AlgoJsonDeserializer(); // class i created inspiration of yael's example
			builder.registerTypeAdapter(DWGraph_DS.class, n);
			Gson gson = builder.create();			
			//Continue us usual
			FileReader reader = new FileReader(file);
			DWGraph_DS parking = gson.fromJson(reader, DWGraph_DS.class);	
			return true;
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
	return false;

	}
}
