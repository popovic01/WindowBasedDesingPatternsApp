package command;

import geometry.Circle;
import geometry.Shape;
import mvc.DrawingModel;

public class RemoveCircleCmd implements Command {
	
	private Circle circle;
	private DrawingModel model;
	int i; //indeks oblika u listi pre brisanja

	public RemoveCircleCmd(Circle circle, DrawingModel model) {
		super();
		this.circle = circle;
		this.model = model;
		i = model.getShapes().indexOf(circle);
	}

	@Override
	public void execute() {
		model.remove(circle);
		model.removeFromSelected(circle);
	}

	@Override
	public void unexecute() {
		model.getShapes().add(i, circle);
		model.addToSelected(circle);
	}

}
