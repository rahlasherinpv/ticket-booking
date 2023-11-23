package com.ticket.bean;

import java.util.List;

public class Bus {

	private int seatCapacity=10;
	private int womenSeatCapacity=2;
	private List<Seat> seats;

	public Bus(List<Seat> seats) {
		super();
		this.seats = seats;
	}

	public Bus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	
	
	public int getSeatCapacity() {
		return seatCapacity;
	}

	public void setSeatCapacity(int seatCapacity) {
		this.seatCapacity = seatCapacity;
	}

	public int getWomenSeatCapacity() {
		return womenSeatCapacity;
	}

	public void setWomenSeatCapacity(int womenSeatCapacity) {
		this.womenSeatCapacity = womenSeatCapacity;
	}
	
	
	@Override
	public String toString() {
		return "Bus [seats=" + seats + "]";
	}

	
	
}
