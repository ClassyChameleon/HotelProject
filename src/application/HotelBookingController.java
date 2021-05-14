package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HotelBookingController {
	private String Username,Password;
	
	@FXML
	private Button Buttonbutton;
	
	public void PassUserData(String username, String password) {
		Username = new String(username);
		Password = new String(password);
	}
	
	public void ButtonbuttonPressed() {
		System.out.println(Username + " " + Password);
	}
}