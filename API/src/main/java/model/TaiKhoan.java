package model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaiKhoan {
	 	@JsonProperty("id")
	    public int id;

	    @JsonProperty("email")
	    public String email;

	    @JsonProperty("password")
	    public String password;
	   
	    @JsonProperty("fullName")
	    public String fullName;

	    @JsonProperty("phone")
	    public String phone;

	    @JsonProperty("gender")
	    public String gender;

	    @JsonProperty("birthday")
	    public String birthday;
	    
	    @JsonProperty("image")
	    public String image;
	    
	    @JsonProperty("role")
	    public int role;
	
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "GMT+7")
	    @JsonProperty("creationTime")
	    public String creationTime;
}
