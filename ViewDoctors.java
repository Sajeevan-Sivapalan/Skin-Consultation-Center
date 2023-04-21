import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import java.util.*;

public class ViewDoctors {
	JTable table;
	JFrame frame;
	String[][] doctorArray;
    String[] columnTitle = { "Name", "Surname", "Date of birth", "Mobile number", "Medical Licence Number", "Specialisation" };
	JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
	
	ViewDoctors(String[][] doctorArray) {
		this.doctorArray = doctorArray;
		frame = new JFrame();
		frame.setTitle("List of doctors");
		frame.setSize(900, 270);
		frame.setResizable(false);
		
		
		table = new JTable(doctorArray, columnTitle);
		table.setBounds(0, 0, 900, 200);
		JScrollPane jp = new JScrollPane(table);
		frame.add(jp);
		
	}
}