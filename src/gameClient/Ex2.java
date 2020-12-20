package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import api.node_data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * this class represent the pokemon game 
 * union the MyFrame of Jframe ans Arena 
 * id of the user and level of the game
 * @author yuval
 *
 */
public class Ex2 implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	private  static long id;
	private static int level;
	private static long dt=100;

	public static void main(String[] a) {
		Thread client = new Thread(new Ex2());
		client.start();
	}

	@Override
	public void run() {
		choos_level_and_id();
		game_service game = Game_Server_Ex2.getServer(level); // you have [0,23] games
		game.login(id);
		String g = game.getGraph();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		init(game);

		game.startGame();
		int ind=0;
		dt=100;

		while(game.isRunning()) {
			game.timeToEnd();
			moveAgants(game, gg);
			try {
				if(ind%1==0) _win.repaint();
				Thread.sleep(dt);
				if(dt < 100) dt+=18;
				if(dt > 100 ) dt =100;
				_win.setInd(ind++);

			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();
		System.out.println(res);
		System.exit(0);
	}
	/** 
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 */
	private static void moveAgants(game_service game, directed_weighted_graph gg) {
		String lg = game.move();
		List<CL_Agent> log = Arena.getAgents(lg, gg);
		_ar.setAgents(log);
		String fs =  game.getPokemons();
		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);

		_ar.setPokemons(ffs);

		for(int i=0;i<log.size();i++) {
			CL_Agent ag = log.get(i);
			int id = ag.getID();
			int dest = ag.getNextNode();
			int src = ag.getSrcNode();
			double v = ag.getValue();
			if(dest==-1 && level != 6 && level != 7) {
				synchronized(ag) {
					List<node_data> shorty =new ArrayList<>();
					synchronized(shorty) {

						shorty = listShortToPokemon(gg ,src ,ag);
						if(shorty.size()>1) {
							for (int j = 1; j < shorty.size(); j++) {
								dest =  shorty.get(j).getKey();
								ag.addCurrPath(dest);
								game.chooseNextEdge(ag.getID(),dest);
								System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+shorty.get(j).getKey());
							}
						}
						else {
							for (int j = 0; j < _ar.getPokemons().size(); j++) {
								edge_data edgepokemon = _ar.getPokemons().get(j).get_edge();
								if(edgepokemon.getSrc() == src) game.chooseNextEdge(ag.getID(),edgepokemon.getDest());ag.getArray().add(edgepokemon.getDest());
								if(edgepokemon.getDest() == src)game.chooseNextEdge(ag.getID(),edgepokemon.getSrc());ag.getArray().add(edgepokemon.getSrc());
							}

						}
						if(ag.getArray().size() >= 4) {
							if(ag.getArray().get(3)==ag.getArray().get(1) && ag.getArray().get(2)==ag.getArray().get(0)|| (ag.getArray().get(3)==ag.getArray().get(0))) {
								dt=0;
								ag.getArray().removeAll(ag.getArray());
							}
							else ag.getArray().removeAll(ag.getArray());
						}
					}
				}
			}
			else if ( level == 6 || level == 7) {
				dest = nextNode(gg, src);
				game.chooseNextEdge(ag.getID(), dest);
				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);

			}
		}
	}
	/**
	 * Create the shortest path of the agent to the pokemon.
	 * @param g
	 * @param src
	 * @param ag
	 * @return  List<node_data> 
	 */
	private synchronized static List<node_data> listShortToPokemon(directed_weighted_graph g , int src ,CL_Agent ag){
		List<node_data> shorty = new ArrayList<>();
		int ans=-1;
		DWGraph_Algo algo = new DWGraph_Algo();
		algo.init(g);
		double path=Double.POSITIVE_INFINITY;
		CL_Pokemon temp = new CL_Pokemon(null, 0, 0, 0, null);
		List<CL_Pokemon> listpo =_ar.getPokemons();
		for (int i = 0; i < listpo.size(); i++) {
			edge_data edgepokemon = listpo.get(i).get_edge();
			double shortestdistC = algo.shortestPathDist(src,edgepokemon.getSrc());
			double shortestdistD = algo.shortestPathDist(src ,edgepokemon.getDest());
			if(shortestdistC<path) {
				path = shortestdistC;
				temp = listpo.get(i);
				ans=1;
			}
			if(shortestdistD<path) {
				path = shortestdistD;
				temp = listpo.get(i);
				ans=0;

			}
		}
		if(ans==1) shorty = algo.shortestPath(src, temp.get_edge().getDest());
		else shorty = algo.shortestPath(src, temp.get_edge().getSrc());


		return shorty;
	}

	/**
	 * chose next node with shortest path algorithms
	 * @param directed_weighted_graph g
	 * @param int src
	 * @return int dest of the next node
	 */
	private synchronized static int nextNode(directed_weighted_graph g, int src) {
		int ans = -1;
		DWGraph_Algo algo = new DWGraph_Algo();
		algo.init(g);
		double path=Double.POSITIVE_INFINITY;
		CL_Pokemon temp = new CL_Pokemon(null, 0, 0, 0, null);
		List<CL_Pokemon> listpo = _ar.getPokemons();
		for (int i = 0; i < listpo.size(); i++) {
			edge_data edgepokemon = listpo.get(i).get_edge();
			double shortestdistC = algo.shortestPathDist(src,edgepokemon.getSrc());
			double shortestdistD = algo.shortestPathDist(src ,edgepokemon.getDest());
			if(shortestdistC<path) {
				path = shortestdistC;
				temp = listpo.get(i);
				ans=1;
			}
			else if(shortestdistD<path) {
				path = shortestdistD; temp = listpo.get(i); ans=0;

			}
		}
		List<node_data> shortPath =new ArrayList<>();

		if (ans == 1) shortPath = algo.shortestPath(src, temp.get_edge().getDest());
		else shortPath = algo.shortestPath(src, temp.get_edge().getSrc());
		if(shortPath.size()>1) ans=shortPath.get(1).getKey();
		else {
			for (int i = 0; i < listpo.size(); i++) {
				edge_data edgepokemon = listpo.get(i).get_edge();
				if(edgepokemon.getSrc() == src) ans =edgepokemon.getDest();
				if(edgepokemon.getDest() == src)ans =edgepokemon.getSrc();
			}

		}
		return ans;
	}
	/**
	 * init the game
	 * @param game
	 */
	private void init(game_service game) {
		String g = game.getGraph();
		String fs = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_win = new MyFrame("test Ex2" , game);
		_win.setGame(game);
		_win.update(_ar);
		System.out.println(game.toString());

		_win.show();
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
			for(int a = 0;a<rs;a++) {
				int ind = a%cl_fs.size();
				CL_Pokemon c = cl_fs.get(ind);
				int nn = c.get_edge().getDest();
				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}

				game.addAgent(nn);
			}
		}
		catch (JSONException e) {e.printStackTrace();}

	}
	private static void choos_level_and_id() {
		JFrame frame = new JFrame();
		frame.setBounds(200, 0, 500, 500);
		try {

			String myid = JOptionPane.showInputDialog(frame, "Hello\nPlease insert your ID");
			String mylevel = JOptionPane.showInputDialog(frame, "Please insert a level [0-23]");
			id = Integer.parseInt(myid);
			level = Integer.parseInt(mylevel);	

			//	if (level > 23 || level < 0) throw new RuntimeException();

		} 
		catch (RuntimeException e) {
			JOptionPane.showMessageDialog(frame, "Error!!!\nPlaying default game", "Error", JOptionPane.ERROR_MESSAGE);
			level = 0;
		}

	}

}