package iterator;

import java.net.MalformedURLException;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import factorymethod.Factory;

public class Main {

	public static void main(String[] args) {
		//The BL is	local
		ConfigXML conf = ConfigXML.getInstance();
		Factory f = new Factory();
		BLFacade appFacadeInterface = null;
		try {
			appFacadeInterface = f.createBLFacade(conf);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ExtendedIterator<String> i = appFacadeInterface.getDepartingCitiesIterator();
		String c;
		System.out.println("_____________________");
		System.out.println("FROM LAST TO FIRST");
		i.goLast(); // Go to last element
		while (i.hasPrevious()) {
			c = i.previous();
			System.out.println(c);
		}
		System.out.println();
		System.out.println("_____________________");
		System.out.println("FROM FIRST TO LAST");
		i.goFirst(); // Go to first element
		while (i.hasNext()) {
			c = i.next();
			System.out.println(c);
		}
	}

}
