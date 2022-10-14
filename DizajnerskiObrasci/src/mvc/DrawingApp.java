package mvc;

import javax.swing.JFrame;

public class DrawingApp {

	public static void main(String[] args) {
	
		DrawingModel model = new DrawingModel(); //prvo inicijalizujemo model
		DrawingFrame frame = new DrawingFrame();
		frame.getView().setModel(model);
		DrawingController controller = new DrawingController(model, frame);
		frame.setController(controller);
		
		frame.setSize(780, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
