package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Complaint implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nor;
	private String nori;
	private Date noiz;
	@ManyToOne
	private Booking booking;
	private String deskripzioa;
	private Boolean aurkeztua;
    private String egoera;
	public Complaint(String nor, String nori, Date noiz, Booking book, String deskripzioa, boolean aurkeztua) {
		super();
		this.nor = nor;
		this.nori = nori;
		this.noiz = noiz;
		this.booking = book;
		this.deskripzioa = deskripzioa;
		this.aurkeztua = aurkeztua;
		this.egoera = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNor() {
		return nor;
	}

	public void setNor(String nor) {
		this.nor = nor;
	}

	public String getNori() {
		return nori;
	}

	public void setNori(String nori) {
		this.nori = nori;
	}

	public Date getNoiz() {
		return noiz;
	}

	public void setNoiz(Date noiz) {
		this.noiz = noiz;
	}

	public String getDeskripzioa() {
		return deskripzioa;
	}

	public void setDeskripzioa(String deskripzioa) {
		this.deskripzioa = deskripzioa;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Boolean getAurkeztua() {
		return aurkeztua;
	}

	public void setAurkeztua(Boolean aurkeztua) {
		this.aurkeztua = aurkeztua;
	}

	public String getEgoera() {
		return egoera;
	}

	public void setEgoera(String egoera) {
		this.egoera = egoera;
	}

}
