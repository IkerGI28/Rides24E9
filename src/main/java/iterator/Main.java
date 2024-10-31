package iterator;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;

public class Main {

	public static void main(String[] args) {
		//The BL is	local
		boolean isLocal = true;
		DataAccess da = new DataAccess();
		BLFacade blFacade = new BLFacadeImplementation(da);
		ExtendedIterator<String> i = blFacade.getDepartingCitiesIterator();
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
