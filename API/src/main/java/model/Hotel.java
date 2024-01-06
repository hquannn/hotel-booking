package model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Hotel {
    @JsonProperty("id")
    public int id;

    @JsonProperty("hotelName")
    public String hotelName;

    @JsonProperty("address")
    public String address;

    @JsonProperty("image")
    public String image;

    @JsonProperty("shortdescription")
    public String shortDescription;

    @JsonProperty("email")
    public String email;

    @JsonProperty("phone")
    public String phone;
}
