package accesstaiwan.model.travel.SML;

public class Addr implements Values {

	
	String CHT = "";
	String CHS = "";
	String KR = "";
	String EN = "";
	String JP = "";
	
	public void setCHT(String s) {
		this.CHT = s;
	}

	public void setCHS(String s) {
		this.CHS = s;		
	}

	public void setKR(String s) {
		this.KR = s;
	}

	public void setEN(String s) {
		this.EN = s;		
	}

	public void setJP(String s) {
		this.JP = s;		
	}

	public String getCHT() {
		return CHT;
	}

	public String getKR() {
		return KR;
	}

	public String getEN() {
		return EN;
	}

	public String getJP() {
		return JP;
	}

	public String getCHS() {
		return	CHS;
	}

}
