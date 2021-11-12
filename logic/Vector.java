package logic;

public class Vector<E> {
	public E x;
	public E y;
	
	public static Vector<Double> zero = new Vector<Double>(0.0,0.0);
	
	public Vector(E x, E y){
		this.x = x;
		this.y = y;
	}
	
	public static Vector<Double> addDouble(Vector<Double> a, Vector<Double> b){
		return new Vector<Double>((a.x + b.x), (a.y + b.y));
	}
	
	@Override
	public String toString() {
		return x + ":" + y;
	}
}
