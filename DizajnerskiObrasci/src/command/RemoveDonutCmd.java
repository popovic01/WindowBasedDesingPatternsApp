package command;

import geometry.Donut;
import geometry.Shape;
import mvc.DrawingModel;

public class RemoveDonutCmd implements Command {
	
	private Donut donut;
	private DrawingModel model;
	int i; //indeks oblika u listi pre brisanja

	public RemoveDonutCmd(Donut donut, DrawingModel model) {
		super();
		this.donut = donut;
		this.model = model;
		i = model.getShapes().indexOf(donut);
	}

	@Override
	public void execute() {
		model.remove(donut);
		model.removeFromSelected(donut);
	}

	@Override
	public void unexecute() {
		//model.add(donut);
		model.getShapes().add(i, donut);
		model.addToSelected(donut);
	}

}
