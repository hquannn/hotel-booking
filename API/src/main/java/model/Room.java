package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Room {
	 	@JsonProperty("id")
	    public int id;
	 	
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
	    
}
