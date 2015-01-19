package accesstaiwan.model.travel.SML;

import java.util.List;

public class Entity {

	String id;
	String tel;
	Type type;
	double[] location;
	County county;
	Addr addr;
	Time time;
	Des des;
	Price price;
	Name name;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public Addr getAddr() {
		return addr;
	}
	public void setAddr(Addr addr) {
		this.addr = addr;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public County getCounty() {
		return county;
	}
	public void setCounty(County county) {
		this.county = county;
	}
	public void setLocaiton(double lng, double lat) {
		this.location = new double[] {lng, lat};
	}
	public double[] getLocation() {
		return location;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public Des getDes() {
		return des;
	}
	public void setDes(Des des) {
		this.des = des;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
}
