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


import DAO.RoomBookDAO;
import config.HttpUtil;
import dtos.RoomBookData;
import model.RoomBook;

/**
 * Servlet implementation class RoomBookController
 */
@WebServlet("/RoomBook")
public class RoomBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomBookDAO service;
	
    public RoomBookController() throws SQLException {
        super();
        this.service = new RoomBookDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String search = request.getParameter("request").toString();
		try {
			List<RoomBookData> list = this.service.getAll(search);
			
			ObjectMapper objectMapper = new ObjectMapper();

	        // Chuyển đối tượng ObjectNode thành chuỗi JSON
	        String jsonResponse = objectMapper.writeValueAsString(list);
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
        RoomBook newRoomBook = HttpUtil.of(request.getReader()).toModel(RoomBook.class);

        try {
            result = this.service.create(newRoomBook);
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
        RoomBook newRoomBook = HttpUtil.of(request.getReader()).toModel(RoomBook.class);

        try {
            result = this.service.update(newRoomBook);
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
        }

        String jsonResponse = "{\"success\": " + result + "}";
        response.getWriter().write(jsonResponse);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
