package gameClient;
import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

/**
 * this class represent the pokemon on the graph 
 * with value and need to eat them
 * @author yuval
 *
 */
public class CL_Pokemon {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;


	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
		_value = v;
		set_edge(e);
		_pos = p;
	}

	/**
	 * init from json string
	 * @param json
	 * @return CL_Pokemon
	 */
	public static CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
			JSONObject p = new JSONObject(json);
			int id = p.getInt("id");

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	/**
	 * to string of the pokemon
	 * @return string
	 */
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}

	/**
	 * get an edge of the pokemon is on 
	 * @return edge_data
	 */
	public edge_data get_edge() {
		return _edge;
	}

	/**
	 * set edge of the pokemon
	 * @param _edge
	 */
	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	/**
	 * get location of the pokemon
	 * @return Point3D
	 */
	public Point3D getLocation() {
		return _pos;
	}

	/**
	 * get type of the pokemon
	 * @return int type
	 */
	public int getType() {return _type;}

	//	public double getSpeed() {return _speed;}

	/**
	 * get value of the pokemon
	 * @return double value
	 */
	public double getValue() {return _value;}


}