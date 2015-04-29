import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Grafiek 
{

	private ArrayList<Integer> yPunten = new ArrayList<Integer>();

	Grafiek()
	{
		
	}
	
	public void addPoints(int x)
	{
		yPunten.add(x);
	}
	
	public Point2D.Double returnPoint(int x)
	{
		return new Point2D.Double(x, yPunten.get(x));
	}
	
	public void paint(Graphics2D graphics, boolean pa)
	{
		
		for (Integer punt: yPunten)
		{
			graphics.drawLine(, y1, x2, y2);
		}
		
	}
	
	
	
}
