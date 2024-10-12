import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Discount;
import domain.Driver;
import domain.OriginDestinationWhen;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideMockTest {
	static DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	
	@Mock
	protected EntityManager db;
	
	@Mock
	protected EntityTransaction et;
	
	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
				.thenReturn(entityManagerFactory);
		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		sut = new DataAccess(db);
	}

	@After
	public void tearDown() {
		persistenceMock.close();
	}
	
	@Test
	public void test4() {
		String driverUsername="Driver Test";
		String driverPassword="123";
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			Driver driver=new Driver(driverUsername,driverPassword);
			driver.addRide(rideFrom, rideTo, rideDate, 2, 10);
			//configure the state through mocks
			Mockito.when(db.find(Driver.class, driverUsername)).thenReturn(driver);
			//equivalent to
			//Mockito.doReturn(driver).when (db).find (Driver.class, driverUsername);
			//invoke System Under Test (sut)
			OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideTo, rideDate);
			sut.open();
			sut.createRide(odw,0,0,driverUsername);
			sut.close();
			fail();
		} catch (RideAlreadyExistException e) {
			//verify the results
			sut.close();
			assertTrue(true);
		} catch (RideMustBeLaterThanTodayException e) {
			fail();
		}
	}
	
	public void createDiscount(Discount d) {
		try {
			db.getTransaction().begin();
			db.persist(d);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			db.getTransaction().rollback();
		}
	}
	
	@Test
	public void testCreateDiscount() {
		try {
			// test params
			String code = "free10";
			double percent = 10;
			boolean active = true;
			Discount d = new Discount(code, percent, active);
			// call sut
			sut.createDiscount(d);
			// test results ???
			Mockito.verify(db, Mockito.times(1)).persist(d);
		} catch (Exception e) {
			fail("If any exception is thrown, it is not working properly");
		}
	}
}


