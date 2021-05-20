package application;

import java.time.LocalDate;
import java.util.Stack;

public class Room {
	private double RoomID;
	private String Hotel,Roomtype;
	private Stack<LocalDate> BookingEntry, BookingExit;
	
	//Class constructor.
	public Room(double id, String hotel, String roomType) {
		RoomID = id;
		Hotel = hotel;
		Roomtype = roomType;
	}
	
	//TODO: check if this is unnecessary, remove it if so.
	public double getRoomID() {
		return RoomID;
	}
	
	public String getHotel() {
		return Hotel;
	}
	
	public String getRoomType() {
		return Roomtype;
	}
	
	public boolean isBookingEmpty() {
		//Assuming we've kept both queues equal in size
		return BookingEntry.isEmpty();
	}
	
	public LocalDate[] popNextBooking() {
		LocalDate[] localDate = new LocalDate[2];
		if (!BookingEntry.isEmpty() && !BookingExit.isEmpty()) {
			localDate[0] = BookingEntry.pop();
			localDate[1] = BookingExit.pop();
			return localDate;
		}
		return null;
	}

	public void addBooking(LocalDate entry, LocalDate exit) {
		if (!(entry == null ^ exit == null) && entry.isBefore(exit)) {
			BookingEntry.push(entry);
			BookingExit.push(exit);
		}
	}
	
}