package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import businessLogic.BLFacade;
import domain.Booking;
import domain.ErreklamazioInfo;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JTextArea;

public class ErreklamazioakGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JLabel jLabelErreklamazioIzenburua;
	private JLabel jLabelNor;
	private JLabel jLabelNori;
	private JLabel jLabelFecha;
	private JLabel jLabelRide;
	private JLabel jLabelDeskripzioa;
	private JLabel jLabelEmaitza;
	private JButton jButtonBidali;
	private JTextArea jtextAreaDeskripzioa;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public ErreklamazioakGUI(String nork, String nori, Booking booking) {
		setBussinessLogic(LoginGUI.getBusinessLogic());

		Date gaur = new Date();
		this.getContentPane().setLayout(null);

		this.setSize(new Dimension(400, 349));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Erreklamazioa"));
		this.setResizable(false);

		jLabelErreklamazioIzenburua = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.HojaDeReclamaciones"));
		jLabelErreklamazioIzenburua.setBounds(125, 11, 135, 14);
		jLabelErreklamazioIzenburua.setForeground(Color.BLACK);
		getContentPane().add(jLabelErreklamazioIzenburua);

		jLabelNor = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Nor") + ": " + nork);
		jLabelNor.setBounds(10, 44, 191, 14);
		getContentPane().add(jLabelNor);

		jLabelNori = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Nori") + ": " + nori);
		jLabelNori.setBounds(10, 69, 191, 14);
		getContentPane().add(jLabelNori);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		jLabelFecha = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Noiz") + ": "
				+ dateFormat.format(gaur));
		jLabelFecha.setBounds(10, 94, 366, 14);
		getContentPane().add(jLabelFecha);

		jLabelRide = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Bidaia") + ": " + booking.getRide());
		jLabelRide.setBounds(10, 119, 376, 14);
		getContentPane().add(jLabelRide);

		jLabelDeskripzioa = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Deskripzioa") + ": ");
		jLabelDeskripzioa.setBounds(10, 159, 376, 14);
		getContentPane().add(jLabelDeskripzioa);

		jLabelEmaitza = new JLabel();
		jLabelEmaitza.setBounds(54, 251, 278, 14);
		getContentPane().add(jLabelEmaitza);

		jtextAreaDeskripzioa = new JTextArea(
				ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Deskripzioa"));
		jtextAreaDeskripzioa.setBounds(10, 184, 366, 56);
		jtextAreaDeskripzioa.setText("");
		getContentPane().add(jtextAreaDeskripzioa);

		jButtonBidali = new JButton(
				ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.ErreklamazioaBidali"));
		jButtonBidali.setBounds(143, 276, 99, 25);
		getContentPane().add(jButtonBidali);
		jButtonBidali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String textua = jtextAreaDeskripzioa.getText();
				if (!textua.isEmpty()) {
					ErreklamazioInfo errekInfo = new ErreklamazioInfo(nori, nork, gaur);
					appFacadeInterface.erreklamazioaBidali(errekInfo, booking, textua, true);
					jButtonClose_actionPerformed(e);
				} else {
					jLabelEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Error"));
				}
			}
		});
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
