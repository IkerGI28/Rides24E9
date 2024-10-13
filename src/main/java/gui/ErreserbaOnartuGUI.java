package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.Booking;
import domain.Ride;
import domain.Traveler;

import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class ErreserbaOnartuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JTable taula;
	private JButton jButtonOnartu;
	private JButton jButtonEzeztatu;
	private JButton jButtonClose;
	private JScrollPane scrollPane;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public ErreserbaOnartuGUI(String username) {

		setBussinessLogic(LoginGUI.getBusinessLogic());
		this.setSize(new Dimension(600, 537));
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Lista
		taula = new JTable();
		List<Booking> TravelsList = appFacadeInterface.getBookingFromDriver(username);
		scrollPane = new JScrollPane(taula);
		getContentPane().add(scrollPane, BorderLayout.NORTH);

		taula.getTableHeader().setReorderingAllowed(false);
		taula.setColumnSelectionAllowed(false);
		taula.setRowSelectionAllowed(true);
		taula.setDefaultEditor(Object.class, null);

		String[] columnNames = { ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.BookingNumber"),
				ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Username"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"),
				ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Egoera") };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		if (TravelsList != null) {
			for (Booking booking : TravelsList) {
				String status;
				switch (booking.getStatus()) {
				case "Completed":
					status = ResourceBundle.getBundle("Etiquetas").getString("Completed");
					break;
				case "Accepted":
					status = ResourceBundle.getBundle("Etiquetas").getString("Accepted");
					break;
				case "Rejected":
					status = ResourceBundle.getBundle("Etiquetas").getString("Rejected");
					break;
				case "NotCompleted":
					status = ResourceBundle.getBundle("Etiquetas").getString("NotCompleted");
					break;
				case "Complained":
					status = ResourceBundle.getBundle("Etiquetas").getString("Complained");
					break;
				case "Valued":
					status = ResourceBundle.getBundle("Etiquetas").getString("Valued");
					break;
				default:
					status = ResourceBundle.getBundle("Etiquetas").getString("NotDefined");
					break;
				}

				Object[] rowData = { booking.getBookNumber(), booking.getTraveler().getUsername(),
						booking.getRide().getFrom(), booking.getRide().getTo(),
						dateFormat.format(booking.getRide().getDate()), status };
				model.addRow(rowData);
			}

		}
		taula.setModel(model);

		// Erroreen textua
		JLabel lblErrorea = new JLabel();
		this.getContentPane().add(lblErrorea, BorderLayout.CENTER);

		// Egoera menua
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Egoera"));

		jButtonOnartu = new JButton(
				ResourceBundle.getBundle("Etiquetas").getString("ErreserbakOnartuGUI.MarkAccepted"));
		jButtonOnartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
				if (pos != -1) {
					Booking booking = TravelsList.get(pos);
					if (!booking.getStatus().equals("NotDefined")) {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(
								ResourceBundle.getBundle("Etiquetas").getString("ErreserbakOnartuGUI.SelectOther"));
					} else {
						booking.setStatus("Accepted");
						appFacadeInterface.updateBooking(booking);
						lblErrorea
								.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.CorrectlyMarked"));
						lblErrorea.setForeground(Color.BLACK);
						model.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Accepted"), pos, 5);
					}
				} else {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Erroraukera"));
				}
			}

		});

		this.getContentPane().add(jButtonOnartu, BorderLayout.WEST);

		// Bertan behera botoia
		jButtonEzeztatu = new JButton(
				ResourceBundle.getBundle("Etiquetas").getString("ErreserbakOnartuGUI.MarkRejected"));
		jButtonEzeztatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
				if (pos != -1) {
					Booking booking = TravelsList.get(pos);

					if (!booking.getStatus().equals("NotDefined")) {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(
								ResourceBundle.getBundle("Etiquetas").getString("ErreserbakOnartuGUI.SelectOther"));
					} else {
						Ride ride = booking.getRide();
						ride.setnPlaces(ride.getnPlaces() + booking.getSeats());
						double bookPrice = booking.prezioaKalkulatu();

						Traveler traveler = booking.getTraveler();

						booking.setStatus("Rejected");
						appFacadeInterface.updateBooking(booking);

						traveler.setIzoztatutakoDirua(traveler.getIzoztatutakoDirua() - bookPrice);
						traveler.setMoney(traveler.getMoney() + bookPrice);
						appFacadeInterface.updateTraveler(traveler);

						appFacadeInterface.addMovement(traveler, "BookDeny", bookPrice);
						lblErrorea
								.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.CorrectlyMarked"));
						lblErrorea.setForeground(Color.BLACK);

						model.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Rejected"), pos, 5);
					}
				} else {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Erroraukera"));
				}
			}

		});

		this.getContentPane().add(jButtonEzeztatu, BorderLayout.EAST);

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
