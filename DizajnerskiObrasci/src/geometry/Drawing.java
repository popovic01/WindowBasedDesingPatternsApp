package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
//javax = java with an extension tj. prosirena verzija osnovnog java paketa
import javax.swing.UIManager;

public class Drawing extends JPanel{

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Drawing"); //title frame-a
		frame.setSize(800, 600); //velicina frame-a
		frame.setBackground(Color.WHITE);
		Drawing drawing = new Drawing();
		frame.getContentPane().add(drawing);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void paint(Graphics g) {
		
		Point p = new Point(50, 50, true, Color.BLUE);
		//p.draw(g);
		
		Line l = new Line(new Point(150, 50), new Point(150, 140), true, Color.DARK_GRAY);
		//l.draw(g);
		
		Circle c = new Circle(new Point(320, 240), 50, true, Color.GRAY, Color.YELLOW);
		//c.draw(g);
		
		Rectangle r = new Rectangle(new Point(620, 220), 60, 120, true, Color.BLUE, Color.MAGENTA);
		//r.draw(g);
		
		Donut d = new Donut(new Point(400, 430), 70, 30, true, Color.BLACK, Color.GREEN);
		//d.draw(g);
		
		ArrayList<Shape> shapes = new ArrayList<Shape>(); //arraylist tipa shape
		shapes.add(p); //redom dodajemo elemente
		shapes.add(l); //indeksiranje pocinje od 0
		shapes.add(c);
		shapes.add(r);
		shapes.add(d);
		
		Iterator<Shape> it = shapes.iterator(); //kreiranje iteratora tipa shape
		//iterator prolazi kroz arraylist shapes
		//it.hasNext() == true - dok element ima sledeceg tj. dok se ne prodje kroz celu listu
		while(it.hasNext()) {
			System.out.println("Selected: " + it.next().isSelected());
			//it.next() - pristupanje trenutnom elementu u listi
		}
		
		// iscrtati treci element iz liste
		shapes.get(2).draw(g);
		// iscrtati poslednji element iz liste
		shapes.get(shapes.size() - 1).draw(g);
		// iscrtati element sa indeksom 3 iz liste
		shapes.get(3).draw(g);
		
		Line l1 = new Line(new Point(200, 100), new Point(200, 190), true, Color.BLACK);
		shapes.add(3, l1); //dodajemo l1 u listu na 4. poziciju tj. indeks = 3
		shapes.get(3).draw(g); //iscrtavanje te linije
		
		shapes.remove(1); //uklanje 2. elementa iz liste
		
		//foreach
		for(Shape s: shapes) {
			s.draw(g);
		}
		
		//iscrtavanje povrsinskih oblika
		for(Shape s: shapes) {
			if(s instanceof SurfaceShape)
			s.draw(g);
		}
		
	}

}
