package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.Node;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.node_data;


class DWGraph_AlgoTest {



	@Test
	void isConnected() {
		directed_weighted_graph g = new DWGraph_DS();
		int size=1000;
		g.addNode(new Node());
		for (int i = 1; i < size; i++) {
			g.addNode(new Node());
			g.connect(i, i-1, i);
			g.connect(i-1, i, i);
		}
		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(g);
		assertTrue(algo.isConnected());
		g.removeNode(size/2);
		assertFalse(algo.isConnected());


	}

	@Test
	void shortestPathDist() {
		directed_weighted_graph g = new DWGraph_DS();
		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(g);
		int size=1000;
		g.addNode(new Node());
		for (int i = 1; i < size; i++) g.addNode(new Node());

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(i!=j) {
					g.connect(i, j, 1);
				}
			}
		}
		double path = algo.shortestPathDist(0, size-1);
		assertEquals(path ,1 );


	}


	@Test
	void shortestPath() {

		directed_weighted_graph g = new DWGraph_DS();
		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(g);
		int size=1000;
		int keys[]= {0 , size-1};
		g.addNode(new Node());
		for (int i = 1; i < size; i++) {g.addNode(new Node()) ;}// keys[i]=i;}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(i!=j) {

					g.connect(i, j, 1);
				}
			}
		}

		List<node_data> collec = algo.shortestPath(0,size-1);
		int i = 0;
		for(node_data n: collec) {
			//assertEquals(n.getTag(), checkTag[i]);
			assertEquals(n.getKey(), keys[i]);
			i++;
		}
	}

	@Test
	void save_load() {
		directed_weighted_graph g = new DWGraph_DS();
		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(g);
		int size=10000;
		g.addNode(new Node());
		for (int i = 1; i < size; i++) {g.addNode(new Node()) ;}

		String str = "yuvaltry.json";
		assertTrue(algo.save(str));
		assertTrue(algo.load(str));
		
	}

}
