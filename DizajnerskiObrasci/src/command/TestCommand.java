package command;

import mvc.DrawingModel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;

import java.awt.Color;

public class TestCommand {

	public static void main(String[] args) {
	
		Point p1 = new Point(10, 10, false, Color.RED);
		Point p2 = new Point(20, 20, false, Color.BLACK);
		Point p3 = new Point(30, 30, false, Color.GREEN);
		Point p4 = new Point(40, 40, false, Color.YELLOW);
		DrawingModel model = new DrawingModel();
		
		/*AddPointCmd addPointCmd = new AddPointCmd(p1, model);
		addPointCmd.execute();
		
		System.out.println(model.getShapes().size());
		
		addPointCmd.unexecute();
		
		System.out.println(model.getShapes().size());
		
		UpdatePointCmd updatePointCmd = new UpdatePointCmd(p1, p2);
		updatePointCmd.execute();*/
		
		/*Line l1 = new Line(p1, p2);
		Line l2 = new Line(p3, p4);*/
		
		/*AddLineCmd alc = new AddLineCmd(l1, model);
		alc.execute();*/
		//System.out.println(model.getShapes().size());
		
		//RemoveLineCmd rlc = new RemoveLineCmd(l2, model);
		//alc.unexecute();
		//System.out.println(model.getShapes().size());

		/*UpdateLineCmd ulc = new UpdateLineCmd(l1, l2);
		ulc.execute(); 
		
		ulc.unexecute();*/
		
		Rectangle r1 = new Rectangle(p1, 10, 10);
		Rectangle r2 = new Rectangle(p2, 20, 20);
		
		AddRectangleCmd arc = new AddRectangleCmd(r1, model);
		arc.execute();
		arc.unexecute();
		

	}

}
