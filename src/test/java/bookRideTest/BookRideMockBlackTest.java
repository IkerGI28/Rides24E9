package bookRideTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import domain.Traveler;

public class BookRideMockBlackTest {
	
	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;
	
	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
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
	
	//Traveler in in the database, ride is in the database, booking is successful, so the method should return true
	@Test
	public void test1() {
		try {
			sut.open();
			Traveler traveler = new Traveler("Patxi", "123456");
			Driver driver = new Driver("driver", "123456");
			Ride ride = sut.createRide("Missouri", "Orlando", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/3000"), 5, 6, driver.getUsername());
			TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(traveler);
			boolean result = sut.bookRide(traveler.getUsername(), ride, 1, 1);
			assertTrue(result);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	//Traveler is not in the database, so the method should return false
	@Test
	public void test2() {
		try {
			Traveler traveler = new Traveler("Patxi", "123456");
			TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class))
					.thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(null);
			boolean result = sut.bookRide(traveler.getUsername(), null, 1, 1);
			assertTrue(!result);
		} catch (Exception e) {
			fail();
		}
	}
	
	//Traveler user name is null, so the method should return false
	@Test
	public void test3() {
		try {
			sut.open();
			TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class))
					.thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(null);
			boolean result = sut.bookRide(null, null, 1, 1);
			assertTrue(!result);
		} finally {
			sut.close();
		}
	}
	
	//Ride is null, so the method should return false
	@Test
	public void test4() {
		try {
			sut.open();
			Traveler traveler = new Traveler("Patxi", "123456");
			TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class))
					.thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(traveler);
			boolean result = sut.bookRide(traveler.getUsername(), null, 1, 1);
			assertTrue(!result);
		} finally {
			sut.close();
		}
	}
	
	//Booking seats is negative, so the method should return false
	@Test
	public void test5() {
		try {
			sut.open();
			Traveler traveler = new Traveler("Patxi", "123456");
			Driver driver = new Driver("driver", "123456");
			Ride ride = sut.createRide("New York", "Appalachia", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2222"), 5, 6, driver.getUsername());
			TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class))
					.thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(traveler);
			boolean result = sut.bookRide(traveler.getUsername(), ride, -1, 1);
			assertTrue(!result);
		} catch (Exception e) {
            fail();
        } finally {
			sut.close();
		}
	}
	
	//Discount is negative, so the method should return false
	@Test
	public void test6() {
		try {
			sut.open();
            Traveler traveler = new Traveler("Patxi", "123456");
            Driver driver = new Driver("driver", "123456");
            Ride ride = sut.createRide("Houston", "Miami", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2411"), 5, 6, driver.getUsername());
            TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class))
					.thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(traveler);
			boolean result = sut.bookRide(traveler.getUsername(), ride, 1, -1);
			assertTrue(!result);
		} catch(Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	//Ride seat amount is less than the booking seats, so the method should return false
	@Test
	public void test7() {
		try {
			sut.open();
            Traveler traveler = new Traveler("Patxi", "123456");
            Driver driver = new Driver("driver", "123456");
            Ride ride = sut.createRide("Zumarraga", "Azpeitia", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2087"), 5, 6, driver.getUsername());
            TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
            Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
            Mockito.when(query.getSingleResult()).thenReturn(traveler);
            boolean result = sut.bookRide(traveler.getUsername(), ride, 6, 1);
            assertTrue(!result);
		} catch(Exception e) {
			fail();
        } finally {
			sut.close();
		}
	}
	
	//Traveler cannot afford the booking, so the method should return false
	@Test
	public void test8() {
		try {
			sut.open();
            Traveler traveler = new Traveler("Patxi", "123456");
            Driver driver = new Driver("driver", "123456");
            Ride ride = sut.createRide("Legorreta", "Itsasondo", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2231"), 5, 6, driver.getUsername());
            TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
            Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
            Mockito.when(query.getSingleResult()).thenReturn(traveler);
            boolean result = sut.bookRide(traveler.getUsername(), ride, 1, 1);
            assertTrue(!result);
		} catch(Exception e) {
            fail();
        } finally {
			sut.close();
		}
	}
	
}
