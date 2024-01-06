package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import model.Hotel;

import config.ConnectionPoolImpl;

public class HotelDAO {
	private ConnectionPoolImpl connectionPool;
    private Connection _connect;

    public HotelDAO() throws SQLException {
        this.connectionPool = new ConnectionPoolImpl();
        this._connect = this.connectionPool.getConnection();
    }
    public List<Hotel> getAll(String request) throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM tblhotel where CONCAT(IFNULL(hotelName, ''), ' ', IFNULL(address,''), ' ', IFNULL(email,'') ) LIKE CONCAT('%',?,'%')";

        PreparedStatement statement = _connect.prepareStatement(sql);
		statement.setString(1, request);
		ResultSet result = statement.executeQuery(); 
        	while (result.next()) {
                Hotel hotel = new Hotel();
                hotel.id = result.getInt("id");
                hotel.hotelName = result.getString("hotelName");
                hotel.address = result.getString("address");
                hotel.image = result.getString("image");
                hotel.shortDescription = result.getString("shortdescription");
                hotel.email = result.getString("email");
                hotel.phone = result.getString("phone");

                hotels.add(hotel);
            }
        	
        return hotels;
        
}
    public Hotel getById(int id) throws SQLException {
        Hotel hotel = new Hotel();
        String sql = "SELECT * FROM tblhotel WHERE id=?";

        try (PreparedStatement statement = _connect.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    hotel.id = result.getInt("id");
                    hotel.hotelName = result.getString("hotelName");
                    hotel.address = result.getString("address");
                    hotel.image = result.getString("image");
                    hotel.shortDescription = result.getString("shortdescription");
                    hotel.email = result.getString("email");
                    hotel.phone = result.getString("phone");
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return hotel;
    }
    
    public boolean create(Hotel data) throws SQLException {
        String sql = "INSERT INTO tblhotel (hotelName, address, image, shortdescription, email, phone) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = _connect.prepareStatement(sql)) {
            statement.setString(1, data.hotelName);
            statement.setString(2, data.address);
            statement.setString(3, data.image);
            statement.setString(4, data.shortDescription);
            statement.setString(5, data.email);
            statement.setString(6, data.phone);

            statement.executeUpdate();
        } catch (SQLException e) {
        	return false;
        }
        return true;
    }
    
    public boolean update(Hotel data) throws SQLException {
        String sql = "UPDATE tblhotel SET hotelName=?, address=?, image=?, shortdescription=?, email=?, phone=? WHERE id=?";

        try (PreparedStatement statement = _connect.prepareStatement(sql)) {
            statement.setString(1, data.hotelName);
            statement.setString(2, data.address);
            statement.setString(3, data.image);
            statement.setString(4, data.shortDescription);
            statement.setString(5, data.email);
            statement.setString(6, data.phone);
            statement.setInt(7, data.id);
            
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM tblhotel WHERE id=?";

        try (PreparedStatement statement = _connect.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
    
    public static void main(String[] args) {
    }   
    
    
}


    
