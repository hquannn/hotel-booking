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
import com.fasterxml.jackson.databind.node.ObjectNode;

import DAO.RoomHotelDAO;
import config.HttpUtil;
import dtos.chartDto;
import model.Room;
import model.RoomData;
import model.RoomHotel;

@WebServlet("/RoomHotel/*")
public class RoomHotelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomHotelDAO service;

	public RoomHotelController() throws SQLException {
		super();
		this.service = new RoomHotelDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pathInfo = request.getPathInfo();
		if (pathInfo.equals("/getAll")) {
			handleGetAll(request, response);
		} else if (pathInfo.equals("/getForChart")) {
			handleGetForChart(request, response);
		} else if (pathInfo.equals("/getSearched")) {
			handleGetSearched(request, response);
		} 
		else {
			response.getWriter().write("Invalid path");
		}
	}
	
	private void handleGetSearched(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String value = request.getParameter("request").toString();
		int hotelID = Integer.parseInt(request.getParameter("hotelID").toString());
		try {
			List<RoomData> listRoomData = this.service.getSearched(hotelID,value);

			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode resultNode = objectMapper.createObjectNode();
	        resultNode.put("listRoom", objectMapper.valueToTree(listRoomData));

	        // Chuyển đối tượng ObjectNode thành chuỗi JSON
	        String jsonResponse = objectMapper.writeValueAsString(resultNode);
            response.getWriter().write(jsonResponse);
		} catch (SQLException e) {
			e.printStackTrace();
			String errorResponse = "{\"error\": \"An error occurred while processing the request.\"}";
			response.getWriter().write(errorResponse);
		}
		
	}

	private void handleGetForChart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		int hotelID = Integer.parseInt(request.getParameter("hotelID").toString());
		try {
			List<chartDto> list = this.service.getForChart(hotelID);

			// Convert the list to JSON
			ObjectMapper objectMapper = new ObjectMapper();
			String result = objectMapper.writeValueAsString(list);

			// Send the JSON response to the client
			response.getWriter().write(result);
		} catch (SQLException e) {
			e.printStackTrace();
			String errorResponse = "{\"error\": \"An error occurred while processing the request.\"}";
			response.getWriter().write(errorResponse);
		}
	}

	private void handleGetAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		int hotelID = Integer.parseInt(request.getParameter("hotelID").toString());
		try {
			List<RoomData> listRoomData = this.service.getAllRoomData(hotelID);
			ObjectMapper objectMapper = new ObjectMapper();

	        // Chuyển đối tượng ObjectNode thành chuỗi JSON
	        String jsonResponse = objectMapper.writeValueAsString(listRoomData);
            response.getWriter().write(jsonResponse);
		} catch (SQLException e) {
			e.printStackTrace();
			String errorResponse = "{\"error\": \"An error occurred while processing the request.\"}";
			response.getWriter().write(errorResponse);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean result = false;
		RoomHotel newRoomHotel = HttpUtil.of(request.getReader()).toModel(RoomHotel.class);
		try {
			result = this.service.create(newRoomHotel);
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		String jsonResponse = "{\"success\": " + result + "}";
		response.getWriter().write(jsonResponse);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean result = false;

		RoomHotel newRoomHotel = HttpUtil.of(request.getReader()).toModel(RoomHotel.class);
		try {
			result = this.service.update(newRoomHotel);
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
		int Id = Integer.parseInt(request.getParameter("id"));
		boolean result = false;
		try {
			result = this.service.delete(Id);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		String jsonResponse = "{\"success\": " + result + "}";
		response.getWriter().write(jsonResponse);
	}
}
