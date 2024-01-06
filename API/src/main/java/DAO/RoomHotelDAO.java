package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.ConnectionPoolImpl;
import model.Room;
import model.RoomData;
import model.RoomHotel;
import dtos.chartDto;

public class RoomHotelDAO {
	private ConnectionPoolImpl connectionPool;
	private Connection _connect;

	public RoomHotelDAO() throws SQLException {
		this.connectionPool = new ConnectionPoolImpl();
		this._connect = this.connectionPool.getConnection();
	}
	
	public RoomHotel getRoomData(int roomID, int hotelID) throws SQLException {
		RoomHotel selectedRoomHotel = new RoomHotel();
		String sql = "SELECT * FROM tblroom_hotel WHERE hotelId = ?";
		PreparedStatement statement = _connect.prepareStatement(sql);
		statement.setInt(1, roomID);
		statement.setInt(2, hotelID);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			selectedRoomHotel.id = result.getInt("id");
			selectedRoomHotel.roomId = result.getInt("roomId");
			selectedRoomHotel.hotelId = result.getInt("hotelId");
			selectedRoomHotel.price= result.getInt("price");
			selectedRoomHotel.isActive = result.getBoolean("isActive");
			selectedRoomHotel.image = result.getString("image");
		}

		return selectedRoomHotel;
	}

	
	public boolean create(RoomHotel data) throws SQLException {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM tblroom_hotel WHERE hotelId = ? and roomId = ?";
	    PreparedStatement check = _connect.prepareStatement(sql);
	        check.setInt(1, data.hotelId);
	        check.setInt(2, data.roomId);
	        ResultSet resultSet = check.executeQuery();
	            if (resultSet.next()) {
	                count = resultSet.getInt(1);
	            }
	      
	    if(count > 0) return false;
	    else
		try {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(
					"INSERT INTO tblroom_hotel (roomId,hotelId,price,isActive,image)");
			sqlBuilder.append("VALUES (?, ?, ?, ?, ?)");

			PreparedStatement statement = _connect.prepareStatement(sqlBuilder.toString());
			statement.setInt(1, data.roomId);
			statement.setInt(2, data.hotelId);
			statement.setInt(3, data.price);
			statement.setBoolean(4, data.isActive);
			statement.setString(5, data.image);
			statement.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(RoomHotel data) throws SQLException {
		data.image = data.image == null ? "": data.image;
		try {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(
					"UPDATE tblroom_hotel SET price= ?, isActive= ?, image= ?, roomId = ? ");
			sqlBuilder.append("WHERE id=?");

			PreparedStatement statement = _connect.prepareStatement(sqlBuilder.toString());
			statement.setInt(1, data.price);
			statement.setBoolean(2, data.isActive);
			statement.setString(3, data.image);
			statement.setInt(4, data.roomId);
			statement.setInt(5, data.id);

			statement.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(int id) {
		try {
			String sql = "DELETE FROM tblroom_hotel WHERE id=?";
			PreparedStatement statement = _connect.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteByRoomID(int roomID) {
		try {
			String sql = "delete from tblroom_hotel where roomId =?;";
			PreparedStatement statement = _connect.prepareStatement(sql);
			statement.setInt(1, roomID);
			statement.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<RoomData> getAllRoomData(int hotelID) throws SQLException {
		List<RoomData> listRoomData = new ArrayList<>();
		String sql = "SELECT tblroom_hotel.id as roomHotelID, tblroom.*, tblroom_hotel.hotelId,tblroom_hotel.price, tblroom_hotel.isActive, tblroom_hotel.image "
				+ "FROM tblroom_hotel join tblroom on tblroom_hotel.roomId = tblroom.id "
				+ "WHERE tblroom_hotel.hotelId = ?;";
		PreparedStatement statement = _connect.prepareStatement(sql);
		statement.setInt(1, hotelID);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			RoomData roomData = new RoomData();
			roomData.id = result.getInt("roomHotelID");
			roomData.roomId = result.getInt("id");
			roomData.roomName = result.getString("roomName");
			roomData.roomType = result.getString("roomType");
			roomData.roomBedType = result.getString("roomBedType");
			roomData.roomSize = result.getInt("roomSize");
			roomData.roomCapacity = result.getInt("roomCapacity");
			roomData.roomDescription = result.getString("roomDescription");
			roomData.hotelId = result.getInt("hotelID");
			roomData.price = result.getInt("price");
			roomData.isActive = result.getBoolean("isActive");
			roomData.image = result.getString("image");

			listRoomData.add(roomData);
		}

		return listRoomData;
	}
	
	public List<chartDto> getForChart(int hotelID) throws SQLException {
		 List<chartDto> list = new ArrayList();
		 String sql = "SELECT tblroom.roomtype, COUNT(*) as total "
		 		+ "FROM tblroom_hotel join tblroom on tblroom_hotel.roomId = tblroom.id "
		 		+ "WHERE tblroom_hotel.hotelId = ? "
		 		+ "GROUP BY tblroom.roomType;";
		 PreparedStatement statement = _connect.prepareStatement(sql);
		 statement.setInt(1, hotelID);
		 ResultSet result = statement.executeQuery();
		 while (result.next()) {
				chartDto data = new chartDto();
				data.data = result.getString("roomType");
				data.total = result.getInt("total");
				list.add(data);
			}
		 
		 return list;
	}
	
	public List<RoomData> getSearched(int hotelID, String request) throws SQLException {
		request = request == null ? "" : request.trim();
		List<RoomData> listRoomData = new ArrayList<>();
		String sql = "SELECT tblroom_hotel.id as roomHotelID, tblroom.*, tblroom_hotel.hotelId,tblroom_hotel.price, tblroom_hotel.isActive, tblroom_hotel.image "
				+ "FROM tblroom_hotel join tblroom on tblroom_hotel.roomId = tblroom.id "
				+ "WHERE tblroom_hotel.hotelId = ? AND CONCAT(IFNULL(tblroom.roomName, ''),' ', IFNULL(tblroom.roomType,''),' ',IFNULL(tblroom.roomBedType,'')) LIKE CONCAT('%',?,'%');";
		PreparedStatement statement = _connect.prepareStatement(sql);
		statement.setInt(1, hotelID);
		statement.setString(2, request);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			RoomData roomData = new RoomData();
			roomData.id = result.getInt("roomHotelID");
			roomData.roomId = result.getInt("id");
			roomData.roomName = result.getString("roomName");
			roomData.roomType = result.getString("roomType");
			roomData.roomBedType = result.getString("roomBedType");
			roomData.roomSize = result.getInt("roomSize");
			roomData.roomCapacity = result.getInt("roomCapacity");
			roomData.roomDescription = result.getString("roomDescription");
			roomData.hotelId = result.getInt("hotelID");
			roomData.price = result.getInt("price");
			roomData.isActive = result.getBoolean("isActive");
			roomData.image = result.getString("image");

			listRoomData.add(roomData);
		}

		return listRoomData;
	}
}
