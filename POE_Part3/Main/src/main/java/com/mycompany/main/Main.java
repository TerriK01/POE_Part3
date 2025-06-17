/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;

/**
 *
 * @author RC_Student_lab
 */
import java.util.*;
import javax.swing.*;
import java.io.*;
import org.json.*;

public class Main {

    static ArrayList<String> sentMessages = new ArrayList<>();
    static ArrayList<String> disregardedMessages = new ArrayList<>();
    static ArrayList<JSONObject> storedMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIds = new ArrayList<>();
    static ArrayList<MyMessage> messageStore = new ArrayList<>();
    static int messageIdCounter = 0;

    public static void main(String[] args) {
        String firstName = JOptionPane.showInputDialog("Enter first name:");
        String lastName = JOptionPane.showInputDialog("Enter last name:");
        String username = null, password = null, cellphoneNumber = null;

        while (username == null || !username.contains("_") || username.length() > 5) {
            username = JOptionPane.showInputDialog("Enter username (must contain an underscore and be no more than 5 characters):");
            if (username == null || !username.contains("_") || username.length() > 5)
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted.");
        }

        while (password == null || !isValidPassword(password)) {
            password = JOptionPane.showInputDialog("Enter password (must contain capital letter, special character, number, max 8 characters):");
            if (!isValidPassword(password))
                JOptionPane.showMessageDialog(null, "Password is incorrectly formatted.");
        }

        while (cellphoneNumber == null || !isValidCellphoneNumber(cellphoneNumber)) {
            cellphoneNumber = JOptionPane.showInputDialog("Enter cellphone number (must start with +27 and be 12 digits):");
            if (!isValidCellphoneNumber(cellphoneNumber))
                JOptionPane.showMessageDialog(null, "Cellphone number is incorrectly formatted.");
        }

        JOptionPane.showMessageDialog(null, "Cellphone Number successfully added");

        boolean loggedIn = false;
        while (!loggedIn) {
            String loginUsername = JOptionPane.showInputDialog("=== User Login ===\nEnter username:");
            String loginPassword = JOptionPane.showInputDialog("Enter password:");
            if (loginUsername != null && loginPassword != null && loginUsername.equals(username) && loginPassword.equals(password)) {
                loggedIn = true;
                JOptionPane.showMessageDialog(null, "Welcome " + firstName + " " + lastName + ", it is great to have you back!");
            } else {
                JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again.");
            }
        }

        loadStoredMessages();
        runQuickChat();
    }

