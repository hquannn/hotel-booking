package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomBookData {
	@JsonProperty("id")
	public int id;
	
	@JsonProperty("hoteRoomId")
	public int hoteRoomId;
	
	@JsonProperty("checkIn")
	public String checkIn;
	
	@JsonProperty("checkOut")
	public String checkOut;
	
	@JsonProperty("status")
	public String status;
	
	@JsonProperty("fullName")
	public String fullName;
	
	@JsonProperty("phone")
	public String phone;
	
	@JsonProperty("description")
	public String description;
	
	
	@JsonProperty("creationTime")
	public String creationTime;
	
	
	@JsonProperty("totalPrice")
	public int totalPrice;
	
	@JsonProperty("price")
    public int price;
	
	@JsonProperty("hotelName")
    public String hotelName;
	
	@JsonProperty("roomType")
    public String roomType;
	
	@JsonProperty("roomName")
    public String roomName;
}
