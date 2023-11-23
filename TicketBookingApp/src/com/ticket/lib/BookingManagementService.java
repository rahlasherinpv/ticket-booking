package com.ticket.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


import com.ticket.bean.Bus;
import com.ticket.bean.Passenger;
import com.ticket.bean.Seat;

public class BookingManagementService {
	Bus bus;
	HashMap<Integer, List<Passenger>> ticketMap;

	public BookingManagementService() {
		super();
		bus = new Bus();

		List<Seat> seats = new ArrayList<Seat>();
		for (int i = 1; i <= 10; i++) {
			Seat seat = new Seat();
			seat.setSeatNo(i);
			seats.add(seat);
		}
		bus.setSeats(seats);
		
		ticketMap = new HashMap<Integer, List<Passenger>>();

		try {
			loadTicketDetails();
		}catch(FileNotFoundException e) {}
		

	}

	Scanner input = new Scanner(System.in);
	String choice = "";

//load data from the file
	private void loadTicketDetails() throws FileNotFoundException {
		String fileName = "ticket.txt";
		Scanner scanner = new Scanner(new File(fileName));
		int tempTicketNo = 0;
		List<Passenger> passengers = new ArrayList<Passenger>();

		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] ticketData = line.split(",");
			if (ticketData.length == 6) {
				int ticketno = Integer.parseInt(ticketData[0]);
				int seatNo = Integer.parseInt(ticketData[1]);
				String passengername = ticketData[2];
				int age = Integer.parseInt(ticketData[3]);
				String gender = ticketData[4];
				String passengerNo = (ticketData[5]);

				Passenger passenger = new Passenger(passengername, gender, age, ticketno, passengerNo);

				for (Seat seat : bus.getSeats()) {
					if (seat.getSeatNo() == seatNo) {
						seat.setPassenger(passenger);
						seat.setVaccant(true);

					}
				}

//				if (passengers.isEmpty() && ticketMap.isEmpty()) {
//					tempTicketNo = ticketno;
//					passengers.add(passenger);
//
//				} else {
//					if (tempTicketNo == ticketno) {
//						passengers.add(passenger);
//					} else {
//						ticketMap.put(tempTicketNo, passengers);
//
//						passengers.clear();
//						tempTicketNo = ticketno;
//						passengers.add(passenger);
//					}
//				}
				//ticketMap.computeIfAbsent(ticketno, k -> new ArrayList<>()).add(passenger);
				if (!ticketMap.containsKey(ticketno)) {
				    ticketMap.put(ticketno, new ArrayList<>());
				}
				ticketMap.get(ticketno).add(passenger);

				
				
			}
		}

		System.out.println(bus);
		System.out.println(ticketMap);
		scanner.close();
	}

//menu driven	
	public void menuDriven() throws IOException {
		do {
			System.out.println("\n\tWELCOME TO ROYAL TRAVELS");
			System.out.println("==============================================================================");
			System.out.println("\n\t1.Book Ticker " + "\n\t2.Cancel Ticket " + "\n\t3.No Of Available Seats"
					+ "\n\t4.Available Women's seats" + "\n\t5.List Of Passengers Deatails" + "\n\t6.List Vaccant Seats"
					+ "\n\t7.List Of Booked Tickets" + "\n\t0.exit");
			System.out.println("\nEnter Choice:");

			choice = input.nextLine();
			switch (choice) {
			case "1":
				booktickets();
				break;
			case "2":
				cancelTickets();
				break;
			case "3":
				noOfavailableSeats();
				break;
			case "4":
				availableWomenSeats();
				break;
			case "5":
				listOfPassengerDetails();
				break;
			case "6":
				listvaccantSeats();
				break;
			case "7":
				listBookedTickets();
				break;
			case "0":
				System.exit(0);

			default:
				System.out.println("Enter a valid input");
			}
		} while (true);
	}

