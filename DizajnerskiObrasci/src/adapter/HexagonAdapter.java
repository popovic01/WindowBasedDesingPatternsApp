package adapter;

import java.awt.Graphics;
import java.awt.Color;

import geometry.Circle;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.SurfaceShape;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	
	private Hexagon hexagon;

	public HexagonAdapter() {
		
	}
	
	public HexagonAdapter(Point center, int radius) {
		hexagon = new Hexagon(0, 0, 0 );
		this.hexagon.setX(center.getX());
		this.hexagon.setY(center.getY());
		this.hexagon.setR(radius);
	}
	
	public HexagonAdapter(Point center, int radius, boolean selected) {
		this(center, radius);
		this.hexagon.setSelected(selected);
	}
	
	public HexagonAdapter(Point center, int radius, boolean selected, Color color) {
		this(center, radius, selected);
		this.hexagon.setBorderColor(color);
	}
	
	public HexagonAdapter(Point center, int radius, boolean selected, Color color, Color innerColor) {
		this(center, radius, selected, color);
		this.hexagon.setAreaColor(innerColor);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof HexagonAdapter) {
			if (this.hexagon.getR() == ((HexagonAdapter) o).getHexagon().getR()) //ako imaju iste precnike
				if (this.hexagon.getX() == ((HexagonAdapter) o).getHexagon().getX()) //ako imaju iste vrednosti x centra
					if (this.hexagon.getY() == ((HexagonAdapter) o).getHexagon().getY()) //ako imaju iste vrednosti x centra
						return 0; //isti su
		}
		return -1;
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
	}

	@Override
	public void moveBy(int byX, int byY) {

	}	
	
	public void setSelected(boolean selected) {
		this.hexagon.setSelected(selected);
	}
	
	public boolean isSelected() {
		return this.hexagon.isSelected();
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public Color getColor() {
		return this.hexagon.getBorderColor();
	}
	
	public Color getInnerColor() {
		return this.hexagon.getAreaColor();
	}
	
	public String toString() {
		//Center = (x, y), radius = radius
		//return "Center = (" + this.getHexagon().getX() + ", " + this.getHexagon().getY()  + "), radius = " + this.getHexagon().getR();
		return this.getHexagon().getX() + "," + this.getHexagon().getY()  + "," + this.getHexagon().getR();
	}
	
	public HexagonAdapter clone() {
		HexagonAdapter hexagon = new HexagonAdapter(new Point(0, 0), 0);
		hexagon.getHexagon().setX(this.getHexagon().getX());
		hexagon.getHexagon().setY(this.getHexagon().getY());
		try {
			hexagon.getHexagon().setR(this.getHexagon().getR());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		hexagon.getHexagon().setBorderColor(this.getHexagon().getBorderColor());
		hexagon.getHexagon().setAreaColor(this.getHexagon().getAreaColor());
		return hexagon;
	}

	@Override
	public boolean contains(Point P) {
		return false;
	}

	@Override
	public void fill(Graphics s) {
		// TODO Auto-generated method stub
	}

}
