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
import gameClient.CL_Pokemon;
import gameClient.util.Point3D;

class PokemonTest {


	@Test
	void get_edgeTest() {
		DWGraph_DS w = new DWGraph_DS();
		w.addNode(new Node());
		for (int i = 1; i < 100; i++) {
			node_data n = new Node();
			w.addNode(n);
			w.connect(i-1, i, i);
		}
		CL_Pokemon c= new CL_Pokemon(new Point3D(1,2,3) ,2,2,2 , w.getEdge(0, 1));
		assertEquals(c.get_edge().getSrc() , 0);
		assertEquals(c.get_edge().getDest() , 1);

	}
	@Test
	void valueTest() {
		CL_Pokemon c= new CL_Pokemon(new Point3D(1,2,3) ,2,2,2 , new Edge(new Node(), new Node() , 1));
		System.out.println(c.getValue()==2);
		assertEquals(c.getValue() , 2);

	}

}
