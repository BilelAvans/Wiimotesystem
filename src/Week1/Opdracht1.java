package Week1;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

public class Opdracht1 
{
	
private Wiimote[] motes;		//* List met array
private Wiimote   mote1,
				  mote2,
				  mote3;			//* 1 mote

Opdracht1()
{

}

private void setConnection()
{
/* Tot en met '2' wiimotes opvragen, Wiimote zal trillen wanneer de verbinding tot stand is verbracht  */
motes = WiiUseApiManager.getWiimotes(3, true);	

//* Wiimote voor variabele 'mote' gebruiken
		mote1 = motes[0];
		mote2 = motes[1];
		mote3 = motes[2];
	}
}
