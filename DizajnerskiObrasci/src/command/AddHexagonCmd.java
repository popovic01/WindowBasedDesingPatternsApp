package command;

import adapter.HexagonAdapter;
import geometry.Shape;
import mvc.DrawingModel;

public class AddHexagonCmd implements Command {
	
	private HexagonAdapter hexAdapter;
	private DrawingModel model;

	public AddHexagonCmd(HexagonAdapter hexAdapter, DrawingModel model) {
		super();
		this.hexAdapter = hexAdapter;
		this.model = model;
	}

	@Override
	public void execute() {
		model.add(hexAdapter);
	}

	@Override
	public void unexecute() {
		model.remove(hexAdapter);
	}

}
