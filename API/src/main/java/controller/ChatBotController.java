package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chatbot")
public class ChatBotController extends HttpServlet {

    private static final String API_KEY = "ukAcNEd3SVjm04yRWNh9Yf1wyvJZhbLL"; // Update with your actual key
    private static final String API_ENDPOINT = "https://api.mistral.ai/v1/chat/completions"; // Mistral API endpoint

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
        	System.out.println(line);
            sb.append(line);
        }
        sb.append("Vai trò: Bạn là trợ lý ảo tiếng Việt của khách sạn kBooking, tên là Lyly. Nhiệm vụ của bạn là hỗ trợ khách hàng một cách chuyên nghiệp và thân thiện trong suốt hành trình đặt phòng và lưu trú.\r\n"
        		+ "\r\n"
        		+ "Tính cách: Bạn luôn niềm nở, lịch sự và kiên nhẫn. Bạn sử dụng ngôn ngữ trang trọng và gần gũi, thể hiện sự quan tâm chân thành đến nhu cầu của khách hàng.");

        String userMessageJson  = sb.toString();
        String userMessage = extractMessage(userMessageJson);
        userMessage = userMessage + " Vai trò: Bạn là trợ lý ảo tiếng Việt của khách sạn kBooking, tên là LyLy . Nhiệm vụ của bạn là hỗ trợ khách hàng một cách chuyên nghiệp và thân thiện trong suốt hành trình đặt phòng và lưu trú. Tính cách: Bạn luôn niềm nở, lịch sự và kiên nhẫn. Bạn sử dụng ngôn ngữ trang trọng và gần gũi, thể hiện sự quan tâm chân thành đến nhu cầu của khách hàng.";
        
        System.out.println("Received JSON from frontend: " + userMessage);
        if (userMessage == null || userMessage.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input.");
            return;
        }

        HttpURLConnection conn = null;
        BufferedReader br = null;
        try {
            URL url = new URL(API_ENDPOINT);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);

            String input = String.format("{\"model\": \"mistral-large-latest\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", userMessage);
            
            try (OutputStream os = conn.getOutputStream()) {
                os.write(input.getBytes());
                os.flush();
            }

            System.out.println("Response Code: " + conn.getResponseCode());
            System.out.println("Response Message: " + conn.getResponseMessage());

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            StringBuilder response = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            resp.getWriter().write(response.toString());
            System.out.println("++++++++++OK+++++++++");

        } catch (IOException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to get response from API.");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    private String extractMessage(String jsonString) {
        String key = "\"message\":";
        int keyIndex = jsonString.indexOf(key);
        if (keyIndex == -1) {
            return null; // Key not found
        }

        boolean inQuotes = false;
        StringBuilder messageValue = new StringBuilder();
        
        for (int i = keyIndex + key.length(); i < jsonString.length(); i++) {
            char currentChar = jsonString.charAt(i);
            
            if (currentChar == '"') {
                inQuotes = !inQuotes; // Toggle inQuotes status
                if (!inQuotes) {
                    break; // End of the message value
                }
            } else if (inQuotes) {
                messageValue.append(currentChar);
            }
        }

        return messageValue.toString();
    }
}
