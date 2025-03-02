import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class HotelAccessControlUI extends JFrame {
    private KeycardSystem keycardSystem;
    private JTextField keycardInput;
    private JPanel roomPanel;

    public HotelAccessControlUI(KeycardSystem system) {
        this.keycardSystem = system;

        setTitle("Hotel Access Control");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Keycard ID:"));
        keycardInput = new JTextField(10);
        inputPanel.add(keycardInput);
        JButton checkButton = new JButton("Check Access");
        inputPanel.add(checkButton);

        JButton manageButton = new JButton("Manage Keycards");
        inputPanel.add(manageButton);

        roomPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        add(roomPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);

        checkButton.addActionListener(e -> updateRoomStatus());
        manageButton.addActionListener(e -> new KeycardManagerUI(keycardSystem));

        setVisible(true);
    }

    private void updateRoomStatus() {
        String keycardID = keycardInput.getText().trim();
        if (keycardID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Keycard ID!");
            return;
        }

        Set<Integer> permittedRooms = keycardSystem.getAccessibleRooms(keycardID);
        if (permittedRooms.isEmpty()) {
            JOptionPane.showMessageDialog(this, "This keycard does not exist!");
            return;
        }

        roomPanel.removeAll();

        for (int room = 101; room <= 110; room++) {
            boolean hasAccess = permittedRooms.contains(room);
            JLabel roomLabel = new JLabel("Room " + room, SwingConstants.CENTER);
            roomLabel.setOpaque(true);
            roomLabel.setBackground(hasAccess ? Color.GREEN : Color.LIGHT_GRAY);
            roomLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            roomPanel.add(roomLabel);
        }

        roomPanel.revalidate();
        roomPanel.repaint();
    }
}
