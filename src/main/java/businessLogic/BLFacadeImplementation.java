package businessLogic;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import domain.User;
//import domain.Admin;
import domain.Alert;
import domain.Booking;
import domain.Car;
import domain.Discount;
import domain.Driver;
import domain.Complaint;
import domain.Movement;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");

		dbManager = new DataAccess();

		// dbManager.close();

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		@SuppressWarnings("unused")
		ConfigXML c = ConfigXML.getInstance();

		dbManager = da;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<String> getDepartCities() {
		dbManager.open();

		List<String> departLocations = dbManager.getDepartCities();

		dbManager.close();

		return departLocations;

	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<String> getDestinationCities(String from) {
		dbManager.open();

		List<String> targetCities = dbManager.getArrivalCities(from);

		dbManager.close();

		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverName)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException {

		dbManager.open();
		Ride ride = dbManager.createRide(from, to, date, nPlaces, price, driverName);
		dbManager.close();
		return ride;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Ride> getRides(String from, String to, Date date) {
		dbManager.open();
		List<Ride> rides = dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		dbManager.open();
		List<Date> dates = dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

	@Override
	public User getUser(String erab) {
		dbManager.open();
		User u = dbManager.getUser(erab);
		dbManager.close();
		return u;
	}

	@Override
	public double getActualMoney(String erab) {
		dbManager.open();
		double money = dbManager.getActualMoney(erab);
		dbManager.close();
		return money;
	}

	@Override
	public boolean isRegistered(String erab, String passwd) {
		dbManager.open();
		boolean registered = dbManager.isRegistered(erab, passwd);
		dbManager.close();
		return registered;
	}

	@Override
	public Driver getDriver(String erab) {
		dbManager.open();
		Driver d = dbManager.getDriver(erab);
		dbManager.close();
		return d;
	}

	@Override
	public Traveler getTraveler(String erab) {
		dbManager.open();
		Traveler t = dbManager.getTraveler(erab);
		dbManager.close();
		return t;
	}

	/*@Override
	public Admin getAdmin(String erab) {
		dbManager.open();
		Admin a = dbManager.getAdmin(erab);
		dbManager.close();
		return a;
	}*/

	@Override
	public String getMotaByUsername(String erab) {
		dbManager.open();
		String mota = dbManager.getMotabyUsername(erab);
		dbManager.close();
		return mota;
	}

	@Override
	public boolean addDriver(String username, String password) {
		dbManager.open();
		boolean ondo = dbManager.addDriver(username, password);
		dbManager.close();
		return ondo;
	}

	@Override
	public boolean addTraveler(String username, String password) {
		dbManager.open();
		boolean ondo = dbManager.addTraveler(username, password);
		dbManager.close();
		return ondo;
	}

	@Override
	public boolean gauzatuEragiketa(String username, double amount, boolean deposit) {
		if (amount <= 0)
			return false;
		boolean ondo;
		dbManager.open();
		ondo = dbManager.gauzatuEragiketa(username, amount, deposit);
		dbManager.close();
		return ondo;
	}

	@Override
	public boolean bookRide(String username, Ride ride, int seats, double desk) {
		boolean ondo;
		dbManager.open();
		ondo = dbManager.bookRide(username, ride, seats, desk);
		dbManager.close();
		return ondo;
	}

	@Override
	public List<Movement> getAllMovements(User user) {
		dbManager.open();
		List<Movement> moves = dbManager.getAllMovements(user);
		dbManager.close();
		return moves;
	}

	@Override
	public void addMovement(User user, String eragiketa, double amount) {
		dbManager.open();
		dbManager.addMovement(user, eragiketa, amount);
		dbManager.close();
	}

	@Override
	public List<Booking> getBookedRides(String username) {
		dbManager.open();
		List<Booking> rides = dbManager.getBookedRides(username);
		dbManager.close();
		return rides;
	}

	@Override
	public List<Booking> getPastBookedRides(String username) {
		dbManager.open();
		List<Booking> rides = dbManager.getPastBookedRides(username);
		dbManager.close();
		return rides;
	}

	@Override
	public void updateTraveler(Traveler traveler) {
		dbManager.open();
		dbManager.updateTraveler(traveler);
		dbManager.close();
	}

	@Override
	public void updateDriver(Driver driver) {
		dbManager.open();
		dbManager.updateDriver(driver);
		dbManager.close();
	}

	@Override
	public void updateUser(User user) {
		dbManager.open();
		dbManager.updateUser(user);
		dbManager.close();
	}

	@Override
	public void updateBooking(Booking booking) {
		dbManager.open();
		dbManager.updateBooking(booking);
		dbManager.close();
	}

	@Override
	public List<Booking> getBookingFromDriver(String username) {
		dbManager.open();
		List<Booking> bookings = dbManager.getBookingFromDriver(username);
		dbManager.close();
		return bookings;
	}

	@Override
	public List<Ride> getRidesByDriver(String username) {
		dbManager.open();
		List<Ride> rides = dbManager.getRidesByDriver(username);
		dbManager.close();
		return rides;
	}

	@Override
	public void cancelRide(Ride ride) {
		dbManager.open();
		dbManager.cancelRide(ride);
		dbManager.close();
	}

	@Override
	public boolean addCar(String username, Car kotxe) {
		dbManager.open();
		boolean ondo = dbManager.addCar(username, kotxe);
		dbManager.close();
		return ondo;
	}

	@Override
	public boolean isAdded(String username, String matr) {
		dbManager.open();
		boolean registered = dbManager.isAdded(username, matr);
		dbManager.close();
		return registered;
	}

	@Override
	public Car getKotxeByMatrikula(String matr) {
		dbManager.open();
		Car kotxe = dbManager.getKotxeByMatrikula(matr);
		dbManager.close();
		return kotxe;
	}

	@Override
	public boolean erreklamazioaBidali(String nor, String nori, Date gaur, Booking book, String textua, boolean aurk) {
		dbManager.open();
		boolean sent = dbManager.erreklamazioaBidali(nor, nori, gaur, book, textua, aurk);
		dbManager.close();
		return sent;
	}

	@Override
	public void updateComplaint(Complaint erreklamazioa) {
		dbManager.open();
		dbManager.updateComplaint(erreklamazioa);
		dbManager.close();
	}

	@Override
	public void createDiscount(Discount di) {
		dbManager.open();
		dbManager.createDiscount(di);
		dbManager.close();
	}

	@Override
	public List<Discount> getAllDiscounts() {
		dbManager.open();
		List<Discount> dis = dbManager.getAllDiscounts();
		dbManager.close();
		return dis;
	}

	@Override
	public void deleteDiscount(Discount dis) {
		dbManager.open();
		dbManager.deleteDiscount(dis);
		dbManager.close();
	}

	@Override
	public void updateDiscount(Discount dis) {
		dbManager.open();
		dbManager.updateDiscount(dis);
		dbManager.close();
	}

	@Override
	public Discount getDiscount(String desk) {
		dbManager.open();
		Discount dis = dbManager.getDiscount(desk);
		dbManager.close();
		return dis;
	}

	@Override
	public void deleteCar(Car car) {
		dbManager.open();
		dbManager.deleteCar(car);
		dbManager.close();
	}

	@Override
	public List<User> getUserList() {
		dbManager.open();
		List<User> lu = dbManager.getUserList();
		dbManager.close();
		return lu;
	}

	@Override
	public void deleteUser(User us) {
		dbManager.open();
		dbManager.deleteUser(us);
		dbManager.close();
	}

	@Override
	public List<Alert> getAlertsByUsername(String username) {
		dbManager.open();
		List<Alert> la = dbManager.getAlertsByUsername(username);
		dbManager.close();
		return la;
	}

	@Override
	public Alert getAlert(int alertNumber) {
		dbManager.open();
		Alert a = dbManager.getAlert(alertNumber);
		dbManager.close();
		return a;

	}

	@Override
	public void updateAlert(Alert alert) {
		dbManager.open();
		dbManager.updateAlert(alert);
		dbManager.close();
	}

	@Override
	public boolean updateAlertaAurkituak(String username) {
		dbManager.open();
		boolean ema = dbManager.updateAlertaAurkituak(username);
		dbManager.close();
		return ema;
	}

	@Override
	public boolean createAlert(Alert alert) {
		dbManager.open();
		boolean ema = dbManager.createAlert(alert);
		dbManager.close();
		return ema;
	}

	@Override
	public boolean deleteAlert(int alertNumber) {
		dbManager.open();
		boolean ema = dbManager.deleteAlert(alertNumber);
		dbManager.close();
		return ema;
	}

	@Override
	public Complaint getComplaintsByBook(Booking book) {
		dbManager.open();
		Complaint er = dbManager.getComplaintsByBook(book);
		dbManager.close();
		return er;
	}

}
