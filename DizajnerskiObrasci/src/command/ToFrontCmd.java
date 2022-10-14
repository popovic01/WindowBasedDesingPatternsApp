package command;

import mvc.DrawingController;

public class ToFrontCmd implements Command {
	
	DrawingController controller;
	
	public ToFrontCmd(DrawingController controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		controller.toFront(1);
	}

	@Override
	public void unexecute() {
		controller.toBack(1);
	}

}
