package command;

import geometry.Shape;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class SelectShapeCmd implements Command {
	
	DrawingModel model;
	DrawingFrame frame;
	Shape s;
	
	public SelectShapeCmd(DrawingModel model, DrawingFrame frame, Shape s) {
		this.model = model;
		this.frame = frame;
		this.s = s;
	}

	@Override
	public void execute() {
		model.getSelected().add(s);
		s.setSelected(true);
		this.frame.getController().getObservable().setNumSelectedShapes(model.getSelected().size());
	}

	@Override
	public void unexecute() {
		s.setSelected(false); //unselektovanje oblika
		model.getSelected().remove(s); //brisanje oblika iz liste selektovanih
		this.frame.getController().getObservable().setNumSelectedShapes(model.getSelected().size());
	}

}
