package command;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command {
	
	DrawingController controller;
	int i;
	int size;
	
	public BringToFrontCmd(DrawingController controller, int i, int size) {
		this.controller = controller;
		this.i = i;
		this.size = size;
	}

	@Override
	public void execute() {
		controller.bringToFront(1);
		//ako prosledimo 1 metodi, necemo da se ispisuje u log
	}

	@Override
	public void unexecute() {
		for (int j = size-1; j > i; j--) {
			controller.toBack(1);
		}
		/*for (int j = 0; j < i; j++) {
			controller.toBack(1);
		} */
		//pozivamo metodu to back onoliko puta koliko je potrebno
	}

}
