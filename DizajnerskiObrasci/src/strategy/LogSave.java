package strategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JFileChooser;

public class LogSave implements FileSave, Serializable {
	
	private static final long serialVersionUID = 1;
	private ArrayList<String> log; //lista izvrsenih komandi
	
	public LogSave(ArrayList<String> log) {
		this.log = log;
	}

	//metoda za cuvanje loga
	@Override
	public void save() throws IOException {
		JFileChooser j = new JFileChooser();
		int result = j.showSaveDialog(null);
		
        // if the user selects a file
        if (result == JFileChooser.APPROVE_OPTION)
        {
    		FileOutputStream fileOutputStream = new FileOutputStream(j.getSelectedFile().getAbsolutePath());
    		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
    		objectOutputStream.writeObject(log);
    		objectOutputStream.close();
    		fileOutputStream.close();
        }
		

	}

}
