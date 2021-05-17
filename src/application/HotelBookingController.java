package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HotelBookingController {
	private String Username,Password;
	
	@FXML
	private Button Buttonbutton;
	
	public void LogOutMenuItemPressed() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
		
		Stage stage = new Stage(StageStyle.DECORATED);
		try {
			stage.setScene(new Scene(loader.load()));
			LoginController controller = loader.getController();
			controller.ReturnUserData(Username, Password);
			controller.PassPrimaryStage(stage);
			
			stage.show();
			
			Stage thisWindow = (Stage) Buttonbutton.getScene().getWindow();
			thisWindow.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void PassUserData(String username, String password) {
		Username = new String(username);
		Password = new String(password);
	}
	
	public void ButtonbuttonPressed() {
		System.out.println(Username + " " + Password);
	}
}