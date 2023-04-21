import java.io.*;

public class Doctor extends Person implements Serializable {
	private String medicalLicenceNumber, specialisation;
	
	public Doctor(String name, String surname, String dateOfBirth, int mobileNumber, String medicalLicenceNumber, String specialisation) {
		super(name, surname, dateOfBirth, mobileNumber);
		this.medicalLicenceNumber = medicalLicenceNumber;
		this.specialisation = specialisation;
	}
	
	public void setMedicalLicenceNumber(String medicalLicenceNumber) {
		this.medicalLicenceNumber = medicalLicenceNumber;
	}
	
	public String getMedicalLicenceNumber() {
		return medicalLicenceNumber;
	}
	
	public void setSpecialisation(String specialisation) {
		this.specialisation = specialisation;
	}
	
	public String getSpecialisation() {
		return specialisation;
	}
		
}
