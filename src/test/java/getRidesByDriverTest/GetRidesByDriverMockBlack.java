package getRidesByDriverTest;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;

public class GetRidesByDriverMockBlack {

	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;

	@Before
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
    }
	
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	
	// Test case to test that the driver is not in the Data base
	@Test
	public void test1() {
		try {
			Driver driverTest = new Driver("driverTest", "123456");
	        TypedQuery<Driver> query = Mockito.mock(TypedQuery.class);
	        Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class))
	               .thenReturn(query);
	        Mockito.when(query.getSingleResult()).thenReturn(null);
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
			Driver driverTest = new Driver("driverTest", "123456");	 
	        TypedQuery<Driver> query = Mockito.mock(TypedQuery.class);
	        Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class))
	               .thenReturn(query);
	        Mockito.when(query.getSingleResult()).thenReturn(driverTest);
			List<Ride> rides = sut.getRidesByDriver(driverTest.getUsername());
			assertTrue(rides.isEmpty());
		} finally {
			sut.close();
		}
	}

	// Test case to test that the driver has rides
	@Test
	public void test2() {
		try {
			sut.open();
			Driver driverTest = new Driver("driverTest", "123456");	
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date rideDate=null;
			try {
				rideDate = sdf.parse("05/10/2026");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			driverTest.addRide("Donostia", "Barcelona", rideDate, 2, 10);
			//Mockito
	        TypedQuery<Driver> query = Mockito.mock(TypedQuery.class);
	        Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class))
	               .thenReturn(query);
	        Mockito.when(query.getSingleResult()).thenReturn(driverTest);
			try {
				List<Ride> rides = sut.getRidesByDriver(driverTest.getUsername());
				assertTrue(!rides.isEmpty());
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		} finally {
			sut.close();
		}
	}
	
	//Test case to test when the username is null
	@Test
	public void test4() {
		try {
			sut.open();
			 TypedQuery<Driver> query = Mockito.mock(TypedQuery.class);
		        Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class))
		               .thenReturn(query);
		        Mockito.when(query.getSingleResult()).thenReturn(null);
			List<Ride> rides = sut.getRidesByDriver(null);
			assertNull(rides);
		} finally {
			sut.close();
		}
	}

}
