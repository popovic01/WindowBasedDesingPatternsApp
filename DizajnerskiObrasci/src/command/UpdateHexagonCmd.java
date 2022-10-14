package command;

import adapter.HexagonAdapter;
import geometry.Point;

public class UpdateHexagonCmd implements Command {
	
	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original = new HexagonAdapter(new Point(0, 0), 0);

	public UpdateHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState) {
		super();
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		//newState.clone(oldState);
		/*original.getHexagon().setY(oldState.getHexagon().getY());
		original.getHexagon().setX(oldState.getHexagon().getX());
		try {
			original.getHexagon().setR(oldState.getHexagon().getR());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		original.getHexagon().setBorderColor(oldState.getHexagon().getBorderColor());
		original.getHexagon().setAreaColor(oldState.getHexagon().getAreaColor());*/
		
		oldState.getHexagon().setY(newState.getHexagon().getY());
		oldState.getHexagon().setX(newState.getHexagon().getX());
		try {
			oldState.getHexagon().setR(newState.getHexagon().getR());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		oldState.getHexagon().setBorderColor(newState.getHexagon().getBorderColor());
		oldState.getHexagon().setAreaColor(newState.getHexagon().getAreaColor());
	}

	@Override
	public void unexecute() {
		//original.clone(oldState);
		oldState.getHexagon().setY(original.getHexagon().getY());
		oldState.getHexagon().setX(original.getHexagon().getX());
		try {
			oldState.getHexagon().setR(original.getHexagon().getR());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		oldState.getHexagon().setBorderColor(original.getHexagon().getBorderColor());
		oldState.getHexagon().setAreaColor(original.getHexagon().getAreaColor());
	}

}
