import java.util.HashMap;
import java.util.Scanner;

// Class ที่ใช้เก็บข้อมูลของ KeyCard
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

    // Getters
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
        return "KeyCard ID: " + keyCardId + ", Room: " + roomNumber + ", Guest: " + guestName + ", Expiry: " + expiryDate;
    }
}

// Class ที่ใช้จัดการระบบ KeyCard
class HotelSystem {
    private HashMap<String, KeyCard> keyCards = new HashMap<>();  // เก็บข้อมูล KeyCard ทั้งหมด

    // Method สร้าง KeyCard ใหม่
    public KeyCard generateNewKeyCard(String keyCardId, String roomNumber, String guestName, String expiryDate) {
        KeyCard newKeyCard = new KeyCard(keyCardId, roomNumber, guestName, expiryDate);
        keyCards.put(keyCardId, newKeyCard);
        return newKeyCard;
    }

    // Method ใช้ KeyCard เปิดประตู
    public boolean openDoor(String keyCardId) {
        if (keyCards.containsKey(keyCardId)) {
            KeyCard keyCard = keyCards.get(keyCardId);
            System.out.println("Opening door for room: " + keyCard.getRoomNumber());
            return true;
        } else {
            System.out.println("Invalid keycard.");
            return false;
        }
    }

    // แสดงข้อมูล KeyCard ทั้งหมด
    public void showAllKeyCards() {
        if (keyCards.isEmpty()) {
            System.out.println("No keycards created.");
        } else {
            keyCards.forEach((id, card) -> System.out.println(card));
        }
    }
}

// Main Class ที่ใช้ทดสอบการทำงาน
public class Card02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelSystem hotelSystem = new HotelSystem();

        while (true) {
            System.out.println("\nHotel K-Card System");
            System.out.println("1. Create New KeyCard");
            System.out.println("2. Show All KeyCards");
            System.out.println("3. Open Door with KeyCard");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // สร้าง KeyCard ใหม่
                    System.out.print("Enter KeyCard ID: ");
                    String keyCardId = scanner.nextLine();
                    System.out.print("Enter Room Number: ");
                    String roomNumber = scanner.nextLine();
                    System.out.print("Enter Guest Name: ");
                    String guestName = scanner.nextLine();
                    System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
                    String expiryDate = scanner.nextLine();
                    hotelSystem.generateNewKeyCard(keyCardId, roomNumber, guestName, expiryDate);
                    System.out.println("New KeyCard created successfully.");
                    break;
                case 2:
                    // แสดงข้อมูลทั้งหมด
                    hotelSystem.showAllKeyCards();
                    break;
                case 3:
                    // ใช้ KeyCard เปิดประตู
                    System.out.print("Enter KeyCard ID to open door: ");
                    String openCardId = scanner.nextLine();
                    hotelSystem.openDoor(openCardId);
                    break;
                case 4:
                    // ออกจากโปรแกรม
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
