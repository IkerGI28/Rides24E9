package bookRideTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.OriginDestinationWhen;
import domain.Ride;
import domain.Traveler;
import testOperations.TestDataAccess;

public class BookRideBDWhiteTest {

	//sut:system under test
	static DataAccess sut=new DataAccess();
	

	//Test case to test that exceptions are handled correctly
	
	
	//Test case to test that the traveler is not in the Data base
	@Test
	public void test2() {
		try {
			sut.open();
			Traveler travelerTest = new Traveler("Pepe", "123456");
			Ride ride = new Ride("Donostia", "Zarautz", new Date(), 2, 10, new Driver("driverTest", "123456"));
			assertFalse(sut.bookRide(travelerTest.getUsername(), ride, 2, 3));
		} finally {
			sut.close();
		}
	}

	
	//Test case to test that ride seats are not enough
	@Test
	public void test3() {
		Traveler travelerTest = new Traveler("Patxi", "123456");
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
			sut.addTraveler(travelerTest.getUsername(), travelerTest.getPassword());
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			try {
				OriginDestinationWhen odw=new OriginDestinationWhen("Donostia", "Zarautz", rideDate);
				Ride ride = sut.createRide(odw, 1, 10, "driverTest");
				assertFalse(sut.bookRide(travelerTest.getUsername(), ride, 2, 3));
			}catch(Exception e){ 
				e.printStackTrace();
				fail();
			}
		}finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
	//Test case to test that the traveler can't afford the ride
	@Test
	public void test4() {
		Traveler travelerTest = new Traveler("Patxi", "123456");
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
			sut.addTraveler(travelerTest.getUsername(), travelerTest.getPassword());
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			OriginDestinationWhen odw=new OriginDestinationWhen("Bilbo", "Mutriku", rideDate);
			Ride ride = sut.createRide(odw, 3, 10, "driverTest");
			assertFalse(sut.bookRide(travelerTest.getUsername(), ride, 2, 3));
		} catch(Exception e){ 
			e.printStackTrace();
			fail();
		} finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
	
	//Test case to test that the ride is booked correctly, batzuetan true bueltatzen du eta beste batzuetan false
	//ez dakit zergatik, inplementazioa ona dirudi.
	@Test
	public void test5() {
		Traveler travelerTest = new Traveler("Patxi", "123456");
		Driver driverTest = new Driver("driverTest", "123456");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		Ride ride = null;
		try {
			rideDate = sdf.parse("05/10/2200");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			sut.open();
			sut.addTraveler(travelerTest.getUsername(), travelerTest.getPassword());
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			System.out.println(driverTest.getUsername());
			sut.gauzatuEragiketa(travelerTest.getUsername(), 100, true);
			OriginDestinationWhen odw=new OriginDestinationWhen("Ordizia", "Anoeta", rideDate);
			ride = sut.createRide(odw, 3, 10, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);
			assertTrue(result);
		} catch(Exception e){ 
			e.printStackTrace();
			fail();
		} finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
}
