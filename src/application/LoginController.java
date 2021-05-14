package application;

import java.io.IOException;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
				if (rs.getInt("Role") == 1) {
					OpenHotelBooking(rs.getString("Username"), rs.getString("Password"));
				}
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
	
	private void OpenHotelBooking(String username, String password) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HotelBooking.fxml"));
		
		Stage stage = new Stage(StageStyle.DECORATED);
		try {
			stage.setScene(new Scene(loader.load()));
			HotelBookingController controller = loader.getController();
			controller.PassUserData(username, password);
			
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
