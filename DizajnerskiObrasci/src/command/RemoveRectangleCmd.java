package command;

import mvc.DrawingModel;
import geometry.Rectangle;
import geometry.Shape;

public class RemoveRectangleCmd implements Command {
	
	private Rectangle rectangle;
	private DrawingModel model;
	int i; //indeks oblika u listi pre brisanja

	public RemoveRectangleCmd(Rectangle rectangle, DrawingModel model) {
		super();
		this.rectangle = rectangle;
		this.model = model;
		i = model.getShapes().indexOf(rectangle);
	}

	@Override
	public void execute() {		
		model.remove(rectangle);
		model.removeFromSelected(rectangle);
	}

	@Override
	public void unexecute() {
		model.getShapes().add(i, rectangle);
		//model.add(rectangle);
		model.addToSelected(rectangle);
	}

}
