package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
	private Stage primaryStage;
	private DBManager database = new DBManager();
	@FXML
	private Button LoginButton;
	@FXML
	private TextField UsernameTextField;
	@FXML
	private TextField PasswordTextField;
	@FXML
	private GridPane LoginGridPane;
	
	//The functions are roughly sorted by size.
	
	//primaryStage is used to resize the window automatically.
	public void PassPrimaryStage(Stage primary) {
		primaryStage = primary;
	}
	
	//If you have UsernameTextField selected and press enter, PasswordTextField will be focused.
	public void UsernameEnterPressed() {
		PasswordTextField.requestFocus();
	}
	
	//After logging out, this returns the login info into the text input.
	public void ReturnUserData(String username, String password) {
		UsernameTextField.setText(username);
		PasswordTextField.setText(password);
	}
	
	//Handles opening the HotelBooking window for customers.
	private void OpenHotelBooking(String username, String password) throws ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HotelBooking.fxml"));
		
		Stage stage = new Stage(StageStyle.DECORATED);
		try {
			stage.setScene(new Scene(loader.load()));
			HotelBookingController controller = loader.getController();
			controller.PassUserData(username, password);
			controller.ClearMenuItemPressed();
			//controller.PassPrimaryStage(stage);
			
			stage.show();
			
			Stage thisWindow = (Stage) LoginButton.getScene().getWindow();
			thisWindow.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Checks the role of the username/password and opens the appropriate window
	public void LoginButtonPressed() throws ClassNotFoundException {
		int role = database.CheckRoleOfUser(UsernameTextField.getText(), PasswordTextField.getText());
		switch(role) {
		case -1:
			Label errorLabel = new Label();
			errorLabel.setText("Sorry, no profile found");
			LoginGridPane.add(errorLabel, 1, 2);
			primaryStage.sizeToScene();
			break;
		case 0:
			break;
		case 1:
			OpenHotelBooking(UsernameTextField.getText(), PasswordTextField.getText());
			break;
		case 2:
			break;
		}
	}
	

}
