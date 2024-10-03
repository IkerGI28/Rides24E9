package businessLogic;

import java.util.Date;
import java.util.List;
import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Alert;
import domain.Booking;
import domain.Car;
import domain.Discount;
import domain.Driver;
import domain.Complaint;
import domain.Movement;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

	/**
	 * This method returns all the cities where rides depart
	 * 
	 * @return collection of cities
	 */
	@WebMethod
	public List<String> getDepartCities();

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod
	public List<String> getDestinationCities(String from);

	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from    the origin location of a ride
	 * @param to      the destination location of a ride
	 * @param date    the date of the ride
	 * @param nPlaces available seats
	 * @param kotxe
	 * @param driver  to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today
	 * @throws RideAlreadyExistException         if the same ride already exists for
	 *                                           the driver
	 */
	@WebMethod
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverName)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException;

	/**
	 * This method retrieves the rides from two locations on a given date
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	@WebMethod
	public List<Ride> getRides(String from, String to, Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	@WebMethod
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);

	/**
	 * This method calls the data access to initialize the database with some events
	 * and questions. It is invoked only when the option "initialize" is declared in
	 * the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();

	public User getUser(String erab);

	public double getActualMoney(String erab);

	public boolean isRegistered(String erab, String passwd);

	public Driver getDriver(String erab);

	public Traveler getTraveler(String erab);

	//public Admin getAdmin(String erab);

	public String getMotaByUsername(String erab);

	public boolean addDriver(String username, String password);

	public boolean addTraveler(String username, String password);

	public boolean gauzatuEragiketa(String username, double amount, boolean b);

	public boolean bookRide(String username, Ride ride, int seats, double desk);

	public List<Movement> getAllMovements(User user);

	public void addMovement(User user, String eragiketa, double amount);

	public List<Booking> getBookedRides(String username);

	public void updateTraveler(Traveler traveler);

	public void updateDriver(Driver driver);

	public void updateUser(User user);

	public List<Booking> getPastBookedRides(String username);

	public void updateBooking(Booking booking);

	public List<Booking> getBookingFromDriver(String username);

	public List<Ride> getRidesByDriver(String username);

	public void cancelRide(Ride ride);

	public boolean addCar(String username, Car kotxe);

	public boolean isAdded(String username, String matr);

	public Car getKotxeByMatrikula(String matr);

	public void deleteCar(Car car);

	public boolean erreklamazioaBidali(String username1, String username2, Date gaur, Booking book, String textua,
			boolean aurk);

	public void updateComplaint(Complaint erreklamazioa);

	public void createDiscount(Discount di);

	public List<Discount> getAllDiscounts();

	public void deleteDiscount(Discount dis);

	public void updateDiscount(Discount dis);

	public Discount getDiscount(String desk);

	public List<User> getUserList();

	public void deleteUser(User us);

	public List<Alert> getAlertsByUsername(String username);

	public Alert getAlert(int alertNumber);

	public void updateAlert(Alert alert);

	public boolean updateAlertaAurkituak(String username);

	public boolean createAlert(Alert newAlert);

	public boolean deleteAlert(int alertNumber);

	public Complaint getComplaintsByBook(Booking bo);

}
