package drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import geometry.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PnlDrawing extends JPanel implements MouseListener{
	
	int check;
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	Shape selected;
	int x;
	int y;
	int x2;
	int y2;
	Point startPoint;
	/**
	 * Create the panel.
	 */
	public PnlDrawing() {	
		setLayout(new BorderLayout(0, 0));	
		addMouseListener(this);	
	} 
	
	public void check(int c) {
		check = c;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
			x = e.getX();
			y = e.getY();			
			if (check == 6) 
				this.selection(getGraphics());
			else if (check == 1 || check == 3 || check == 4 || check == 5 || check == 2) 
				this.paint(getGraphics());					
	}
	
	public void delete() {	
			if (shapes.isEmpty())
				JOptionPane.showMessageDialog(null, "You need to add shape first!");
			else if (selected == null)
				JOptionPane.showMessageDialog(null, "You need to select a shape!");
			else
			{
				int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure?",
						"Delete shape", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (confirmation == 0) {
					for (int i = 0; i < shapes.size(); i++) { 		      
						if (shapes.get(i).isSelected()) {									
							shapes.remove(shapes.get(i));
							selected = null;
						}	
				      }
					getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
					for (int j = 0; j < shapes.size(); j++)
						shapes.get(j).draw(getGraphics());
						}
			}
		
	}
	public void deleteAll() {
		if (shapes.isEmpty())
			JOptionPane.showMessageDialog(null, "There is nothing to delete!");
		else
		{
			int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure?",
					"Delete shape", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (confirmation == 0) {
				getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
				selected = null;
				shapes.clear();
		}
	}
	}
	
	public void modify() {		
		if (shapes.isEmpty())
			JOptionPane.showMessageDialog(null, "You need to add shape first!");
		else if (selected == null)
			JOptionPane.showMessageDialog(null, "You need to select a shape!");
		else
		{
			for (int i = 0; i < shapes.size(); i++) { 		      
				if (shapes.get(i).isSelected() && shapes.get(i) instanceof Circle && (shapes.get(i) instanceof Donut == false))
				{
					DlgCircle dlg = new DlgCircle();
					Circle temp = (Circle) shapes.get(i);		
					dlg.getTxtXc().setText(String.valueOf(temp.getCenter().getX()));
					dlg.getTxtYc().setText(String.valueOf(temp.getCenter().getY()));
					dlg.getTxtR().setText(String.valueOf(temp.getRadius()));
					dlg.getBtnColor().setBackground(temp.getColor());
					dlg.getBtnInnerColor().setBackground(temp.getInnerColor());
					dlg.setTitle("Modify circle");
					dlg.setVisible(true);
					if (dlg.isOk()) {
					Circle c = new Circle(new Point(Integer.parseInt(dlg.getTxtXc().getText().toString()), Integer.parseInt(dlg.getTxtYc().getText().toString())), Integer.parseInt(dlg.getTxtR().getText().toString()), false, dlg.getBtnColor().getBackground(), dlg.getBtnInnerColor().getBackground());
					shapes.add(c);
					shapes.remove(shapes.get(i));
					selected = null;
				}
			} else if (shapes.get(i).isSelected() && shapes.get(i) instanceof Donut) {				
					DlgDonut dlg = new DlgDonut();
					Donut temp = (Donut) shapes.get(i);		
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
					shapes.add(d);
					shapes.remove(shapes.get(i));
					selected = null;
				}
			
			
			} else if (shapes.get(i).isSelected() && shapes.get(i) instanceof Rectangle)
			{
				DlgRect dlg = new DlgRect();			
				Rectangle temp = (Rectangle) shapes.get(i);			
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
					shapes.add(r);
					shapes.remove(shapes.get(i));
					selected = null;
				}
			} else if (shapes.get(i).isSelected() && shapes.get(i) instanceof Point) {
				DlgPoint dlg = new DlgPoint();
				Point temp = (Point) shapes.get(i);		
				dlg.getTxtX().setText(String.valueOf(temp.getX()));
				dlg.getTxtY().setText(String.valueOf(temp.getY()));
				dlg.getBtnColor().setBackground(temp.getColor());
				dlg.setTitle("Modify point");
				dlg.setVisible(true);
				if (dlg.isOk()) {
						Point p3 = new Point(Integer.parseInt(dlg.getTxtX().getText().toString()), Integer.parseInt(dlg.getTxtY().getText().toString()), false, dlg.getBtnColor().getBackground());
						shapes.add(p3);
						shapes.remove(shapes.get(i));		
						selected = null;
				}
			} else if (shapes.get(i).isSelected() && shapes.get(i) instanceof Line) {
				DlgLine dlg = new DlgLine();
				Line temp = (Line) shapes.get(i);		
				dlg.getTxtX1().setText(String.valueOf(temp.getStartPoint().getX()));
				dlg.getTxtX2().setText(String.valueOf(temp.getEndPoint().getX()));
				dlg.getTxtY1().setText(String.valueOf(temp.getStartPoint().getY()));
				dlg.getTxtY2().setText(String.valueOf(temp.getEndPoint().getY()));
				dlg.getBtnColor().setBackground(temp.getColor());
				dlg.setTitle("Modify line");
				dlg.setVisible(true);
				if (dlg.isOk()) {
						Line l = new Line(new Point(Integer.parseInt(dlg.getTxtX1().getText().toString()), Integer.parseInt(dlg.getTxtY1().getText().toString())), new Point(Integer.parseInt(dlg.getTxtX2().getText().toString()), Integer.parseInt(dlg.getTxtY2().getText().toString())), false, dlg.getBtnColor().getBackground());
						shapes.add(l);
						shapes.remove(shapes.get(i));
						selected = null;
				}
			}
			}
			getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
			for (int j = 0; j < shapes.size(); j++)
				shapes.get(j).draw(getGraphics());	
		}
	}
	public void selection(Graphics g) {
		for (int i = 0; i < shapes.size(); i++) { 		      
			if (shapes.get(i).contains(x, y)) {		
				selected = shapes.get(i);
				getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
				for (int j = 0; j < shapes.size(); j++) {
					shapes.get(j).setSelected(false);
					shapes.get(j).draw(getGraphics());
				}
				selected.setSelected(true);
				selected.draw(getGraphics());
			} 
		}
			}
	
	public void paint(Graphics g) {		
			if (check == 1)
			{
				Point p = new Point(x, y);
				p.draw(getGraphics());
				p.setColor(Color.BLACK);
				shapes.add(p);
			} else if (check == 2) {
				if (startPoint == null)
				{
					startPoint = new Point(x, y);
				}
				else
				{
					Point eP = new Point(x, y);
					Line l = new Line(startPoint, eP);
					l.draw(getGraphics());
					l.setColor(Color.BLACK);
					shapes.add(l);
					startPoint = null;
				}

			} else if (check == 3) {
				DlgRect dlgR = new DlgRect();			
				dlgR.getTxtX().setText(String.valueOf(x));
				dlgR.getTxtY().setText(String.valueOf(y));
				dlgR.getTxtX().setEditable(false);
				dlgR.getTxtY().setEditable(false);
				dlgR.setVisible(true);
				if (dlgR.isOk()) {
					try {
						String width = dlgR.getTxtWidth().getText().toString();
						int intWidth = Integer.parseInt(width);
						String height = dlgR.getTxtHeight().getText().toString();
						int intHeight = Integer.parseInt(height);	
						Color c = dlgR.getColor();
						Color innerC = dlgR.getInnerColor();
						Rectangle r = new Rectangle(new Point(x, y), intWidth, intHeight, false, c, innerC);
						r.draw(getGraphics());
						shapes.add(r);
					}
					catch (Exception e)
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
				dlgC.setVisible(true);
				
				if (dlgC.isOk()) {
					String radius = dlgC.getTxtR().getText().toString();
					int intRadius = Integer.parseInt(radius);	
					Color c = dlgC.getColor();
					Color innerC = dlgC.getInnerColor();
					Circle circle = new Circle(new Point(x, y), intRadius, false, c, innerC);
					circle.draw(getGraphics());
					shapes.add(circle);
				}
			} else if (check == 5) {
				DlgDonut dlgD = new DlgDonut();
				dlgD.getTxtXc().setText(String.valueOf(x));
				dlgD.getTxtYc().setText(String.valueOf(y));
				dlgD.getTxtXc().setEditable(false);
				dlgD.getTxtYc().setEditable(false);
				dlgD.setVisible(true);
				
				if (dlgD.isOk()) {
					Color c = dlgD.getColor();
					Color innerC = dlgD.getInnerColor();
					Donut d = new Donut(new Point(x, y), Integer.parseInt(dlgD.getTxtR().getText().toString()), Integer.parseInt(dlgD.getTxtIR().getText().toString()), false, c, innerC);
					d.draw(getGraphics());
					shapes.add(d);
				}
			}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

}
