package command;

import geometry.Line;
import geometry.Shape;
import mvc.DrawingModel;

public class AddLineCmd implements Command {
	
	private Line line;
	private DrawingModel model;

	public AddLineCmd(Line line, DrawingModel model) {
		super();
		this.line = line;
		this.model = model;
	}

	@Override
	public void execute() {
		model.add(line);
	}

	@Override
	public void unexecute() {
		model.remove(line);
	}

}
