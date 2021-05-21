package application;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBManager {
	
	//Returns the role of the given username/password according to the database hotel.db
	//Check hotel.db for what the role integer represents.
	public int CheckRoleOfUser(String username, String password) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = null;
		int roleresult = -1;
		try
		{
			conn = DriverManager.getConnection("jdbc:sqlite:hotel.db");
			
			PreparedStatement loginQuery = conn.prepareStatement("select * from Profile where Username = ? and Password = ?");
			loginQuery.setString(1, username);
			loginQuery.setString(2, password);
			ResultSet rs = loginQuery.executeQuery();
			if (rs.next())
			{
				roleresult = rs.getInt("Role");
			}
			rs.close();
		}
		//TODO: make user-friendly error handlers after every catch.
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
			}
			catch(SQLException e)
			{
				System.err.println(e);
			}
		}
		return roleresult;
	}
	
	public ObservableList<String> GiveHotelNames() throws ClassNotFoundException {
		ObservableList<String> hotels = FXCollections.observableArrayList();
		Class.forName("org.sqlite.JDBC");
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:sqlite:hotel.db");
			
			PreparedStatement query = conn.prepareStatement("select distinct Name from HOTEL order by Name ASC");
			ResultSet rs = query.executeQuery();
			while (rs.next())
			{
				hotels.add(rs.getString("Name"));
			}
			rs.close();
		}
		//TODO: make user-friendly error handlers after every catch.
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
			}
			catch(SQLException e)
			{
				System.err.println(e);
			}
		}
		return hotels;
	}
	
	public ObservableList<String> GiveRoomTypes() throws ClassNotFoundException {
		ObservableList<String> hotels = FXCollections.observableArrayList();
		Class.forName("org.sqlite.JDBC");
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:sqlite:hotel.db");
			
			PreparedStatement query = conn.prepareStatement("select distinct Room_Type from ROOM order by Room_Type ASC");
			ResultSet rs = query.executeQuery();
			while (rs.next())
			{
				hotels.add(rs.getString("Room_Type"));
			}
			rs.close();
		}
		//TODO: make user-friendly error handlers after every catch.
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
			}
			catch(SQLException e)
			{
				System.err.println(e);
			}
		}
		return hotels;
	}
	
	public Queue<Room> FindRoomsBasedOnSearch(String hotel, String roomType, String entrydate, String exitdate) throws ClassNotFoundException {
		Queue<Room> RoomList = new LinkedList<Room>();
		Class.forName("org.sqlite.JDBC");
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:sqlite:hotel.db");

			String query = "select * from room";
			if (!(hotel.equals("Select a hotel") && roomType.equals("Select a room type") && entrydate.isBlank() && exitdate.isBlank())) {
				query = query.concat(" where");
				if (!hotel.equals("Select a hotel")) query = query.concat(" hotel_name = '" + hotel + "' AND");
				if (!roomType.equals("Select a room type")) query = query.concat(" room_type = '" + roomType + "' AND");
				if (!entrydate.isBlank()) query = query.concat(" NOT EXISTS ("
						+ "SELECT * FROM booking WHERE"
						+ " booking.Room_ID = room.Room_ID AND"
						+ " Entry_Date <= '" + entrydate + "' AND"
						+ " '" + entrydate + "' < Exit_Date) AND");
				if (!exitdate.isBlank()) query = query.concat(" NOT EXISTS ("
						+ "SELECT * FROM booking WHERE"
						+ " booking.Room_ID = room.Room_ID AND"
						+ " Entry_Date < '" + exitdate + "' AND"
						+ " '" + exitdate + "' <= Exit_Date) AND");
				query = query.substring(0, query.length()-4);
			}
			PreparedStatement roomSearchQuery = conn.prepareStatement(query);
			ResultSet rs = roomSearchQuery.executeQuery();
			while (rs.next())
			{
				Room roomItem = new Room(
						rs.getDouble("Room_ID"),
						rs.getString("Hotel_Name"),
						rs.getString("Room_Type"),
						rs.getString("Hotel_Location"));
				query = "SELECT * FROM booking WHERE Room_ID = " + roomItem.getRoomID();
				PreparedStatement bookingSearchQuery = conn.prepareStatement(query);
				ResultSet bookingResults = bookingSearchQuery.executeQuery();
				while (bookingResults.next()) {
					roomItem.addBooking(LocalDate.parse(bookingResults.getString("Entry_Date")), LocalDate.parse(bookingResults.getString("Exit_Date")));
				}
				bookingResults.close();
				RoomList.add(roomItem);
			}
			rs.close();
		}
		//TODO: make user-friendly error handlers after every catch.
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
			}
			catch(SQLException e)
			{
				System.err.println(e);
			}
		}
		return RoomList;
	}
	
	
}