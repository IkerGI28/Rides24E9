package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlIDREF
	@OneToMany(mappedBy = "traveler", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Booking> bookedRides = new ArrayList<>();

	@XmlIDREF
	@OneToMany(mappedBy = "traveler", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Alert> alerts = new ArrayList<>();

	public Traveler(String username, String passwd) {
		super(username, passwd, "Traveler");
	}

	public List<Booking> getBookedRides() {
		return bookedRides;
	}

	public void setBookedRides(List<Booking> bookedRides) {
		this.bookedRides = bookedRides;
	}

	public List<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public void addBookedRide(Booking bookedRide) {
		bookedRides.add(bookedRide);
		bookedRide.setTraveler(this);
	}

	public void addAlert(Alert alert) {
		alerts.add(alert);
		alert.setTraveler(this);
	}

	public void removeAlert(Alert alert) {
		alerts.remove(alert);
	}
}
