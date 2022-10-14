package command;

import mvc.DrawingController;

public class ToBackCmd implements Command {
	
	DrawingController controller;
	
	public ToBackCmd(DrawingController controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		controller.toBack(1);
	}

	@Override
	public void unexecute() {
		controller.toFront(1);
	}

}
