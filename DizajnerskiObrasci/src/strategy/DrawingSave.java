package strategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import geometry.Shape;

public class DrawingSave implements FileSave {
	
	private static final long serialVersionUID = 2;
	private ArrayList<Shape> shapes;
	
	public DrawingSave(ArrayList<Shape> shapes) {
		this.shapes = shapes; //lista iscrtanih oblika
	}

	//metoda za cuvanje crteza
	@Override
	public void save() throws IOException {
		JFileChooser j = new JFileChooser();
		int result = j.showSaveDialog(null);
		
        // if the user selects a file
        if (result == JFileChooser.APPROVE_OPTION) 
        {
    		FileOutputStream fileOutputStream = new FileOutputStream(j.getSelectedFile().getAbsolutePath());
    		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
    		objectOutputStream.writeObject(shapes);
    		objectOutputStream.close();
    		fileOutputStream.close();
        }

	}

}
