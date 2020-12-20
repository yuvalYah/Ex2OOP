package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a  GUI class to present a
 * game on a graph.
 * 
 *@author yuval 
 */
public class MyFrame extends JFrame {
	private int _ind ;
	private Arena _ar;
	private game_service game;
	private int ind=0;
	private gameClient.util.Range2Range _w2f;

	private static int WIDTH = 1000;
	private static int HEIGHT = 700;


	MyFrame(String a , game_service game) {
		super(a);
		int _ind = 0;
		this.game=game;
		initFrame();

	}

	/**
	 * init the frame
	 * write the title of the game
	 * set the size of the game
	 * exit on close option
	 * can Resize of the window
	 * 
	 */
	public void initFrame() {
		this.setTitle("Ex2 - OOP: "+game.toString());
		this.setSize(WIDTH,HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		getContentPane().addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Component c=(Component)e.getSource();
				WIDTH = c.getWidth();
				HEIGHT= c.getHeight();
				updateFrame() ;
			}
		});

	}

	/**
	 * update the arena 
	 * @param ar
	 */
	public void update(Arena ar) {
		this._ar = ar;
		updateFrame();
	}
	/**
	 * set the game service
	 * @param game
	 */
	public void setGame(game_service game) {
		this.game=game;
	}
	/**
	 * set ind
	 * @param i
	 */
	public void setInd(int i) {ind = i;}

	private void updateFrame() {
		Range rx = new Range(20,this.getWidth()-40);
		Range ry = new Range(this.getHeight()-40,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g,frame);

	}

	/**
	 *  paint the window with the graph 
	 *  paint the agents
	 *  paint the pokemon
	 *  paint the time info
	 *  @param Graphics g
	 */
	public void paint(Graphics g) {
		BufferedImage  image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graph2d = image.createGraphics();
		graph2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graph2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graph2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		Color color =new Color(240, 240, 240);
		graph2d.setBackground(color);
		graph2d.clearRect(0, 0, WIDTH, HEIGHT);

		drawTime(graph2d);
		drawInfo(graph2d);
		drawGraph(graph2d);
		drawAgants(graph2d);
		drawPokemons(graph2d);

		Graphics2D g2dComponent = (Graphics2D) g;
		g2dComponent.drawImage(image, null, 0, 0);

	}

	private void drawInfo(Graphics2D g) {
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		int a=70;
		for (int i = 0; i < _ar.getAgents().size(); i++) {
			g.drawString(_ar.getAgents().get(i).toString1(),WIDTH / 6, a);
			a+=20;
		}

	}
	private void drawTime(Graphics2D g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("Time: " + game.timeToEnd()/1000, 45, 70);
		double sumScour=0;
		for (int i = 0; _ar.getAgents() != null &&  i <_ar.getAgents().size(); i++) {
			sumScour=sumScour+_ar.getAgents().get(i).getValue();			
		}
		g.drawString("Score: " + sumScour , 45, 90);
		g.drawString("Moves: " + ind , 45, 110);
	}

	private void drawGraph(Graphics2D g) {

		directed_weighted_graph gg = _ar.getGraph();
		Iterator<node_data> iter = gg.getV().iterator();
		while(iter.hasNext()) {
			node_data n = iter.next();
			Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
			while(itr.hasNext()) {
				edge_data e = itr.next();
				g.setColor(Color.gray);
				drawEdge(e, g);
			}
			g.setColor(Color.blue);
			drawNode(n,5,g);
		}
	}
	private void drawPokemons(Graphics2D g) {
		List<CL_Pokemon> fs = _ar.getPokemons();
		if(fs!=null) {
			Iterator<CL_Pokemon> itr = fs.iterator();

			while(itr.hasNext()) {

				CL_Pokemon f = itr.next();
				Point3D c = f.getLocation();
				int r=10;
				g.setColor(Color.green);
				if(f.getType()<0) {g.setColor(Color.orange);}
				if(c!=null) {

					geo_location fp = this._w2f.world2frame(c);
					g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
				}
			}
		}
	}
	private void drawAgants(Graphics2D g) {

		List<CL_Agent> rs = _ar.getAgents();
		g.setColor(Color.red);
		int i=0;
		while(rs!=null && i<rs.size()) {
			geo_location c = rs.get(i).getLocation();
			CL_Agent agent= rs.get(i);
			int r=8;
			i++;
			if(c!=null) {
				geo_location fp = this._w2f.world2frame(c);
				g.setColor(Color.red);
				g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
				g.setColor(Color.black);
				g.setFont(new Font("Arial", Font.CENTER_BASELINE, 12));
				g.drawString(""+agent.getID(), (int)fp.x()-5, (int)fp.y()+5);

			}
		}

	}
	private void drawNode(node_data n, int r, Graphics2D g) {
		geo_location pos = n.getLocation();
		geo_location fp = this._w2f.world2frame(pos);
		g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
	}
	private void drawEdge(edge_data e, Graphics2D g) {
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this._w2f.world2frame(s);
		geo_location d0 = this._w2f.world2frame(d);
		g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
		g.setColor(Color.BLACK);
	}



}