package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import adapter.HexagonAdapter;
import command.AddCircleCmd;
import command.AddDonutCmd;
import command.AddHexagonCmd;
import command.AddLineCmd;
import command.AddPointCmd;
import command.AddRectangleCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.RemoveCircleCmd;
import command.RemoveDonutCmd;
import command.RemoveHexagonCmd;
import command.RemoveLineCmd;
import command.RemovePointCmd;
import command.RemoveRectangleCmd;
import command.RemoveShapesCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UnselectAllCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import dialogs.DlgCircle;
import dialogs.DlgDonut;
import dialogs.DlgHexagon;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRect;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.SurfaceShape;
import hexagon.Hexagon;
import observer.Observer;
import observer.Subject;
import strategy.DrawingSave;
import strategy.LogSave;
import strategy.SavingManager;

public class DrawingController implements Serializable {
	
	private DrawingModel model;
	private DrawingFrame frame;
	int check; //pomocna promenljiva 
	Point startPoint; //pomocna promenljiva za iscrtavanje linije
	private Subject observable; 
	private Observer observer;
	private ArrayList<Command> commands = new ArrayList<Command>(); //lista komandi za undo/redo
	private ArrayList<Command> temp = new ArrayList<Command>(); //pomocna lista komandi za undo/redo
	private ArrayList<String> log = new ArrayList<String>(); 
	//lista izvrsenih komandi koje se nalaze u log-u
	private SavingManager savingManager1 = new SavingManager(); //strategy obrazac
	private SavingManager savingManager2 = new SavingManager();
	private int counter; //brojac - za next dugme pri crtanju crteza, da se zna do koje komande se stiglo
	boolean moreShapes = false;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		observable = new Subject();
		observer = new Observer(this.frame);
		observable.addPropertyChangeListener(observer); //dodajemo observer u listu da bi bio obavesten o promenama u subjectu 
	}
	
	public void check(int c) {
		check = c;
	}

	public void mouseClicked(MouseEvent e) {
		if (check == 6)
			this.selection(frame.getView().getGraphics(), e.getX(), e.getY());
		else 
			this.paint(frame.getView().getGraphics(), e.getX(), e.getY());
	}
	
	public void paint(Graphics g, int x, int y) {
		if (check == 1)
		{	
			Point p = new Point(x, y, false, frame.getColor());
			model.add(p);
			AddPointCmd apc = new AddPointCmd(p, model);
			commands.add(apc); //dodavanje komande u listu
			frame.repaint();
			log("Point added", p);
		} else if (check == 2) {
			if (startPoint == null)
			{
				startPoint = new Point(x, y);
			}
			else
			{
				Point eP = new Point(x, y);
				Line l = new Line(startPoint, eP, false, frame.getColor());
				model.add(l);
				AddLineCmd alc = new AddLineCmd(l, model);
				commands.add(alc);
				startPoint = null;
				frame.repaint();
				log("Line added", l);
			}

		} else if (check == 3) {
			DlgRect dlgR = new DlgRect();			
			dlgR.getTxtX().setText(String.valueOf(x));
			dlgR.getTxtY().setText(String.valueOf(y));
			dlgR.getTxtX().setEditable(false);
			dlgR.getTxtY().setEditable(false);
			dlgR.getBtnColor().setBackground(this.frame.getColor()); //setovanje boje dugmeta na osnovu aktivne boje
			dlgR.getBtnInnerColor().setBackground(this.frame.getInnerColor()); //setovanje boje dugmeta na osnovu aktivne boje
			dlgR.setVisible(true);
			if (dlgR.isOk()) {
				try {
					String width = dlgR.getTxtWidth().getText().toString();
					int intWidth = Integer.parseInt(width);
					String height = dlgR.getTxtHeight().getText().toString();
					int intHeight = Integer.parseInt(height);	
					Color c = dlgR.getBtnColor().getBackground(); //setovanje boje na osnovu boje dugmeta iz dijaloga
					Color innerC = dlgR.getBtnInnerColor().getBackground(); //setovanje boje na osnovu boje dugmeta iz dijaloga
					Rectangle r = new Rectangle(new Point(x, y), intWidth, intHeight, false, c, innerC);
					model.add(r);
					AddRectangleCmd arc = new AddRectangleCmd(r, model);
					commands.add(arc);
					frame.repaint();
					log("Rectangle added", r);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, "You need to enter numbers!");
				}
						
			}	
			
		} else if (check == 4) {
			DlgCircle dlgC = new DlgCircle();
			dlgC.getTxtXc().setText(String.valueOf(x));
			dlgC.getTxtYc().setText(String.valueOf(y));
			dlgC.getTxtXc().setEditable(false);
			dlgC.getTxtYc().setEditable(false);
			dlgC.getBtnColor().setBackground(this.frame.getColor());
			dlgC.getBtnInnerColor().setBackground(this.frame.getInnerColor());
			dlgC.setVisible(true);
			
			if (dlgC.isOk()) {
				String radius = dlgC.getTxtR().getText().toString();
				int intRadius = Integer.parseInt(radius);	
				Color c = dlgC.getBtnColor().getBackground();
				Color innerC = dlgC.getBtnInnerColor().getBackground();
				Circle circle = new Circle(new Point(x, y), intRadius, false, c, innerC);
				model.add(circle);
				AddCircleCmd acc = new AddCircleCmd(circle, model);
				commands.add(acc);
				frame.repaint();
				log("Circle added", circle);
			}
		} else if (check == 5) {
			DlgDonut dlgD = new DlgDonut();
			dlgD.getTxtXc().setText(String.valueOf(x));
			dlgD.getTxtYc().setText(String.valueOf(y));
			dlgD.getTxtXc().setEditable(false);
			dlgD.getTxtYc().setEditable(false);
			dlgD.getBtnColor().setBackground(this.frame.getColor());
			dlgD.getBtnInnerColor().setBackground(this.frame.getInnerColor());
			dlgD.setVisible(true);
			
			if (dlgD.isOk()) {
				Color c = dlgD.getBtnColor().getBackground();
				Color innerC = dlgD.getBtnInnerColor().getBackground();
				Donut d = new Donut(new Point(x, y), Integer.parseInt(dlgD.getTxtR().getText().toString()), Integer.parseInt(dlgD.getTxtIR().getText().toString()), false, c, innerC);
				model.add(d);
				AddDonutCmd adc = new AddDonutCmd(d, model);
				commands.add(adc);
				frame.repaint();
				log("Donut added", d);
			}
		} else if (check == 6)
			this.selection(frame.getView().getGraphics(), x, y);
		else if (check == 7) {
			DlgHexagon dlgH = new DlgHexagon();
			dlgH.getTxtX().setText(String.valueOf(x));
			dlgH.getTxtY().setText(String.valueOf(y));
			dlgH.getTxtX().setEditable(false);
			dlgH.getTxtY().setEditable(false);
			dlgH.getBtnColor().setBackground(this.frame.getColor());
			dlgH.getBtnInnerColor().setBackground(this.frame.getInnerColor());
			dlgH.setVisible(true);
			
			if (dlgH.isOk()) {
				Color c = dlgH.getBtnColor().getBackground();
				Color innerC = dlgH.getBtnInnerColor().getBackground();
				HexagonAdapter hexAdapter = new HexagonAdapter(new Point(Integer.parseInt(dlgH.getTxtX().getText().toString()), Integer.parseInt(dlgH.getTxtY().getText().toString())),
						Integer.parseInt(dlgH.getTxtR().getText().toString()), false, c, innerC);
				model.add(hexAdapter);
				AddHexagonCmd ahc = new AddHexagonCmd(hexAdapter, model);
				commands.add(ahc);
				frame.repaint();
				log("Hexagon added", hexAdapter);
			}
		}
	}
	
	public void selection(Graphics g, int x, int y) { 
		int counter = 0;
		//prolazak kroz listu svih oblika
		for (int i = 0; i < model.getShapes().size(); i++) { 		      
			if (model.getShapes().get(i).contains(x, y)) { //ako i-ti oblik iz liste sadrzi tacku klika
				if (!model.getSelected().contains(model.getShapes().get(i))) { //provera da li se oblik vec nalazi u listi
					model.addToSelected(model.getShapes().get(i)); //dodavanje oblika u listu selektovanih, ukoliko sadrzi tacku klika
					//model.getShapes().get(i).setSelected(true);
					SelectShapeCmd s = new SelectShapeCmd(model, frame, model.getShapes().get(i));
					commands.add(s); //dodavanje u listu komandi
					if (model.getShapes().get(i) instanceof Circle && !(model.getShapes().get(i) instanceof Donut))
						log("Circle selected", model.getShapes().get(i));
					else if (model.getShapes().get(i) instanceof Point)
						log("Point selected", model.getShapes().get(i));
					else if (model.getShapes().get(i) instanceof Line)
						log("Line selected", model.getShapes().get(i));
					else if (model.getShapes().get(i) instanceof Rectangle)
						log("Rectangle selected", model.getShapes().get(i));
					else if (model.getShapes().get(i) instanceof Donut)
						log("Donut selected", model.getShapes().get(i));
					else if (model.getShapes().get(i) instanceof HexagonAdapter)
						log("Hexagon selected", model.getShapes().get(i));
				}
			} else if (!model.getShapes().get(i).contains(x, y)) {
				//ako i-ti oblik u listi ne sadrzi tacku klika
				counter++;
			} 
			//ukoliko nijedan oblik u listi oblika ne sadrzi tacku klika
			if (counter == model.getShapes().size()) {
				if (model.getSelected().size() > 0) {
					List<Shape> list = Arrays.asList(new Shape[model.getSelected().size()]);
					Collections.copy(list, model.getSelected()); //kopiranje liste selektovanih oblika
					log("All shapes unselected", null); //ispisati u logu ako ima nekih selektovanih oblika
					UnselectAllCmd un = new UnselectAllCmd(model, this, list); //prosledjujemo listu selektovanih
					commands.add(un);
				}
				model.getSelected().clear(); //brisanje liste selektovanih
				for (int j = 0; j < model.getShapes().size(); j++) {
					model.getShapes().get(j).setSelected(false); //postavljanje selected na false za sve oblike iz liste
				}
			}
		}
		observable.setNumSelectedShapes(model.getSelected().size());
		frame.repaint();
	}
	
	public void delete(int c) {	
		if (model.getShapes().isEmpty())
			JOptionPane.showMessageDialog(null, "You need to add shape first!");
		else if (model.getSelected() == null)
			JOptionPane.showMessageDialog(null, "You need to select a shape!");
		else if (model.getSelected().size() > 1) {
			if (c == 1) {
				int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure?",
						"Delete shapes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (confirmation == 0) {
					log("Selected shapes deleted", null);
				}
			}
			RemoveShapesCmd rs = new RemoveShapesCmd(model);
			commands.add(rs);
			rs.execute();
			frame.repaint();
			moreShapes = true; //da ne udje u else if
		}
		else if (moreShapes == false) 
		{
			int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure?",
					"Delete shape", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (confirmation == 0) {
				for (int i = 0; i < model.getShapes().size(); i++) { 	
					if (model.getShapes().get(i).isSelected()) {
						if (model.getShapes().get(i) instanceof Point) {
							RemovePointCmd rpc = new RemovePointCmd((Point) model.getShapes().get(i), model);
							commands.add(rpc);
							log("Point deleted", (Point) model.getShapes().get(i));
						} else if (model.getShapes().get(i) instanceof Line) {
							RemoveLineCmd rlc = new RemoveLineCmd((Line) model.getShapes().get(i), model);
							commands.add(rlc);
							log("Line deleted", (Line) model.getShapes().get(i));
						} else if (model.getShapes().get(i) instanceof Rectangle) {
							RemoveRectangleCmd rrc = new RemoveRectangleCmd((Rectangle) model.getShapes().get(i), model);
							commands.add(rrc);
							log("Rectangle deleted", (Rectangle) model.getShapes().get(i));
						} else if (model.getShapes().get(i) instanceof Donut) {
							RemoveDonutCmd rdc = new RemoveDonutCmd((Donut) model.getShapes().get(i), model);
							commands.add(rdc);
							log("Donut deleted", (Donut) model.getShapes().get(i));
						} else if (model.getShapes().get(i) instanceof Circle) {
							RemoveCircleCmd rcc = new RemoveCircleCmd((Circle) model.getShapes().get(i), model);
							commands.add(rcc);
							log("Circle deleted", (Circle) model.getShapes().get(i));
						} else if (model.getShapes().get(i) instanceof HexagonAdapter) {
							RemoveHexagonCmd rhc = new RemoveHexagonCmd((HexagonAdapter) model.getShapes().get(i), model);
							commands.add(rhc);
							log("Hexagon deleted", (HexagonAdapter) model.getShapes().get(i));
						} 
					}	
			    }			
				int size = model.getSelected().size();
				for (int i = size - 1; i >= 0; i--) {
					Shape s = model.getSelected().get(i);
					model.getShapes().remove(s); //uklanjanje oblika iz liste
					model.getSelected().remove(s); //uklanjanje oblika iz liste selektovanih oblika - jer vise ne postoji
				}
				frame.repaint();
				} 
				observable.setNumSelectedShapes(model.getSelected().size());
		}
		moreShapes = false;
	
	}
	
	public void modify() {		
		if (model.getShapes().isEmpty())
			JOptionPane.showMessageDialog(null, "You need to add shape first!");
		else if (model.getSelected() == null) 
			JOptionPane.showMessageDialog(null, "You need to select a shape!");
		else
		{
			for (int i = 0; i < model.getShapes().size(); i++) { 		      
				if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof Circle && (model.getShapes().get(i) instanceof Donut == false))
				{
					DlgCircle dlg = new DlgCircle();
					Circle temp = (Circle) model.getShapes().get(i);		
					dlg.getTxtXc().setText(String.valueOf(temp.getCenter().getX()));
					dlg.getTxtYc().setText(String.valueOf(temp.getCenter().getY()));
					dlg.getTxtR().setText(String.valueOf(temp.getRadius()));
					dlg.getBtnColor().setBackground(temp.getColor());
					dlg.getBtnInnerColor().setBackground(temp.getInnerColor());
					dlg.setTitle("Modify circle");
					dlg.setVisible(true);
					if (dlg.isOk()) {
					Circle c = new Circle(new Point(Integer.parseInt(dlg.getTxtXc().getText().toString()), Integer.parseInt(dlg.getTxtYc().getText().toString())), 
							Integer.parseInt(dlg.getTxtR().getText().toString()), 
							false, dlg.getBtnColor().getBackground(), dlg.getBtnInnerColor().getBackground());
					UpdateCircleCmd ucc = new UpdateCircleCmd(temp, c);
					commands.add(ucc);
					log("Circle updated", temp, c);
					ucc.execute();
				}
			} else if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof Donut) {				
					DlgDonut dlg = new DlgDonut();
					Donut temp = (Donut) model.getShapes().get(i);		
					dlg.getTxtXc().setText(String.valueOf(temp.getCenter().getX()));
					dlg.getTxtYc().setText(String.valueOf(temp.getCenter().getY()));
					dlg.getTxtR().setText(String.valueOf(temp.getRadius()));
					dlg.getTxtIR().setText(String.valueOf(temp.getInnerRadius()));
					dlg.getBtnColor().setBackground(temp.getColor());
					dlg.getBtnInnerColor().setBackground(temp.getInnerColor());
					dlg.setTitle("Modify donut");
					dlg.setVisible(true);
					if (dlg.isOk()) {
					Donut d = new Donut(new Point(Integer.parseInt(dlg.getTxtXc().getText().toString()), Integer.parseInt(dlg.getTxtYc().getText().toString())), Integer.parseInt(dlg.getTxtR().getText().toString()), Integer.parseInt(dlg.getTxtIR().getText().toString()), false, dlg.getBtnColor().getBackground(), dlg.getBtnInnerColor().getBackground());
					//model.getShapes().add(d);
					UpdateDonutCmd udc = new UpdateDonutCmd(temp, d);
					commands.add(udc);
					log("Donut updated", temp, d);
					udc.execute();
				}
			} else if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof Rectangle)
			{
				DlgRect dlg = new DlgRect();			
				Rectangle temp = (Rectangle) model.getShapes().get(i);			
				dlg.getTxtY().setText(String.valueOf(temp.getUpperLeftPoint().getY()));
				dlg.getTxtX().setText(String.valueOf(temp.getUpperLeftPoint().getX()));
				dlg.getTxtWidth().setText(String.valueOf(temp.getWidth()));
				dlg.getTxtHeight().setText(String.valueOf(temp.getHeight()));
				dlg.getBtnColor().setBackground(temp.getColor());
				dlg.getBtnInnerColor().setBackground(temp.getInnerColor());
				dlg.setTitle("Modify rectangle");
				dlg.setVisible(true);
				if (dlg.isOk()) {
					Rectangle r = new Rectangle(new Point(Integer.parseInt(dlg.getTxtX().getText().toString()), Integer.parseInt(dlg.getTxtY().getText().toString())), Integer.parseInt(dlg.getTxtWidth().getText().toString()), Integer.parseInt(dlg.getTxtHeight().getText().toString()), false, dlg.getBtnColor().getBackground(), dlg.getBtnInnerColor().getBackground());
					//model.getShapes().add(r);
					UpdateRectangleCmd urc = new UpdateRectangleCmd(temp, r);
					commands.add(urc);
					log("Rectangle updated", temp, r);
					urc.execute();
				}
			} else if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof Point) {
				DlgPoint dlg = new DlgPoint();
				Point temp = (Point) model.getShapes().get(i);	
				//tacka cije vrednosti menjamo
				dlg.getTxtX().setText(String.valueOf(temp.getX()));
				dlg.getTxtY().setText(String.valueOf(temp.getY()));
				dlg.getBtnColor().setBackground(temp.getColor());
				dlg.setTitle("Modify point");
				dlg.setVisible(true);
				if (dlg.isOk()) {
						Point p3 = new Point(Integer.parseInt(dlg.getTxtX().getText().toString()), Integer.parseInt(dlg.getTxtY().getText().toString()), false, dlg.getBtnColor().getBackground());
						//nove vrednosti tacke koju menjamo
						//model.getShapes().add(p3);
						UpdatePointCmd upc = new UpdatePointCmd(temp, p3);
						log("Point updated", temp, p3);
						commands.add(upc);
						upc.execute();
						//execute se izvrsava da bi se zapamtile vrednosti originala
						/*umesto da brisemo stari oblik iz liste i dodajemo novi, 
						treba samo da promenimo stari oblik*/
				}
			} else if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof Line) {
				DlgLine dlg = new DlgLine();
				Line temp = (Line) model.getShapes().get(i);		
				dlg.getTxtX1().setText(String.valueOf(temp.getStartPoint().getX()));
				dlg.getTxtX2().setText(String.valueOf(temp.getEndPoint().getX()));
				dlg.getTxtY1().setText(String.valueOf(temp.getStartPoint().getY()));
				dlg.getTxtY2().setText(String.valueOf(temp.getEndPoint().getY()));
				dlg.getBtnColor().setBackground(temp.getColor());
				dlg.setTitle("Modify line");
				dlg.setVisible(true);
				if (dlg.isOk()) {
						Line l = new Line(new Point(Integer.parseInt(dlg.getTxtX1().getText().toString()), Integer.parseInt(dlg.getTxtY1().getText().toString())), new Point(Integer.parseInt(dlg.getTxtX2().getText().toString()), Integer.parseInt(dlg.getTxtY2().getText().toString())), false, dlg.getBtnColor().getBackground());
						UpdateLineCmd ulc = new UpdateLineCmd(temp, l);
						commands.add(ulc);
						log("Line updated", temp, l);
						ulc.execute();
						frame.repaint();
				}
			} else if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof HexagonAdapter) {
				DlgHexagon dlg = new DlgHexagon();
				HexagonAdapter temp = (HexagonAdapter) model.getShapes().get(i);		
				dlg.getTxtX().setText(String.valueOf(temp.getHexagon().getX()));
				dlg.getTxtY().setText(String.valueOf(temp.getHexagon().getY()));
				dlg.getTxtR().setText(String.valueOf(temp.getHexagon().getR()));
				dlg.getBtnColor().setBackground(temp.getHexagon().getBorderColor());
				dlg.getBtnInnerColor().setBackground(temp.getHexagon().getAreaColor());
				dlg.setTitle("Modify hexagon");
				dlg.setVisible(true);
				if (dlg.isOk()) {
					Color c = dlg.getBtnColor().getBackground();
					Color innerC = dlg.getBtnInnerColor().getBackground();
					HexagonAdapter hexAdapter = new HexagonAdapter(new Point(Integer.parseInt(dlg.getTxtX().getText().toString()), Integer.parseInt(dlg.getTxtY().getText().toString())),
							Integer.parseInt(dlg.getTxtR().getText().toString()), false, c, innerC);
					UpdateHexagonCmd uhc = new UpdateHexagonCmd(temp, hexAdapter);
					commands.add(uhc);
					log("Hexagon updated", temp, hexAdapter);
					uhc.execute();
				}
			}
			}
			frame.repaint();
		}
	}

	public void undo() {
		try {
			temp.add(commands.get(commands.size()-1));
			log("Undo command: " + commands.get(commands.size()-1), null);
			commands.remove(commands.size()-1).unexecute();
			//uklanjanje poslednje dodate komande u listi i undo
			frame.repaint();
			observable.setNumSelectedShapes(model.getSelected().size());
		} catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "No more commands");
			//ako nema nijedne komande
		}

	}

	public void redo() {
		try {
			commands.add(temp.get(temp.size()-1));
			log("Redo command: " + (temp.get(temp.size()-1)), null);
			temp.remove(temp.size()-1).execute();
			frame.repaint(); 
			observable.setNumSelectedShapes(model.getSelected().size());
		} catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "No more commands");
			//ako nema nijedne komande
		}
	}
	
	public void log(String command, Shape s) {
		if (!command.contains("Undo command"))
			frame.getBtnRedo().setEnabled(false);
		else 
			frame.getBtnRedo().setEnabled(true);
		if (s == null) {
			this.frame.getTextArea().append(command + "\n");
			//ispis loga u text area
			log.add(command);
		}
		else if (s instanceof SurfaceShape) {
			this.frame.getTextArea().append(command + ":" + s.toString() + "," + colorToHEX(s.getColor()) + "," + colorToHEX(((SurfaceShape) s).getInnerColor()) + "\n");
			log.add(command + ":" + s.toString() + "," + colorToHEX(s.getColor()) + "," + colorToHEX(((SurfaceShape) s).getInnerColor()));
		}
		else {
			this.frame.getTextArea().append(command + ":" + s.toString() + "," + colorToHEX(s.getColor()) + "\n");
			log.add(command + ":" + s.toString() + "," + colorToHEX(s.getColor()));
		}
		
	}
	
	//overloading - za update 
	public void log(String command, Shape s1, Shape s2) {
		if (s1 == null) {
			this.frame.getTextArea().append(command + "\n");
			//ispis loga u text area
			log.add(command);
		}
		else if (s1 instanceof SurfaceShape) {
			this.frame.getTextArea().append(command + ":" + s1.toString() + "," + colorToHEX(s1.getColor()) + "," + colorToHEX(((SurfaceShape) s1).getInnerColor()) +
			"->" + s2.toString() + "," + colorToHEX(s2.getColor()) + "," + colorToHEX(((SurfaceShape) s2).getInnerColor()) + "\n");
			log.add(command + ":" + s1.toString() + "," + colorToHEX(s1.getColor()) + "," + colorToHEX(((SurfaceShape) s1).getInnerColor()) +
					"->" + s2.toString() + "," + colorToHEX(s2.getColor()) + "," + colorToHEX(((SurfaceShape) s2).getInnerColor()));
		}
		else {
			this.frame.getTextArea().append(command + ":" + s1.toString() + "," + colorToHEX(s1.getColor()) +
					"->" + s2.toString() + "," + colorToHEX(s2.getColor()) + "\n");
			log.add(command + ":" + s1.toString() + "," + colorToHEX(s1.getColor()) + 
					"->" + s2.toString() + "," + colorToHEX(s2.getColor()));
		}
	}
	
	private String colorToHEX (Color c) {
		String red = Integer.toHexString(c.getRed());
		String green = Integer.toHexString(c.getGreen());
		String blue = Integer.toHexString(c.getBlue());

		if (red.length() == 1) red = "0" + red;
		if (green.length() == 1) green = "0" + green;
		if (blue.length() == 1) blue = "0" + blue;

		String hexColor = "#" + red + green + blue;
		return hexColor;
	}

	public void save() throws IOException {
		LogSave log = new LogSave(this.log); //prosledjivanje liste izvrsenih komandi 
		savingManager2.setFileSave(log); //setovanje objekta za koji se izvrsava metoda save
		savingManager2.save(); //cuvanje loga
		
		DrawingSave drawing = new DrawingSave(model.getShapes());
		savingManager1.setFileSave(drawing); //prosledjivanje liste iscrtanih oblika
		savingManager1.save(); //cuvanje crteza
	}

	public void open() throws ClassNotFoundException, IOException {
		JFileChooser j = new JFileChooser();
		int result = j.showOpenDialog(null); 
		
        // if the user selects a file
        if (result == JFileChooser.APPROVE_OPTION) 
        {
    		FileInputStream fileInputStream = new FileInputStream(j.getSelectedFile().getAbsolutePath());
    		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
    		log = (ArrayList<String>)objectInputStream.readObject(); //setovanje log liste na sadrzaj fajla koji iscitavamo
    		objectInputStream.close(); 
    		fileInputStream.close();
    		
    		this.frame.getTextArea().setText("");; 

    		frame.repaint();
    		model.getShapes().clear();
    		model.getSelected().clear();
    		commands.clear();
    		temp.clear();
    		counter = 0;
        }

	}

	public void loadDrawing() {
		if (counter == log.size())
			JOptionPane.showMessageDialog(null, "Drawing is done!"); //dodati alert za ovo
		else {
			String s = log.get(counter); //trenutna komanda
			this.frame.getTextArea().append(s + "\n"); //ispis trenutne komande u log
			if (s.equals("All shapes unselected")) {
				for (int i = 0; i < model.getSelected().size(); i++) {
					model.getSelected().get(i).setSelected(false);
				}
				List<Shape> list = Arrays.asList(new Shape[model.getSelected().size()]);
				Collections.copy(list, model.getSelected()); //kopiranje liste selektovanih oblika
				UnselectAllCmd un = new UnselectAllCmd(model, this, list); //prosledjujemo listu selektovanih
				commands.add(un);
				model.getSelected().clear();
				observable.setNumSelectedShapes(model.getSelected().size());
				frame.repaint();
			} else if (s.equals("Selected shapes deleted")) {
				delete(0);
			} else {
			String[] arrayOfStr1 = s.split(":"); //splitovanje cele komande na dva dela
			String[] arrayOfStr2 = arrayOfStr1[0].split(" "); //splitovanje prvog dela komande po razmaku
			String[] arrayOfStr3 = arrayOfStr1[1].split(","); //splitovanje drugog dela komande po zarezima
			//ako se radi o dodavanju oblika
			if (arrayOfStr2[1].equals("added")) {
				Shape sh = null;
				Command cmd = null;
				if (arrayOfStr2[0].equals("Point")) {
					//u pitanju je tacka
					sh = new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1]), false, Color.decode(arrayOfStr3[2]));
					cmd = new AddPointCmd((Point) sh, model);
				} else if (arrayOfStr2[0].equals("Line")) {
					String[] arrayOfStr4 = arrayOfStr3[1].split("-");
					sh = new Line(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr4[0])), 
							new Point(Integer.parseInt(arrayOfStr4[1]), Integer.parseInt(arrayOfStr3[2])), false, Color.decode(arrayOfStr3[3]));
					cmd = new AddLineCmd((Line) sh, model);
				} else if (arrayOfStr2[0].equals("Rectangle")) {
					sh = new Rectangle(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), Integer.parseInt(arrayOfStr3[3]), false, 
							Color.decode(arrayOfStr3[4]), Color.decode(arrayOfStr3[5]));
					cmd = new AddRectangleCmd((Rectangle) sh, model);
				} else if (arrayOfStr2[0].equals("Circle")) {
					sh = new Circle(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), false, Color.decode(arrayOfStr3[3]), Color.decode(arrayOfStr3[4]));
					cmd = new AddCircleCmd((Circle) sh, model);
				} else if (arrayOfStr2[0].equals("Donut")) {
					sh = new Donut(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), Integer.parseInt(arrayOfStr3[3]), false, 
							Color.decode(arrayOfStr3[4]), Color.decode(arrayOfStr3[5]));
					cmd = new AddDonutCmd((Donut) sh, model);
				} else if (arrayOfStr2[0].equals("Hexagon")) {
					sh = new HexagonAdapter(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])),
							Integer.parseInt(arrayOfStr3[2]), false, Color.decode(arrayOfStr3[3]), Color.decode(arrayOfStr3[4]));
					cmd = new AddHexagonCmd((HexagonAdapter) sh, model);
				}
				model.add(sh);
				commands.add(cmd); //dodavanje komande u listu
				frame.repaint();
			} else if (arrayOfStr2[1].equals("selected")) {
				Shape sh = null;
				if (arrayOfStr2[0].equals("Point")) {
					sh = findShape(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1]), true, Color.decode(arrayOfStr3[2])));
				} else if (arrayOfStr2[0].equals("Line")) {
					String[] arrayOfStr4 = arrayOfStr3[1].split("-");
					sh = findShape(new Line(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr4[0])), 
							new Point(Integer.parseInt(arrayOfStr4[1]), Integer.parseInt(arrayOfStr3[2])), true, Color.decode(arrayOfStr3[3])));
				} else if (arrayOfStr2[0].equals("Rectangle")) {
					sh = findShape(new Rectangle(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), Integer.parseInt(arrayOfStr3[3]), true, 
							Color.decode(arrayOfStr3[4]), Color.decode(arrayOfStr3[5])));
				} else if (arrayOfStr2[0].equals("Circle")) {
					sh = findShape(new Circle(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), true, Color.decode(arrayOfStr3[3]), Color.decode(arrayOfStr3[4])));
				} else if (arrayOfStr2[0].equals("Donut")) {
					sh = findShape(new Donut(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), Integer.parseInt(arrayOfStr3[3]), true, 
							Color.decode(arrayOfStr3[4]), Color.decode(arrayOfStr3[5])));
				} else if (arrayOfStr2[0].equals("Hexagon")) {
					sh = findShape(new HexagonAdapter(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])),
							Integer.parseInt(arrayOfStr3[2]), false, Color.decode(arrayOfStr3[3]), Color.decode(arrayOfStr3[4])));

				}
				sh.setSelected(true);
				frame.repaint();
				model.getSelected().add(sh);
				SelectShapeCmd cmd = new SelectShapeCmd(model, frame, sh);
				commands.add(cmd);
				observable.setNumSelectedShapes(model.getSelected().size());
			} else if (arrayOfStr2[1].equals("deleted")) {		
				Shape sh = null;
				Command cmd = null;
				if (arrayOfStr2[0].equals("Point")) {
					sh = findShape(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1]), true, Color.decode(arrayOfStr3[2])));
					cmd = new RemovePointCmd((Point) sh, model);
				} else if (arrayOfStr2[0].equals("Line")) {
					String[] arrayOfStr4 = arrayOfStr3[1].split("-");
					sh = findShape(new Line(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr4[0])), 
							new Point(Integer.parseInt(arrayOfStr4[1]), Integer.parseInt(arrayOfStr3[2])), true, Color.decode(arrayOfStr3[3])));
					cmd = new RemoveLineCmd((Line) sh, model);
				} else if (arrayOfStr2[0].equals("Rectangle")) {
					sh = findShape(new Rectangle(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), Integer.parseInt(arrayOfStr3[3]), true, 
							Color.decode(arrayOfStr3[4]), Color.decode(arrayOfStr3[5])));
					cmd = new RemoveRectangleCmd((Rectangle) sh, model);
				} else if (arrayOfStr2[0].equals("Circle")) {
					sh = findShape(new Circle(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), true, Color.decode(arrayOfStr3[3]), Color.decode(arrayOfStr3[4])));
					cmd = new RemoveCircleCmd((Circle) sh, model);
				} else if (arrayOfStr2[0].equals("Donut")) {
					sh = findShape(new Donut(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), Integer.parseInt(arrayOfStr3[3]), true, 
							Color.decode(arrayOfStr3[4]), Color.decode(arrayOfStr3[5])));
					cmd = new RemoveDonutCmd((Donut) sh, model);
				} else if (arrayOfStr2[0].equals("Hexagon")) {
					sh = findShape(new HexagonAdapter(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])),
							Integer.parseInt(arrayOfStr3[2]), false, Color.decode(arrayOfStr3[3]), Color.decode(arrayOfStr3[4])));
					cmd = new RemoveHexagonCmd((HexagonAdapter) sh, model);
				}
				model.getShapes().remove(sh); //uklanjanje oblika iz liste
				model.getSelected().remove(sh);
				commands.add(cmd);
				frame.repaint();
			} else if (arrayOfStr2[1].equals("updated")) {
				Shape sh = null;
				Command cmd = null;
				if (arrayOfStr2[0].equals("Point")) {
					String[] arrayOfStr5 = arrayOfStr3[2].split("->");
					//tacka koju menjamo
					sh = findShape(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1]), true, Color.decode(arrayOfStr5[0])));
					//nova tacka
					Point p2 = new Point(Integer.parseInt(arrayOfStr5[1]), Integer.parseInt(arrayOfStr3[3]), true, Color.decode(arrayOfStr3[4]));
					cmd = new UpdatePointCmd((Point) sh, p2);
				} else if (arrayOfStr2[0].equals("Line")) {
					String[] arrayOfStr5 = arrayOfStr3[3].split("->");
					String[] arrayOfStr4 = arrayOfStr3[1].split("-");
					String[] arrayOfStr6 = arrayOfStr3[4].split("-");
					sh = findShape(new Line(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr4[0])), 
							new Point(Integer.parseInt(arrayOfStr4[1]), Integer.parseInt(arrayOfStr3[2])), true, Color.decode(arrayOfStr5[0])));
					Line l2 = new Line(new Point(Integer.parseInt(arrayOfStr5[1]), Integer.parseInt(arrayOfStr6[0])), 
							new Point(Integer.parseInt(arrayOfStr6[1]), Integer.parseInt(arrayOfStr3[5])), true, Color.decode(arrayOfStr3[6]));
					cmd = new UpdateLineCmd((Line) sh, l2);
				} else if (arrayOfStr2[0].equals("Rectangle")) {
					String[] arrayOfStr5 = arrayOfStr3[5].split("->");
					sh = findShape(new Rectangle(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), Integer.parseInt(arrayOfStr3[3]), true, 
							Color.decode(arrayOfStr3[4]), Color.decode(arrayOfStr5[0])));
					Rectangle r2 = new Rectangle(new Point(Integer.parseInt(arrayOfStr5[1]), Integer.parseInt(arrayOfStr3[6])), 
							Integer.parseInt(arrayOfStr3[7]), Integer.parseInt(arrayOfStr3[8]), true, 
							Color.decode(arrayOfStr3[9]), Color.decode(arrayOfStr3[10]));
					cmd = new UpdateRectangleCmd((Rectangle) sh, r2);
				} else if (arrayOfStr2[0].equals("Circle")) {
					String[] arrayOfStr5 = arrayOfStr3[4].split("->");
					sh = findShape(new Circle(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), true, Color.decode(arrayOfStr3[3]), Color.decode(arrayOfStr5[0])));
					Circle c2 = new Circle(new Point(Integer.parseInt(arrayOfStr5[1]), Integer.parseInt(arrayOfStr3[5])), 
							Integer.parseInt(arrayOfStr3[6]), true, Color.decode(arrayOfStr3[7]), Color.decode(arrayOfStr3[8]));
					cmd = new UpdateCircleCmd((Circle) sh, c2);
				} else if (arrayOfStr2[0].equals("Donut")) {
					String[] arrayOfStr5 = arrayOfStr3[5].split("->");
					sh = findShape(new Donut(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])), 
							Integer.parseInt(arrayOfStr3[2]), Integer.parseInt(arrayOfStr3[3]), true, 
							Color.decode(arrayOfStr3[4]), Color.decode(arrayOfStr5[0])));
					Donut d2 = new Donut(new Point(Integer.parseInt(arrayOfStr5[1]), Integer.parseInt(arrayOfStr3[6])), 
							Integer.parseInt(arrayOfStr3[7]), Integer.parseInt(arrayOfStr3[8]), true, 
							Color.decode(arrayOfStr3[9]), Color.decode(arrayOfStr3[10]));
					cmd = new UpdateDonutCmd((Donut) sh, d2);
				} else if (arrayOfStr2[0].equals("Hexagon")) {
					String[] arrayOfStr5 = arrayOfStr3[4].split("->");
					sh = findShape(new HexagonAdapter(new Point(Integer.parseInt(arrayOfStr3[0]), Integer.parseInt(arrayOfStr3[1])),
							Integer.parseInt(arrayOfStr3[2]), true, Color.decode(arrayOfStr3[3]), Color.decode(arrayOfStr5[0])));
					HexagonAdapter hex2 = new HexagonAdapter(new Point(Integer.parseInt(arrayOfStr5[1]), Integer.parseInt(arrayOfStr3[5])),
							Integer.parseInt(arrayOfStr3[6]), true, Color.decode(arrayOfStr3[7]), Color.decode(arrayOfStr3[8]));
					cmd = new UpdateHexagonCmd((HexagonAdapter) sh, hex2);
				}
				sh.setSelected(true);
				commands.add(cmd);
				cmd.execute();
				frame.repaint();
			} else if (arrayOfStr2[0].equals("Undo")) {
				try {
					temp.add(commands.get(commands.size()-1));
					commands.remove(commands.size()-1).unexecute();
					//uklanjanje poslednje dodate komande u listi i undo
					frame.repaint();
				} catch (IndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "No more commands");
					//ako nema nijedne komande
					//dodaj alert da nema sta da se ponisti
				}
			} else if (arrayOfStr2[0].equals("Redo")) {
				try {
					commands.add(temp.get(temp.size()-1));
					temp.remove(temp.size()-1).execute();
					frame.repaint();
				} catch (IndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "No more commands");
					//ako nema nijedne komande
				}
			} else if (arrayOfStr2[0].equals("Bring") && arrayOfStr2[1].equals("to") && arrayOfStr2[2].equals("front")) {
				bringToFront(2);
			} else if (arrayOfStr2[0].equals("Bring") && arrayOfStr2[1].equals("to") && arrayOfStr2[2].equals("back")) {
				bringToBack(2);
			} else if (arrayOfStr2[0].equals("To") && arrayOfStr2[1].equals("back")) {
				toBack(2);
			} else if (arrayOfStr2[0].equals("To") && arrayOfStr2[1].equals("front")) {
				toFront(2);
			} 
			} 
			counter++;
		}
	}
	
	private Shape findShape(Shape s) {
		for (int i = 0; i < model.getShapes().size(); i++) 
			if (model.getShapes().get(i).compareTo(s) == 0) {
				return model.getShapes().get(i);
			}
		return null;
	}

	public void toFront(int c) {
		Shape selected = model.getSelected().get(0); //jedini selektovani oblik
		if (selected.equals(model.getShapes().get(model.getShapes().size()-1))) {
			JOptionPane.showMessageDialog(null, "Selected shape is already in the front");
		}
		for (int i = 0; i < model.getShapes().size()-1; i++) {
			 if (model.getShapes().get(i).compareTo(selected) == 0) {
				selected.setSelected(true);
				model.getShapes().set(i, model.getShapes().get(++i));
				model.getShapes().set(i, selected); //na poslednje mesto u listi oblika postavljamo selektovani
			}
		}
		if (c == 0) {
			log("To front", selected);
			ToFrontCmd tf = new ToFrontCmd(this);
			commands.add(tf);
		}
		if (c == 2) {
			ToFrontCmd tf = new ToFrontCmd(this);
			commands.add(tf);
		}
		frame.repaint();
	}

	public void toBack(int c) {
		Shape selected = model.getSelected().get(0); //jedini selektovani oblik
		for (int i = 0; i < model.getShapes().size(); i++) {
			if (selected.equals(model.getShapes().get(0))) {
				JOptionPane.showMessageDialog(null, "Selected shape is already in the back");
				break;
			} else if (model.getShapes().get(i).compareTo(selected) == 0) { 
				selected.setSelected(true);
				model.getShapes().set(i, model.getShapes().get(i-1));
				model.getShapes().set(--i, selected); 
				i++;
				break;
			}
		}
		if (c == 0) {
			log("To back", selected);
			ToBackCmd tb = new ToBackCmd(this);
			commands.add(tb);
		}
		if (c == 2) {
			ToBackCmd tb = new ToBackCmd(this);
			commands.add(tb);
		}
		frame.repaint();
	}

	public void bringToFront(int c) {
		Shape selected = model.getSelected().get(0); //jedini selektovani oblik
		int ind = model.getShapes().indexOf(selected);
		for (int i = 0; i < model.getShapes().size(); i++) {
			if (selected.equals(model.getShapes().get(model.getShapes().size()-1))) {
				JOptionPane.showMessageDialog(null, "Selected shape is already in the front");
				break;
			} else if (model.getShapes().get(i).compareTo(selected) == 0) {
				selected.setSelected(true);
				for (int j = i; j < model.getShapes().size()-1; j++) {
					model.getShapes().set(j, model.getShapes().get(++j));
					j--;
				}
				model.getShapes().set(model.getShapes().size()-1, selected); //na poslednje mesto u listi oblika postavljamo selektovani
				break;
			}  
		}
		if (c == 0) {
			log("Bring to front", selected);
			BringToFrontCmd bf = new BringToFrontCmd(this, ind, model.getShapes().size());
			commands.add(bf);
		}
		if (c == 2) {
			BringToFrontCmd bf = new BringToFrontCmd(this, ind, model.getShapes().size());
			commands.add(bf);
		}
		frame.repaint();
	}

	public void bringToBack(int c) {
		Shape selected = model.getSelected().get(0); //jedini selektovani oblik
		int ind = model.getShapes().indexOf(selected);
		for (int i = 0; i < model.getShapes().size(); i++) {
			if (selected.equals(model.getShapes().get(0))) {
				System.out.println(model.getShapes().indexOf(selected));
				JOptionPane.showMessageDialog(null, "Selected shape is already in the back");
				break;
			} else if (model.getShapes().get(i).compareTo(selected) == 0) { //prolazimo kroz listu oblika dok ne nadjemo selektovani
				selected.setSelected(true);
				for (int j = i; j > 0; j--) {
					model.getShapes().set(j, model.getShapes().get(--j)); //pomeranje svakog oblika u listi za jedno mesto unapred
					j++;
				}
				model.getShapes().set(0, selected); //na prvo mesto u listi oblika postavljamo selektovani
				break;
			}
		}
		if (c == 0) {
			log("Bring to back", selected);
			BringToBackCmd bb = new BringToBackCmd(this, ind);
			commands.add(bb);	
		}
		//ako se poziva metoda iz loga
		if (c == 2) {
			BringToBackCmd bb = new BringToBackCmd(this, ind);
			commands.add(bb);	
		}
		frame.repaint();
	}

	public Subject getObservable() {
		return observable;
	}
	
}
