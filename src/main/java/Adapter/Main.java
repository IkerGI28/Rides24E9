package Adapter;

import java.net.MalformedURLException;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Driver;
import factorymethod.Factory;

public class Main {

	public static void main(String[] args) {
		ConfigXML conf = ConfigXML.getInstance();
		Factory f = new Factory();
		BLFacade appFacadeInterface = null;
		try {
			appFacadeInterface = f.createBLFacade(conf);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Driver d = appFacadeInterface.getDriver("Urtzi");
		DriverTable dt = new DriverTable(d);
		dt.setVisible(true);
	}

}
