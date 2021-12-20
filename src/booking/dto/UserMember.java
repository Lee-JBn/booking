package booking.dto;

public class UserMember {
	
	private static String ssAirport;
	private static String saAirport;
	private static String asAirport;
	private static String aaAirport;
	private static String sDate;
	private static String aDate;
	private static int adult;
	private static int children;
	private static int baby;
	
	public UserMember() {}

	public UserMember(String ssAirport, String saAirport, String sDate, String asAirport, String aaAirport, String aDate, int adult, int children, int baby) {
		this.ssAirport = ssAirport;
		this.saAirport = saAirport;
		this.sDate = sDate;
		this.asAirport = asAirport;
		this.aaAirport = aaAirport;
		this.aDate = aDate;
		this.adult = adult;
		this.children = children;
		this.baby = baby;
	}

	public static String getSsAirport() {
		return ssAirport;
	}

	public void setSsAirport(String ssAirport) {
		this.ssAirport = ssAirport;
	}

	public static String getSaAirport() {
		return saAirport;
	}

	public void setSaAirport(String saAirport) {
		this.saAirport = saAirport;
	}

	public static String getAsAirport() {
		return asAirport;
	}

	public void setAsAirport(String asAirport) {
		this.asAirport = asAirport;
	}

	public static String getAaAirport() {
		return aaAirport;
	}

	public void setAaAirport(String aaAirport) {
		this.aaAirport = aaAirport;
	}

	public static int getAdult() {
		return adult;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public static int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public static int getBaby() {
		return baby;
	}

	public void setBaby(int baby) {
		this.baby = baby;
	}
	
	public static String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public static String getaDate() {
		return aDate;
	}

	public void setaDate(String aDate) {
		this.aDate = aDate;
	}

	
	

}
