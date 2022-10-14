package command;

import geometry.Point;
import geometry.Shape;

public class UpdatePointCmd implements Command {

	private Point oldState;
	//tacka koju menjamo
	private Point newState;
	//nova tacka
	private Point original = new Point();

	//ne treba nam referenca na model, jer menjamo tacku na koju vec imamo referencu
	public UpdatePointCmd(Point oldState, Point newState) {
		this.oldState = oldState; //temp
		this.newState = newState; //p3
	}

	public void execute() {
		original = oldState.clone();
		//oldState = newState.clone();
		/*System.out.println("original " + original);
		System.out.println("oldState " + oldState);*/
		
		/*original.setX(oldState.getX());
		original.setY(oldState.getY());
		original.setColor(oldState.getColor());*/
		//pamtimo vrednosti originalne tacke
		
		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setColor(newState.getColor());
		//ne igramo se sa referencama vec sa vrednostima
		//postavljamo vrednosti za X i Y na nove vrednosti
	}
	//koristimo metodu execute kada zelimo redo

	@Override
	public void unexecute() {
		//oldState = original.clone(); //kloniranje originala
		
		oldState.setX(original.getX());
		oldState.setY(original.getY());
		oldState.setColor(original.getColor());
		//vracanje na originalno stanje koje je zapamceno u original
	}
	//koristimo metodu unexecute kada zelimo undo

}
