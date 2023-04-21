import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import java.util.*;
import java.security.*;
import javax.crypto.*;

class ConsultationForm extends JFrame implements KeyListener
{
	JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10;
	JTextField t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
	
	Connection con;
	
	String name, surname, dateOfBirth, mobileNumber, patientID, date, medicalLicenceNumber;
	String notes;
	int	cost;
	
	boolean recordAvailable = false;
	ConsultationForm(){
		super("Book for consultation");
		setSize(900, 490);
		setResizable(false);
		setLayout(null);
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowsize = getSize();
		
		this.setBounds((screensize.width - windowsize.width)/2, (screensize.height - windowsize.height)/2, windowsize.width, windowsize.height);
		
		l1 = new JLabel("Date");
		add(l1);
		l1.setBounds(60, 50, 100, 25);
		
		l2 = new JLabel("Medical licence number");
		add(l2);
		l2.setBounds(60, 100, 100, 25);
		
		l7 = new JLabel("Patient ID");
		add(l7);
		l7.setBounds(60, 150, 100, 25);
		
		l3 = new JLabel("name");
		add(l3);
		l3.setBounds(60, 200, 100, 25);
		
		l4 = new JLabel("Surname");
		add(l4);
		l4.setBounds(60, 250, 100, 25);
		
		l5 = new JLabel("Date of birth");
		add(l5);
		l5.setBounds(60, 300, 100, 25);
		
		l6 = new JLabel("Moblie Number");
		add(l6);
		l6.setBounds(60, 350, 100, 25);
		
		l8 = new JLabel("Notes");
		add(l8);
		l8.setBounds(400, 50, 100, 25);
		
		l9 = new JLabel("Images");
		add(l9);
		l9.setBounds(400, 100, 100, 25);
		
		l10 = new JLabel("Cost");
		add(l10);
		l10.setBounds(400, 150, 100, 25);
		
		t1 = new JTextField();
		add(t1);
		t1.setBounds(190, 50, 100, 25);
		t1.addKeyListener(this);
		
		t2 = new JTextField();
		add(t2);
		t2.setBounds(190, 100, 100, 25);
		t2.setEditable(false);
		t2.addKeyListener(this);
		
		t3 = new JTextField();
		add(t3);
		t3.setBounds(190, 200, 100, 25);
		t3.setEditable(false);
		t3.addKeyListener(this);
		
		t4 = new JTextField();
		add(t4);
		t4.setBounds(190, 250, 100, 25);
		t4.setEditable(false);
		t4.addKeyListener(this);
		
		t5 = new JTextField();
		add(t5);
		t5.setBounds(190, 300, 100, 25);
		t5.setEditable(false);
		t5.addKeyListener(this);
		
		t6 = new JTextField();
		add(t6);
		t6.setBounds(190, 350, 100, 25);
		t6.setEditable(false);
		t6.addKeyListener(this);
		
		t7 = new JTextField();
		add(t7);
		t7.setBounds(190, 150, 100, 25);
		t7.setEditable(false);
		t7.addKeyListener(this);
		
		t8 = new JTextField();
		add(t8);
		t8.setBounds(530, 50, 200, 25);
		t8.setEditable(false);
		t8.addKeyListener(this);
		
		/*t9 = new JTextField();
		add(t9);
		t9.setBounds(530, 100, 100, 25);
		t9.setEditable(false);
		t9.addKeyListener(this);*/
		
		t10 = new JTextField();
		add(t10);
		t10.setBounds(530, 150, 100, 25);
		t10.setEditable(false);
		t10.addKeyListener(this);
		
		
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skinconsultationcentre", "root", "");
		}
		catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
	}
	
	public void keyPressed(KeyEvent e){
		int code = e.getKeyChar();
		if((e.getSource() == t1) && (code == 10)) {
			onDateVal();
		}
		else 
			if((e.getSource() == t2) && (code == 10)) {
			onMLNVal();
		}
		else
			if((e.getSource() == t7) && (code == 10)) {
			onPatientIDVal();
		}
		else
			if((e.getSource() == t3) && (code == 10)) {
			onNameVal();
		}
		else 
			if((e.getSource() == t4) && (code == 10)) {
			onSurNameVal();
		}
		else
			if((e.getSource() == t5) && (code == 10)) {
			onDOBVal();
		}
		else
			if((e.getSource() == t6) && (code == 10)) {
			onMobleNumberVal();
		}
		else
			if((e.getSource() == t8) && (code == 10)) {
			onNotesVal();
		}
		else
			if((e.getSource() == t10) && (code == 10)) {
			onCostVal();
		}
		
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	
	public void onDateVal() {
		this.date = t1.getText();
		if(date.equals(""))
			JOptionPane.showMessageDialog(null, "Date is not entered", "Error", JOptionPane.ERROR_MESSAGE);
		else
			if(checkDate(date) == false){
				JOptionPane.showMessageDialog(null, "No consultation available for this date", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				t2.setEditable(true);
				t2.requestFocus();
			}
	}
	
	public void onMLNVal() {
		this.medicalLicenceNumber = t2.getText();
		if(medicalLicenceNumber.equals(""))
			JOptionPane.showMessageDialog(null, "Medical licence number is not entered", "Error", JOptionPane.ERROR_MESSAGE);
		else {
				boolean result = SkinConsultationManagerMain.isDoctorAvailable(medicalLicenceNumber);
				if(result == false)
					JOptionPane.showMessageDialog(null, "Incorrect medical licence number", "Error", JOptionPane.ERROR_MESSAGE);
				else {
					try {
							String query = "select * from doctorconsultation where medicalLicenceNumber	= '" + medicalLicenceNumber + "' and consultationDate = '" + date + "'";
							PreparedStatement ps = con.prepareStatement(query);
						
							ResultSet rs = ps.executeQuery();
							
							if(rs.next()) {
								t7.setEditable(true);
								t7.requestFocus();
								t1.setEditable(false);
								t2.setEditable(false);
							}
							else {
								int confirm = JOptionPane.showConfirmDialog(null, "Selected doctor not available we have assign another doctor \nDo you want to continue", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
								if(confirm == JOptionPane.YES_OPTION) {
									ArrayList<String> list = new ArrayList<String>();
									
									query = "select medicalLicenceNumber from doctorconsultation where consultationDate = '" + date + "'";
									ps = con.prepareStatement(query);
								
									rs = ps.executeQuery();
									
									while(rs.next()) {
										list.add(rs.getString("medicalLicenceNumber"));
									}
									
									String[] availableDoctor = list.toArray(new String[list.size()]);
									int x = (int)(Math.random()*list.size());
									
									t2.setText(list.get(x));
									t7.setEditable(true);
									t7.requestFocus();
									t1.setEditable(false);
									t2.setEditable(false);
								}
								else
									this.hide();
							}
						}
						catch(Exception e) {
							System.out.println(e);
						}
				}
			}
	}
	
	
	public void onPatientIDVal() {
		this.patientID = t7.getText();
		if(patientID.equals(""))
			JOptionPane.showMessageDialog(null, "Patient ID is not entered", "Error", JOptionPane.ERROR_MESSAGE);
		else {
			boolean result = isPatientAvailable(patientID);
			
			if(result == true) {
					try{	
						String query = "select * from patient where patientID	= '" + patientID + "'";
						PreparedStatement ps = con.prepareStatement(query);
					
						ResultSet rs = ps.executeQuery();
						
						if(rs.next()) {
							this.recordAvailable = true;
							t3.setText(rs.getString(1));
							t4.setText(rs.getString(2));
							t5.setText(rs.getString(3));
							t6.setText(rs.getString(4));
							t3.setEditable(true);
							t3.requestFocus();
							t4.setEditable(true);
							t5.setEditable(true);
							t6.setEditable(true);
						}
					}
					catch(Exception e) {
						System.out.println(e);
					}
			}
			else {
				t3.setEditable(true);
				t3.requestFocus();
			}
		}
	}
	
	public void onNameVal() {
		this.name = t3.getText();
		if(name.equals(""))
			JOptionPane.showMessageDialog(null, "Name is not entered", "Error", JOptionPane.ERROR_MESSAGE);
		else {
				t4.setEditable(true);
				t4.requestFocus();
			}
	}
	
	public void onSurNameVal() {
		this.surname = t4.getText();
		if(surname.equals(""))
			JOptionPane.showMessageDialog(null, "Surname is not entered", "Error", JOptionPane.ERROR_MESSAGE);
		else {
			t5.setEditable(true);
			t5.requestFocus();
		}
	}
	
	public void onDOBVal() {
		this.dateOfBirth = t5.getText();
		if(dateOfBirth.equals(""))
			JOptionPane.showMessageDialog(null, "Date of birth is not entered", "Error", JOptionPane.ERROR_MESSAGE);
		else {
			t6.setEditable(true);
			t6.requestFocus();
		}	
	}
	
	public void onMobleNumberVal() {
		this.mobileNumber = t6.getText();
		if(mobileNumber.equals(""))
			JOptionPane.showMessageDialog(null, "Moblie number is not entered", "Error", JOptionPane.ERROR_MESSAGE);
		else
			if(mobileNumber.length() != 9) {
				JOptionPane.showMessageDialog(null, "Moblie number length should be 9", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				for (int i = 0; i < mobileNumber.length(); i++) {
				if (!Character.isDigit(mobileNumber.charAt(i))) {
					JOptionPane.showMessageDialog(null, "Moblie number is contain only numbers", "Error", JOptionPane.ERROR_MESSAGE);
					t6.requestFocus();
					t8.setEditable(false);
					t9.setEditable(false);
					break;
				}
				else {
					t8.setEditable(true);
					t8.requestFocus();
				}
			}
			
		}	
	}
	
	public void onNotesVal() {
		this.notes = t8.getText();
		if(notes.equals(""))
			JOptionPane.showMessageDialog(null, "Notes not entered", "Error", JOptionPane.ERROR_MESSAGE);
		else {
			t10.requestFocus();
		}
	}
	
	public void onCostVal() {
		boolean result1 = calCost(patientID);
		
		if(result1 == true) 
			this.cost = 25;
		else
			this.cost = 15;
		
		t10.setText(Integer.toString(cost));
		
		int confirm = JOptionPane.showConfirmDialog(null, "Date : " + date + 
		"\nMedica licence number : " + medicalLicenceNumber + "\nPatient ID : " + patientID +
		"\nName : " + name + "\nSurname : " + surname + "Date of birh : " + dateOfBirth + 
		"\nMobile number : " + mobileNumber + "\nCost : " + cost + "\nDo you want to continue", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if(confirm == JOptionPane.YES_OPTION) {
			boolean result;
			if(recordAvailable == true) {
				result = updateRocord();
				if(result == true) {
					JOptionPane.showMessageDialog(null, "Record added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					this.hide();
				}
				else {
					JOptionPane.showMessageDialog(null, "Unsuccess", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				result = addRocord();
				if(result == true) {
					JOptionPane.showMessageDialog(null, "Record added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					this.hide();
				}
				else {
					JOptionPane.showMessageDialog(null, "Unsuccess", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	
	public boolean checkDate(String date) {
		boolean result = false;
		
		try {
			String query = "select * from doctorconsultation where consultationDate = '" + date + "'";
			PreparedStatement ps = con.prepareStatement(query);
		
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				result = true;
			else
				result = false;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	public boolean updateRocord() {
		boolean result = false;
		boolean result1 = false;
		boolean result2 = false;
		
		try {
			String query = "update patient set name = ?, surname = ?, dateOfBirth = ?, mobileNumber = ? where patientID = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, surname);
			ps.setString(3, dateOfBirth);
			ps.setInt(4, Integer.parseInt(mobileNumber));
			ps.setString(5, patientID);

			int rs = ps.executeUpdate();

			if (rs > 0)
				result1 = true;
			else
				result1 = false;
			
			query = "insert into consultation values(?,?,?,?,?)";
			ps = con.prepareStatement(query);
			ps.setString(1, date);
			ps.setInt(2, cost);
			notes = encryptData(notes);
			ps.setString(3, notes);
			ps.setString(4, patientID);
			ps.setString(5, medicalLicenceNumber);
			
			rs = ps.executeUpdate();

			if (rs > 0)
				result2 = true;
			else
				result2 = false;
			
			if(result1 == true && result2 == true)
				result = true;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	public boolean addRocord() {
		boolean result = false;
		boolean result1 = false;
		boolean result2 = false;
		
		try {
			String query = "insert into patient values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, surname);
			ps.setString(3, dateOfBirth);
			ps.setInt(4, Integer.parseInt(mobileNumber));
			ps.setString(5, patientID);

			int rs = ps.executeUpdate();
			
			query = "insert into consultation values(?,?,?,?,?)";
			ps = con.prepareStatement(query);
			ps.setString(1, date);
			ps.setInt(2, cost);
			notes = encryptData(notes);
			ps.setString(3, notes);
			ps.setString(4, patientID);
			ps.setString(5, medicalLicenceNumber);
			
			rs = ps.executeUpdate();

			if (rs > 0)
				result2 = true;
			else
				result2 = false;
			
			if(result1 == true && result2 == true)
				result = true;

			if (rs > 0)
				result = true;
			else
				result = false;
		}
		catch(Exception e) {
			System.out.println(e);
		}
			
			return result;
	}
	
	public boolean calCost(String patientID) { 
		boolean result = false;
		
		try{
			String query = "select * from consultation where patientID = '" + patientID + "'";
			PreparedStatement ps = con.prepareStatement(query);
		
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				result = true;
			else
				result = false;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	public boolean isPatientAvailable(String patientID) {
		boolean result = false;
		
		try {
			String query = "select * from patient where patientID = '" + patientID + "'";
			PreparedStatement ps = con.prepareStatement(query);
		
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				result = true;
			else
				result = false;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	public String encryptData(String input) {
		byte[] output = null;
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(512);
			KeyPair keyPair = keyPairGen.generateKeyPair();   
			PublicKey publicKey = keyPair.getPublic();  
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] inputByte = input.getBytes();	  
			cipher.update(inputByte);
			output = cipher.doFinal();	 
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return(new String(output));
	}
}