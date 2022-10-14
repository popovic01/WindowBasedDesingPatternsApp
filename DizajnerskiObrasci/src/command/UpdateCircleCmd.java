package command;

import geometry.Circle;
import geometry.Shape;

public class UpdateCircleCmd implements Command {
	
	private Circle oldState;
	private Circle newState;
	private Circle original = new Circle();

	public UpdateCircleCmd(Circle oldState, Circle newState) {
		super();
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		//newState.clone(oldState);
		/*original.getCenter().setX(oldState.getCenter().getX());
		original.getCenter().setY(oldState.getCenter().getY());
		try {
			original.setRadius(oldState.getRadius());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		original.setColor(oldState.getColor());
		original.setInnerColor(oldState.getInnerColor());*/
		
		oldState.getCenter().setX(newState.getCenter().getX());
		oldState.getCenter().setY(newState.getCenter().getY());
		try {
			oldState.setRadius(newState.getRadius());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
	}

	@Override
	public void unexecute() {
		//original.clone(oldState);
		oldState.getCenter().setX(original.getCenter().getX());
		oldState.getCenter().setY(original.getCenter().getY());
		try {
			oldState.setRadius(original.getRadius());
			
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
	}

}
