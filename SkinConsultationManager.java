import java.util.*;

public interface SkinConsultationManager {
	public static int countDoctor() {
		return 0;
	}
	public static boolean addDoctor(Doctor doctor) {
		return false;
	}
	public static boolean deleteDoctor(String medicalLicenceNumber) {
		return false;
	}
	
	public static ArrayList<Doctor> getDoctorList() {
		return null;
	}
	
	public static boolean addDoctorConsultation(String medicalLicenceNumber, String consultationDate) {
		return false;
	}
	
	public static boolean isconsultationAvailable(String medicalLicenceNumber, String consultationDate) {
		return false;
	}
	
	public static boolean deleteConsultation(String medicalLicenceNumber, String consultationDate) {
		return false;
	}
}