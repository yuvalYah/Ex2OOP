package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;

import java.util.ArrayList;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * this class represent the agent of the game.
 * this agent should run on the graph and eat pokemons .
 * @author yuval
 *
 */
public class CL_Agent {
	public static final double EPS = 0.0001;
	private int _id;
	private geo_location _pos;
	private double _speed;
	private edge_data _curr_edge;
	private node_data _curr_node;
	private directed_weighted_graph _gg;
	private CL_Pokemon _curr_fruit;
	private long _sg_dt;
	/**
	 *  Help me to recognize if the agent stack on adge
	 */
	private static ArrayList<Integer> currPath = new ArrayList<>();
	/**
	 * the shortest path of this agent on pokemon
	 */
	private static ArrayList<Integer> shortestPath = new ArrayList<>();
	private double _value;


	public CL_Agent(directed_weighted_graph g, int start_node) {
		_gg = g;
		setMoney(0);
		this._curr_node = _gg.getNode(start_node);
		_pos = _curr_node.getLocation();
		_id = -1;
		setSpeed(0);
	}
	/**
	 * this function update the agent from json string
	 * @param json
	 */
	public void update(String json) {
		JSONObject line;
		try {
			line = new JSONObject(json);
			JSONObject ttt = line.getJSONObject("Agent");
			int id = ttt.getInt("id");
			if(id==this.getID() || this.getID() == -1) {
				if(this.getID() == -1) {_id = id;}
				double speed = ttt.getDouble("speed");
				String p = ttt.getString("pos");
				Point3D pp = new Point3D(p);
				int src = ttt.getInt("src");
				int dest = ttt.getInt("dest");
				double value = ttt.getDouble("value");
				this._pos = pp;
				this.setCurrNode(src);
				this.setSpeed(speed);
				this.setNextNode(dest);
				this.setMoney(value);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//@Override
	public int getSrcNode() {return this._curr_node.getKey();}
	/**
	 * this function writes agent to json
	 * @return
	 */
	public String toJSON() {		
		Gson gson = new GsonBuilder().create();
		String ans=gson.toJson(this);
		return ans;	
	}
	/**
	 * set the value of the agent
	 * @param v
	 */
	private void setMoney(double v) {_value = v;}

	/**
	 * update the next node 
	 * if there is edge between curr node to dest so edge != null
	 * if the edge is null --> ans is false 
	 * else ans is true 
	 * @param dest
	 * @return
	 */
	public boolean setNextNode(int dest) {
		boolean ans = false;
		int src = this._curr_node.getKey();
		this._curr_edge = _gg.getEdge(src, dest);
		if(_curr_edge!=null) {
			ans=true;
		}
		else {_curr_edge = null;}
		return ans;
	}

	/**
	 * set curr node
	 * @param src
	 */
	public void setCurrNode(int src) {
		this._curr_node = _gg.getNode(src);
	}

	/**
	 * check is the agent is moving
	 * @return true if curr edge != null
	 */
	public boolean isMoving() {
		return this._curr_edge!=null;
	}

	/**
	 * to string of the agent
	 * @return strung json
	 */
	public String toString() {
		return toJSON();
	}
	/**
	 * to string 2 of the agent
	 * @return string
	 */
	public String toString1() {
		String ans=" Agent Id : "+this.getID()+" , pos : "+_pos+", is moving : "+isMoving()+", value : "+this.getValue();	
		return ans;
	}
	/**
	 * get the id of the agent
	 * @return int id
	 */
	public int getID() {
		return this._id;
	}

	/**
	 * get location of the agent
	 * @return geo_location
	 */
	public geo_location getLocation() {
		return _pos;
	}

	/**
	 * get the value of the agent
	 * @return double value
	 */
	public double getValue() {
		return this._value;
	}

	/**
	 * get Next Node
	 * @return dest of the curr edge or null if curr edge is null
	 */
	public int getNextNode() {
		int ans = -2;
		if(this._curr_edge==null) {
			ans = -1;}
		else {
			ans = this._curr_edge.getDest();
		}
		return ans;
	}

	/**
	 * get the speed of the agent
	 * @return double speed
	 */
	public double getSpeed() {
		return this._speed;
	}

	/**
	 * set the speed
	 * @param v
	 */
	public void setSpeed(double v) {
		this._speed = v;
	}

	/**
	 * get the curr pokemon
	 * @return CL_Pokemon
	 */
	public CL_Pokemon get_curr_fruit() {
		return _curr_fruit;
	}

	/**
	 * set the curr pokemon
	 * @param curr_fruit
	 */
	public void set_curr_fruit(CL_Pokemon curr_fruit) {
		this._curr_fruit = curr_fruit;
	}

	/**
	 * set dt
	 * @param ddtt
	 */
	public void set_SDT(long ddtt) {
		long ddt = ddtt;
		if(this._curr_edge!=null) {
			double w = get_curr_edge().getWeight();
			geo_location dest = _gg.getNode(get_curr_edge().getDest()).getLocation();
			geo_location src = _gg.getNode(get_curr_edge().getSrc()).getLocation();
			double de = src.distance(dest);
			double dist = _pos.distance(dest);
			if(this.get_curr_fruit().get_edge()==this.get_curr_edge()) {
				dist = _curr_fruit.getLocation().distance(this._pos);
			}
			double norm = dist/de;
			double dt = w*norm / this.getSpeed(); 
			ddt = (long)(1000.0*dt);
		}
		this.set_sg_dt(ddt);
	}

	/**
	 * get curr edge
	 * @return edge_data
	 */
	public edge_data get_curr_edge() {
		return this._curr_edge;
	}

	/**
	 * set the curr edge
	 * @param c
	 */
	public void set_curr_edge(edge_data c) {
		this._curr_edge=c;
	}

	/**
	 * get the dt
	 * @return
	 */
	public long get_sg_dt() {
		return _sg_dt;
	}

	/**
	 * set the dt
	 * @param _sg_dt
	 */
	public void set_sg_dt(long _sg_dt) {
		this._sg_dt = _sg_dt;
	}

	/**
	 * add to the curr path the node is move on
	 * @param currPath2
	 */
	public void addCurrPath(int currPath2) {
		currPath.add(currPath2);
	}

	/**
	 * return the arraylist of the path of the agent on the graph
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getArray(){
		return currPath;
	}

	/**
	 * add to the shortest path int 
	 * @param shortestPath2
	 */
	public void shortestPathAdd(int shortestPath2) {
		shortestPath.add(shortestPath2);
	}

	/**
	 * get the list of the shortest path of the pokemon to the agent
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getArrayshortestPath(){
		return shortestPath;
	}

	/**
	 * set the list of the shortest path of the pokemon to the agent
	 * @param ArrayList<Integer> other
	 */
	public void setArrayshortestPath(ArrayList<Integer> other){
		shortestPath = other;
	}
}