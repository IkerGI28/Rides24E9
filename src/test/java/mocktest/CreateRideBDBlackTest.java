package mocktest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.Driver;

public class CreateRideBDBlackTest {

	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();

	@SuppressWarnings("unused")
	private Driver driver; 

	@Test
	//sut.createRide:  The Driver("Driver Test") HAS  NOT one ride "from" "to" in that "date". 
	// and the Ride must be created in DB
	//The test supposes that the "Driver Test" does not exist in the DB

	public void test1() {
		String driverUsername="Driver Test";
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
		
		testDA.open();
		
			testDA.createDriver(driverUsername,null);
		
		testDA.close();
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(rideFrom, rideTo, rideDate, 2, 10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNotNull(ride);
			
			//q is in DB
			testDA.open();
			boolean exist=testDA.existRide(driverUsername,rideFrom, rideTo, rideDate);
				
			assertTrue(exist);
			testDA.close();
			
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
		
		
		finally {   

			testDA.open();
				testDA.removeDriver(driverUsername);
			testDA.close();
			
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
		
		testDA.open();
		
			testDA.createDriver(driverUsername,null);
		
		testDA.close();
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(rideFrom, rideTo, rideDate, 2, 10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			//q is in DB
			testDA.open();
			boolean exist=testDA.existRide(driverUsername,rideFrom, rideTo, rideDate);
				
			assertTrue(!exist);
			testDA.close();
			
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
		
		
		finally {   

			testDA.open();
				testDA.removeDriver(driverUsername);
			testDA.close();
			
		        }
		   }
	
	@Test
	//sut.createRide:  The ride "to" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
	public void test3() {
		String driverUsername="Test driver";
		String rideFrom="Donostia";
		String rideTo=null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		
		testDA.open();
		
			testDA.createDriver(driverUsername,null);
		
		testDA.close();
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(rideFrom, rideTo, rideDate, 2, 10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			//q is in DB
			testDA.open();
			boolean exist=testDA.existRide(driverUsername,rideFrom, rideTo, rideDate);
				
			assertTrue(!exist);
			testDA.close();
			
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
		
		
		finally {   

			testDA.open();
				testDA.removeDriver(driverUsername);
			testDA.close();
			
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
		
		testDA.open();
			testDA.createDriver(driverUsername,null);
		testDA.close();
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(rideFrom, rideTo, rideDate, 2, 10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			//q is in DB
			testDA.open();
			boolean exist=testDA.existRide(driverUsername,rideFrom, rideTo, rideDate);

			assertTrue(!exist);
			testDA.close();
			
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
		
		
		finally {   

			testDA.open();
				testDA.removeDriver(driverUsername);
			testDA.close();
			
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
		
		testDA.open();
		
			testDA.createDriver(driverUsername,null);
		
		testDA.close();
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(rideFrom, rideTo, rideDate, -2, 0, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			//q is in DB
			testDA.open();
			boolean exist=testDA.existRide(driverUsername,rideFrom, rideTo, rideDate);
				
			assertTrue(!exist);
			testDA.close();
			
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
		
		
		finally {   

			testDA.open();
				testDA.removeDriver(driverUsername);
			testDA.close();
			
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
		
		testDA.open();
		
			testDA.createDriver(driverUsername,null);
		
		testDA.close();
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(rideFrom, rideTo, rideDate, 2, -10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			//q is in DB
			testDA.open();
			boolean exist=testDA.existRide(driverUsername,rideFrom, rideTo, rideDate);
				
			assertTrue(!exist);
			testDA.close();
			
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
		
		
		finally {   

			testDA.open();
				testDA.removeDriver(driverUsername);
			testDA.close();
			
		        }
		   }
	
	@Test
	//sut.createRide:  The driverUsername is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
		public void test7() {
		Ride ride=null;
			try {
				
				//define parameters
				driver=null;

				String rideFrom="Donostia";
				String rideTo="Zarautz";
				
				String driverUsername=null;

				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date rideDate=null;;
				try {
					rideDate = sdf.parse("05/10/2026");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				
				
				//invoke System Under Test (sut)  
				sut.open();
			    ride=sut.createRide(rideFrom, rideTo, rideDate, 2, 10, driverUsername);

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
		
		boolean driverCreated=false;

		try {
			rideDate = sdf.parse("05/10/2018");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			
			//define parameters
			testDA.open();
			if (!testDA.existDriver(driverUsername)) {
				testDA.createDriver(driverUsername,driverPassword);
			    driverCreated=true;
			}
			testDA.close();		
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.createRide(rideFrom, rideTo, rideDate, 2, 10, driverUsername);
			sut.close();
			
			fail();
			
		   } catch (RideMustBeLaterThanTodayException  e) {
			 //verify the results
				sut.close();
				assertTrue(true);
			} catch (RideAlreadyExistException e) {
				sut.close();
				fail();
		} finally {
				  //Remove the created objects in the database (cascade removing)   
				testDA.open();
				  if (driverCreated) 
					  testDA.removeDriver(driverUsername);
		          testDA.close();
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
		
		testDA.open();
		
			testDA.createDriver(driverUsername,null);
		
		testDA.close();
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(rideFrom, rideFrom, rideDate, 2, 10, driverUsername);
			sut.close();			
			
			//verify the results
			assertNull(ride);
			
			//q is in DB
			testDA.open();
			boolean exist=testDA.existRide(driverUsername,rideFrom, rideFrom, rideDate);
				
			assertTrue(!exist);
			testDA.close();
			
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
		
		
		finally {   

			testDA.open();
				testDA.removeDriver(driverUsername);
			testDA.close();
			
		        }
		   }
	
	@Test
	//sut.createRide:  The Driver("Driver Test") HAS  one ride "from" "to" in that "date". 
	// and the Exception RideAlreadyExistException must be thrown
	public void test10() {
		//define paramaters
		String driverUsername="Driver Test";

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
			//Check if exist this ride for this driver, and if exist, remove it.
			
			testDA.open();
			testDA.addDriverWithRide( driverUsername,  rideFrom,  rideTo,   rideDate,2,10);
			testDA.close();
			
			
			//invoke System Under Test (sut)  
			sut.open();
			Ride ride=sut.createRide(rideFrom, rideTo, rideDate, 2, 10, driverUsername);
			

			sut.close();
			//verify the results
			assertNotNull(ride);
			assertEquals(ride.getFrom(),rideFrom);
			assertEquals(ride.getTo(),rideTo);
			assertEquals(ride.getDate(),rideDate);
			
			fail();
		
			
		   } catch (RideAlreadyExistException e) {
			// if the program goes to this point fail  
				assertTrue(true);
			
			
			} catch (RideMustBeLaterThanTodayException e) {
				// if the program goes to this point fail  

			fail();
			
		} finally {

			testDA.open();
			//reestablish the state of the system (create object in the database)

				testDA.removeDriver(driverUsername);

			testDA.close();	      
		        }
		   } 
		   
}

