package domain;

import java.util.Date;

public class OriginDestinationWhen {
	private String origin;
	private String destination;
	private Date date;
	
	public OriginDestinationWhen(String origin, String destination, Date date) {
		this.origin = origin;
		this.destination = destination;
		this.date = date;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
}
