package geometry;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
/*import java.awt.Shape;*/
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.UIManager;

public class Donut extends Circle {

	private int innerRadius;
	
	public Donut() {
		
	} 
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius); //poziv metoda iz superClass
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius); 
		setSelected(selected);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected); 
		setColor(color);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color); 
		setInnerColor(innerColor);
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			if (this.getRadius() == ((Donut) o).getRadius()) //ako imaju iste precnike
				if (this.getInnerRadius() == ((Donut) o).getInnerRadius()) //ako imaju iste precnike
					if (this.getCenter().getX() == ((Donut) o).getCenter().getX())
						if (this.getCenter().getY() == ((Donut) o).getCenter().getY())
							return 0; //isti su
		}
		return -1;
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor()); //postavljanje boje unutrasnjosti
		//super.fill(g);
		//prvo se oboji ceo krug pozivajuci metodu iz Circle klase
		//postavljamo boju koja je i kao background color na frame-u
		/*g.fillOval(this.getCenter().getX()-this.innerRadius+1, this.getCenter().getY()-this.innerRadius+1,
				this.innerRadius*2-2, this.innerRadius*2-2);*/
		//zatim se oboji unutrasnji krug tj. on treba da bude iste boje kao pozadina 
		
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double oval1 = new Ellipse2D.Double(); //deklarisanje veceg kruga
		Ellipse2D.Double oval2 = new Ellipse2D.Double(); //deklarisanje manjeg kruga
		oval1.setFrame(this.getCenter().getX()-this.getRadius(), this.getCenter().getY()-this.getRadius(), this.getRadius()*2, this.getRadius()*2);
		//postavljanje vrednosti za veci krug
		oval2.setFrame(this.getCenter().getX()-this.getInnerRadius(), this.getCenter().getY()-this.getInnerRadius(), this.getInnerRadius()*2, this.getInnerRadius()*2);
		//postavljanje vrednosti za manji krug
		Area area1 = new Area(oval1); //kreiranje povrsine za veci krug
		Area area2 = new Area(oval2); ////kreiranje povrsine za veci krug
		area1.subtract(area2); //oduzimanje manjeg kruga od veceg
		g2.fill(area1); //iscrtavanje i bojenje dobijene povrsine - krofne

	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g); 
		g.setColor(getColor());
		//g.drawOval(this.getCenter().getX()-this.innerRadius, this.getCenter().getY()-this.innerRadius, 
				//this.innerRadius*2, this.innerRadius*2);
		//this.fill(g);
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Donut) {
			Donut temp = (Donut) obj;
			if(this.getCenter().equals(temp.getCenter()) && this.getRadius() == temp.getRadius() && this.innerRadius == temp.innerRadius)
				//super.equals(temp)
				return true;
			else
				return false;
		} else
			return false;
	}
	
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}
	
	@Override
	public String toString() {
		return super.toString() + "," + innerRadius;
	}
	
	public Donut clone() {
		Donut donut = new Donut();
		donut.getCenter().setX(this.getCenter().getX());
		donut.getCenter().setY(this.getCenter().getY());
		try {
			donut.setRadius(this.getRadius());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		donut.setInnerRadius(this.getInnerRadius());
		donut.setColor(this.getColor());
		donut.setInnerColor(this.getInnerColor());
		return donut;
	}
	
}
