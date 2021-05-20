package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Queue;

import com.sun.javafx.scene.control.behavior.DatePickerBehavior;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
	@FXML
	private AnchorPane ResultAnchorPane;
	
	//Functions are sorted roughly by size.
	//Functions in the works are at the bottom.
	
	/*
	//primaryStage is used to resize the window automatically.
	public void PassPrimaryStage(Stage primary) {
		primaryStage = primary;
	}
	*/
	
	public void PassUserData(String username, String password) {
		Username = new String(username);
		Password = new String(password);
	}
	
	//Clears search filters
	public void ClearMenuItemPressed() throws ClassNotFoundException {
		ObservableList<String> hotels = FXCollections.observableArrayList("Select a hotel");
		hotels.addAll(database.GiveHotelNames());
		HotelChoiceBox.setItems(hotels);
		ObservableList<String> roomTypes = FXCollections.observableArrayList("Select a room type");
		roomTypes.addAll(database.GiveRoomTypes());
		RoomTypeChoiceBox.setItems(roomTypes);
		HotelChoiceBox.setValue("Select a hotel");
		RoomTypeChoiceBox.setValue("Select a room type");
		EntryDatePicker.setValue(null);
		ExitDatePicker.setValue(null);
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
	
	public void SearchButtonPressed() throws ClassNotFoundException {
		ResultAnchorPane.getChildren().clear();
		Queue<Room> roomList = database.FindRoomsBasedOnSearch(
				HotelChoiceBox.getValue(),
				RoomTypeChoiceBox.getValue(),
				EntryDatePicker.getValue().toString(),
				ExitDatePicker.getValue().toString()
		);
		VBox vbox = new VBox();
		while (!roomList.isEmpty()) {
			Room roomItem = roomList.remove();
			GridPane resultGridPane = new GridPane();
			Insets resultPadding = new Insets(2);
			resultGridPane.setPadding(resultPadding);
			Label hotelName = new Label(roomItem.getHotel() + " ");
			resultGridPane.add(hotelName, 0, 0);
			Label roomID = new Label("Room number: " + roomItem.getRoomID() + " ");
			resultGridPane.add(roomID, 0, 1);
			Label entryDate = new Label("Check-in Date");
			resultGridPane.add(entryDate, 1, 0);
			DatePicker checkInDatePicker = new DatePicker();
			ConfigureDatePicker(checkInDatePicker);
			Label exitDate = new Label("Check-out Date");
			resultGridPane.add(exitDate, 2, 0);
			DatePicker checkOutDatePicker = new DatePicker();
			ConfigureDatePicker(checkInDatePicker);
			disableBookedDatesOfRoom(checkInDatePicker, checkOutDatePicker, roomItem);
			resultGridPane.add(checkInDatePicker, 1, 1);
			resultGridPane.add(checkOutDatePicker, 2, 1);
			Button BookingButton = new Button("Book");
			resultGridPane.add(BookingButton, 3, 1);
			vbox.getChildren().add(resultGridPane);
		}
		for (int i = 0; i < 6; i++) {
			GridPane ResultGridPane = new GridPane();
			Insets ResultPadding = new Insets(2);
			ResultGridPane.setPadding(ResultPadding);
			Label HotelName = new Label("HotelName ");
			ResultGridPane.add(HotelName, 0, 0);
			Label RoomID = new Label("Room no." + Integer.toString(i) + ": ");
			ResultGridPane.add(RoomID, 0, 1);
			Label EntryDate = new Label("Check-in Date");
			ResultGridPane.add(EntryDate, 1, 0);
			DatePicker CheckInDatePicker = new DatePicker();
			ConfigureDatePicker(CheckInDatePicker);
			ResultGridPane.add(CheckInDatePicker, 1, 1);
			Label ExitDate = new Label("Check-out Date");
			ResultGridPane.add(ExitDate, 2, 0);
			DatePicker CheckOutDatePicker = new DatePicker();
			ResultGridPane.add(CheckOutDatePicker, 2, 1);
			Button BookingButton = new Button("Book");
			ResultGridPane.add(BookingButton, 3, 1);
			vbox.getChildren().add(ResultGridPane);
		}
		ResultAnchorPane.getChildren().add(vbox);
	}
	
	public void ConfigureLocalDatePicker() {
		EntryDatePicker.getEditor().setDisable(true);
		EntryDatePicker.getEditor().setOpacity(1);
		EntryDatePicker.setDayCellFactory(param -> new DateCell() {
		                @Override
		                public void updateItem(LocalDate date, boolean empty) {
		                    super.updateItem(date, empty);
		                    setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
		                }
		            });
		ExitDatePicker.getEditor().setDisable(true);
		ExitDatePicker.getEditor().setOpacity(1);
		ExitDatePicker.setDayCellFactory(param -> new DateCell() {
		                @Override
		                public void updateItem(LocalDate date, boolean empty) {
		                    super.updateItem(date, empty);
		                    setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
		                }
		            });
	}
	
	private void ConfigureDatePicker(DatePicker datePicker) {
		datePicker.getEditor().setDisable(true);
		datePicker.getEditor().setOpacity(1);
		datePicker.setDayCellFactory(param -> new DateCell() {
		                @Override
		                public void updateItem(LocalDate date, boolean empty) {
		                    super.updateItem(date, empty);
		                    setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
		                }
		            });
	}
	
	private void disableBookedDatesOfRoom(DatePicker entryDatePicker, DatePicker exitDatePicker, Room room) {
		while(!room.isBookingEmpty()) {
            LocalDate[] bookingDates = room.popNextBooking();
			entryDatePicker.setDayCellFactory(param -> new DateCell() {
	            @Override
	            public void updateItem(LocalDate date, boolean empty) {
	                super.updateItem(date, empty);
	                //setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
	                setDisable(empty || 
	                		date.compareTo(bookingDates[0]) >= 0 && date.compareTo(bookingDates[1]) < 0);
	            }
	        });
			exitDatePicker.setDayCellFactory(param -> new DateCell() {
	            @Override
	            public void updateItem(LocalDate date, boolean empty) {
	                super.updateItem(date, empty);
	                //setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
	                setDisable(empty || 
	                		date.compareTo(bookingDates[0]) > 0 && date.compareTo(bookingDates[1]) <= 0);
	            }
	        });
	}
	}

}