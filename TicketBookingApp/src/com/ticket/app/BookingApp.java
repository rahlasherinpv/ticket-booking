package com.ticket.app;

import java.io.IOException;

import com.ticket.lib.BookingManagementService;

public class BookingApp {

	public static void main(String[] args) throws IOException {
		BookingManagementService bookingManagementService =new BookingManagementService();
		bookingManagementService.menuDriven();

	}

}
