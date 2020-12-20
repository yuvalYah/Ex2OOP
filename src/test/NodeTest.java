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

class NodeTest {

	@Test
	void getWeightTest() {
		node_data n1= new Node();
		assertTrue(n1.getWeight()==0);
	}
	@Test
	void getKeyTest() {
		for (int i = 1; i < 100; i++) {
			node_data n2= new Node();
			assertEquals(n2.getKey(),i);

		}
	}

}
