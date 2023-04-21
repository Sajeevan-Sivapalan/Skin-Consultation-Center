import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import java.util.*;
import java.security.*;
import javax.crypto.*;

public class ViewConsultation extends JFrame implements KeyListener{
	JLabel l1, l2, l3;
	JTextField t1, t2, t3;
	String medicalLicenceNumber, patientID, consultationDate;
	Connection con;
	
	ViewConsultation() {
		super("View consultation");
		setSize(500, 400);
		setResizable(false);
		setLayout(null);
		
		l1 = new JLabel("Date");
		add(l1);
		l1.setBounds(60, 50, 100, 25);
		
		l2 = new JLabel("Medical licence number");
		add(l2);
		l2.setBounds(60, 100, 100, 25);
		
		l3 = new JLabel("Patient ID");
		add(l3);
		l3.setBounds(60, 150, 100, 25);
		
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
		t3.setBounds(190, 150, 100, 25);
		t3.setEditable(false);
		t3.addKeyListener(this);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skinconsultationcentre", "root", "");
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
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
			if((e.getSource() == t3) && (code == 10)) {
			onPatientIDVal();
		}
		
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	public void onDateVal() {
		this.consultationDate = t1.getText();
		if(consultationDate.equals(""))
			JOptionPane.showMessageDialog(null, "Date is not entered", "Error", JOptionPane.ERROR_MESSAGE);
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
			t3.setEditable(true);
			t3.requestFocus();
		}
					
	}
	
	
	public void onPatientIDVal() {
		this.patientID = t3.getText();
		if(patientID.equals(""))
			JOptionPane.showMessageDialog(null, "Patient ID is not entered", "Error", JOptionPane.ERROR_MESSAGE);
		else{
			checkConsultation(medicalLicenceNumber, patientID, consultationDate);
			t2.setEditable(false);
			t3.setEditable(false);
			t1.requestFocus();
			t1.setText("");
			t2.setText("");
			t3.setText("");
		}	
	}
	
	public void checkConsultation(String medicalLicenceNumber, String patientID, String consultationDate) {
		boolean result = false;
		
		try{
			String query = "select * from consultation where patientID = '" + patientID + "' and medicalLicenceNumber = '" + medicalLicenceNumber +"' and consultationDate = '" + consultationDate + "'";
			PreparedStatement ps = con.prepareStatement(query);
		
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "Date : " + rs.getString(1) + 
		"\nMedica licence number : " +  rs.getString(5) + "\nPatient ID : " +rs.getString(4) + 
		"\nCost : " + rs.getString(2) + "\nNotes : " + decryptData(rs.getString(3)), "View", JOptionPane.INFORMATION_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null, "Record not available", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public String decryptData(String input) {
		byte[] output = new byte[input.length()];
		byte[] inputByte = new byte[input.length()];
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(512);
			KeyPair keyPair = keyPairGen.generateKeyPair();   
			PublicKey publicKey = keyPair.getPublic();  
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
			System.out.println(input);
			inputByte = input.getBytes();
			cipher.update(inputByte);			
			System.out.println(inputByte);
			output = cipher.doFinal(inputByte);	 
			System.out.println(new String(output));
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return(new String(output));
	}
}