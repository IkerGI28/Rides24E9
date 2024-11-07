package Adapter;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Driver;

public class Main {

	public static void main(String[] args) {
		boolean isLocal = true;
		DataAccess da = new DataAccess();
		BLFacade blFacade = new BLFacadeImplementation(da);
		Driver d = blFacade.getDriver("Urtzi");
		System.out.println(d);
		System.out.println(d.getUsername());
		DriverTable dt = new DriverTable(d);
		dt.setVisible(true);
	}

}
