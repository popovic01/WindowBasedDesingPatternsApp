package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {
	
	private Point startPoint = new Point();
	private Point endPoint = new Point();

	public Line() {
		
	}
	
	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		setSelected(selected);
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected, Color color) {
		this(startPoint, endPoint, selected);
		this.setColor(color);
	}
	
	/*public Line(Line line) {
		this.startPoint.setX(line.getStartPoint().getX());
		this.startPoint.setY(line.getStartPoint().getY());
		this.endPoint.setX(line.getEndPoint().getX());
		this.endPoint.setY(line.getEndPoint().getY());
	}*/
	
	@Override
	public int compareTo (Object o) {
		if (o instanceof Line) {
			if (this.endPoint.getX() == this.getEndPoint().getX())
				if (this.endPoint.getY() == this.getEndPoint().getY())
					if (this.startPoint.getX() == this.getStartPoint().getX())
						if (this.startPoint.getY() == this.getStartPoint().getY())
							return 0;
		}
		return -1;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		this.startPoint.moveBy(byX, byY);
		this.endPoint.moveBy(byX, byY);
	}
	
	public Point middleOfLine() {
		int middleByX = (this.startPoint.getX() + this.endPoint.getX()) / 2;
		int middleByY = (this.startPoint.getY() + this.endPoint.getY()) / 2;
		Point middlePoint = new Point(middleByX, middleByY);
		return middlePoint;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		g.drawLine(this.startPoint.getX(), this.startPoint.getY(), this.endPoint.getX(), this.endPoint.getY());
		//pamti boju koja je prethodno koriscena pa svaki put postavljamo opet tj. resetovanje boje
		
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.startPoint.getX()-3, this.startPoint.getY()-3, 6, 6);
			g.drawRect(this.endPoint.getX()-3, this.endPoint.getY()-3, 6, 6);
			g.drawRect(this.middleOfLine().getX()-3, this.middleOfLine().getY()-3, 6, 6);
		}	
	}
	
	public boolean contains(int x, int y) {
		if((startPoint.distance(x, y) + endPoint.distance(x, y)) - lenght() <= 0.05){
			return true;
		} else
			return false;
	}
	
	public double lenght() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Line) {
			Line temp = (Line) obj;
			if(this.startPoint.equals(temp.startPoint) && this.endPoint.equals(temp.endPoint)) 
				return true;
				else
					return false;
			
		} else
			return false;
	}
	
	public Point getStartPoint() {
		return startPoint;
	}
	
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	
	public Point getEndPoint() {
		return endPoint;
	}
	
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	
	@Override
	public String toString(){
		//(x, y) --> (x, y)
		//return startPoint + " --> " + endPoint;
		return startPoint + "-" + endPoint;
	}
	
	public Line clone() {
		Line line = new Line();
		//kao parametar se prima linija koju setujemo
		line.getStartPoint().setX(this.getStartPoint().getX());
		line.getStartPoint().setY(this.getStartPoint().getY());
		line.getEndPoint().setX(this.getEndPoint().getX());
		line.getEndPoint().setY(this.getEndPoint().getY());
		line.setColor(this.getColor());
		return line;
	}
	
}