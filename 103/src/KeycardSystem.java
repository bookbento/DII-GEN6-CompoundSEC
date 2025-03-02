import java.io.*;
import java.util.*;

public class KeycardSystem {
    private HashMap<String, Set<Integer>> keycardPermissions;
    private static final String FILE_NAME = "keycards.dat";

    public KeycardSystem() {
        keycardPermissions = new HashMap<>();
        loadKeycardData();
    }

    public void assignKeycard(String keycardID, Set<Integer> rooms) {
        keycardPermissions.put(keycardID, rooms);
        saveKeycardData();
    }

    public void revokeKeycard(String keycardID) {
        keycardPermissions.remove(keycardID);
        saveKeycardData();
    }

    public void addSinglePermission(String keycardID, int roomNumber) {
        keycardPermissions.putIfAbsent(keycardID, new HashSet<>());
        keycardPermissions.get(keycardID).add(roomNumber);
        saveKeycardData();
    }

    public void removeSinglePermission(String keycardID, int roomNumber) {
        if (keycardPermissions.containsKey(keycardID)) {
            keycardPermissions.get(keycardID).remove(roomNumber);
            if (keycardPermissions.get(keycardID).isEmpty()) {
                keycardPermissions.remove(keycardID);
            }
            saveKeycardData();
        }
    }

    public boolean validateAccess(String keycardID, int roomNumber) {
        return keycardPermissions.getOrDefault(keycardID, Collections.emptySet()).contains(roomNumber);
    }

    public Set<Integer> getAccessibleRooms(String keycardID) {
        return keycardPermissions.getOrDefault(keycardID, Collections.emptySet());
    }

    private void saveKeycardData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(keycardPermissions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadKeycardData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            keycardPermissions = (HashMap<String, Set<Integer>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            keycardPermissions = new HashMap<>();
        }
    }
}
