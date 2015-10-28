public class Weather {
	String city;
	String country;
	String time;
	String temperature;
	String weatherType;
	String windDirection;
	String windSpeed;
	String humidity;
	String pressure;
	String imageName;

	public Weather() {
		// TODO Auto-generated constructor stub
	}
	public String toString() {
		return  "city=" + city
				+ ", country=" + country
				+ ", time=" + time
				+ ", temperature=" + temperature
				+ ", weatherType=" + weatherType
				+ ", windDirection=" + windDirection
				+ ", windSpeed=" + windSpeed
				+ ", humidity=" + humidity
				+ ", pressure=" + pressure
				+", imageName=" + imageName;
	}
}
