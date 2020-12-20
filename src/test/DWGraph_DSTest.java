package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import org.junit.jupiter.api.Test;

import api.DWGraph_DS;
import api.Node;
import api.directed_weighted_graph;
import api.node_data;


class DWGraph_DSTest {



	@Test
	void nodeSize() {
		directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new Node());
		g.addNode(new Node());
		g.addNode(new Node());
		g.addNode(new Node());

		for (int i = 4; i < 50000; i++) {
			g.addNode(new Node());


		}
		int nodesize = g.nodeSize();
		int count=0;
		for (int i = 0; i < 50000; i+=20) {
			g.removeNode(i);
			count++;
		}
		nodesize= nodesize-count;
		assertEquals(nodesize,g.nodeSize());

	}

	@Test
	void edgeSize() {
		directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new Node());
		g.addNode(new Node());
		g.addNode(new Node());
		g.addNode(new Node());
		int EdgeSize=0;

		for (int i = 4; i < 10000; i++) {
			g.addNode(new Node());
			g.connect(i, i-1, (i+i+1)/(i+20));
			g.connect(i, i-2, (2*i)/(20));
			g.connect(i, i-3, (1000)/(20*i));
			g.connect(i, i-4, i);
			EdgeSize+=4;

		}
		assertEquals(EdgeSize,g.edgeSize());
	}

	@Test
	void getV() {
		directed_weighted_graph g = new DWGraph_DS();
		g.addNode(new Node());
		g.addNode(new Node());
		g.addNode(new Node());
		g.addNode(new Node());
		for (int i = 4; i < 10000; i++) {
			g.addNode(new Node());
			g.connect(i, i-1, (i+i+1)/(i+20));
			g.connect(i, i-2, (2*i)/(20));
			g.connect(i, i-3, (1000)/(20*i));
			g.connect(i, i-4, i);


		}
		Collection<node_data> v = g.getV();
		assertNotNull(v);

		java.util.Iterator<node_data> iter=  g.getV().iterator();//// max to do get v of nodeinfo
		while (iter.hasNext()) {
			node_data n = iter.next();
			assertNotNull(n);

		}
	}

	//	@Test
	//	void getEdge() {
	//		int size= 10000;
	//		directed_weighted_graph g = new DWGraph_DS();
	//		g.addNode(new Node());
	//		g.addNode(new Node());
	//		g.addNode(new Node());
	//		g.addNode(new Node());
	//		for (int i = 4; i < size; i++) {
	//			g.addNode(new Node());
	//			g.connect(i, i-1, (i+i+1)/(i+20));
	//			g.connect(i, i-2, (2*i)/(20));
	//			g.connect(i, i-3, (size)/(20*i));
	//			g.connect(i, i-4, i);
	//		}
	//		for (int i = 4; i < size; i+=4) {
	//			assertTrue(g.getEdge(i-4,i)!=null);	
	//		}
	//	}

	@Test
	void connect() {
		int size=10000;
		directed_weighted_graph g = new DWGraph_DS();

		for (int i = 0; i < size; i++) {
			g.addNode(new Node());
		}
		for (int i = 1; i < size; i++) {
			assertFalse(g.getEdge(i, i -1)!=null);
			g.connect(i, i-1, i);
			assertTrue(g.getEdge(i, i-1)!=null);
			double w = g.getEdge(i,i-1).getWeight();
			assertEquals(w,i);
		}
	}


	@Test
	void removeNode() {
		directed_weighted_graph g = new DWGraph_DS();
		int size=10000;
		g.addNode(new Node());
		for (int i = 1; i < size; i++) {
			g.addNode(new Node());
			g.connect(i, i-1, i);
		}
		g.connect(size-1, 0, size-1);
		for (int i = 0; i < size; i+=20) {
			node_data k= g.removeNode(i);
			g.connect(i, i-1, i);
			assertNotNull(k);
			assertNull(g.getNode(i));
		}
		for (int i = 0; i < size; i+=20) {
			assertFalse(g.getEdge(i, i+1)!=null);
		}
		assertEquals(g.nodeSize(), size - size/20);
		assertEquals(g.edgeSize(), size-size/10);

	}

	@Test
	void removeEdge() {
		directed_weighted_graph g = new DWGraph_DS();
		int size=10000;
		g.addNode(new Node());
		for (int i = 1; i < size; i++) {
			g.addNode(new Node());
			g.connect(i, i-1, i);
			assertEquals(g.getEdge(i,i-1).getWeight(),i);
			g.removeEdge(i, i-1);
			assertTrue(g.getEdge(i,i-1)==null);

		}

	}
	@Test
	void getEdge() {
		directed_weighted_graph g = new DWGraph_DS();
		int size=10000;
		g.addNode(new Node());
		for (int i = 1; i < size; i++) {
			g.addNode(new Node());
			g.connect(i, i-1, i);
			assertTrue(g.getEdge(i, i-1)!= null);

		}

	}
	@Test
	void getNode() {
		directed_weighted_graph g = new DWGraph_DS();
		int size=10000;
		for (int i = 0; i < size; i++) {
			g.addNode(new Node());
			node_data n= g.getNode(i);
			assertTrue(n.getKey()==i);
		}		
	}
}
