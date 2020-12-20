package api;

import gameClient.util.Point3D;

/**
 * this class implements nodes
 * part of the graph
 * @author yuval
 *
 */
public class Node implements node_data{
	private int key ,tag;
	private double weight; 
	private String info;
	private static int countKey;
	private geo_location p;

	public Node() {
		key=countKey++;
		info="";
		tag=0;
		weight=0;
		geo_location p = new Point3D(0,0,0);
	}
	public static void uptCount() {
		countKey=0;
	}
	@Override
	public int getKey() {
		return key;
	}

	@Override
	public geo_location getLocation() {
		return p;
	}

	@Override
	public void setLocation(geo_location p) {
		this.p=p;

	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(double w) {
		this.weight=w;		
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
