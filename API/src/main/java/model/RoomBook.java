package model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomBook {
	
	@JsonProperty("id")
	public int id;
	
	@JsonProperty("hoteRoomId")
	public int hoteRoomId;
	
	@JsonProperty("accountId")
	public int accountId;
	
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
	
}
