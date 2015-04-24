import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;


public class WiimotePanel extends JPanel implements WiimoteListener
{
	
	
	//* List of Wiimotes
	private List<Wiimote> motes = new ArrayList<Wiimote>(WiiUseApiManager.getNbConnectedWiimotes());
	
	private Line2D.Double 	lijnHorizontaal = new Line2D.Double(-150, 0, 150, 0),
							lijnVerticaal	= new Line2D.Double(0, 150, 0, -150);
	
	//* Dit balletje gaat meebewegen met de Wiimote bewegingen
	private Ellipse2D.Double motePosition = new Ellipse2D.Double(0, 0, 20, 20);
	
	
	WiimotePanel()
	{
		
	}
	
	private void initComponents()
	{
		
		
		
		
	}
	
	private void defineMotes()
	{
		for (int x = 0; x < motes.size(); x++)
		{
			motes.get(x).addWiiMoteEventListeners(this);
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.translate(getWidth() /2, getHeight()/2);
		g2.scale(1, -1);
		
		g2.setColor(Color.RED);
		g2.draw(lijnHorizontaal);
		g2.draw(lijnVerticaal);
		
		g2.setColor(Color.GREEN);
		g2.draw(motePosition);
				
		g2.drawString("X: "+ (int)(motePosition.getX() + motePosition.width) + " Y:" + (int)(motePosition.getY() + motePosition.height), 
				(int)(motePosition.getX() + motePosition.width), (int)(motePosition.getY() + motePosition.height));
		
		
		
		
	}
	
	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		System.out.println("Knop ingedrukt");
		
	}
	@Override
	public void onClassicControllerInsertedEvent(
			ClassicControllerInsertedEvent arg0) {
		System.out.println("Classico Controllero Insertototo");
		
	}
	@Override
	public void onClassicControllerRemovedEvent(
			ClassicControllerRemovedEvent arg0) {
		System.out.println("Classico Controllero Removedododo");
		
	}
	@Override
	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		System.out.println("Mote verwijderd");
		
	}
	@Override
	public void onExpansionEvent(ExpansionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIrEvent(IREvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusEvent(StatusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	//* Mochten we ooit guitar hero willen spelen
	
	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {}
	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) 	{}
	
}
