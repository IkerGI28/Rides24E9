package getRidesByDriverTest;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.OriginDestinationWhen;
import domain.Ride;

public class GetRidesByDriverBDBlackTest {

	// sut:system under test
	static DataAccess sut = new DataAccess();

	// Test case to test that the driver is not in the Data base
	@Test
	public void test1() {
		try {
			sut.open();
			Driver driverTest = new Driver("driverTest", "123456");
			List<Ride> rides = sut.getRidesByDriver(driverTest.getUsername());
			assertNull(rides);
		} finally {
			sut.close();
		}
	}

	// Test case to test that the driver is in the DB and has no rides
	@Test
	public void test3() {
		Driver driverTest = new Driver("driverTest", "123456");
		try {
			sut.open();
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			List<Ride> rides = sut.getRidesByDriver(driverTest.getUsername());
			assertTrue(rides.isEmpty());
		} finally {
			sut.deleteUser(driverTest);
			sut.close();
		}
	}

	// Test case to test that the driver has rides
	@Test
	public void test2() {
		Driver driverTest = new Driver("driverTest", "123456");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			sut.open();
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			try {
				OriginDestinationWhen odw=new OriginDestinationWhen("Bilbao", "Donostia", rideDate);
				sut.createRide(odw, 4, (float) 1.5, driverTest.getUsername());
				List<Ride> rides = sut.getRidesByDriver(driverTest.getUsername());
				assertTrue(!rides.isEmpty());
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		} finally {
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
	
	//Test case to test when the username is null
	@Test
	public void test4() {
		try {
			sut.open();
			List<Ride> rides = sut.getRidesByDriver(null);
			assertNull(rides);
		} finally {
			sut.close();
		}
	}
}
