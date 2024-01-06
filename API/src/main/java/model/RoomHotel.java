package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomHotel {
 	@JsonProperty("id")
    public int id;
 	
 	@JsonProperty("roomId")
    public int roomId;
 	
 	@JsonProperty("hotelId")
    public int hotelId;
 	
 	@JsonProperty("price")
    public int price;

    @JsonProperty("isActive")
    public boolean isActive;

    @JsonProperty("image")
    public String image;
    
    public RoomHotel(int roomID, int hotelID, int price, boolean isActive, String image) {
    	this.roomId = roomID;
    	this.hotelId = hotelID;
    	this.price = price;
    	this.isActive = isActive;
    	this.image = image;
    }

	public RoomHotel() {
	}
}
