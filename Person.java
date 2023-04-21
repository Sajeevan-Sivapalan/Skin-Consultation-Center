public class Person {
	private String name, surname, dateOfBirth;
	private int mobileNumber;
	
	public Person(String name, String surname, String dateOfBirth, int mobileNumber) {
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.mobileNumber = mobileNumber;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSurName(String surname) {
		this.surname = surname;
	}
	
	public String getSurName() {
		return surname;
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public int getMobileNumber() {
		return mobileNumber;
	}
}
