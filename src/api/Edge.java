package api;

/**
 * this class implements edge how be part in the graph
 * @author yuval
 *
 */
public class Edge implements edge_data{
	private node_data scr , dest;
	private double weight;
	private String info;
	private int tag;

	public Edge(node_data a ,node_data b , double w) {
		this.scr=a;
		this.dest=b;
		this.weight=w;
		info="";
		tag=0;
	}
	@Override
	public int getSrc() {
		return scr.getKey();
	}

	@Override
	public int getDest() {
		return dest.getKey();
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public void setInfo(String s) {
		this.info=s;		
	}

	@Override
	public int getTag() {
		return tag;
	}

	@Override
	public void setTag(int t) {
		this.tag=t;
	}

}
