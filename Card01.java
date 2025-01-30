import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

class KeyCard {
    private String keyCardId;
    private String roomNumber;
    private String guestName;
    private String expiryDate;

    // Constructor
    public KeyCard(String keyCardId, String roomNumber, String guestName, String expiryDate) {
        this.keyCardId = keyCardId;
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.expiryDate = expiryDate;
    }

    public String getKeyCardId() {
        return keyCardId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        return keyCardId + " | " + roomNumber + " | " + guestName + " | " + expiryDate;
    }
}

class HotelSystem {
    private HashMap<String, KeyCard> keyCards = new HashMap<>();  // เก็บข้อมูล KeyCard

    public KeyCard generateNewKeyCard(String keyCardId, String roomNumber, String guestName, String expiryDate) {
        KeyCard newKeyCard = new KeyCard(keyCardId, roomNumber, guestName, expiryDate);
        keyCards.put(keyCardId, newKeyCard);
        return newKeyCard;
    }

    public KeyCard getKeyCardById(String keyCardId) {
        return keyCards.get(keyCardId);
    }

    public boolean openDoor(String keyCardId) {
        if (keyCards.containsKey(keyCardId)) {
            return true;
        } else {
            return false;
        }
    }

    public ObservableList<KeyCard> getAllKeyCards() {
        return FXCollections.observableArrayList(keyCards.values());
    }
}

public class Card01 extends Application {
    private HotelSystem hotelSystem = new HotelSystem();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel K-Card System");

        // สร้าง UI Components
        Label keyCardIdLabel = new Label("KeyCard ID:");
        TextField keyCardIdField = new TextField();

        Label roomNumberLabel = new Label("Room Number:");
        TextField roomNumberField = new TextField();

        Label guestNameLabel = new Label("Guest Name:");
        TextField guestNameField = new TextField();

        Label expiryDateLabel = new Label("Expiry Date (YYYY-MM-DD):");
        TextField expiryDateField = new TextField();

        Button createKeyCardButton = new Button("Create KeyCard");
        Button openDoorButton = new Button("Open Door");

        TableView<KeyCard> keyCardTable = new TableView<>();
        keyCardTable.setEditable(false);
        TableColumn<KeyCard, String> idColumn = new TableColumn<>("KeyCard ID");
        TableColumn<KeyCard, String> roomColumn = new TableColumn<>("Room Number");
        TableColumn<KeyCard, String> guestColumn = new TableColumn<>("Guest Name");
        TableColumn<KeyCard, String> expiryColumn = new TableColumn<>("Expiry Date");

        idColumn.setCellValueFactory(cellData -> javafx.beans.property.SimpleStringProperty(cellData.getValue().getKeyCardId()));
        roomColumn.setCellValueFactory(cellData -> javafx.beans.property.SimpleStringProperty(cellData.getValue().getRoomNumber()));
        guestColumn.setCellValueFactory(cellData -> javafx.beans.property.SimpleStringProperty(cellData.getValue().getGuestName()));
        expiryColumn.setCellValueFactory(cellData -> javafx.beans.property.SimpleStringProperty(cellData.getValue().getExpiryDate()));

        keyCardTable.getColumns().addAll(idColumn, roomColumn, guestColumn, expiryColumn);

        // ตั้งค่าการจัด layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(
                keyCardIdLabel, keyCardIdField,
                roomNumberLabel, roomNumberField,
                guestNameLabel, guestNameField,
                expiryDateLabel, expiryDateField,
                createKeyCardButton, openDoorButton,
                keyCardTable
        );

        // Action สำหรับปุ่มสร้าง KeyCard
        createKeyCardButton.setOnAction(e -> {
            String keyCardId = keyCardIdField.getText();
            String roomNumber = roomNumberField.getText();
            String guestName = guestNameField.getText();
            String expiryDate = expiryDateField.getText();

            if (keyCardId.isEmpty() || roomNumber.isEmpty() || guestName.isEmpty() || expiryDate.isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            hotelSystem.generateNewKeyCard(keyCardId, roomNumber, guestName, expiryDate);
            keyCardTable.setItems(hotelSystem.getAllKeyCards());
            clearInputFields(keyCardIdField, roomNumberField, guestNameField, expiryDateField);
        });

        // Action สำหรับปุ่มเปิดประตู
        openDoorButton.setOnAction(e -> {
            String keyCardId = keyCardIdField.getText();
            if (hotelSystem.openDoor(keyCardId)) {
                showAlert("Success", "Door Opened for KeyCard: " + keyCardId);
            } else {
                showAlert("Error", "Invalid KeyCard ID");
            }
        });

        Scene scene = new Scene(vbox, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ฟังก์ชันแสดงการแจ้งเตือน
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ฟังก์ชันเคลียร์ช่องกรอกข้อมูล
    private void clearInputFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}