// book tickets
	private void booktickets() throws IOException {
		int lastKey = 0;
		List<Passenger> passengers = new ArrayList<Passenger>();
		List<String> tempPassengerData = new ArrayList();
		List<String> updatedPassengerData = new ArrayList();

		System.out.println("Enter the number of passengers");
		int tempPassengerNo = Integer.parseInt(input.nextLine());

		for (int i = 1; i <= tempPassengerNo; i++) {
			Passenger passenger = new Passenger();

			System.out.println("Enter Name:");
			passenger.setPassengerName(input.nextLine());
			System.out.println("Enter  Age:");
			passenger.setAge(Integer.parseInt(input.nextLine()));
			System.out.println("Enter Gender:");
			passenger.setGender(input.nextLine());

			/*
			 * checking the seats availability
			 */

			HashSet<Integer> availableSeats = new HashSet<Integer>();
			if (passenger.getGender().equalsIgnoreCase("female")) {
				for (Seat seat : bus.getSeats()) {
					if (seat.isVaccant() == false) {
						availableSeats.add(seat.getSeatNo());
					}
				}

			} else {
				for (Seat seat : bus.getSeats()) {
					if (seat.isVaccant() == false && seat.getSeatNo() > bus.getWomenSeatCapacity()) {
						availableSeats.add(seat.getSeatNo());
					}
				}

			}
			if (availableSeats.isEmpty()) {
				System.out.println("No available seats");
				if (i != tempPassengerNo) {
					System.out.println("Do you want to continue?(y/n)");
					if (input.nextLine().equalsIgnoreCase("n")) {
						i = tempPassengerNo++;
					}
				}
				break;
			} else {
				System.out.println("Available seats are: ");
				System.out.println(availableSeats);
			}

			/*
			 * booking the seat
			 */
			int flag = 0;

			System.out.println("Enter the seatNo");
			int tempSeatno = Integer.parseInt(input.nextLine());
			do {
				if (availableSeats.contains(tempSeatno)) {
					for (Seat seat : bus.getSeats()) {
						if (seat.getSeatNo() == tempSeatno) {
							System.out.println("Do you want to book this seat(y/n)");
							if (input.nextLine().equalsIgnoreCase("y")) {
								seat.setPassenger(passenger);
								seat.setVaccant(true);
								passengers.add(passenger);
								String passengerStr = seat.getSeatNo() + "," + passenger.getPassengerName() + ","
										+ passenger.getAge() + "," + passenger.getGender();

								tempPassengerData.add(passengerStr);

								System.out.println("seat book succssefuly");
								flag = 1;
							} else {
//								System.out.println("Do you want to continue?(y/n)");
//								if(input.nextLine().equalsIgnoreCase("n")) {
//									i=tempPassengerNo++;
//								}else {
//									break;
//								}
								flag = 1;
							}
						}
					}
					if (flag == 1) {
						break;
					}
				} else {
					System.out.println("Enter valid seat number");
					tempSeatno = Integer.parseInt(input.nextLine());
				}
			} while (true);
		}
		/*
		 * setting the Ticket number
		 */
		int tempTicketNo = 0;
		if (!passengers.isEmpty()) {
			if (ticketMap.isEmpty()) {
				tempTicketNo = 1001;
				int i =   1;
				for (Passenger p : passengers) {
					p.setTicketno(tempTicketNo);
					String strPassNo=Integer.toString(tempTicketNo)+"."+Integer.toString(i);
					p.setPassengerNo(strPassNo);
					i = i + 1;
				}
				int j = 1;
				for(String data:tempPassengerData) {
					String strPassNo=Integer.toString(tempTicketNo)+"."+Integer.toString(j);
					data=data+","+strPassNo;
					updatedPassengerData.add(data);
					j=j+1;
				}
				ticketMap.put(1001, passengers);

			} else {
				for (Integer key : ticketMap.keySet()) {
					lastKey = key;
				}
				tempTicketNo = lastKey + 1;
				int i =   1;
				for (Passenger p : passengers) {
					p.setTicketno(tempTicketNo);
					String strPassNo=Integer.toString(tempTicketNo)+"."+Integer.toString(i);
					p.setPassengerNo(strPassNo);
					i = i + 1;
				}
				int j = 1;
				for(String data:tempPassengerData) {
					String strPassNo=Integer.toString(tempTicketNo)+"."+Integer.toString(j);
					data=data+","+strPassNo;
					updatedPassengerData.add(data);
					j=j+1;
				}
				
				ticketMap.put(tempTicketNo, passengers);
			}
			
			
			
			for (Entry<Integer, List<Passenger>> entry : ticketMap.entrySet()) {
				System.out.println(entry.getKey() + " -> " + entry.getValue());
			}
		}
		System.out.println(bus);
		/*
		 * storing details to file
		 */
		FileWriter myWriter = new FileWriter("ticket.txt", true);
		for (String data : updatedPassengerData) {
			String dataTicket = tempTicketNo + "," + data;
			myWriter.write(dataTicket);
			myWriter.write(System.lineSeparator());
		}
		myWriter.close();

	}

// cancel ticket
	private void cancelTickets() {

		
		System.out.println("Enter the ticket number");
		int cancelTicketNo = Integer.parseInt(input.nextLine());
		if (ticketMap.containsKey(cancelTicketNo)) {
			List<Passenger> passengers = ticketMap.get(cancelTicketNo);

			if (passengers.size() > 1) { // handling the multiple passenger tickets
				System.out.println("do you want to cancel the entire ticket(y/n)");
				String choice = input.nextLine();
				if (choice.equalsIgnoreCase("y")) {
					cancelTicket(cancelTicketNo);

				} else if (choice.equalsIgnoreCase("n")) {
					cancelPassenger(cancelTicketNo);
					do {

						System.out.println("Do you want to continue?(y/n)");
						String choiceTemp = input.nextLine();
						if (choiceTemp.equalsIgnoreCase("y")) {
							cancelPassenger(cancelTicketNo);
						} else if (choiceTemp.equalsIgnoreCase("n")) {
							break;
						}

					} while (true);

				} else {
					System.out.println("invalid input");

				}
			} else { // handling the single passenger ticket
				cancelTicket(cancelTicketNo);

			}
		} else {
			System.out.println("Ticket not found");
		}

		System.out.println(bus);
	}
	
	
