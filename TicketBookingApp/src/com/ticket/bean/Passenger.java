package com.ticket.bean;

public class Passenger {

	private String passengerName;
	private String gender;
	private int age;
	private int ticketno;
	private String passengerNo;
	public Passenger(String passengerName, String gender, int age, int ticketno, String passengerNo) {
		super();
		this.passengerName = passengerName;
		this.gender = gender;
		this.age = age;
		this.ticketno = ticketno;
		this.passengerNo = passengerNo;
	}
	
	public Passenger() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPassengerNo() {
		return passengerNo;
	}
	public void setPassengerNo(String strPassNo) {
		this.passengerNo = strPassNo;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getTicketno() {
		return ticketno;
	}
	public void setTicketno(int ticketno) {
		this.ticketno = ticketno;
	}
	@Override
	public String toString() {
		return "Passenger [passengerName=" + passengerName + ", gender=" + gender + ", age=" + age + ", ticketno="
				+ ticketno + ", passengerNo=" + passengerNo + "]";
	}
	
	
	
}
