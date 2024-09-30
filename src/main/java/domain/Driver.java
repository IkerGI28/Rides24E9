package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlIDREF
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Car> cars = new ArrayList<>();
	@XmlIDREF
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Ride> createdRides = new Vector<Ride>();
	
	transient Logger logger = Logger.getLogger(getClass().getName());

	public Driver(String username, String passwd) {
		super(username, passwd, "Driver");
	}
	
	@Override
	public String toString() {
		return (super.toString());
	}

	public List<Ride> getCreatedRides() {
		return createdRides;
	}

	public void setCreatedRides(List<Ride> createdRides) {
		this.createdRides = createdRides;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void addCar(Car kotxea) {
		cars.add(kotxea);
		kotxea.setDriver(this);
	}

	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual
	 * profit
	 * 
	 * @param question   to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, int nPlaces, float price) {
		Ride ride = new Ride(from, to, date, nPlaces, price, this);
		createdRides.add(ride);
		return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location
	 * @param to   the destination location
	 * @param date the date of the ride
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date) {
		for (Ride r : createdRides)
			if ((java.util.Objects.equals(r.getFrom(), from)) && (java.util.Objects.equals(r.getTo(), to))
					&& (java.util.Objects.equals(r.getDate(), date)))
				return true;

		return false;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);

	}

	public Ride removeRide(String from, String to, Date date) {
		Ride r=null;
		int pos=0;
		int index=0;
		boolean encontrado=false;
		for (Ride ride : createdRides) {
			//if (ride.getFrom().equals(from) && ride.getTo().equals(to) && ride.getDate().equals(date)) {
			if ((ride.getFrom()==from) && (ride.getTo()==to) && (ride.getDate().equals(date))) {

				encontrado=true;
				pos=index;
			}
			index++;
		}
		if (encontrado) {
			    if (logger.isLoggable(Level.INFO)) {
			        logger.info(String.format("Position: %d", pos));
			    }
				 r=createdRides.get(pos);
				 System.out.println("ride recuperado "+r);
				 createdRides.remove(pos);
			}
		return r;
	}

	public Car bilatumatrikula(String matri) {
		boolean atera = false;
		Car kotx = null;
		int pos = 0;
		while (!atera) {
			if (cars.get(pos).getMatrikula().equals(matri)) {
				kotx = cars.get(pos);
				atera = true;
			}
		}
		return kotx;
	}

	public void removeCar(Car kotxe) {
		cars.remove(kotxe);
	}
}
