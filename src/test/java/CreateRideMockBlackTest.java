import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import domain.Driver;
import domain.OriginDestinationWhen;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideMockBlackTest {
	
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
	
	
	Driver driver;
	
	@Test
	//sut.createRide:  The Driver("Driver Test") HAS  NOT one ride "from" "to" in that "date". 
		// and the Ride must be created in DB
		//The test supposes that the "Driver Test" does not exist in the DB before the test
		public void test1() {
			//define parameters
			String driverUsername="Driver Test";
			String driverPassword="123";
			
			
			String rideFrom="Donostia";
			String rideTo="Zarautz";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date rideDate=null;;
			try {
				rideDate = sdf.parse("05/10/2026");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			try {
				Driver driver1=new Driver(driverUsername,driverPassword);

				//configure the state through mocks 
		        Mockito.when(db.find(Driver.class, driver1.getUsername())).thenReturn(driver1);
				OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideTo, rideDate);
				//invoke System Under Test (sut)  
				sut.open();
				Ride ride=sut.createRide(odw, 2, 10, driverUsername);
				sut.close();
				//verify the results
				assertNotNull(ride);
				assertEquals(ride.getFrom(),rideFrom);
				assertEquals(ride.getTo(),rideTo);
				assertEquals(ride.getDate(),rideDate);
				
				
			   } catch (RideAlreadyExistException e) {
				// if the program goes to this point fail  
				fail();
				
				} catch (RideMustBeLaterThanTodayException e) {
					// if the program goes to this point fail  

				fail();
				//redone state of the system (create object in the database)
				
			} 
		} 
	
	
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
	public void test2() {
		String driverUsername="Test driver";
		String rideFrom=null;
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		
		Driver driver1=new Driver(driverUsername,"123");

		//configure the state through mocks 
        Mockito.when(db.find(Driver.class, driver1.getUsername())).thenReturn(driver1);
		try {
			OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideTo, rideDate);
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(odw, 2, 10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			//q is in DB
			//Mirar con Mockito que no se ha invocado a su creación
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
			}
	   }
	@Test
	//sut.createRide:  The ride "to" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
	public void test3() {
		String driverUsername="Test driver";
		String rideFrom="Donostia";
		String rideTo="null";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		
		Driver driver1=new Driver(driverUsername,"123");

		//configure the state through mocks 
        Mockito.when(db.find(Driver.class, driver1.getUsername())).thenReturn(driver1);
		try {
			OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideTo, rideDate);
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(odw, 2, 10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			//q is in DB
			//Mirar con Mockito que no se ha invocado a su creación
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
			}
	   }
	
	@Test
	//sut.createRide:  The ride date is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
	public void test4() {
		String driverUsername="Test driver";
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		Date rideDate=null;
		
		Ride ride=null;
		
		Driver driver1=new Driver(driverUsername,"123");

		//configure the state through mocks 
        Mockito.when(db.find(Driver.class, driver1.getUsername())).thenReturn(driver1);
        
		try {
			OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideTo, rideDate);
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(odw, 2, 10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {
			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
			}
		
		   }

	@Test
	//sut.createRide:  The ride nPlaces is negative. The test must return null. If  an Exception is returned the createRide method is not well implemented.
	public void test5() {
		String driverUsername="Test driver";
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		
		Driver driver1=new Driver(driverUsername,"123");

		//configure the state through mocks 
        Mockito.when(db.find(Driver.class, driver1.getUsername())).thenReturn(driver1);
		try {
			OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideTo, rideDate);
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(odw, -2, 0, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			//q is in DB
			
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
			}
		
		   }
	@Test
	//sut.createRide:  The ride price is negative. The test must return null. If  an Exception is returned the createRide method is not well implemented.
	public void test6() {
		String driverUsername="Test driver";
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		
		Driver driver1=new Driver(driverUsername,"123");

		//configure the state through mocks 
        Mockito.when(db.find(Driver.class, driver1.getUsername())).thenReturn(driver1);
		try {
			OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideTo, rideDate);
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(odw, 2, -10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
			}
		   }
	
	@Test
	//sut.createRide:  The driverUsername is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
		public void test7() {
			try {
				
				//define parameters
				driver=null;

				String rideFrom="Donostia";
				String rideTo="Zarautz";
				
				String driverUserName=null;

				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date rideDate=null;;
				try {
					rideDate = sdf.parse("05/10/2026");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				Mockito.when(db.find(Driver.class, null)).thenReturn(null);

				OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideTo, rideDate);
				//invoke System Under Test (sut)  
				sut.open();
				Ride ride=sut.createRide(odw, 0, 0, driverUserName);

				//verify the results
				assertNull(ride);
				
			   } catch (RideAlreadyExistException e) {
				// TODO Auto-generated catch block
				// if the program goes to this point fail  
				fail();

				} catch (RideMustBeLaterThanTodayException e) {
				// TODO Auto-generated catch block
					fail();

				} catch (Exception e) {
					e.toString();
				// TODO Auto-generated catch block
					fail();

				} finally {
					sut.close();
				}
			
			   } 
	@Test
	//sut.createRide:  the date of the ride must be later than today. The RideMustBeLaterThanTodayException 
	// exception must be thrown. 
	public void test8() {
		
		String driverUsername="Driver Test";
		String driverPassword="123";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		

		try {
			rideDate = sdf.parse("05/10/2018");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			
			Driver driver1=new Driver(driverUsername,"123");

			//configure the state through mocks 
	        Mockito.when(db.find(Driver.class, driver1.getUsername())).thenReturn(driver1);	
			OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideTo, rideDate);
			//invoke System Under Test (sut)  
			sut.open();
		    sut.createRide(odw, 2, 10, driverUsername);
			sut.close();
			
			fail();
			
		   } catch (RideMustBeLaterThanTodayException  e) {
			 //verify the results
				sut.close();
				assertTrue(true);
			} catch (RideAlreadyExistException e) {
				sut.close();
				fail();
			}
		   } 
	
	
	@Test
	//sut.createRide:  The ride from==to. The test must return null. If  an Exception is returned the createRide method is not well implemented.
	public void test9() {
		String driverUsername="Test driver";
		
		String rideFrom="Donostia";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		
		Driver driver1=new Driver(driverUsername,"123");

		//configure the state through mocks 
        Mockito.when(db.find(Driver.class, driver1.getUsername())).thenReturn(driver1);	
		try {
			OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideFrom, rideDate);
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(odw, 2, 10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
			}

		   }
	
	
	
	@Test
	//sut.createRide:  The Driver("Driver Test") HAS one ride "from" "to" in that "date". 
	public void test10() {


        
		String driverUsername="Driver Test";
		String driverPassword="123";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
					
			 driver=new Driver(driverUsername,driverPassword);
			 driver.addRide(rideFrom, rideTo, rideDate, 2, 10);
			//configure the state through mocks 
	        Mockito.when(db.find(Driver.class, driver.getUsername())).thenReturn(driver);
		
			OriginDestinationWhen odw=new OriginDestinationWhen(rideFrom, rideTo, rideDate);
			//invoke System Under Test (sut)  
			sut.open();
		    sut.createRide(odw, 2, 10, driverUsername);
			sut.close();
			
			fail();
			
		   } catch (RideAlreadyExistException e) {
			 //verify the results
				sut.close();
				assertTrue(true);
			} catch (RideMustBeLaterThanTodayException e) {
			// TODO Auto-generated catch block
			fail();
		} 
	} 
	

}
