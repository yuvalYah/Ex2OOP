package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.DWGraph_DS;
import api.Edge;
import api.Node;
import api.node_data;
import gameClient.CL_Agent;

class AgentTest {

	@Test
	void currNodeTest() {
		DWGraph_DS w = new DWGraph_DS();
		w.addNode(new Node());
		for (int i = 1; i < 100; i++) {
			node_data n = new Node();
			w.addNode(n);
			w.connect(i-1, i, i);
		}
		CL_Agent ag = new CL_Agent(w, 2);
		assertTrue(ag.getSrcNode()==w.getNode(2).getKey());
	}
	@Test
	void isMovingTest() {
		DWGraph_DS w = new DWGraph_DS();
		w.addNode(new Node());
		for (int i = 1; i < 100; i++) {
			node_data n = new Node();
			w.addNode(n);
			w.connect(i-1, i, i);
		}
		CL_Agent ag = new CL_Agent(w, 2);
		ag.set_curr_edge(new Edge(w.getNode(0) , w.getNode(1),1));
		assertTrue(ag.isMoving());
		
	}
	@Test
	void getIdTest() {
		DWGraph_DS w = new DWGraph_DS();
		w.addNode(new Node());
		for (int i = 1; i < 100; i++) {
			node_data n = new Node();
			w.addNode(n);
			w.connect(i-1, i, i);
		}
		CL_Agent ag = new CL_Agent(w, 2);
		
		assertEquals(ag.getID(),-1);

	}
	@Test
	void getNextNodeTest(){
		DWGraph_DS w = new DWGraph_DS();
		w.addNode(new Node());
		for (int i = 1; i < 100; i++) {
			node_data n = new Node();
			w.addNode(n);
			w.connect(i-1, i, i);
		}
		CL_Agent ag = new CL_Agent(w, 2);
		ag.set_curr_edge(new Edge(w.getNode(0) , w.getNode(1),1));
		assertTrue(ag.getNextNode()==1);
		
	}


}
