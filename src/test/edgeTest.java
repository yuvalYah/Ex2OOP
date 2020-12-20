package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.Edge;
import api.Node;
import api.edge_data;
import api.node_data;

class edgeTest {

	@Test
	void getSrcTest() {
		node_data n1= new Node();
		node_data n2 = new Node();
		edge_data e= new Edge(n1,n2,1);
		assertTrue(n1.getKey() == e.getSrc());
		edge_data e2= new Edge(n2,n1,1);
		assertTrue(n2.getKey() == e2.getSrc());
		
	}
	@Test
	void getDestTest() {
		node_data n1= new Node();
		node_data n2 = new Node();
		edge_data e= new Edge(n1,n2,1);
		assertTrue(n2.getKey() == e.getDest());
		edge_data e2= new Edge(n2,n1,1);
		assertTrue(n1.getKey() == e2.getDest());
		
	}
	@Test
	void getWeightTest() {
		node_data n1= new Node();
		node_data n2 = new Node();
		edge_data e= new Edge(n1,n2,1);
		assertTrue(1 == e.getWeight());
		edge_data e2= new Edge(n2,n1,10000);
		assertTrue(10000 == e2.getWeight());
		
	}
	

}
