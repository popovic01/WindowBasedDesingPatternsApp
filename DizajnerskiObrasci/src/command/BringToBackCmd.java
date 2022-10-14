package command;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

public class BringToBackCmd implements Command {
	
	DrawingController controller;
	int i; //indeks oblika u listi
	
	public BringToBackCmd(DrawingController controller, int i) {
		this.controller = controller;
		this.i = i;
	}

	@Override
	public void execute() {
		controller.bringToBack(1); 
		//ako prosledimo 1 metodi, necemo da se ispisuje u log
	}

	@Override
	public void unexecute() {
		for (int j = 0; j < i; j++) {
			controller.toFront(1);
		} //pozivamo metodu to front onoliko puta koliko je potrebno
	}

}
