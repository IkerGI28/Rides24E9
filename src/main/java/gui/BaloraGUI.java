package gui;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JSlider;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.User;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class BaloraGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JSlider slider;
	private JLabel lbltxt;
	private JPanel jContentPane = null;
	private JButton baloratu = null;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public BaloraGUI(String username) {

		setBussinessLogic(LoginGUI.getBusinessLogic());
		this.setSize(495, 290);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.Baloratu"));
		this.setResizable(false);

		// Bi aukerak aztertu.
		User us = appFacadeInterface.getUser(username);

		lbltxt = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BaloraGUI.Izena") + ": " + username);

		slider = new JSlider(SwingConstants.HORIZONTAL, 1, 5, 1);
		slider.setForeground(new Color(100, 100, 100));
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);

		baloratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BaloraGUI.Baloratu"));
		baloratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int kont = slider.getValue();
				double bal;
				int kop;

				bal = us.getBalorazioa();
				kop = us.getBalkop();

				us.setBalorazioa(bal + kont);
				us.setBalkop(kop + 1);
				appFacadeInterface.updateUser(us);

				jButtonClose_actionPerformed(e);
			}
		});
		baloratu.setBounds(new Rectangle(100, 263, 130, 30));
		baloratu.setText("Baloratu");

		// tamainak aldatu
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(3, 1, 0, 0));
		jContentPane.add(lbltxt);
		jContentPane.add(slider);
		jContentPane.add(baloratu);
		setContentPane(jContentPane);

	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