//cancel individual passengers
	private void cancelPassenger(int cancelTicketNo) {
		
		
		System.out.println("Enter passenger Number");
		String cancelPassengerNo = input.nextLine();
		String lineToRemove =(cancelPassengerNo);
		List<Passenger> passengers = ticketMap.get(cancelTicketNo);
		
		
		for (Iterator<Passenger> iterator = passengers.iterator(); iterator.hasNext();) {
			
			Passenger p = iterator.next();
		
			if ( p.getPassengerNo().equalsIgnoreCase(cancelPassengerNo)) {
				System.out.println(p.getPassengerNo());
				iterator.remove();

				ticketMap.put(cancelTicketNo, passengers);
			}
		}
		for (Seat seat : bus.getSeats()) {
			if (seat.getPassenger()!=null &&seat.getPassenger().getPassengerNo().equalsIgnoreCase(cancelPassengerNo)) {
				seat.setPassenger(null);
				seat.setVaccant(false);
			}
		}

		String fileName = "ticket.txt";
        
        try {
            File inputFile = new File(fileName);
            File tempFile = new File(fileName + ".tmp");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // If the current line matches the line to remove, skip it
                if (currentLine.contains(lineToRemove)) {
                    continue;
                }
                writer.write(currentLine);
                writer.newLine();
            }

            writer.close();
            reader.close();

            // Delete the original file and rename the temporary file to the original filename
            inputFile.delete();
            tempFile.renameTo(inputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		System.out.println(bus);
	}

	
//cancel the entire tickets
	private void cancelTicket(int cancelTicketNo) {
		String lineToRemove = Integer.toString(cancelTicketNo);
		ticketMap.remove(cancelTicketNo);
		for (Seat seat : bus.getSeats()) {
			if (seat.getPassenger()!=null&& seat.getPassenger().getTicketno() == cancelTicketNo) {
				seat.setPassenger(null);
				seat.setVaccant(false);
			}
		}
		
		 String fileName = "ticket.txt";
	        
	        try {
	            File inputFile = new File(fileName);
	            File tempFile = new File(fileName + ".tmp");

	            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

	            String currentLine;

	            while ((currentLine = reader.readLine()) != null) {
	                // If the current line matches the line to remove, skip it
	                if (currentLine.contains(lineToRemove)) {
	                    continue;
	                }
	                writer.write(currentLine);
	                writer.newLine();
	            }

	            writer.close();
	            reader.close();

	            // Delete the original file and rename the temporary file to the original filename
	            inputFile.delete();
	            tempFile.renameTo(inputFile);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		
		
	}
//Show all booked tickets and corresponding passenger's details. 
	private void listBookedTickets() {
		System.out.println("\n\tBOOKED TICKETS");
		System.out.println("==============================================================================");
		for (Entry<Integer, List<Passenger>> entry : ticketMap.entrySet()) {
            System.out.println("Ticket no: "+entry.getKey() + " -> "  );
            for(Passenger p:entry.getValue()) {
            	System.out.println(" Passenger No: "+p.getPassengerNo()+" Passenger Name: "+p.getPassengerName()+
            			" Passenger Gender: "+p.getGender());
            }
        }
		
	}
//Show remaining seats with seat number. 
	private void listvaccantSeats() {
		System.out.println("LIST OF VACCANT SEATS\n");
		for(Seat s:bus.getSeats()) {
			if(s.isVaccant()==false) {
				System.out.print(s.getSeatNo()+" ,");
			}
		}
	}
//Show passenger details with seat number. 
	private void listOfPassengerDetails() {
		for(Seat s:bus.getSeats()) {
			if(s.isVaccant()==true) {
				System.out.println("Seat No: "+s.getSeatNo()+ " Passenger Name: "+s.getPassenger().getPassengerName()+ " Ticket No: "+s.getPassenger().getPassengerNo()+" Gender: "+ s.getPassenger().getGender() );
			}
		}

	}
//Show available women's seats. 
	private void availableWomenSeats() {
		int i=0;
		for (Seat seat : bus.getSeats()) {
			if (seat.isVaccant() == false && seat.getSeatNo() <= bus.getWomenSeatCapacity()) {
				i++;
			}
		}
		System.out.println("No of available Women's seats are :"+i);
	}
//Show available total seats. 
	private void noOfavailableSeats() {

		int i=0;
		for (Seat seat : bus.getSeats()) {
			if (seat.isVaccant() == false) {
				i++;
			}
		}
		System.out.println("No of available seats are :"+i);
	}

}
