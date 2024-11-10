package Adapter;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import domain.Driver;

public class DriverTable extends JFrame {
	private Driver driver;
	private JTable tabla;

	public DriverTable(Driver driver) {
		super(driver.getUsername() + "’s	rides ");
		this.setBounds(100, 100, 700, 200);
		this.driver = driver;
		DriverAdapter adapt = new DriverAdapter(driver);
		tabla = new JTable(adapt);
		tabla.setPreferredScrollableViewportSize(new Dimension(500, 70));
		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane = new JScrollPane(tabla);
		// Agregamos el JScrollPane al contenedor
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
}