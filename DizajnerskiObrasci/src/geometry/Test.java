package geometry;

import java.util.Arrays;
import java.util.HashMap;

public class Test {

	public static void main(String[] args) {
		
		Point p = new Point();
		p.setX(10);
		p.setY(20);
		p.setSelected(true);
		
		System.out.println(p.getX() + " " + p.getY() + " " + p.isSelected());

		double result = p.distance(40, 50);
		System.out.println("Distance between points is: " + result);
		
		Point p1 = new Point();
		p1.setX(15);
		p1.setY(27);
		p1.setSelected(true);
		
		Rectangle r1 = new Rectangle();
		Line l1 = new Line();
		Circle c1 = new Circle();
		
		p.setX(p1.getY());
		System.out.println("X of p = " + p.getX());
		l1.setStartPoint(p);
		l1.setEndPoint(p1);
		l1.getEndPoint().setY(23);
		System.out.println("Y of end point of l1 = " + l1.getEndPoint().getY());
		l1.getStartPoint().setX(l1.getEndPoint().getY());
		System.out.println("X of start point of l1 = " + l1.getStartPoint().getX());
		l1.getEndPoint().setX((int) (l1.lenght() - (l1.getStartPoint().getX() + l1.getStartPoint().getY())));
		System.out.println("X of end point of l1" + l1.getEndPoint().getX());
		r1.setUpperLeftPoint(p); //moramo prvo odrediti upl da bi joj pristupili
		r1.getUpperLeftPoint().setX(10);
		r1.getUpperLeftPoint().setY(15);
		System.out.println("X of ulp of r1 = " + r1.getUpperLeftPoint().getX());
		System.out.println("Y of ulp of r1 = " + r1.getUpperLeftPoint().getY());
		c1.setCenter(r1.getUpperLeftPoint());
		r1.setHeight(20); //postavljamo visinu i sirinu da povrsina ne bi bila 0, kao po defaultu
		r1.setWidth(30);
		c1.getCenter().setX(r1.area() - l1.getStartPoint().getY());
		System.out.println("X of center of c1 = " + c1.getCenter().getX());
		
		Point p2 = new Point(50, 100);
		//kreiranjem konstruktora koji prima vise parametara mozemo ovako kreirati objekte
		//na taj nacin pisemo kod u manje redova
		Line l2 = new Line(p2, new Point(400, 500));
		Rectangle r2 = new Rectangle(p1, 50, 80);
		Circle c2 = new Circle(new Point(300, 300), 60);
		
		System.out.println(p2.toString());
		//ne moramo pisati .toString()
		System.out.println(l2);
		System.out.println(c2);
		System.out.println(r2);
		//object je nadklasa svih objekata
		
		int a = 5;
		int b = 5;

		System.out.println(a == b);
		//poredi po vrednosti
		// == se koristi za primitivne tipove za poredjenje po vrednosti

		String s1 = new String("Hello World");
		String s2 = new String("Hello World");

		System.out.println(s1 == s2); // poredjenje po referenci
		//false jer pokazuje na razlicite objekte u memoriji
		System.out.println(c2 == c1);
		System.out.println(s1.equals(s2)); // poredjenje po vrednosti
		//equals se koristi za poredjenje po vrednosti kod slozenih tipova podataka

		System.out.println(p2 instanceof Point); //true
		//p2 je instanca klase Point
		System.out.println(p2 instanceof Object); //true
		//p2 je instanca klase Object jer je ona nadklasa svih klasa

		System.out.println(p2.equals(p1));
		//jer nisu iste tacke tj. nemaju iste koordinate
		System.out.println(p2.equals(c2));
		//jer nisu istog tipa podatka tj. jer c2 nije instanca Point-a
		
		Donut d = new Donut();
		System.out.println(d instanceof Donut); //true
		System.out.println(d instanceof Circle); //true
		System.out.println(d instanceof SurfaceShape); //true
		System.out.println(d instanceof Shape); //true
		System.out.println(d instanceof Object); //true
		
		Circle d1 = new Donut(); //dinamicko povezivanje
		
		Point p4 = new Point(10, 10);
		Point p5 = new Point(5, 5);
		Point p6 = new Point(2, 2);
		Point p7 = new Point(20, 20);
		
		Point[] points = {p4, p5, p6, p7};
		
		System.out.println("Nesortiran niz tacaka: ");
		for (int i = 0; i < points.length; i++)
			System.out.println(points[i]);
		
		//sortiranje niza
		Arrays.sort(points);
		System.out.println("Sortiran niz tacaka: ");
		for (int i = 0; i < points.length; i++)
			System.out.println(points[i]);
		
		//HashMap - prima vise parametara koji mogu biti razlicitog tipa
		HashMap<String, Shape> map = new HashMap<String, Shape>();
		map.put("point", p1);
		map.put("rectangle", r1);
		map.put("Point", p2);
		
		System.out.println("point from map is: " + map.get("point"));
		System.out.println("point from map is: " + map.get("Point"));
		
		Point p3 = new Point(40, 40);
		map.put("point", p3);
		System.out.println("point from map is: " + map.get("point"));
		
		Circle c4 = new Circle(p6, 40);
		try {
			c4.setRadius(-150);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Finally se uvek izvrsava");
		}
		
	}

}
