package command;

import mvc.DrawingModel;
import geometry.Line;
import geometry.Shape;

public class RemoveLineCmd implements Command {
	
	private Line line;
	private DrawingModel model;
	int i; //indeks oblika u listi pre brisanja

	public RemoveLineCmd(Line line, DrawingModel model) {
		super();
		this.line = line;
		this.model = model;
		i = model.getShapes().indexOf(line);
	}

	@Override
	public void execute() {
		model.remove(line);
		model.removeFromSelected(line);
	}

	@Override
	public void unexecute() {
		//model.add(line);
		model.getShapes().add(i, line);
		model.addToSelected(line);
	}

}
