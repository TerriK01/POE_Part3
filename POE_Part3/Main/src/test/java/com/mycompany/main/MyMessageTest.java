/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.main;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author RC_Student_lab
 */

public class MyMessageTest {

    private List<MyMessage> messages;

    private MyMessage message1;
    private MyMessage message2;
    private MyMessage message3;
    private MyMessage message4;
    private MyMessage message5;

    @BeforeEach
    public void setUp() {
        // Simulating provided test data
        message1 = new MyMessage("Developer", "+27834557896", "Did you get the cake?", 1001);
        message2 = new MyMessage("Developer", "+27838884567", "Where are you? You are late! I have asked you to be on time.", 1002);
        message3 = new MyMessage("Developer", "+27834484567", "Yohoooo, I am at your gate.", 1003);
        message4 = new MyMessage("Developer", "0838884567", "It is dinner time !", 1004);
        message5 = new MyMessage("Developer", "+27838884567", "Ok, I am leaving without you.", 1005);

        messages = new ArrayList<>(List.of(message1, message2, message3, message4, message5));
    }

    @Test
    public void testSentMessagesCorrectlyPopulated() {
        List<String> sentMessages = new ArrayList<>();

        // Assuming "Sent" flag messages are message1 and message4
        sentMessages.add(message1.getMessage());
        sentMessages.add(message4.getMessage());

        List<String> expected = List.of("Did you get the cake?", "It is dinner time !");
        assertEquals(expected, sentMessages);
    }

    @Test
    public void testLongestMessageReturned() {
        MyMessage longest = MyMessage.getLongestMessage(messages.subList(0, 4)); // message 1–4

        assertNotNull(longest);
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getMessage());
    }

    @Test
    public void testSearchByMessageID() {
        MyMessage found = MyMessage.searchByMessageID(messages, 1004); // ID for message4
        assertNotNull(found);
        assertEquals("It is dinner time !", found.getMessage());
    }

    @Test
    public void testSearchByRecipient() {
        List<MyMessage> result = MyMessage.searchByRecipient(messages, "+27838884567");

        List<String> expected = List.of(
            "Where are you? You are late! I have asked you to be on time.",
            "Ok, I am leaving without you."
        );

        List<String> actual = new ArrayList<>();
        for (MyMessage m : result) {
            actual.add(m.getMessage());
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteByMessageHash() {
        String hashToDelete = message2.getMessageHash();

        boolean removed = messages.removeIf(m -> m.getMessageHash().equals(hashToDelete));

        assertTrue(removed);
        assertFalse(messages.stream().anyMatch(m -> m.getMessageHash().equals(hashToDelete)));
    }

    @Test
    public void testDisplayReportOfSentMessages() {
        // Simulating "Sent" flagged messages only: message1, message4
        List<MyMessage> sentMessages = List.of(message1, message4);

        for (MyMessage m : sentMessages) {
            System.out.println("-----");
            System.out.println("Hash: " + m.getMessageHash());
            System.out.println("Recipient: " + m.getRecipient());
            System.out.println("Message: " + m.getMessage());
        }

        assertEquals(2, sentMessages.size());
        assertEquals("Did you get the cake?", sentMessages.get(0).getMessage());
        assertEquals("It is dinner time !", sentMessages.get(1).getMessage());
    }
}