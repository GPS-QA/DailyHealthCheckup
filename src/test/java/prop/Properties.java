package prop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Properties {

	// public static String time = "5:15 pm";

	public static final String DATE_PATTERN = "MM/dd/yyyy";
	public static final DateTimeFormatter LD_FOMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

	static LocalDate today = LocalDate.now();
	public static String date = today.toString();
	static String day = date.substring(date.length() - 2);

	public static String FitnoteFromDate() {
		String month = date.substring(date.indexOf("-") + 1);
		month = month.substring(0, month.indexOf("-"));
		String FitnoteFromDate = month + "/" + day;
		return FitnoteFromDate;
	}

	static LocalDate futureDate = today.plusDays(5);
	public static String FitnoteExpirationDate = LD_FOMATTER.format(futureDate);

	public static String date_onlyNo = date.replaceAll("\\D", "");
	public static String firstName = "Test" + date_onlyNo;
	public static String emailPatient_gps_web = "gpstest" + date_onlyNo + "@mailinator.com";
	public static String emailPatient_sd_web = "sdtest" + date_onlyNo + "@mailinator.com";
	public static String phone_gps_web = "70" + date_onlyNo;
	public static String phone_sd_web = "71" + date_onlyNo;

	public static String lastName_gps_web = "Silva";
	public static String lastName_sd_web = "Perera";
	public static String lastName_gps_and = "Wijerathna";
	public static String lastName_gps_ios = "Bandara";
	public static String lastName_sd_and = "Fonseka";
	public static String birthDate = "01031990";
	public static String password = "Thanu@93";
	public static String emailDoctor = "doctortest@mailinator.com";
	public static String emailDoctorNonGPS = "thanud@mailinator.com";
	public static String emailVet = "thanuv@mailinator.com";
	public static String emailPhamacist = "thanup@mailinator.com";
	public static String passwordDoctor = "Qwerty123";
	public static String storeId = "6668";
	public static String pin = "0000";

}