    private static boolean isValidPassword(String password) {
        if (password.length() > 8) return false;
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if ("@#$%^&+=!".contains(String.valueOf(c))) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    private static boolean isValidCellphoneNumber(String cellphone) {
        return cellphone.startsWith("+27") && cellphone.length() == 12 && cellphone.substring(3).matches("\\d+");
    }

    private static void loadStoredMessages() {
        try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject msg = new JSONObject(line);
                storedMessages.add(msg);
                sentMessages.add(msg.getString("messageContent"));
                messageHashes.add(msg.getString("messageHash"));
                messageIds.add(msg.getString("messageId"));

                MyMessage m = new MyMessage(
                    msg.getString("sender"),
                    msg.getString("recipient"),
                    msg.getString("messageContent"),
                    msg.getInt("messageId"),
                    msg.getString("messageHash")
                );
                messageStore.add(m);

                messageIdCounter = Math.max(messageIdCounter, msg.getInt("messageId") + 1);
            }
        } catch (IOException | JSONException e) {
            System.out.println("No existing messages found or error reading file.");
        }
    }

    private static void saveMessages() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("messages.json"))) {
            for (JSONObject msg : storedMessages) {
                writer.write(msg.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving messages.");
        }
    }

    private static void runQuickChat() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        boolean running = true;

        while (running) {
            String[] options = {
                "Send Message",
                "Show Sender/Recipient",
                "Longest Sent Message",
                "Search by Message ID",
                "Search by Recipient",
                "Delete by Message Hash",
                "Message Report",
                "Quit"
            };

            int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an option:",
                "QuickChat Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
            );

            switch (choice) {
                case 0: // Send Message
                    String recipient = JOptionPane.showInputDialog("Enter recipient number:");
                    if (recipient == null) break;

                    for (int i = 1; i <= 5; i++) {
                        String messageText = JOptionPane.showInputDialog("Enter message " + i + " of 5:");
                        if (messageText == null) {
                            JOptionPane.showMessageDialog(null, "Message entry cancelled.");
                            break;
                        }

                        if (messageText.length() > 250) {
                            JOptionPane.showMessageDialog(null, "Message too long. Max 250 characters.");
                            i--; // retry
                        } else {
                            MyMessage msg = new MyMessage(
                                "", recipient, messageText, messageIdCounter++
                            );
                            messageStore.add(msg);

                            JSONObject jsonMsg = new JSONObject();
                            jsonMsg.put("sender", msg.getSender());
                            jsonMsg.put("recipient", msg.getRecipient());
                            jsonMsg.put("messageContent", msg.getMessage());
                            jsonMsg.put("messageId", msg.getMessageId());
                            jsonMsg.put("messageHash", msg.getMessageHash());
                            storedMessages.add(jsonMsg);

                            saveMessages();

                            JOptionPane.showMessageDialog(null, "Message " + i + " sent.");
                        }
                    }
                    break;

                case 1: // Show sender and recipient
                    StringBuilder sbr = new StringBuilder("Senders and Recipients:\n");
                    for (MyMessage msg : messageStore) {
                        sbr.append("Sender: ").append(msg.getSender())
                           .append(" | Recipient: ").append(msg.getRecipient()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, sbr.toString());
                    break;

                case 2: // Longest Message
                    MyMessage longest = MyMessage.getLongestMessage(messageStore);
                    if (longest != null)
                        JOptionPane.showMessageDialog(null, "Longest Message:\n" + longest.printMessage());
                    else
                        JOptionPane.showMessageDialog(null, "No messages found.");
                    break;

                case 3: // Search by Message ID
                    String idStr = JOptionPane.showInputDialog("Enter Message ID to search:");
                    if (idStr != null) {
                        try {
                            int id = Integer.parseInt(idStr);
                            MyMessage foundMsg = MyMessage.searchByMessageID(messageStore, id);
                            if (foundMsg != null)
                                JOptionPane.showMessageDialog(null, "Found message:\n" + foundMsg.printMessage());
                            else
                                JOptionPane.showMessageDialog(null, "No message found with ID " + id);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid message ID.");
                        }
                    }
                    break;

                case 4: // Search by Recipient
                    String recSearch = JOptionPane.showInputDialog("Enter recipient to search:");
                    if (recSearch != null) {
                        List<MyMessage> foundMsgs = MyMessage.searchByRecipient(messageStore, recSearch);
                        if (!foundMsgs.isEmpty()) {
                            StringBuilder sb = new StringBuilder("Messages for " + recSearch + ":\n");
                            for (MyMessage m : foundMsgs) {
                                sb.append(m.printMessage()).append("\n\n");
                            }
                            JOptionPane.showMessageDialog(null, sb.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "No messages found for recipient " + recSearch);
                        }
                    }
                    break;

                case 5: // Delete by Message Hash
                    String hashToDelete = JOptionPane.showInputDialog("Enter Message Hash to delete:");
                    if (hashToDelete != null) {
                        boolean removed = false;
                        Iterator<MyMessage> iter = messageStore.iterator();
                        while (iter.hasNext()) {
                            MyMessage m = iter.next();
                            if (m.getMessageHash().equals(hashToDelete)) {
                                iter.remove();
                                removed = true;
                            }
                        }

                        Iterator<JSONObject> jsonIter = storedMessages.iterator();
                        while (jsonIter.hasNext()) {
                            JSONObject obj = jsonIter.next();
                            if (obj.getString("messageHash").equals(hashToDelete)) {
                                jsonIter.remove();
                            }
                        }

                        if (removed) {
                            saveMessages();
                            JOptionPane.showMessageDialog(null, "Message(s) with hash " + hashToDelete + " deleted.");
                        } else {
                            JOptionPane.showMessageDialog(null, "No message found with that hash.");
                        }
                    }
                    break;

                case 6: // Message Report
                    int totalMessages = messageStore.size();
                    int sent = totalMessages; // Assuming all are sent messages
                    int disregarded = disregardedMessages.size();
                    JOptionPane.showMessageDialog(null, "Message Report:\nSent: " + sent + "\nDisregarded: " + disregarded);
                    break;

                case 7: // Quit
                default:
                    running = false;
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    break;
            }
        }
    }
}
