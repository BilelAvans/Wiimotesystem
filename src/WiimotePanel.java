import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.JoystickEvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.NunchukButtonsEvent;
import wiiusej.wiiusejevents.physicalevents.NunchukEvent;
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
	private Wiimote[] motes;
	private Wiimote   mote;
	
	private boolean staatAan = true;

	private Line2D.Double 	lijnHorizontaal,
							lijnVerticaal;
	//* Dit balletje gaat meebewegen met de Wiimote bewegingen
	private Ellipse2D.Double motePosition 	= new Ellipse2D.Double(0, 0, 20, 20);
	
	//* Snelheid van de cursor
	private int cursorSpeed = 5;
	
	WiimotePanel()
	{
		initLines(0);
		defineMotes();
	}
	
 	private void defineMotes()
	{
 		motes = WiiUseApiManager.getWiimotes(1, false);				//* Aangesloten wii motes opslaan in array, Rumble bij opzetten van verbinding
 		
 		if (motes.length > 0)
 		{
			mote = motes[0];											//* 1 mote aangesloten, dus staat op 1e positie in array.		
			mote.addWiiMoteEventListeners(this);						//* Listeners implementeren
			mote.activateMotionSensing();
 		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.translate(getWidth() /2, getHeight()/2);
		
		g2.setColor(Color.RED);
		g2.draw(lijnHorizontaal);
		g2.draw(lijnVerticaal);
		
		g2.setColor(Color.GREEN);
		g2.draw(motePosition);
			
		Font defaultFont = new Font("Arial", Font.PLAIN, 10);
		g2.setFont(defaultFont);
		g2.drawString("X: "+ (int)(motePosition.getX() + motePosition.width) + " Y:" + (int)(motePosition.getY() + motePosition.height), 
				(int)(motePosition.getX() + motePosition.width), (int)(motePosition.getY() + motePosition.height));
		
	}
	
	//* Lijnen tekenen op basis van accelerometer data in nunchuck
	private void initLines(double gForceX)
	{
		//* Afstand tussen middelpunt en punten
		int afstand = 250;
		
		double gForceRadians = gForceX * Math.PI / 180;
		
		//* Punten op elke 90 graden van een cirkel zetten, later veschuiven met de hoek verkregen uit de nunchuk bewegingen
		Point2D.Double 	punt1 = new Point2D.Double(afstand * Math.cos(gForceRadians)				, afstand * Math.sin(gForceRadians)),
						punt2 = new Point2D.Double(afstand * Math.cos(gForceRadians + Math.PI)		, afstand * Math.sin(gForceRadians + Math.PI)), 
						punt3 = new Point2D.Double(afstand * Math.cos(gForceRadians - Math.PI / 2)	, afstand * Math.sin(gForceRadians - Math.PI / 2)), 
						punt4 = new Point2D.Double(afstand * Math.cos(gForceRadians + Math.PI / 2)	, afstand * Math.sin(gForceRadians + Math.PI / 2));
		
		//* Lijnen trekken tussen punten om X en Y as te verkrijgen
		lijnHorizontaal = new Line2D.Double(punt1, punt2);
		lijnVerticaal  	= new Line2D.Double(punt3, punt4);
		
		repaint();
	}
	
	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		
		if (arg0.isButtonLeftPressed() || arg0.isButtonLeftHeld()) 
			motePosition.setFrame(motePosition.getX() - cursorSpeed, motePosition.getY(), motePosition.getWidth(), motePosition.getHeight());
		else if (arg0.isButtonRightHeld() || arg0.isButtonRightJustPressed())
			motePosition.setFrame(motePosition.getX() + cursorSpeed, motePosition.getY(), motePosition.getWidth(), motePosition.getHeight());
		else if (arg0.isButtonUpHeld() || arg0.isButtonUpJustPressed())
			motePosition.setFrame(motePosition.getX(), motePosition.getY() - cursorSpeed, motePosition.getWidth(), motePosition.getHeight());
		else if (arg0.isButtonDownHeld() || arg0.isButtonDownJustPressed())
			motePosition.setFrame(motePosition.getX(), motePosition.getY() + cursorSpeed, motePosition.getWidth(), motePosition.getHeight());
		
		if (!isWithinFrame(motePosition.getX(), motePosition.getY()))
			motePosition.setFrame(0, 0, motePosition.getWidth(), motePosition.getHeight());

		
		if (arg0.isButtonMinusHeld() && cursorSpeed > 1)
			cursorSpeed--;
		else if (arg0.isButtonPlusHeld() && cursorSpeed < 16)
			cursorSpeed++;
		
		repaint();
	}
	
	//* Controleren of cursor nog in het paneel zit
	private boolean isWithinFrame(double x, double y)
	{
		//* Cursor binnen de parameters van het paneel
		if (x > getWidth() / 2  || x < -(getWidth() / 2) ||
			y > getHeight() / 2 || y < -(getHeight() /2)  )
		{
			motePosition.setFrame(0, 0, motePosition.getWidth(), motePosition.getHeight());
			return false;
		}
		
		return true;
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
		
	}
	@Override
	public void onExpansionEvent(ExpansionEvent arg0) {
		
		//* Nunchuck code
		if (arg0 instanceof NunchukEvent)
		{
			NunchukEvent nunchuk = (NunchukEvent) arg0;
			
			//* Knoppen op de nunchuck uitlezen
			NunchukButtonsEvent nunchukButtons 	 = nunchuk.getButtonsEvent();
			JoystickEvent 		joystick		 = nunchuk.getNunchukJoystickEvent();
			MotionSensingEvent	beweging 		 = nunchuk.getNunchukMotionSensingEvent();
			
			//* Wanneer er '0.5' kracht op de thumpstick detecteerd wordt.
			if (joystick.getMagnitude() > 0.5)
			{
				double newX, newY;
				//* Let op: 90 graden aftrekken om de thumbstick angle te synchroniseren met Java
				newX = motePosition.getX() + cursorSpeed * joystick.getMagnitude() * Math.cos((joystick.getAngle() - 90) * Math.PI / 180);
				newY = motePosition.getY() + cursorSpeed * joystick.getMagnitude() * Math.sin((joystick.getAngle() - 90) * Math.PI / 180);
			
				if (isWithinFrame(newX, newY))
					motePosition.setFrame(newX, newY, motePosition.getWidth(), motePosition.getHeight());
			}

			
			//* Lijnen draaien wanneer 'c'-knop ingedrukt is
			if (nunchukButtons.isButtonCHeld())
				initLines(beweging.getGforce().getX() * 300);
			
			System.out.println(beweging.getOrientation().toString());
			
		}
		
		
	}

	@Override
	public void onIrEvent(IREvent arg0) {
		
	}
	@Override
	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		
	}
	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0){	//* Nunchuck is aangeslot op mote

	}
	
	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {	//* Nunchuck is losgekoppeld van mote
	}
	@Override
	public void onStatusEvent(StatusEvent arg0) {
		
	}
	
	
	//* Mochten we ooit guitar hero willen spelen
	
	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {}
	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) 	{}
	
}
