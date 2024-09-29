package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Ride implements Serializable {
	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer rideNumber;
	private String from;
	private String to;
	private int nPlaces;
	private Date date;
	private double price;
	@ManyToOne
	private Driver driver;
	@OneToMany(mappedBy = "ride")
	private List<Booking> bookings;
	private boolean active;

	public Ride(String from, String to, Date date, int nPlaces, double price, Driver driver) {
		super();
		this.from = from;
		this.to = to;
		this.nPlaces = nPlaces;
		this.date = date;
		this.price = price;
		this.driver = driver;
		this.active = true;		this.bookings = new ArrayList<Booking>();
	}

	/**
	 * Get the number of the ride
	 * 
	 * @return the ride number
	 */
	public Integer getRideNumber() {
		return rideNumber;
	}

	/**
	 * Set the ride number to a ride
	 * 
	 * @param ride Number to be set
	 */

	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}

	/**
	 * Get the origin of the ride
	 * 
	 * @return the origin location
	 */

	public String getFrom() {
		return from;
	}

	/**
	 * Set the origin of the ride
	 * 
	 * @param origin to be set
	 */

	public void setFrom(String origin) {
		this.from = origin;
	}

	/**
	 * Get the destination of the ride
	 * 
	 * @return the destination location
	 */

	public String getTo() {
		return to;
	}

	/**
	 * Set the origin of the ride
	 * 
	 * @param destination to be set
	 */
	public void setTo(String destination) {
		this.to = destination;
	}

	/**
	 * Get the free places of the ride
	 * 
	 * @return the available places
	 */

	/**
	 * Get the date of the ride
	 * 
	 * @return the ride date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the date of the ride
	 * 
	 * @param date to be set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public int getnPlaces() {
		return nPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param nPlaces places to be set
	 */

	public void setBetMinimum(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public String toString() {
		return from + "; " + to + "; " + date + "; " + nPlaces+" "+price;
	}

	public void setnPlaces(int f) {
		this.nPlaces = f;

	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
