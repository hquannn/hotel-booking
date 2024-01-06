package model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HoaDon {
	 @JsonProperty("id")
	    public int id;

	    @JsonProperty("tenKhachHang")
	    public String tenKhachHang;

	    @JsonProperty("dienThoai")
	    public String dienThoai;

	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "GMT+7")
	    @JsonProperty("ngayNhanPhong")
	    public String ngayNhanPhong;

	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "GMT+7")
	    @JsonProperty("ngayTraPhong")
	    public String ngayTraPhong;

	    @JsonProperty("loaiPhong")
	    public String loaiPhong;

	    @JsonProperty("tongTien")
	    public int tongTien;

	    @JsonProperty("donGiaPhong")
	    public int donGiaPhong;

	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "GMT+7")
	    @JsonProperty("ngayTao")
	    public String ngayTao;
}
