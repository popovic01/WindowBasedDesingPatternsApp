package strategy;

import java.io.IOException;

public class SavingManager implements FileSave {

	private FileSave fileSave;
	
	public SavingManager() {
		
	}
	
	@Override
	public void save() throws IOException {
		fileSave.save();
		//izvrsava se metoda za cuvanje na osnovu toga da li je u pitanju crtez ili log
	}
	
	public void setFileSave(FileSave fileSave) {
		this.fileSave = fileSave;
	}

}
