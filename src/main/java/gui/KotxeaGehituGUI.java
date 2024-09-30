package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Car;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class KotxeaGehituGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static BLFacade appFacadeInterface;
	private JLabel lblNewLabelizenburua;
	private JTextField textFieldmatrikula;
	private JTextField textFieldmodeloa;
	private JTextField textFieldeserlekuak;
	private JLabel lblNewLabeleserlekuak;
	private JLabel lblNewLabelmodeloa;
	private JLabel lblNewLabelmatrikula;
	private JButton btnNewButtonGehitu;
	private JButton jButtonClose;
	private JLabel lblNewLabel;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	/**
	 * Create the frame.
	 */
	public KotxeaGehituGUI(String username) {

		KotxeaGehituGUI.setBussinessLogic(MainGUI.getBusinessLogic());

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 250));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.KotxeaGehitu"));
		this.setResizable(false);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabelizenburua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.KotxeaGehitu"));
		lblNewLabelizenburua.setBounds(149, 33, 125, 14);
		contentPane.add(lblNewLabelizenburua);

		btnNewButtonGehitu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Gehitu"));
		btnNewButtonGehitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String error = field_Errors();
				lblNewLabel.setText("");
				if (error != null) {
					lblNewLabel.setText(error);
				} else {
					String matrikula = textFieldmatrikula.getText();
					String modeloa = textFieldmodeloa.getText();
					int eserlekuak = Integer.parseInt(textFieldeserlekuak.getText());
					Car kotxe = new Car(matrikula, modeloa, eserlekuak);
					boolean b = appFacadeInterface.addCar(username, kotxe);
					if (b) {
						lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Gehitua"));
					} else {
						lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Errorea"));
					}
				}
			}
		});
		btnNewButtonGehitu.setBounds(89, 196, 89, 23);
		contentPane.add(btnNewButtonGehitu);

		textFieldmatrikula = new JTextField();
		textFieldmatrikula.setBounds(249, 72, 96, 20);
		contentPane.add(textFieldmatrikula);
		textFieldmatrikula.setColumns(10);

		textFieldmodeloa = new JTextField();
		textFieldmodeloa.setBounds(249, 103, 96, 20);
		contentPane.add(textFieldmodeloa);
		textFieldmodeloa.setColumns(10);

		textFieldeserlekuak = new JTextField();
		textFieldeserlekuak.setBounds(249, 135, 96, 20);
		contentPane.add(textFieldeserlekuak);
		textFieldeserlekuak.setColumns(10);

		lblNewLabelmatrikula = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Matrikula"));
		lblNewLabelmatrikula.setBounds(68, 75, 110, 14);
		contentPane.add(lblNewLabelmatrikula);

		lblNewLabelmodeloa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Modeloa"));
		lblNewLabelmodeloa.setBounds(68, 107, 110, 14);
		contentPane.add(lblNewLabelmodeloa);

		lblNewLabeleserlekuak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Eserlekuak"));
		lblNewLabeleserlekuak.setBounds(68, 138, 110, 14);
		contentPane.add(lblNewLabeleserlekuak);

		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonClose.setBounds(243, 196, 89, 23);
		contentPane.add(jButtonClose);

		lblNewLabel = new JLabel(); // $NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(89, 171, 243, 14);
		contentPane.add(lblNewLabel);
		jButtonClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}

		});

	}

	private String field_Errors() {
		try {
			if ((textFieldmatrikula.getText().length() == 0) || (textFieldmodeloa.getText().length() == 0)
					|| (textFieldeserlekuak.getText().length() == 0))
				return ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.ErrorQuery");
			else {
				int inputSeats = Integer.parseInt(textFieldeserlekuak.getText());
				if (inputSeats <= 0) {
					return ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.SeatsMustBeGreaterThan0");
				} else {
					return null;
				}
			}
		} catch (java.lang.NumberFormatException e1) {
			return ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.ErrorNumber");
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

}