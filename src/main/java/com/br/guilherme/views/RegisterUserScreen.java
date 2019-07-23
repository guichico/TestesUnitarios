package com.br.guilherme.views;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegisterUserScreen extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8683715843845075594L;

	private JTextField txtName;	
	private JTextField txtCpf;
	private JTextField txtBirthDate;
	private JTextField txtGender;
	private JTextField txtCep;
	private JTextField txtState;
	private JTextField txtCity;
	private JTextField txtDistrict;
	private JTextField txtAddress;
	private JTextField txtAddressNumber;
	private JTextField txtAddressComplement;
	
	private JLabel lblName;	
	private JLabel lblCpf;
	private JLabel lblBirthDate;
	private JLabel lblGender;
	private JLabel lblCep;
	private JLabel lblState;
	private JLabel lblCity;
	private JLabel lblDistrict;
	private JLabel lblAddress;
	private JLabel lblAddressNumber;
	private JLabel lblAddressComplement;
	
	public RegisterUserScreen(String title) {
		super(title);
		
		txtName = new JTextField();
		txtCpf = new JTextField();
		txtBirthDate = new JTextField();
		txtGender = new JTextField();
		txtCep = new JTextField();
		txtState = new JTextField();
		txtCity = new JTextField();
		txtDistrict = new JTextField();
		txtAddress = new JTextField();
		txtAddressNumber = new JTextField();
		txtAddressComplement = new JTextField();

		lblName = new JLabel("Nome:");
		lblCpf = new JLabel("CPF:");
		lblBirthDate = new JLabel("Data de nascimento:");
		lblGender = new JLabel("Sexo:");
		lblCep = new JLabel("CEP:");
		lblState = new JLabel("Estado:");
		lblCity = new JLabel("Cidade:");
		lblDistrict = new JLabel("Bairro:");
		lblAddress = new JLabel("Endereço:");
		lblAddressNumber = new JLabel("Número:");
		lblAddressComplement = new JLabel("Complemento:");
	}
	
	public void showScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(150, 300);
		setLayout(new GridLayout(2, 1));

		JPanel txtFields = new JPanel();
		txtFields.setLayout(new GridLayout(4, 4));
		txtFields.add(txtName);
		txtFields.add(txtCpf);
		txtFields.add(txtBirthDate);
		txtFields.add(txtGender);
		txtFields.add(txtCep);
		txtFields.add(txtState);
		txtFields.add(txtCity);
		txtFields.add(txtDistrict);
		txtFields.add(txtAddress);
		txtFields.add(txtAddressNumber);
		txtFields.add(txtAddressComplement);

		JPanel labels = new JPanel();
		labels.setLayout(new GridLayout(2, 6));
		labels.add(lblName);
		labels.add(lblCpf);
		labels.add(lblBirthDate);
		labels.add(lblGender);
		labels.add(lblCep);
		labels.add(lblState);
		labels.add(lblCity);
		labels.add(lblDistrict);
		labels.add(lblAddress);
		labels.add(lblAddressNumber);
		labels.add(lblAddressComplement);
		
		Container cp = getContentPane();
		cp.add(txtFields);
		cp.add(labels);
				
		pack();
		setVisible(true);		
	}
}