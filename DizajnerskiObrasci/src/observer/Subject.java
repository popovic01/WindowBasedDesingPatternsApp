package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Subject {
	
	private PropertyChangeSupport propertyChangeSupport; //ugradjena klasa u javi - modeluje observerable
	private int numSelectedShapes; //stanje koje je bitno za observere
	
	public Subject() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertychangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertychangeListener);
		//pozivamo metodu iz klase PropertyChangeSupport i prosledjujemo joj listener koji dodajemo
	}
	//koristimo termin listener umesto observer 
	
	public void removePropertyChangeListener(PropertyChangeListener propertychangeListener) {
		propertyChangeSupport.removePropertyChangeListener(propertychangeListener);
	}
	
	public void setNumSelectedShapes(int numSelectedShapes) {
		propertyChangeSupport.firePropertyChange("selected shapes", this.numSelectedShapes, numSelectedShapes);
		//promena nekog obelezja - obavezno pre setovanja da ne izgubimo staru vrednost
		this.numSelectedShapes = numSelectedShapes;
	}
	
}
