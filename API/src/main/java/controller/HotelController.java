package controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.HotelDAO;
import model.Hotel;
import config.HttpUtil;

@WebServlet("/Hotel/*")
public class HotelController extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private HotelDAO service;

    public HotelController() throws SQLException {
        super();
        this.service = new HotelDAO(); // Assuming you have a HotelDAO class
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pathInfo = request.getPathInfo();

		if (pathInfo.equals("/getAll")) {
			handleGetAll(request, response);
		} else if (pathInfo.equals("/getById")) {
			handleGetById(request, response);
		} else {
			response.getWriter().write("Invalid path");
		}
	}
    
    private void handleGetAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
        try {
        	String search = request.getParameter("request").toString();
            List<Hotel> list = this.service.getAll(search);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(list);
            response.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "{\"error\": \"An error occurred while processing the request.\"}";
            response.getWriter().write(errorResponse);
        }
    }
    
    private void handleGetById(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Hotel hotel = this.service.getById(id);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(hotel);
            response.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "{\"error\": \"An error occurred while processing the request.\"}";
            response.getWriter().write(errorResponse);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

        boolean result = false;
        Hotel newHotel = HttpUtil.of(request.getReader()).toModel(Hotel.class);

        try {
            result = this.service.create(newHotel);
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
        }

        String jsonResponse = "{\"success\": " + result + "}";
        response.getWriter().write(jsonResponse);
    }
    
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        boolean result = false;
        Hotel updatedHotel = HttpUtil.of(request.getReader()).toModel(Hotel.class);

        try {
            result = this.service.update(updatedHotel);
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
        }

        String jsonResponse = "{\"success\": " + result + "}";
        response.getWriter().write(jsonResponse);
    }
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        boolean result = false;

        result = this.service.delete(id);

        String jsonResponse = "{\"success\": " + result + "}";
        response.getWriter().write(jsonResponse);
    }
    
    
}
