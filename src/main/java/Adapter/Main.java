package Adapter;

import java.sql.Driver;

import businessLogic.BLFacade;

public class Main {

	public static void main(String[] args) {
		boolean isLocal =	true;
		BLFacade blFacade =	new BLFactory().getBusinessLogicFactory(isLocal);
		Driver	d= blFacade.getDriver("Urtzi");
		DriverTable	dt=new	DriverTable(d);
		dt.setVisible(true);
	}

}
