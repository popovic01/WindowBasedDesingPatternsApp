package command;

import mvc.DrawingModel;
import geometry.Point;
import geometry.Shape;

public class RemovePointCmd implements Command {
	
	private Point point;
	private DrawingModel model;
	int i; //indeks oblika u listi pre brisanja

	public RemovePointCmd(Point point, DrawingModel model) {
		this.point = point;
		this.model = model;
		i = model.getShapes().indexOf(point);
	}

	@Override
	public void execute() {
		model.remove(point);
		model.removeFromSelected(point);
	}
	//koristimo metodu execute kada zelimo redo

	@Override
	public void unexecute() {
		//model.add(point);
		model.getShapes().add(i, point);
		model.addToSelected(point);
	}
	//koristimo metodu unexecute kada zelimo undo

}
