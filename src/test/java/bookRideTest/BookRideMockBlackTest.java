package bookRideTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import domain.OriginDestinationWhen;
import domain.Ride;
import domain.Traveler;
import domain.User;

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
	
	//Erreserba ondo egin behar da eta true bueltatu beharko luke baina false bueltatzen du.
	@Test
	public void test1() {
		Traveler travelerTest = new Traveler("Patxi", "123456");
		Driver driverTest = new Driver("driver", "123456");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		Ride ride = null;
		try {
			date = sdf.parse("01-01-3000");
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
	
	//Traveler is not in the database, so the method should return false
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
			Driver driver = new Driver("driver", "123456");
			
	        Mockito.when(db.find(Traveler.class, traveler.getUsername())).thenReturn(traveler);
	        TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
	        Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class))
	               .thenReturn(query);
	        
	        List<Traveler> travelerList = new ArrayList<>();
	        travelerList.add(traveler);
	        Mockito.when(query.getResultList()).thenReturn(travelerList);
	        
	        Mockito.when(db.find(Driver.class, driver.getUsername())).thenReturn(driver);
	        TypedQuery<Driver> query2 = Mockito.mock(TypedQuery.class);
	        Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class))
	               .thenReturn(query2);
	        
	        List<Driver> driverList = new ArrayList<>();
	        driverList.add(driver);
	        Mockito.when(query2.getResultList()).thenReturn(driverList);
			
			
			sut.addTraveler(traveler.getUsername(), traveler.getPassword());
			sut.addDriver(driver.getUsername(), driver.getPassword());
			boolean result = sut.bookRide(traveler.getUsername(), null, 1, 1);
			assertTrue(!result);
		} finally {
			sut.close();
		}
	}
	
	//Booking seats is negative, so the method should return false
	@Test
	public void test5() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = sdf.parse("01-01-2222");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			sut.open();
			try {
				Traveler traveler = new Traveler("Patxi", "123456");
				Driver driver = new Driver("driver", "123456");			
				
		        Mockito.when(db.find(Traveler.class, traveler.getUsername())).thenReturn(traveler);
		        TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
		        Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class))
		               .thenReturn(query);
		        
		        List<Traveler> travelerList = new ArrayList<>();
		        travelerList.add(traveler);
		        Mockito.when(query.getResultList()).thenReturn(travelerList);
		        
		        Mockito.when(db.find(Driver.class, driver.getUsername())).thenReturn(driver);
		        TypedQuery<Driver> query2 = Mockito.mock(TypedQuery.class);
		        Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class))
		               .thenReturn(query2);
		        
		        List<Driver> driverList = new ArrayList<>();
		        driverList.add(driver);
		        Mockito.when(query2.getResultList()).thenReturn(driverList);
				
				sut.addTraveler(traveler.getUsername(), traveler.getPassword());
				sut.addDriver(driver.getUsername(), driver.getPassword());			
				OriginDestinationWhen odw=new OriginDestinationWhen("New York", "Appalachia", date);
				Ride ride = sut.createRide(odw, 5, 6, driver.getUsername());
				driver.addRide("New York", "Appalachia", date, 5, 6);
				boolean result = sut.bookRide(traveler.getUsername(), ride, -1, 1);
				assertTrue(!result);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
        } finally {
			sut.close();
		}
	}
	
	//Discount is negative, so the method should return false
	@Test
	public void test6() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = sdf.parse("01-01-2411");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			sut.open();
			try {
	            Traveler traveler = new Traveler("Patxi", "123456");
	            Driver driver = new Driver("driver", "123456");
	            
	            
		        Mockito.when(db.find(Traveler.class, traveler.getUsername())).thenReturn(traveler);
		        TypedQuery<Traveler> query = Mockito.mock(TypedQuery.class);
		        Mockito.when(db.createQuery("SELECT t FROM Traveler t WHERE t.username = :username", Traveler.class))
		               .thenReturn(query);
		        
		        List<Traveler> travelerList = new ArrayList<>();
		        travelerList.add(traveler);
		        Mockito.when(query.getResultList()).thenReturn(travelerList);
		        
		        Mockito.when(db.find(Driver.class, driver.getUsername())).thenReturn(driver);
		        TypedQuery<Driver> query2 = Mockito.mock(TypedQuery.class);
		        Mockito.when(db.createQuery("SELECT d FROM Driver d WHERE d.username = :username", Driver.class))
		               .thenReturn(query2);
		        
                sut.gauzatuEragiketa(traveler.getUsername(), 100, true);
		        
		        List<Driver> driverList = new ArrayList<>();
		        driverList.add(driver);
		        Mockito.when(query2.getResultList()).thenReturn(driverList);
		        
	            sut.addTraveler(traveler.getUsername(), traveler.getPassword());
	            sut.addDriver(driver.getUsername(), driver.getPassword());
	            OriginDestinationWhen odw=new OriginDestinationWhen("Houston", "Miami", date);
	            Ride ride = sut.createRide(odw, 5, 6, driver.getUsername());
	            driver.addRide("Houston", "Miami", date, 5, 6);
				boolean result = sut.bookRide(traveler.getUsername(), ride, 1, -1);
				assertTrue(!result);
			} catch(Exception e) {
				e.printStackTrace();
				fail();
			}
		} finally {
			sut.close();
		}
	}
	
	//Ride seat amount is less than the booking seats, so the method should return false
	@Test
	public void test7() {
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
	
	//Traveler cannot afford the booking, so the method should return false
	@Test
	public void test8() {
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
	
}
