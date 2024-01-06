package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomData {
	
 	@JsonProperty("id")
    public int id;
	
 	@JsonProperty("roomId")
    public int roomId;
 	
 	@JsonProperty("roomName")
    public String roomName;
 	
 	@JsonProperty("roomType")
    public String roomType;
 	
 	@JsonProperty("roomBedType")
    public String roomBedType;

    @JsonProperty("roomSize")
    public int roomSize;

    @JsonProperty("roomCapacity")
    public int roomCapacity;
    
    @JsonProperty("roomDescription")
    public String roomDescription;

    @JsonProperty("hotelId")
    public int hotelId;
	
    @JsonProperty("price")
    public int price;
	
 	@JsonProperty("isActive")
    public boolean isActive;
 	
 	@JsonProperty("image")
    public String image;
 	
 	public RoomData(Room room,int hotelId, int price, boolean isActive, String image) {
 		this.roomId = room.id;
 		this.roomName = room.roomName;
 		this.roomType = room.roomType;
 		this.roomBedType = room.roomBedType;
 		this.roomSize = room.roomSize;
 		this.roomCapacity = room.roomCapacity;
 		this.roomDescription = room.roomDescription;
 		this.hotelId = hotelId;
 		this.price = price;
 		this.isActive = isActive;
 		this.image = image;
 	}

	public RoomData() {
	}
}
