package domain;

import java.util.Date;

public class ErreklamazioInfo {
	private String nor;
	private String nori;
	private Date gaur;
	
	public ErreklamazioInfo(String nor, String nori, Date gaur) {
		this.nor = nor;
		this.nori = nori;
		this.gaur = gaur;
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

	public Date getGaur() {
		return gaur;
	}

	public void setGaur(Date gaur) {
		this.gaur = gaur;
	}
	
}
