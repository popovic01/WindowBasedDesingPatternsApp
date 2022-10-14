package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mvc.DrawingFrame;

public class Observer implements PropertyChangeListener {
	
	DrawingFrame drawingFrame;
	
	public Observer(DrawingFrame drawingFrame) {
		this.drawingFrame = drawingFrame;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		//this.numSelectedShapes = (int) evt.getNewValue();
		if ((int) evt.getNewValue() == 0)
			disable();
		else if ((int) evt.getNewValue() == 1)
			enableModifyDelete();
		else if ((int) evt.getNewValue() > 1)
			enableDelete();
	}
	//metoda se pokrece svaki put kad dodje do promene stanja nekog obelezja
	//tj. svaki put kad se pozove metoda firePropertyChange u okviru nekog subjecta
	
	public void disable() {
		drawingFrame.getBtnModify().setEnabled(false);
		drawingFrame.getBtnDelete().setEnabled(false);
		drawingFrame.getBtnBack().setEnabled(false);
		drawingFrame.getBtnBringBack().setEnabled(false);
		drawingFrame.getBtnFront().setEnabled(false);
		drawingFrame.getBtnBringFront().setEnabled(false);
	}
	
	public void enableModifyDelete() {
		drawingFrame.getBtnModify().setEnabled(true);
		drawingFrame.getBtnDelete().setEnabled(true);
		drawingFrame.getBtnBack().setEnabled(true);
		drawingFrame.getBtnBringBack().setEnabled(true);
		drawingFrame.getBtnFront().setEnabled(true);
		drawingFrame.getBtnBringFront().setEnabled(true);
	}
	
	public void enableDelete() {
		drawingFrame.getBtnDelete().setEnabled(true);
		drawingFrame.getBtnModify().setEnabled(false);
		drawingFrame.getBtnBack().setEnabled(false);
		drawingFrame.getBtnBringBack().setEnabled(false);
		drawingFrame.getBtnFront().setEnabled(false);
		drawingFrame.getBtnBringFront().setEnabled(false);
	}


}
