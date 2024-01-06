package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.ConnectionPoolImpl;
import dtos.chartDto;
import model.Room;

public class RoomDAO {
	private ConnectionPoolImpl connectionPool;
	private Connection _connect;

	public RoomDAO() throws SQLException {
		this.connectionPool = new ConnectionPoolImpl();
		this._connect = this.connectionPool.getConnection();
	}

	public List<Room> getAll() throws SQLException {
		List<Room> listRoom = new ArrayList<>();
		String sql = "SELECT * FROM tblroom ";
		PreparedStatement statement = _connect.prepareStatement(sql);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			Room room = new Room();

			room.id = result.getInt("id");
			room.roomName = result.getString("roomName");
			room.roomType = result.getString("roomType");
			room.roomBedType = result.getString("roomBedType");
			room.roomSize = result.getInt("roomSize");
			room.roomCapacity = result.getInt("roomCapacity");
			room.roomDescription = result.getString("roomDescription");

			listRoom.add(room);
		}

		return listRoom;
	}
	
	public List<Room> getSearchedRoom(String request) throws SQLException {
		request = request == null ? "" : request.trim();
		List<Room> listRoom = new ArrayList<>();
		String sql = "SELECT * FROM tblroom WHERE CONCAT(IFNULL(roomName, ''),' ', IFNULL(roomType,''),' ',IFNULL(roomBedType,'')) LIKE CONCAT('%',?,'%')";
		PreparedStatement statement = _connect.prepareStatement(sql);
		statement.setString(1, request);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			Room room = new Room();

			room.id = result.getInt("id");
			room.roomName = result.getString("roomName");
			room.roomType = result.getString("roomType");
			room.roomBedType = result.getString("roomBedType");
			room.roomSize = result.getInt("roomSize");
			room.roomCapacity = result.getInt("roomCapacity");
			room.roomDescription = result.getString("roomDescription");

			listRoom.add(room);
		}

		return listRoom;
	}
	
	public List<chartDto> getForChart() throws SQLException {
		 List<chartDto> list = new ArrayList();
		 String sql = "select roomType, Count(*) as total from tblroom group by roomType;";
		 PreparedStatement statement = _connect.prepareStatement(sql);
		 ResultSet result = statement.executeQuery();
		 while (result.next()) {
				chartDto data = new chartDto();
				data.data = result.getString("roomType");
				data.total = result.getInt("total");
				list.add(data);
			}
		 
		 return list;
	}
	
	public Room getById(int id) throws SQLException {
		Room selectedRoom = new Room();
		String sql = "SELECT * FROM tblroom WHERE id=?";
		PreparedStatement statement = _connect.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		if (result.next()) {
			selectedRoom.id = result.getInt("id");
			selectedRoom.roomName = result.getString("roomName");
			selectedRoom.roomType = result.getString("roomType");
			selectedRoom.roomBedType = result.getString("roomBedType");
			selectedRoom.roomSize = result.getInt("roomSize");
			selectedRoom.roomCapacity = result.getInt("roomCapacity");
			selectedRoom.roomDescription = result.getString("roomDescription");
		}

		return selectedRoom;
	}

	public boolean create(Room data) throws SQLException {
		try {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(
					"INSERT INTO tblroom (roomName,roomType,roomBedType,roomSize,roomCapacity,roomDescription)");
			sqlBuilder.append("VALUES (?, ?, ?, ?, ?, ?)");

			PreparedStatement statement = _connect.prepareStatement(sqlBuilder.toString());
			statement.setString(1, data.roomName);
			statement.setString(2, data.roomType);
			statement.setString(3, data.roomBedType);
			statement.setInt(4, data.roomSize);
			statement.setInt(5, data.roomCapacity);
			statement.setString(6, data.roomDescription);
			statement.executeUpdate();
			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Room data) throws SQLException {
		try {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(
					"UPDATE tblroom SET roomName= ?, roomType= ?, roomBedType= ?, roomSize= ?, roomCapacity= ?, roomDescription= ?");
			sqlBuilder.append("WHERE id=?");

			PreparedStatement statement = _connect.prepareStatement(sqlBuilder.toString());
			statement.setString(1, data.roomName);
			statement.setString(2, data.roomType);
			statement.setString(3, data.roomBedType);
			statement.setInt(4, data.roomSize);
			statement.setInt(5, data.roomCapacity);
			statement.setString(6, data.roomDescription);
			statement.setInt(7, data.id);

			statement.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(int id) {
		try {
			String sql = "DELETE FROM tblRoom WHERE id=?";
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
