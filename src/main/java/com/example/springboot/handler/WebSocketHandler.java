package com.example.springboot.handler;

import com.example.springboot.dto.PriceRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final Queue<Double> priceQueue = new LinkedList<>();
    private double sum = 0.0;
    private int count = 0;
    private WebSocketSession currentSession;
    private final Object lock = new Object();

    private final int windowSize;

    public WebSocketHandler(@Value("${window.size}") int windowSize) {
        this.windowSize = windowSize;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Client connected: " + session.getId()); // should be logged in a real application
        currentSession = session;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        PriceRequestDto request = new ObjectMapper().readValue(message.getPayload(), PriceRequestDto.class);
        new Thread(() -> {
            double average = calculateAverage(request.getPrice());
            try {
                sendAverage(average);
            } catch (IOException e) {
                e.printStackTrace(); // should be logged in a real application
            }
        }).start();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Client disconnected: " + session.getId()); // should be logged in a real application
        currentSession = null;
    }

    public void sendAverage(double average) throws IOException {
        if (currentSession != null && currentSession.isOpen()) {
            PriceRequestDto averagePrice = new PriceRequestDto(average);
            currentSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(averagePrice)));
        }
    }

    public synchronized double calculateAverage(Double lastPrice) {

        if (lastPrice == null || windowSize <= 0) return count > 0 ? sum / count : 0;

        if (count < windowSize) count++;

        else  {
            Double removedPrice = priceQueue.poll();
            sum -= removedPrice ; // assumed that priceQueue is only modified in this method
        }

        priceQueue.offer(lastPrice);
        sum += lastPrice;
        return sum / count;
    }

}