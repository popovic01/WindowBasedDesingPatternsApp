package mvc;

import java.util.ArrayList;

import command.Command;
import geometry.Shape;
import strategy.SavingManager;

public class DrawingModel {
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	//lista svih oblika
	private ArrayList<Shape> selected = new ArrayList<Shape>();
	//lista selektovanih oblika


	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	public void add(Shape s) {
		shapes.add(s);
	}
	
	public void remove(Shape s) {
		shapes.remove(s);
	}
	
	public Shape get(int i) {
		return shapes.get(i);
	}

	public ArrayList<Shape> getSelected() {
		return selected;
	}
	
	public void addToSelected(Shape s) {
		s.setSelected(true);
		selected.add(s);
	}
	
	public void removeFromSelected(Shape s) {
		selected.remove(s);
	}
	
	public Shape getFromSelected(int i) {
		return selected.get(i);
	}

}
