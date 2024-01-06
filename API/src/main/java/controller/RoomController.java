package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import DAO.RoomDAO;
import DAO.RoomHotelDAO;
import config.HttpUtil;
import dtos.chartDto;
import model.Room;
import model.RoomData;
import model.RoomHotel;

@WebServlet("/Room/*")
public class RoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomDAO serviceRoom;
	private RoomHotelDAO serviceRoomHotel;
	
	public RoomController() throws SQLException {
		super();
		this.serviceRoom = new RoomDAO();
		this.serviceRoomHotel = new RoomHotelDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();

		if (pathInfo.equals("/getAll")) {
			handleGetAll(request, response);
		} else if (pathInfo.equals("/getForChart")) {
			handleGetForChart(request, response);
		} else if (pathInfo.equals("/getSearchedRoom")) {
			handleGetSearchedRoom(request, response);
		}
		else {
			response.getWriter().write("Invalid path");
		}
	}

	private void handleGetSearchedRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String value = request.getParameter("request").toString();
		try {
			List<Room> listRoom = this.serviceRoom.getSearchedRoom(value);

			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode resultNode = objectMapper.createObjectNode();
	        resultNode.put("listRoom", objectMapper.valueToTree(listRoom));

	        // Chuyển đối tượng ObjectNode thành chuỗi JSON
	        String jsonResponse = objectMapper.writeValueAsString(resultNode);
            response.getWriter().write(jsonResponse);
		} catch (SQLException e) {
			e.printStackTrace();
			String errorResponse = "{\"error\": \"An error occurred while processing the request.\"}";
			response.getWriter().write(errorResponse);
		}
	}

	private void handleGetAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<Room> listRoom = this.serviceRoom.getAll();
			ObjectMapper objectMapper = new ObjectMapper();

	        // Chuyển đối tượng ObjectNode thành chuỗi JSON
	        String jsonResponse = objectMapper.writeValueAsString(listRoom);
            response.getWriter().write(jsonResponse);
		} catch (SQLException e) {
			e.printStackTrace();
			String errorResponse = "{\"error\": \"An error occurred while processing the request.\"}";
			response.getWriter().write(errorResponse);
		}
	}

	private void handleGetForChart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<chartDto> list = this.serviceRoom.getForChart();

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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean result = false;
		Room newRoom = HttpUtil.of(request.getReader()).toModel(Room.class);
		try {	
			result = this.serviceRoom.create(newRoom);

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

		Room newRoom = HttpUtil.of(request.getReader()).toModel(Room.class);
		try {
			result = this.serviceRoom.update(newRoom);
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
			result = this.serviceRoomHotel.deleteByRoomID(Id);
			result = this.serviceRoom.delete(Id);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		String jsonResponse = "{\"success\": " + result + "}";
		response.getWriter().write(jsonResponse);
	}
}
