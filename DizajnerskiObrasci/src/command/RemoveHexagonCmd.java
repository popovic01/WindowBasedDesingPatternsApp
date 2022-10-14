package command;

import adapter.HexagonAdapter;
import geometry.Shape;
import mvc.DrawingModel;

public class RemoveHexagonCmd implements Command {
	
	private HexagonAdapter hexAdapter;
	private DrawingModel model;
	int i; //indeks oblika u listi pre brisanja

	public RemoveHexagonCmd(HexagonAdapter hexAdapter, DrawingModel model) {
		super();
		this.hexAdapter = hexAdapter;
		this.model = model;
		i = model.getShapes().indexOf(hexAdapter);
	}

	@Override
	public void execute() {
		model.remove(hexAdapter);
		model.removeFromSelected(hexAdapter);
	}

	@Override
	public void unexecute() {
		//model.add(hexAdapter);
		model.getShapes().add(i, hexAdapter);
		model.addToSelected(hexAdapter);
	}

}
