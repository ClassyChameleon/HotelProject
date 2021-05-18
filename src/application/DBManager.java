package application;

import java.sql.*;

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
			
			PreparedStatement loginQuery = conn.prepareStatement("select distinct Name from HOTEL order by Name ASC");
			ResultSet rs = loginQuery.executeQuery();
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
			
			PreparedStatement loginQuery = conn.prepareStatement("select distinct Room_Type from ROOM order by Room_Type ASC");
			ResultSet rs = loginQuery.executeQuery();
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
	
}