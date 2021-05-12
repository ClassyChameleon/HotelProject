package application;

import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
	@FXML
	private Button LoginButton;
	@FXML
	private TextField UsernameTextField;
	@FXML
	private TextField PasswordTextField;
	
	public void LoginButtonPressed() throws Exception {
		System.out.println("Button pressed");
		Class.forName("org.sqlite.JDBC");
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:sqlite:hotel.db");
			
			PreparedStatement loginQuery = conn.prepareStatement("select * from Profile where Username = ? and Password = ?");
			loginQuery.setString(1, UsernameTextField.getText());
			loginQuery.setString(2, PasswordTextField.getText());
			ResultSet rs = loginQuery.executeQuery();
			if (rs.next())
			{
				System.out.println("UserName = " + rs.getString("Username"));
				System.out.println("Password = " + rs.getString("Password"));
				System.out.println("Role = " + rs.getInt("Role"));
			} else {
				System.out.println("Sorry, no profile found");
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
