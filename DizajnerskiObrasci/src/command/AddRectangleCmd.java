package command;

import mvc.DrawingModel;
import geometry.Rectangle;
import geometry.Shape;

public class AddRectangleCmd implements Command {
	
	private Rectangle rectangle;
	private DrawingModel model;

	public AddRectangleCmd(Rectangle rectangle, DrawingModel model) {
		super();
		this.rectangle = rectangle;
		this.model = model;
	}

	@Override
	public void execute() {
		model.add(rectangle);
	}

	@Override
	public void unexecute() {
		model.remove(rectangle);
	}

}
