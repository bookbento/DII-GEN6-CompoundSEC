import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class KeycardManagerUI extends JFrame {
    private KeycardSystem keycardSystem;
    private JTextField keycardInput;
    private JCheckBox[] roomCheckboxes;
    private JTextArea logArea;
    private JButton confirmButton, addKeycardButton, deleteKeycardButton;
    private String currentKeycardID;

    public KeycardManagerUI(KeycardSystem system) {
        this.keycardSystem = system;

        setTitle("Keycard Manager");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 🔹 ส่วนกรอก Keycard ID + ปุ่มยืนยัน
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel keycardLabel = new JLabel("Keycard ID:");
        keycardLabel.setFont(new Font("Arial", Font.BOLD, 14));
        keycardInput = new JTextField(12);
        confirmButton = new JButton("Confirm");
        addKeycardButton = new JButton("Add Keycard");
        deleteKeycardButton = new JButton("Delete Keycard");

        topPanel.add(keycardLabel);
        topPanel.add(keycardInput);
        topPanel.add(confirmButton);
        topPanel.add(addKeycardButton);
        topPanel.add(deleteKeycardButton);

        // 🔹 ส่วนเลือกห้อง
        JPanel roomPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        roomPanel.setBorder(BorderFactory.createTitledBorder("Select Accessible Rooms"));

        roomCheckboxes = new JCheckBox[10];
        for (int i = 0; i < 10; i++) {
            final int roomNum = 101 + i;
            final JCheckBox checkBox = new JCheckBox("Room " + roomNum);
            checkBox.setFont(new Font("Arial", Font.PLAIN, 13));
            roomCheckboxes[i] = checkBox;
            roomPanel.add(checkBox);

            checkBox.addActionListener(e -> updatePermissions(roomNum, checkBox.isSelected()));
        }

        // 🔹 Log Area
        logArea = new JTextArea(8, 30);
        logArea.setFont(new Font("Arial", Font.PLAIN, 13));
        logArea.setEditable(false);
        logArea.setBorder(BorderFactory.createTitledBorder("Log"));
        JScrollPane logScrollPane = new JScrollPane(logArea);

        add(topPanel, BorderLayout.NORTH);
        add(roomPanel, BorderLayout.CENTER);
        add(logScrollPane, BorderLayout.EAST);

        confirmButton.addActionListener(e -> loadKeycardPermissions());
        addKeycardButton.addActionListener(e -> showAddKeycardDialog());
        deleteKeycardButton.addActionListener(e -> deleteKeycard());

        setVisible(true);
    }

    private void showAddKeycardDialog() {
        JTextField keycardField = new JTextField(10);
        int result = JOptionPane.showConfirmDialog(this, keycardField, "Enter New Keycard ID", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String newKeycardID = keycardField.getText().trim();
            if (newKeycardID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Keycard ID!");
                return;
            }
            if (!keycardSystem.getAccessibleRooms(newKeycardID).isEmpty()) {
                JOptionPane.showMessageDialog(this, "This Keycard already exists!");
                return;
            }
            keycardSystem.assignKeycard(newKeycardID, new HashSet<>()); // เพิ่ม Keycard เปล่า
            logArea.append("🆕 Created new Keycard: " + newKeycardID + "\n");
        }
    }

    private void loadKeycardPermissions() {
        currentKeycardID = keycardInput.getText().trim();
        if (currentKeycardID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Keycard ID!");
            return;
        }

        Set<Integer> permittedRooms = keycardSystem.getAccessibleRooms(currentKeycardID);

        for (int i = 0; i < roomCheckboxes.length; i++) {
            int roomNumber = 101 + i;
            roomCheckboxes[i].setSelected(permittedRooms.contains(roomNumber));
        }

        logArea.append("✅ Loaded Keycard " + currentKeycardID + " -> Rooms: " + permittedRooms + "\n");
    }

    private void updatePermissions(int roomNumber, boolean isChecked) {
        if (currentKeycardID == null || currentKeycardID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please confirm a Keycard ID first!");
            return;
        }

        if (isChecked) {
            keycardSystem.addSinglePermission(currentKeycardID, roomNumber);
            logArea.append("🔹 Added Room " + roomNumber + " to Keycard " + currentKeycardID + "\n");
        } else {
            keycardSystem.removeSinglePermission(currentKeycardID, roomNumber);
            logArea.append("🔻 Removed Room " + roomNumber + " from Keycard " + currentKeycardID + "\n");
        }
    }

    private void deleteKeycard() {
        String keycardID = keycardInput.getText().trim();
        if (keycardID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Keycard ID!");
            return;
        }
        if (keycardSystem.getAccessibleRooms(keycardID).isEmpty()) {
            JOptionPane.showMessageDialog(this, "This keycard does not exist!");
            return;
        }
        keycardSystem.revokeKeycard(keycardID);
        logArea.append("❌ Deleted Keycard " + keycardID + "\n");
    }
}
