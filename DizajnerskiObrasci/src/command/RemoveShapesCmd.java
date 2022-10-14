package command;

import java.util.ArrayList;
import java.util.List;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapesCmd implements Command {
	
	ArrayList<Shape> selected = new ArrayList<Shape>();
	List<Integer> list = new ArrayList<>(); //lista indeksa oblika koje brisemo
	DrawingModel model;
	int size;
	int sizeAllShapes;
	ArrayList<Shape> temp = new ArrayList<Shape>();
	List<Integer> tempList = new ArrayList<>(); //lista indeksa oblika koje brisemo

	public RemoveShapesCmd(DrawingModel model) {
		this.model = model;
		for (int i = 0; i < model.getSelected().size(); i++) {
			this.selected.add(model.getSelected().get(i));
			this.list.add(model.getShapes().indexOf(this.selected.get(i))); //pamcenje indeksa
		} //pamcenje selektovanih oblika
		size = selected.size(); //velicina liste
		sizeAllShapes = model.getShapes().size();
	}

	@Override
	public void execute() {
		int j = selected.size();
		for (int i = 0; i < model.getShapes().size(); i++) {
			if (!model.getSelected().contains(model.getShapes().get(i))) {
				this.tempList.add(model.getShapes().indexOf(model.getShapes().get(i))); //cuvanje indeksa oblika iz liste koji nisu selektovani
				this.temp.add(model.getShapes().get(i));
			}
		}
			
		for (int i = j - 1; i >= 0; i--) {
			model.getShapes().remove(selected.get(i)); //brisanje iz liste oblika
			model.getSelected().remove(selected.get(i)); //brisanje iz liste selektovanih oblika
		}
		
		/*for (int i = 0; i < this.temp.size(); i++) {
			System.out.println("temp " + temp.get(i));
			System.out.println("index " + tempList.get(i));
		}
		
		for (int i = 0; i < this.selected.size(); i++) {
			System.out.println("selected " + selected.get(i));
			System.out.println("index " + list.get(i));
		}*/
	}

	@Override
	public void unexecute() {	
		model.getShapes().clear();
		int m = 0;
		int n = 0;
		
		for (int i = 0; i < sizeAllShapes; i++) {
			if (list.contains(i)) {
				model.getShapes().add(selected.get(m));
				model.addToSelected(selected.get(m));
				m++;
			} else {
				model.getShapes().add(temp.get(n));
				n++;
			}
		}
		
	}

}
