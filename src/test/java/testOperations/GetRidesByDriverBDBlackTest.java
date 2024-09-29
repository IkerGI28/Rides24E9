package testOperations;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;

public class GetRidesByDriverBDBlackTest {

	// sut:system under test
	static DataAccess sut = new DataAccess();
	Driver driverTest = new Driver("driverTest", "123456");
	
    LocalDate localDate = LocalDate.of(2026, 10, 2);
    Date data = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

	// Test case to test that the driver is not in the Data base
	@Test
	public void test1() {
		try {
			sut.open();
			List<Ride> rides = sut.getRidesByDriver(driverTest.getUsername());
			assertNull(rides);
		} finally {
			sut.close();
		}
	}

	// Test case to test that the driver is in the DB and has no rides
	@Test
	public void test3() {
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
		try {
			sut.open();
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			try {
				sut.createRide("Bilbao", "Donostia", data, 4, (float) 1.5, driverTest.getUsername());
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
