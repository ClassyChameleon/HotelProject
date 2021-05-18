package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HotelBookingController {
	private String Username,Password;
	private DBManager database = new DBManager();
	
	@FXML
	private Button Buttonbutton;
	@FXML
	private ChoiceBox<String> HotelChoiceBox;
	@FXML
	private ChoiceBox<String> RoomTypeChoiceBox;
	@FXML
	private DatePicker EntryDatePicker;
	@FXML
	private DatePicker ExitDatePicker;
	
	//Functions are sorted roughly by size.
	//Functions in the works are at the bottom.
	
	public void ButtonbuttonPressed() {
		System.out.println(Username + " " + Password);
	}
	
	public void PassUserData(String username, String password) {
		Username = new String(username);
		Password = new String(password);
	}
	
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
	
	public void ClearMenuItemPressed() throws ClassNotFoundException {
		ObservableList<String> hotels = FXCollections.observableArrayList("Select a hotel");
		hotels.addAll(database.GiveHotelNames());
		HotelChoiceBox.setItems(hotels);
		ObservableList<String> roomTypes = FXCollections.observableArrayList("Select a room type");
		roomTypes.addAll(database.GiveRoomTypes());
		RoomTypeChoiceBox.setItems(roomTypes);
		HotelChoiceBox.setValue("Select a hotel");
		RoomTypeChoiceBox.setValue("Select a room type");
	}

}