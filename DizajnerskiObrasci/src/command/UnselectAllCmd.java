package command;

import mvc.DrawingController;
import mvc.DrawingModel;
import java.util.ArrayList;
import java.util.List;

import geometry.Shape;

public class UnselectAllCmd implements Command {
	
	DrawingModel model;
	DrawingController controller;
	List<Shape> selected;
	
	public UnselectAllCmd(DrawingModel model, DrawingController controller, List<Shape> list) {
		this.model = model;
		this.controller = controller;
		this.selected = list;
	}

	@Override
	public void execute() {
		this.model.getSelected().removeAll(selected);
		for (int i = 0; i < selected.size(); i++) 
			selected.get(i).setSelected(false);
		controller.getObservable().setNumSelectedShapes(model.getSelected().size());
	}

	@Override
	public void unexecute() {
		this.model.getSelected().addAll(selected);
		for (int i = 0; i < selected.size(); i++) 
			selected.get(i).setSelected(true);
		controller.getObservable().setNumSelectedShapes(model.getSelected().size());
	}

}
