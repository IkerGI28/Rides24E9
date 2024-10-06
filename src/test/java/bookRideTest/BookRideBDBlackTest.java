/*package bookRideTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import testOperations.TestDataAccess;

public class BookRideBDBlackTest {

	//sut:system under test
	static DataAccess sut = new DataAccess();
	
	//additional operations needed to execute the test
	static TestDataAccess  testDA = new TestDataAccess();
	
	//Traveler in the DB and no attributes are null and booking is successful so it is created in the DB
	//batzuetan true bueltatzen du eta beste batzuetan false ez dakit zergatik, inplementazioa ona dirudi.
	@Test
	public void test1() {
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
			ride = sut.createRide("Ordizia", "Anoeta", rideDate, 3, 10, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);;
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
	
	//Traveler is not in the DB but rest of data is, nevertheless it returns false since traveler cannot be found
	@Test
	public void test2() {
		Traveler travelerTest = new Traveler("Patxi", "123456");
		Driver driverTest = new Driver("driverTest", "123456");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Ride ride=null;
		Date rideDate=null;
		try {
			rideDate = sdf.parse("23/05/2028");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
            sut.open();
            sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
            ride = sut.createRide("Milan", "Roma", rideDate, 5, 6, driverTest.getUsername());
            boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);
            assertTrue(!result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
        } finally {
            sut.deleteUser(driverTest);
            sut.close();
        }
	}
	
	//Parameter username is null, so it is not possible to book a ride and should return false
	@Test
	public void test3() {
		Traveler travelerTest = new Traveler(null, "123456");
		Ride r=null;
		try {
			sut.open();
			boolean result = sut.bookRide(travelerTest.getUsername(), r, 2, 3);
			assertTrue(!result);			
		} finally {
			sut.deleteUser(travelerTest);
			sut.close();
		}
	}
	
	//Ride is null, so it is not possible to book a ride and should return false
	@Test
	public void test4() {
		Traveler travelerTest = new Traveler("Patxi", "123456");
		Ride r=null;
		try {
			sut.open();
			boolean result = sut.bookRide(travelerTest.getUsername(), r, 2, 3);
			assertTrue(!result);
		} finally {
			sut.deleteUser(travelerTest);
			sut.close();
		}
	}
	
	//Booking seats amount is 0, so it is not possible to book a ride and should return false
	@Test
	public void test5() {
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
			sut.gauzatuEragiketa(travelerTest.getUsername(), 7, true);
			Ride ride = sut.createRide("Donostia", "Zarautz", rideDate, 5, 6, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 0, 3);
			assertTrue(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
	
	//Discount is negative, so it is not possible to book a ride and should return false
	@Test
	public void test6() {
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
			sut.gauzatuEragiketa(travelerTest.getUsername(), 7, true);
			Ride ride = sut.createRide("Donostia", "Zarautz", rideDate, 5, 6, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, -3);
			assertTrue(!result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
	
	//Booking seats amount is greater than available seats, so it is not possible to book a ride and should return false
	@Test
	public void test7() {
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
			sut.gauzatuEragiketa(travelerTest.getUsername(), 7, true);
			Ride ride = sut.createRide("Donostia", "Zarautz", rideDate, 5, 6, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 6, 3);
			assertTrue(!result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
	
	//Traveler cannot book a ride if there is not enough money in the account
	@Test
	public void test8() {
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
			sut.gauzatuEragiketa(travelerTest.getUsername(), 7, true);
			Ride ride = sut.createRide("Donostia", "Zarautz", rideDate, 5, 6, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 1);
			assertTrue(!result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
}*/



package bookRideTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import testOperations.TestDataAccess;

public class BookRideBDBlackTest {

	//sut:system under test
	static DataAccess sut = new DataAccess();
	
	//additional operations needed to execute the test
	static TestDataAccess  testDA = new TestDataAccess();
	
	//Traveler in the DB and no attributes are null and booking is successful so it is created in the DB
	//batzuetan true bueltatzen du eta beste batzuetan false ez dakit zergatik, inplementazioa ona dirudi.
	@Test
	public void test1() {
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
			ride = sut.createRide("Ordizia", "Anoeta", rideDate, 3, 10, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);;
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
	
	//Traveler is not in the DB but rest of data is, nevertheless it returns false since traveler cannot be found
	@Test
	public void test2() {
		Traveler travelerTest = new Traveler("Patxi", "123456");
		Driver driverTest = new Driver("driverTest", "123456");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Ride ride=null;
		Date rideDate=null;
		try {
			rideDate = sdf.parse("23/05/2028");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
            sut.open();
            sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
            ride = sut.createRide("Milan", "Roma", rideDate, 5, 6, driverTest.getUsername());
            boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);
            assertTrue(!result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
        } finally {
            sut.deleteUser(driverTest);
            sut.close();
        }
	}
	
	//Parameter username is null, so it is not possible to book a ride and should return false
	@Test
	public void test3() {
		Traveler travelerTest = new Traveler(null, "123456");
		Ride r=null;
		try {
			sut.open();
			boolean result = sut.bookRide(travelerTest.getUsername(), r, 2, 3);
			assertTrue(!result);			
		} finally {
			sut.deleteUser(travelerTest);
			sut.close();
		}
	}
	
	//Ride is null, so it is not possible to book a ride and should return false
	@Test
	public void test4() {
		Traveler travelerTest = new Traveler("Patxi", "123456");
		Ride r=null;
		try {
			sut.open();
			boolean result = sut.bookRide(travelerTest.getUsername(), r, 2, 3);
			assertTrue(!result);
		} finally {
			sut.deleteUser(travelerTest);
			sut.close();
		}
	}
	
	//Booking seats amount is 0, so it is not possible to book a ride and should return false
	@Test
	public void test5() {
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
			sut.gauzatuEragiketa(travelerTest.getUsername(), 7, true);
			Ride ride = sut.createRide("Donostia", "Zarautz", rideDate, 5, 6, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 0, 3);
			assertTrue(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
	
	//Discount is negative, so it is not possible to book a ride and should return false
	@Test
	public void test6() {
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
			sut.gauzatuEragiketa(travelerTest.getUsername(), 7, true);
			Ride ride = sut.createRide("Donostia", "Zarautz", rideDate, 5, 6, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, -3);
			assertTrue(!result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
	
	//Booking seats amount is greater than available seats, so it is not possible to book a ride and should return false
	@Test
	public void test7() {
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
			sut.gauzatuEragiketa(travelerTest.getUsername(), 7, true);
			Ride ride = sut.createRide("Donostia", "Zarautz", rideDate, 5, 6, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 6, 3);
			assertTrue(!result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
	
	//Traveler cannot book a ride if there is not enough money in the account
	@Test
	public void test8() {
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
			sut.gauzatuEragiketa(travelerTest.getUsername(), 7, true);
			Ride ride = sut.createRide("Donostia", "Zarautz", rideDate, 5, 6, driverTest.getUsername());
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 1);
			assertTrue(!result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			sut.deleteUser(travelerTest);
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
}
