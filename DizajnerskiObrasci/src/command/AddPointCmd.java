package command;

import mvc.DrawingModel;
import geometry.Point;
import geometry.Shape;

public class AddPointCmd implements Command {
	
	private Point point;
	private DrawingModel model;

	public AddPointCmd(Point point, DrawingModel model) {
		this.point = point;
		this.model = model;
	}

	@Override
	public void execute() {
		model.add(point);
	}
	//koristimo metodu execute kada zelimo redo

	@Override
	public void unexecute() {
		model.remove(point);
	}
	//koristimo metodu unexecute kada zelimo undo

}
