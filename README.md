*OOP Ex2 .*
------------
*Part 1 create directed weighted graph :*
in this part I create graph

**Class Node :**

in class node there is key , weight , tag , info and loction (of the node)

**Class Edge : **

in class edge there is  node src , node dest , weight , in fo and tag .

**class DWGraph_DS :**

This class implements directed weighted graph .

To store a list of vertices I used on ArrayList how save the nodes

For example : node with key = n so he be on index n of the list.

To save a list of Edges I used HashMaph when the key is the key of the node ,

and the value is ArrayList of Edges that the src is the key of the node and the dest is

For example : list of nodes : [ key =0 ,1,2,3 ,7]  

list of edges of node 0 is : <hashmaph key =0 , value : arraylist=[ #, 0->1 , 0->2 ,0->3 , # , # , # ,0->7] >.

To save node size , add counter.

To save edge size add counter.

to save MC  add counter.

**Class DWGraph_Algo :**

implements dw_graph_algorithms with Methods : 
* init - init the graph.
* get graph.
* copy - copy the pointer to the graph to algo graph.
* is connected - check if the graph is strongly connected.
* shortest Path Dist - get the smallest distance of src to dest.
* shortest Path - get list of node_data of the shortest path between src to dest.
* save - save the graph with json file.
* load - load the graph from json file.

 
 *part 2 : pokemon game* 
 
 In this part i create a game with agent and pokemons who eaten by the agents.
 
 In the game there is 23 levels [0,23].
 
 The player put his Id number and choose level , and cold see the pokemon runs to eat the pokemons.
 
 The game is on time.
 
 You need to eat a lot of pokemons and need to call to moveAgent function less times.
 
 
 
 