package application;

import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginController {
	@FXML
	private Button button;
	
	public void buttonPressed() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:sqlite:hotel.db");
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from Hotel");
			while(rs.next())
			{
				System.out.println("hotelName = " + rs.getString("Name"));
				System.out.println("hotelLocation = " + rs.getString("Location"));
			}
			rs.close();
		}
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
	}
}
