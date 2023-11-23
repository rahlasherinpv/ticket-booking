package com.ticket.bean;

public class Seat {

	private int seatNo;
	private boolean isVaccant=false;
	private Passenger passenger;
	public Seat(int seatNo, boolean isVaccant, Passenger passenger) {
		super();
		this.seatNo = seatNo;
		this.isVaccant = isVaccant;
		this.passenger = passenger;
	}
	public Seat() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}
	public boolean isVaccant() {
		return isVaccant;
	}
	public void setVaccant(boolean isVaccant) {
		this.isVaccant = isVaccant;
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	@Override
	public String toString() {
		return "Seat [seatNo=" + seatNo + ", isVaccant=" + isVaccant + ", passenger=" + passenger + "]";
	}
	
}
