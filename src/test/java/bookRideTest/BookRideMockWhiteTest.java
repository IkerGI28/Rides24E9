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

public class BookRideMockWhiteTest {

	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;
	
	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
	protected EntityTransaction et;
	
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
	
	//Test case to test that exceptions are handled correctly
	@Test
	public void test1() {
		
	}
	
	//Test case to test that the traveler is not in the Data base so it should return false
	@Test
	public void test2() {
		Traveler travelerTest = new Traveler("travelerTest", "123456");
		Driver driverTest = new Driver("driverTest", "123456");
		Ride ride = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = sdf.parse("01-01-2029");
			ride = sut.createRide("Donostia", "Zarautz", date, 4, 10, driverTest.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			sut.open();
			TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(null);
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);
			assertTrue(!result);
		}finally {
			sut.close();
		}
	}
	
	//Test case to test that seat amount is not enough so it should return false
	@Test
	public void test3() {
		Traveler travelerTest = new Traveler("Patxi", "123456");
        Driver driverTest = new Driver("driverTest", "123456");
        Ride ride = null;
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			date = sdf.parse("01-01-2030");
			ride = sut.createRide("Donostia", "Zarautz", date, 1, 10, driverTest.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			sut.open();
			TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(travelerTest);
			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);
			assertTrue(!result);
		} finally {
			sut.close();
		}
	}
	
	//Test case to test that traveler can not afford the ride so it should return false
	@Test
	public void test4() {
		Traveler travelerTest = new Traveler("Patxi", "123456");
        Driver driverTest = new Driver("driverTest", "123456");
        Ride ride = null;
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = sdf.parse("01-01-2031");
            ride = sut.createRide("Donostia", "Zarautz", date, 2, 10, driverTest.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sut.open();
            TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
            Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
            Mockito.when(query.getSingleResult()).thenReturn(travelerTest);
            boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);
            assertTrue(!result);
        } finally {
            sut.close();
        }
	}
	
	//Test case to test that the ride is booked correctly so it should return true
	@Test
	public void test5() {
		Traveler travelerTest = new Traveler("travelerTest", "123456");
        Driver driverTest = new Driver("driverTest", "123456");
        Ride ride = null;
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = sdf.parse("01-12-2065");
            ride = sut.createRide("Madrid", "Tolosa", date, 2, 2, driverTest.getUsername());
            sut.gauzatuEragiketa(travelerTest.getUsername(), 100, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sut.open();
            try {
            	TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
                Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
                Mockito.when(query.getSingleResult()).thenReturn(travelerTest);
                boolean result = sut.bookRide(travelerTest.getUsername(), ride, 1, 1);
                assertTrue(result);
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        } finally {
            sut.close();
        }
	}
}
