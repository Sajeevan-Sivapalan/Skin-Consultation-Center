import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class SkinConsultationManagerMain implements SkinConsultationManager {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			System.out.println("1: Add a new doctor");
			System.out.println("2: Delete a doctor");
			System.out.println("3: List of the doctors");
			System.out.println("4: Add a new consultation (Doctor)");
			System.out.println("5: Cancel consultation (Doctor)");
			System.out.println("6: View consultation (Patient)");
			System.out.println("7: Exit");
			
			System.out.println();
			
			System.out.print("Enter Number: ");
			int num = scan.nextInt();
			System.out.println();
			
			switch(num) {
				case 1:
					System.out.print("Enter name: ");
					String name = scan.next();
					System.out.print("Enter surname: ");
					String surname = scan.next();
					System.out.print("Enter date of birth (YYYY-MM-DD): ");
					String dob = scan.next();
					System.out.print("Enter mobile number: ");
					int mobileNumber = scan.nextInt();
					System.out.print("Enter medical licence number: ");
					String medicalLicenceNumber = scan.next();
					System.out.print("Enter specialisation: ");
					String specialisation = scan.next();
					
					Doctor doctor = new Doctor(name, surname, dob, mobileNumber, medicalLicenceNumber, specialisation);
					
					int doctorCount = countDoctor();
					
					if(doctorCount < 10) {
						boolean isAvailable = isDoctorAvailable(medicalLicenceNumber);
						if(isAvailable == false){
							boolean result = addDoctor(doctor);
							if(result == true)
								System.out.println("\nSuccessfully doctor added to the database \n");
							else
								System.out.println("\nError\n");
						}
						else
							System.out.println("\nDoctor already exist in the database\n");
					}
					else {
						System.out.println("\nNumber of total doctor is 10\n");
					}
					
					break;
					
				case 2:
					System.out.print("Enter medical licence number: ");
					medicalLicenceNumber = scan.next();
					
					boolean isAvailable = isDoctorAvailable(medicalLicenceNumber);
					
					if(isAvailable == true){
						boolean reslut = deleteDoctor(medicalLicenceNumber);
						
						if(reslut == true){
							System.out.println("\nSuccessfully doctor removed from the database");
							System.out.println("Available numbers of doctors : " + countDoctor() + "\n");
						}
						else
							System.out.println("\nError\n");
					}
					else
						System.out.println("\nIncorrect medical licence number\n");
					break;
					
				case 3:
					ArrayList<Doctor> doctorList = new ArrayList<Doctor>();
					doctorList = getDoctorList();
					String[][] doctorArray = new String[doctorList.size()][6];
					int x = 0;
					for(Doctor list : doctorList) {
						doctorArray[x][0] = list.getName();
						doctorArray[x][1] = list.getSurName();
						doctorArray[x][2] = list.getDateOfBirth();
						doctorArray[x][3] = Integer.toString(list.getMobileNumber());
						doctorArray[x][4] = list.getMedicalLicenceNumber();
						doctorArray[x][5] = list.getSpecialisation();
						x++;
					}
					
					ViewDoctors vd = new ViewDoctors(doctorArray);
					vd.frame.setVisible(true);
					ConsultationForm cf = new ConsultationForm();
					cf.setVisible(true);
					break;
					
				case 4:
					System.out.print("Enter medical licence number: ");
					medicalLicenceNumber = scan.next();
					System.out.print("Enter date (YYYY-MM-DD): ");
					String consultationDate = scan.next();
					
					isAvailable = isDoctorAvailable(medicalLicenceNumber);
					
					if(isAvailable == true){
						boolean isConsultAvailable = isconsultationAvailable(medicalLicenceNumber, consultationDate);
						
						if(isConsultAvailable == false) {
							boolean reslut = addDoctorConsultation(medicalLicenceNumber, consultationDate);
						
							if(reslut == true)
								System.out.println("\nSuccessfully consultation added to the database \n");
							else
								System.out.println("\nError\n");
						}
						else
							System.out.println("\nAlready consultation available on this date\n");
					}
					else
						System.out.println("\nIncorrect medical licence number\n");
					break;
					
				case 5:
					System.out.print("Enter medical licence number: ");
					medicalLicenceNumber = scan.next();
					System.out.print("Enter date (YYYY-MM-DD): ");
					consultationDate = scan.next();
					
					isAvailable = isconsultationAvailable(medicalLicenceNumber, consultationDate);
					
					if(isAvailable == true){
						boolean reslut = deleteConsultation(medicalLicenceNumber, consultationDate);
						
						if(reslut == true)
							System.out.println("\nSuccessfully consultation removed from the database\n");
						else
							System.out.println("\nError\n");
					}
					else
						System.out.println("\nRecord not available in the database\n");
					break;
					
				case 6: 
					ViewConsultation vc = new ViewConsultation();
					vc.setVisible(true);
					
					break;
					
				case 7:
					System.out.println("Thank You");
					System.exit(0);
					break;
				default:
					System.out.println("\nInvalid Number. Please Re-Enter\n");
			}
		}
	}

	public static int countDoctor() {
		int count = 0;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skinconsultationcentre", "root", "");
			String query = "select count(*) from doctor";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt("count(*)");
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return count;
	}
	
	public static boolean isDoctorAvailable(String medicalLicenceNumber) {
		boolean result = false;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skinconsultationcentre", "root", "");
			String query = "select * from doctor where medicalLicenceNumber	= '" + medicalLicenceNumber + "'";
			PreparedStatement ps = con.prepareStatement(query);
		
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				result = true;
			else
				result = false;
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return result;
	}
	
	public static boolean addDoctor(Doctor doctor) {
		boolean result = false;

		try{			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skinconsultationcentre", "root", "");
			String query = "insert into doctor values(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, doctor.getName());
			ps.setString(2, doctor.getSurName());
			ps.setString(3, doctor.getDateOfBirth());
			ps.setInt(4, doctor.getMobileNumber());
			ps.setString(5, doctor.getMedicalLicenceNumber());
			ps.setString(6, doctor.getSpecialisation());

			int rs = ps.executeUpdate();

			if (rs > 0)
				result = true;
			else
				result = false;
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return result;
	}
	
	public static boolean deleteDoctor(String medicalLicenceNumber) {
		boolean result = false;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skinconsultationcentre", "root", "");
			String query = "delete from doctor where medicalLicenceNumber='" + medicalLicenceNumber + "'";
			PreparedStatement ps = con.prepareStatement(query);
			
			int rs = ps.executeUpdate();

			if (rs > 0)
				result = true;
			else
				result = false;
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return result;
	}
	
	public static ArrayList<Doctor> getDoctorList(){
		ArrayList<Doctor> doctorList = new ArrayList<Doctor>();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skinconsultationcentre", "root", "");
			String query = "select * from doctor order by name";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				Doctor doctor = new Doctor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6));
				doctorList.add(doctor);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return doctorList;
	}
	
	public static boolean addDoctorConsultation(String medicalLicenceNumber, String consultationDate) {
		boolean result = false;
		
		try{			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skinconsultationcentre", "root", "");
			String query = "insert into doctorconsultation values(?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, medicalLicenceNumber);
			ps.setString(2, consultationDate);

			int rs = ps.executeUpdate();

			if (rs > 0)
				result = true;
			else
				result = false;
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return result;
	}
	
	public static boolean isconsultationAvailable(String medicalLicenceNumber, String consultationDate)  {
		boolean result = false;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skinconsultationcentre", "root", "");
			String query = "select * from doctorconsultation where medicalLicenceNumber	= '" + medicalLicenceNumber + "' and consultationDate = '" + consultationDate + "'";
			PreparedStatement ps = con.prepareStatement(query);
		
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				result = true;
			else
				result = false;
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return result;
	}
	
	public static boolean deleteConsultation(String medicalLicenceNumber, String consultationDate) {
		boolean result = false;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skinconsultationcentre", "root", "");
			String query = "delete from doctorconsultation where medicalLicenceNumber	= '" + medicalLicenceNumber + "' and consultationDate = '" + consultationDate + "'";
			PreparedStatement ps = con.prepareStatement(query);
			
			int rs = ps.executeUpdate();

			if (rs > 0)
				result = true;
			else
				result = false;
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return result;
	}
}