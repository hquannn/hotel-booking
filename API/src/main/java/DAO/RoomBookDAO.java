package DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.ConnectionPoolImpl;
import dtos.RoomBookData;
import model.RoomBook;
import model.Hotel;
import model.Room;
import model.RoomHotel;

public class RoomBookDAO {
	private ConnectionPoolImpl connectionPool;
	private Connection _connect;

	public RoomBookDAO() throws SQLException {
		this.connectionPool = new ConnectionPoolImpl();
		this._connect = this.connectionPool.getConnection();
	}

	public List<RoomBookData> getAll(String request) throws SQLException {
	    request = request == null ? "" : request.trim();
	    List<RoomBookData> roombooks = new ArrayList<>();
	    String sql = "SELECT rbook.*, hotel.hotelName, rhotel.price, room.roomName, room.roomType  FROM it6020003_data_btl.tblroombook rbook "
	    		+ "	    		left join tblroom_hotel rhotel on rbook.hoteRoomId = rhotel.Id "
	    		+ "	    		left join tblroom room on rhotel.roomId = room.id "
	    		+ "	    		left join tblhotel hotel on rhotel.hotelId = hotel.id "
	    		+ "	            WHERE CONCAT(IFNULL(rbook.fullName, ''), ' ', IFNULL(rbook.phone, ''), ' ', IFNULL(rbook.status, '')) LIKE CONCAT('%', ?, '%')";

	    try (PreparedStatement statement = _connect.prepareStatement(sql)) {
	        statement.setString(1, request);
	        try (ResultSet result = statement.executeQuery()) {
	            while (result.next()) {
	                RoomBookData roombook = new RoomBookData();

	                roombook.id = result.getInt("id");
	                roombook.hoteRoomId = result.getInt("hoteRoomId");
	                roombook.checkIn = result.getString("checkIn");
	                roombook.checkOut = result.getString("checkOut");
	                roombook.status = result.getString("status");
	                roombook.fullName = result.getString("fullName");
	                roombook.phone = result.getString("phone");
	                roombook.description = result.getString("description");
	                roombook.creationTime = result.getString("creationTime");
	                roombook.totalPrice = result.getInt("totalPrice");
	                
	                roombook.roomName = result.getString("roomName");
	                roombook.roomType = result.getString("roomType");
	                roombook.price = result.getInt("price");        
	                roombook.hotelName = result.getString("hotelName");
	                
	                roombooks.add(roombook);
	            }
	        }
	    }

	    return roombooks;
	}

	
	
	
	
	public RoomBookData getById(int id) throws SQLException {
		RoomBookData roombook = new RoomBookData();
		String sql = "SELECT rbook.*, hotel.hotelName, rhotel.price, room.roomName, room.roomType  FROM it6020003_data_btl.tblroombook rbook "
	    		+ "join tblroom_hotel rhotel on rbook.hoteRoomId = rhotel.Id "
	    		+ "join tblroom room on rhotel.roomId = room.id "
	    		+ "join tblhotel hotel on rhotel.hotelId = hotel.id "
				+ "WHERE id = ?";
		PreparedStatement statement = _connect.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		if (result.next()) {

			roombook.id = result.getInt("id");
            roombook.hoteRoomId = result.getInt("hoteRoomId");
            roombook.checkIn = result.getString("checkIn");
            roombook.checkOut = result.getString("checkOut");
            roombook.status = result.getString("status");
            roombook.fullName = result.getString("fullName");
            roombook.phone = result.getString("phone");
            roombook.description = result.getString("description");
            roombook.creationTime = result.getString("creationTime");
            roombook.totalPrice = result.getInt("totalPrice");
            
            roombook.roomName = result.getString("roomName");
            roombook.roomType = result.getString("roomType");
            roombook.price = result.getInt("price");        
            roombook.hotelName = result.getString("hotelName");
         
		}

		return roombook;
	}

	public boolean create(RoomBook data) throws SQLException {
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(
				"INSERT INTO tblroombook (accountId, hoteRoomId, checkIn, checkOut, status, fullName, phone, description, creationTime, totalPrice)  ");
		sqlBuilder.append(" VALUES (?,?,?,?,?,?,?,?,?,?);");
		PreparedStatement statement = _connect.prepareStatement(sqlBuilder.toString());
		statement.setInt(1, data.accountId);
		statement.setInt(2, data.hoteRoomId);
		statement.setString(3, data.checkIn);
		statement.setString(4, data.checkOut);
		statement.setString(5, data.status);
		statement.setString(6, data.fullName);
		statement.setString(7, data.phone);
		statement.setString(8, data.description);
		statement.setString(9, data.creationTime);
		statement.setInt(10, data.totalPrice);
		statement.executeUpdate();
		return true;
	}

	public boolean update(RoomBook data) throws SQLException {
		try {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(
					"UPDATE tblroombook SET  status= ? ");
			sqlBuilder.append("WHERE id=?");

			PreparedStatement statement = _connect.prepareStatement(sqlBuilder.toString());
			statement.setString(1, data.status);		
			statement.setInt(2, data.id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(int id) {
		try {
			String sql = "DELETE FROM tblroombook WHERE id=?";
			PreparedStatement statement = _connect.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}	
}
