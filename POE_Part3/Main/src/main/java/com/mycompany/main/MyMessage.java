/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

/**
 *
 * @author RC_Student_lab
 */
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import java.util.*;


public class MyMessage {
    private String sender;
    private String recipient;
    private String message;
    private int messageId;
    private String messageHash;

    public MyMessage(String sender, String recipient, String message, int messageId) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.messageId = messageId;
        this.messageHash = generateHash();
    }

    public MyMessage(String sender, String recipient, String message, int messageId, String messageHash) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.messageId = messageId;
        this.messageHash = messageHash;
    }

     private String generateMessageId() {
        return String.valueOf((int)(Math.random() * 1_000_000_000));
    }

    // Check if the generated message ID is valid
    public boolean checkMessageID() {
      //  return messageId != null && messageId.length() <= 10;
        return false;
      //  return messageId != null && messageId.length() <= 10;
    }

    // Check if recipient cell number is valid (starts with + and has 10–15 digits after that)
    public static boolean checkRecipientCell(String recipient) {
        return recipient != null &&
               recipient.startsWith("+") &&
               recipient.substring(1).matches("\\d{10,15}");
    }

    private String generateHash() {
        return Integer.toHexString((sender + recipient + message + messageId).hashCode());
    }

    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public int getMessageId() { return messageId; }
    public String getMessageHash() { return messageHash; }

    public String printMessage() {
        return "Sender: " + sender +
               "\nRecipient: " + recipient +
               "\nMessage ID: " + messageId +
               "\nHash: " + messageHash +
               "\nContent: " + message;
    }

    public static MyMessage getLongestMessage(List<MyMessage> messages) {
        if (messages.isEmpty()) return null;
        MyMessage longest = messages.get(0);
        for (MyMessage m : messages) {
            if (m.message.length() > longest.message.length()) longest = m;
        }
        return longest;
    }

    public static MyMessage searchByMessageID(List<MyMessage> messages, int id) {
        for (MyMessage m : messages) {
            if (m.messageId == id) return m;
        }
        return null;
    }

    public static List<MyMessage> searchByRecipient(List<MyMessage> messages, String recipient) {
        List<MyMessage> results = new ArrayList<>();
        for (MyMessage m : messages) {
            if (m.recipient.equalsIgnoreCase(recipient)) results.add(m);
        }
        return results;
    }
    
   public static List<MyMessage> readMessagesFromJson(String filePath) {
    List<MyMessage> messagesList = new ArrayList<>();

    try (FileReader reader = new FileReader(filePath)) {
        JSONParser parser = new JSONParser();
        JSONArray messages = (JSONArray) parser.parse(reader);

        for (Object obj : messages) {
            JSONObject jsonObj = (JSONObject) obj;

            String recipient = (String) jsonObj.get("recipient");
            String messageText = (String) jsonObj.get("messageText");
            String flag = (String) jsonObj.get("flag");

            // You can generate dummy values for sender and messageId for now
            String sender = "System";
            int messageId = (int) (Math.random() * 1_000_000);

            if (!flag.equalsIgnoreCase("Disregard")) {
                MyMessage message = new MyMessage(sender, recipient, messageText, messageId);
                messagesList.add(message);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return messagesList;
}}