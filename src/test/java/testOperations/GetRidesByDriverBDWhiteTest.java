package testOperations;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import businessLogic.BLFacade;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import gui.MainGUI;

public class GetRidesByDriverBDWhiteTest {
	
	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 
	Driver driverTest = new Driver("driverTest", "123456");
	
	@After
	public void driverEzabatu() {
		sut.open();
		sut.deleteUser(driverTest);
		sut.close();
	}
	
	//Test case to test that the driver is not in the Data base
	@Test
	public void test1() {
		sut.open();
		List<Ride> rides = facade.getRidesByDriver(driverTest.getUsername());
		assertNull(rides);
	}

	//Test case to test that the driver has no rides and no active rides
	@Test
    public void test2() {
		facade.addDriver(driverTest.getUsername(), driverTest.getPassword());
		List<Ride> rides = facade.getRidesByDriver(driverTest.getUsername());
		assertTrue(rides.isEmpty());
    }
	
	//Test case to test that the driver has rides but no active rides
	@Test
	public void test3() {
		facade.addDriver(driverTest.getUsername(), driverTest.getPassword());
		try {
			Ride ride = facade.createRide("Bilbao", "Donostia", new Date(), 4, (float) 1.5, driverTest.getUsername());
			facade.cancelRide(ride);
			List<Ride> rides = facade.getRidesByDriver(driverTest.getUsername());
			assertTrue(rides.isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	//Test case to test that the driver has active rides
	@Test
	public void test4() {
		facade.addDriver(driverTest.getUsername(), driverTest.getPassword());
		try {
			facade.createRide("Bilbao", "Donostia", new Date(), 4, (float) 1.5, driverTest.getUsername());
			List<Ride> rides = facade.getRidesByDriver(driverTest.getUsername());
			assertTrue(!rides.isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
