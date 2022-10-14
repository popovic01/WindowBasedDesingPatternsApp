package command;

import geometry.Line;
import geometry.Shape;

public class UpdateLineCmd implements Command {
	
	private Line oldState;
	private Line newState;
	private Line original = new Line();

	public UpdateLineCmd(Line oldState, Line newState) {
		super();
		this.oldState = oldState;
		//this.oldState = oldState.clone(); 
		this.newState = newState;
		//this.newState = newState.clone();
	}

	@Override
	public void execute() {
		original = oldState.clone();
		//oldState = newState.clone();
		/*original.getStartPoint().setX(oldState.getStartPoint().getX());
		original.getStartPoint().setY(oldState.getStartPoint().getY());
		original.getEndPoint().setX(oldState.getEndPoint().getX());
		original.getEndPoint().setY(oldState.getEndPoint().getY());
		original.setColor(oldState.getColor());*/
		
		oldState.getStartPoint().setX(newState.getStartPoint().getX());
		oldState.getStartPoint().setY(newState.getStartPoint().getY());
		oldState.getEndPoint().setX(newState.getEndPoint().getX());
		oldState.getEndPoint().setY(newState.getEndPoint().getY());
		oldState.setColor(newState.getColor());
	}

	@Override
	public void unexecute() {
		//oldState = original.clone();
		oldState.getStartPoint().setX(original.getStartPoint().getX());
		oldState.getStartPoint().setY(original.getStartPoint().getY());
		oldState.getEndPoint().setX(original.getEndPoint().getX());
		oldState.getEndPoint().setY(original.getEndPoint().getY());
		oldState.setColor(original.getColor());
	}

}
