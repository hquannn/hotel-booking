package controller;

import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/Contact/*")
public class ContactController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ContactController() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pathInfo = request.getPathInfo();
		

		if (pathInfo.equals("/sendEmail")) {
			sendMail(request, response);
		} else if (pathInfo.equals("/sendConfirm")) {
			sendConfirm(request, response);
		} else {
			response.getWriter().write("Invalid path");
		}
	}
    protected void sendMail (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set request and response encoding to UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read parameters
        String Reciver = "caikhoa19@gmail.com";
        boolean result = false;
        JsonObject json = new Gson().fromJson(request.getReader(), JsonObject.class);
        String name = json.get("name").getAsString();
        String email = json.get("email").getAsString();
        String subject = json.get("subject").getAsString();
        String message = json.get("message").getAsString();

        // Check for null parameters
        if (name == null || email == null || subject == null || message == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Missing required parameters\"}");
            return;
        }

        // Set email properties
        String host = "smtp.gmail.com";
        final String username = "dungdodz@gmail.com"; // your email id
        final String password = "iajr zber izmx onch"; // your password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(email, name)); // Set the user's email as the sender
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Reciver)); // Admin's email as recipient
            msg.setSubject(subject);
            msg.setContent("Name: " + name + "<br>" + 
                           "Email: " + email + "<br>" + 
                           "Message: " + message, "text/html");
            msg.setSentDate(new java.util.Date());

            Transport.send(msg);
            result = true;
            String jsonResponse = "{\"success\": " + result + "}";
            response.getWriter().write(jsonResponse);

        } catch (MessagingException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error sending message: " + e.getMessage());
        }    
        }
    protected void sendConfirm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set request and response encoding to UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read parameters
        boolean result = false;
        String Reciver = "caikhoa19@gmail.com";
        String user_email = "caikhoa21@gmail.com";
        String name = "Dung";
        JsonObject json = new Gson().fromJson(request.getReader(), JsonObject.class);
//        String name = json.get("name").getAsString();
//        String email = json.get("email").getAsString();
        String subject = json.get("subject").getAsString();
        String message = json.get("message").getAsString();
//        String user_email = json.get("email").getAsString();

        // Check for null parameters
        if (subject == null || message == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Missing required parameters\"}");
            return;
        }

        // Set email properties
        String host = "smtp.gmail.com";
        final String username = "dungdodz@gmail.com"; // your email id
        final String password = "iajr zber izmx onch"; // your password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user_email)); 
            msg.setSubject(subject);
            msg.setContent(message, "text/html");
            msg.setSentDate(new java.util.Date());

            Transport.send(msg);

            result = true;
            String jsonResponse = "{\"success\": " + result + "}";
            response.getWriter().write(jsonResponse);
            System.out.println("==========success");

        } catch (MessagingException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error sending message: " + e.getMessage());
        }    
        }
}