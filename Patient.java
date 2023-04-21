public class Patient extends Person {
	private String patientID;
	
	public Patient(String name, String surname, String dateOfBirth, int mobileNumber, String patientID) {
		super(name, surname, dateOfBirth, mobileNumber);
		this.patientID = patientID;
	}
	
	public void setpatientID(String patientID) {
		this.patientID = patientID;
	}
	
	public String getpatientID() {
		return patientID;
	}
}