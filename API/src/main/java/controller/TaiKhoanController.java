package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import DAO.TaiKhoanDAO;
import config.HttpUtil;
import model.TaiKhoan;

@WebServlet("/Account/*")
public class TaiKhoanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TaiKhoanDAO service;

	public TaiKhoanController() throws SQLException {
		super();
		this.service = new TaiKhoanDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();

	    switch (pathInfo) {
	    	case "/login":
	    		handleLogin(request, response);
            break;
	        case "/getAll":
	            handleGetAll(request, response);
	            break;
	        case "/getById":
	            handleGetById(request, response);
	            break;
	        case "/changePassword":
	            handleChangePassword(request, response);
	            break;
	        default:
	            response.getWriter().write("Invalid path");
	    }
	}

	private void handleGetAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String search = request.getParameter("request").toString();
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		try {
			List<TaiKhoan> list = this.service.getAll(search, pageNumber);
			int totalAccount = this.service.getTotal(search);
			
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode resultNode = objectMapper.createObjectNode();
	        resultNode.put("list", objectMapper.valueToTree(list));
	        resultNode.put("total", totalAccount);

	        // Chuyển đối tượng ObjectNode thành chuỗi JSON
	        String jsonResponse = objectMapper.writeValueAsString(resultNode);
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
			TaiKhoan account = this.service.getById(id);

			// Convert the list to JSON
			ObjectMapper objectMapper = new ObjectMapper();
			String result = objectMapper.writeValueAsString(account);

			// Send the JSON response to the client
			response.getWriter().write(result);
		} catch (SQLException e) {
			e.printStackTrace();
			String errorResponse = "{\"error\": \"An error occurred while processing the request.\"}";
			response.getWriter().write(errorResponse);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		
	    switch (pathInfo) {
	        
	        case "/create":
	            handleCreate(request, response);
	            break;
	        default:
	            response.getWriter().write("Invalid path");
	    }
	}

	private void handleCreate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean result = false;
		TaiKhoan newTaiKhoan = HttpUtil.of(request.getReader()).toModel(TaiKhoan.class);
		try {
			if (!this.service.checkEmail(newTaiKhoan.email)) {
	            result = this.service.create(newTaiKhoan);
	        } else {
	            result = false;
	        }
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		String jsonResponse = "{\"success\": " + result + "}";
		response.getWriter().write(jsonResponse);
				
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    String pathInfo = request.getPathInfo();

	    switch (pathInfo) {
	        case "/updateInfo":
	            handleUpdateInfo(request, response);
	            break;
	        default:
	            response.getWriter().write("Invalid path");
	    }
	}

	private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean result = false;

		//lấy email và pass cũ và pass mới
        String email = request.getParameter("email");
        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");

		try {
			result = this.service.changePassword(email, oldPass, newPass);
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		String jsonResponse = "{\"success\": " + result + "}";
		response.getWriter().write(jsonResponse);				
	}

	private void handleUpdateInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean result = false;

		TaiKhoan newTaiKhoan = HttpUtil.of(request.getReader()).toModel(TaiKhoan.class);
		try {
			result = this.service.updateInfo(newTaiKhoan);
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		String jsonResponse = "{\"success\": " + result + "}";
		response.getWriter().write(jsonResponse);		
	}

	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
        	TaiKhoan login = this.service.checkLogin(email, password);

            if (login != null) {
            	
                ObjectMapper objectMapper = new ObjectMapper();
                
                String jsonResponse = objectMapper.writeValueAsString(login);
                
                response.getWriter().write(jsonResponse);
                // trả về tài khoản
                  
            } else {
                String jsonResponse = "{\"success\": false, \"error\": \"Invalid email or password.\"}";
                response.getWriter().write(jsonResponse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "{\"error\": \"An error occurred while processing the request.\"}";
            response.getWriter().write(errorResponse);
        }
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

