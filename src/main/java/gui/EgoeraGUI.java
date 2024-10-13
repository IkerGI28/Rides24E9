package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.Booking;
import domain.Driver;
import domain.Traveler;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class EgoeraGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JTable taula;
	private JButton jButtonEgina;
	private JButton jButtonBertanBehera;
	private JButton jButtonClose;
	private JScrollPane scrollPane;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public EgoeraGUI(String username) {

		BezeroGUI bezGUI = new BezeroGUI(username);
		setBussinessLogic(LoginGUI.getBusinessLogic());
		this.setSize(new Dimension(600, 537));
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Lista
		taula = new JTable();
		List<Booking> TravelsList = appFacadeInterface.getBookedRides(username);
		scrollPane = new JScrollPane(taula);
		getContentPane().add(scrollPane, BorderLayout.NORTH);

		taula.getTableHeader().setReorderingAllowed(false);
		taula.setColumnSelectionAllowed(false);
		taula.setRowSelectionAllowed(true);
		taula.setDefaultEditor(Object.class, null);

		String[] columnNames = { ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.BookingNumber"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"),
				ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Egoera") };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		for (Booking booking : TravelsList) {
			String status=bezGUI.checkStatus(booking);

			Object[] rowData = { booking.getBookNumber(), booking.getRide().getFrom(), booking.getRide().getTo(),
					dateFormat.format(booking.getRide().getDate()), status };
			model.addRow(rowData);
		}

		taula.setModel(model);

		// Erroreen textua
		JLabel lblErrorea = new JLabel();
		this.getContentPane().add(lblErrorea, BorderLayout.CENTER);

		// Egoera menua
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Egoera"));

		jButtonEgina = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.MarkCompleted"));
		jButtonEgina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
				if (pos != -1) {
					Date gaur = new Date();
					Booking booking = TravelsList.get(pos);

					if (!booking.getStatus().equals("Accepted")) {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.SelectOther"));
					} else if (booking.getRide().getDate().before(gaur)) {
						double ridePrice = booking.prezioaKalkulatu();

						Driver driver = booking.getRide().getDriver();
						Traveler traveler = booking.getTraveler();

						booking.setStatus("Completed");
						appFacadeInterface.updateBooking(booking);

						traveler.setIzoztatutakoDirua(
								traveler.getIzoztatutakoDirua() - ridePrice);
						appFacadeInterface.updateTraveler(traveler);

						appFacadeInterface.gauzatuEragiketa(driver.getUsername(), ridePrice,
								true);
						appFacadeInterface.addMovement(traveler, "UnfreezeCompleteT", 0);
						appFacadeInterface.addMovement(driver, "UnfreezeCompleteD", ridePrice);
						lblErrorea
								.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.CorrectlyMarked"));
						lblErrorea.setForeground(Color.BLACK);

						model.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Completed"), pos, 4);

						JFrame a = new BaloraGUI(driver.getUsername());
						a.setVisible(true);
					} else {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Errordata"));
					}
				} else {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Erroraukera"));
				}
			}

		});

		this.getContentPane().add(jButtonEgina, BorderLayout.WEST);

		// Probak egiteko
		/*
		 * Calendar cal = Calendar.getInstance(); cal.setTime(new Date());
		 * cal.set(Calendar.MONTH, 8); Date gaur = cal.getTime();
		 */

		// Bertan behera botoia
		jButtonBertanBehera = new JButton(
				ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.MarkNotCompleted"));
		jButtonBertanBehera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
				if (pos != -1) {

					Date gaur = new Date();

					Booking booking = TravelsList.get(pos);

					if (!booking.getStatus().equals("Accepted")) {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.SelectOther"));
					} else if (booking.getRide().getDate().before(gaur)) {

						/*
						 * Hau erreklamazioa onartzen bada egingo da double ridePrice =
						 * booking.getRide().getPrice();
						 * 
						 * Traveler traveler = booking.getTraveler();
						 * traveler.setIzoztatutakoDirua(traveler.getIzoztatutakoDirua() - ridePrice);
						 * traveler.setMoney(traveler.getMoney() + ridePrice);
						 * appFacadeInterface.updateTraveler(traveler);
						 * 
						 * appFacadeInterface.addMovement(traveler, "UnfreezeNotComplete", ridePrice);
						 */
						booking.setStatus("NotCompleted");
						appFacadeInterface.updateBooking(booking);
						lblErrorea
								.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.CorrectlyMarked"));
						lblErrorea.setForeground(Color.BLACK);
						model.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("NotCompleted"), pos, 4);
						JFrame a = new ArazoaGUI(username, booking.getRide().getDriver().getUsername(), booking);
						a.setVisible(true);
					} else {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Errordata"));
					}
				} else {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Erroraukera"));
				}
			}

		});

		this.getContentPane().add(jButtonBertanBehera, BorderLayout.EAST);

		// Itxi botoia
		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Close"));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		this.getContentPane().add(jButtonClose, BorderLayout.SOUTH);

	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

}
