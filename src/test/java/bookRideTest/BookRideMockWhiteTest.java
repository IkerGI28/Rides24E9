package bookRideTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import domain.OriginDestinationWhen;
import domain.Ride;
import domain.Traveler;
import domain.User;

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
	
	//Lehen eta bigarren proba kasuak berdinak dira.
	/*@Test
	public void test1() {
		
	}*/
	
	//False bueltatu behar du, ez delako travelerik aurkitu datubasean.
	@Test
	public void test2() {
		Traveler travelerTest = new Traveler("travelerTest", "123456");
		Driver driverTest = new Driver("driverTest", "123456");
		Ride ride = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = sdf.parse("01-01-2029");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			sut.open();
			try {
                Mockito.when(db.find(Driver.class, driverTest.getUsername())).thenReturn(driverTest);
            	TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
    			Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
    			Mockito.when(query.getSingleResult()).thenReturn(null);
    			
            	TypedQuery<Driver> query2 = Mockito.mock(TypedQuery.class);
    			Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class)).thenReturn(query2);
    			Mockito.when(query2.getSingleResult()).thenReturn(driverTest);
    			
				sut.addTraveler(travelerTest.getUsername(), travelerTest.getPassword());
                sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
                OriginDestinationWhen odw=new OriginDestinationWhen("Donostia", "Zarautz", date);
            	ride = sut.createRide(odw, 4, 10, driverTest.getUsername());
            	driverTest.addRide("Donostia", "Zarautz", date, 4, 10);
    			boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);
    			assertTrue(!result);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			sut.open();
			try {      
				TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
				Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
				Mockito.when(query.getResultList()).thenReturn(Arrays.asList(travelerTest));
				
                Mockito.when(db.find(Driver.class, driverTest.getUsername())).thenReturn(driverTest);
            	TypedQuery<Driver> query2 = Mockito.mock(TypedQuery.class);
    			Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class)).thenReturn(query2);
    			Mockito.when(query2.getSingleResult()).thenReturn(driverTest);
    			
				sut.addTraveler(travelerTest.getUsername(), travelerTest.getPassword());
                sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
                OriginDestinationWhen odw=new OriginDestinationWhen("Donostia", "Zarautz", date);
            	ride = sut.createRide(odw, 1, 10, driverTest.getUsername());
            	driverTest.addRide("Donostia", "Zarautz", date, 1, 10);

				boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);
				assertTrue(!result);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sut.open();
            try {
				TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
				Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
				Mockito.when(query.getResultList()).thenReturn(Arrays.asList(travelerTest));
				
                Mockito.when(db.find(Driver.class, driverTest.getUsername())).thenReturn(driverTest);
            	TypedQuery<Driver> query2 = Mockito.mock(TypedQuery.class);
    			Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class)).thenReturn(query2);
    			Mockito.when(query2.getSingleResult()).thenReturn(driverTest);
    			
            	sut.addTraveler(travelerTest.getUsername(), travelerTest.getPassword());
                sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
                OriginDestinationWhen odw=new OriginDestinationWhen("Donostia", "Zarautz", date);
            	ride = sut.createRide(odw, 2, 10, driverTest.getUsername());
            	driverTest.addRide("Donostia", "Zarautz", date, 2, 10);
                boolean result = sut.bookRide(travelerTest.getUsername(), ride, 2, 3);
                assertTrue(!result);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
        } finally {
            sut.close();
        }
	}
	
	//True bueltatu beharko luke baina ez du true bueltatzen, false baizik.
	@Test
	public void test5() {
		Traveler travelerTest = new Traveler("travelerTest", "123456");
        Driver driverTest = new Driver("driverTest", "123456");
        Ride ride = null;
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = sdf.parse("01-12-2065");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sut.open();
            try {
				TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
				Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class)).thenReturn(query);
				Mockito.when(query.getResultList()).thenReturn(Arrays.asList(travelerTest));
				
                Mockito.when(db.find(Driver.class, driverTest.getUsername())).thenReturn(driverTest);
            	TypedQuery<Driver> query2 = Mockito.mock(TypedQuery.class);
    			Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class)).thenReturn(query2);
    			Mockito.when(query2.getSingleResult()).thenReturn(driverTest);
    			
            	TypedQuery<User> query3 = Mockito.mock(TypedQuery.class);
    			Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(query3);
    			Mockito.when(query3.getSingleResult()).thenReturn(travelerTest);
    			
                sut.gauzatuEragiketa(travelerTest.getUsername(), 100, true);
    			
                sut.addTraveler(travelerTest.getUsername(), travelerTest.getPassword());
                sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
                OriginDestinationWhen odw=new OriginDestinationWhen("Madrid", "Tolosa", date);
            	ride = sut.createRide(odw, 2, 2, driverTest.getUsername());
            	driverTest.addRide("Madrid", "Tolosa", date, 2, 2);
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
