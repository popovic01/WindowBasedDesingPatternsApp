package command;

import geometry.Rectangle;
import geometry.Shape;

public class UpdateRectangleCmd implements Command {
	
	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original = new Rectangle();

	public UpdateRectangleCmd(Rectangle oldState, Rectangle newState) {
		super();
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		//newState.clone(oldState);
		/*original.getUpperLeftPoint().setX(oldState.getUpperLeftPoint().getX());
		original.getUpperLeftPoint().setY(oldState.getUpperLeftPoint().getY());
		original.setHeight(oldState.getHeight());
		original.setWidth(oldState.getWidth());
		original.setColor(oldState.getColor());
		original.setInnerColor(oldState.getInnerColor());*/
		
		oldState.getUpperLeftPoint().setX(newState.getUpperLeftPoint().getX());
		oldState.getUpperLeftPoint().setY(newState.getUpperLeftPoint().getY());
		oldState.setHeight(newState.getHeight());
		oldState.setWidth(newState.getWidth());
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
	}

	@Override
	public void unexecute() {
		//original.clone(oldState);
		oldState.getUpperLeftPoint().setX(original.getUpperLeftPoint().getX());
		oldState.getUpperLeftPoint().setY(original.getUpperLeftPoint().getY());
		oldState.setHeight(original.getHeight());
		oldState.setWidth(original.getWidth());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
	}
	
}
