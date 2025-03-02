import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KeycardSystem keycardSystem = new KeycardSystem();
            new HotelAccessControlUI(keycardSystem); // UI ตรวจสอบสถานะห้องพัก
        });
    }
}
