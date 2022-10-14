package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {

	private int x; 
	private int y;
	
	public Point() {
		
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, int y, boolean selected) {
		this(x, y); //mora biti 1. linija koda
		setSelected(selected);
	}
	
	public Point(int x, int y, boolean selected, Color color) {
		this(x, y, selected);
		this.setColor(color);
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Point) {
			if (this.getX() == ((Point)o).getX())
				if (this.getY() == ((Point)o).getY())
					return 0;
		}
		return -1;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		this.x = this.x + byX;
		this.y += byY;
	}
	
	@Override //overridovana metoda nadklase Shape
	public void draw(Graphics g) {
		g.setColor(getColor()); //defaultna boja crna
		//umesto tacke crtamo dve linice koje se seku pod pravim uglov da bi se tacka videla
		g.drawLine(this.x-2, this.y, this.x+2, this.y);
		g.drawLine(this.x, this.y-2, this.x, this.y+2);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.x-3, this.y-3, 6, 6);
		}
	}
	
	public boolean contains(int x, int y) {
		return this.distance(x, y) <= 3;
		//ako je selectovana tacka za <= 3 piksela udaljena od nase, vraca se true
	}
	
	public double distance(int x2, int y2) {
		int dx = this.x - x2;
		int dy = this.y - y2;
		double d = Math.sqrt(dx * dx + dy * dy);
		return d;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Point){
			Point temp = (Point) obj; //downcast, Object --> Point
			if(this.x == temp.x && this.y == temp.y)
				return true;
			else
				return false;
		} else
			return false;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override //overridovana je metoda nadklase Object
	public String toString() {
		//(x, y)
		//return "(" + x + ", " + y + ")";
		return x + "," + y;
	}

	public Point clone() {
		//kao parametar se prima tacka koju setujemo
		Point point = new Point();
		point.setX(this.getX());
		point.setY(this.getY());
		point.setColor(this.getColor());
		point.setSelected(true);
		return point;
		/*ako ne prosledimo tacku cije vrednosti treba setovati, onda ce se promeniti vrednosti tacke 
		 ali se to nece videti na crtezu*/
	}

}
