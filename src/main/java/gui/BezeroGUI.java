package gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Booking;
import domain.Complaint;
import domain.Driver;
import domain.Traveler;

public class BezeroGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JTable taula;
	private JButton jButtonBaloratu;
	private JButton jButtonErreklamatu;
	private JButton jButtonClose;
	private JScrollPane scrollPane;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public BezeroGUI(String username) {

		setBussinessLogic(DriverGUI.getBusinessLogic());
		this.setSize(new Dimension(600, 537));
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Lista
		taula = new JTable(); 
		List<Booking> travelList = appFacadeInterface.getBookingFromDriver(username);
		List<Booking> BezeroLista = new ArrayList<>();

		scrollPane = new JScrollPane(taula);
		getContentPane().add(scrollPane, BorderLayout.NORTH);

		// Etiketak
		String[] columnNames = { ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.BookingNumber"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"),
				ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.Bezeroa"),
				ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Egoera"),
				ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.Zergatia") };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		if (travelList  != null) {
			for (Booking bo : travelList ) {
				
				String status;
				switch (bo.getStatus()) {
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
				
				if (bo.getStatus().equals("NotCompleted")) {
					Complaint er = appFacadeInterface.getComplaintsByBook(bo);
					if (er != null) {
						if (er.getAurkeztua()) {
							er.setEgoera("Erreklamazioa");
						} else {
							er.setEgoera("Ez aurkeztua");
						}

						Object[] rowData = { bo.getBookNumber(), dateFormat.format(bo.getRide().getDate()),
								bo.getTraveler().getUsername(), status, er.getEgoera() };
						model.addRow(rowData);
						BezeroLista.add(bo);
					}

				} else if (bo.getStatus().equals("Completed") || bo.getStatus().equals("Valued")
						|| bo.getStatus().equals("Complained")) {
					Object[] rowData = { bo.getBookNumber(), dateFormat.format(bo.getRide().getDate()),
							bo.getTraveler().getUsername(), status, "" };
					model.addRow(rowData);
					BezeroLista.add(bo);
				}

			}
		}
		taula.setModel(model);

		taula.getTableHeader().setReorderingAllowed(false);
		taula.setColumnSelectionAllowed(false);
		taula.setRowSelectionAllowed(true);
		taula.setDefaultEditor(Object.class, null);
		taula.setModel(model);

		// Erroreen textua
		JLabel lblErrorea = new JLabel();
		this.getContentPane().add(lblErrorea, BorderLayout.CENTER);

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.Bezeroak"));

		// Baloratu botoia
		jButtonBaloratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.Baloratu"));
		jButtonBaloratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
				if (pos != -1) {
					Booking bo = BezeroLista.get(pos);
					if (bo.getStatus().equals("Completed")) {
						bo.setStatus("Valued");
						appFacadeInterface.updateBooking(bo);
						JFrame a = new BaloraGUI(bo.getTraveler().getUsername());
						a.setVisible(true);
						model.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Valued"), pos, 3);
					} else if (bo.getStatus().equals(ResourceBundle.getBundle("Etiquetas").getString("Valued"))) {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(
								ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.BezeroaJadanikBaloratuta"));
					} else {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(
								ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.AukeratuOsatutakoBidaia"));
					}
				} else {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.Erroraukera"));
				}

			}
		});
		
	
		
		this.getContentPane().add(jButtonBaloratu, BorderLayout.WEST);

		// Erraklamazio botoia
		jButtonErreklamatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.Onartu")
				+ " / " + ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.Erreklamatu"));
		jButtonErreklamatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
				if (pos != -1) {
					Booking booking = BezeroLista.get(pos);
					if (!taula.getValueAt(pos, 4).equals("")) {
						double prez = booking.prezioaKalkulatu();
						laguntzailea(pos, booking, prez, model, lblErrorea, username);
					} else if (booking.getStatus()
							.equals(ResourceBundle.getBundle("Etiquetas").getString("Complained"))) {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(
								ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.BezeroaErreklamazioa"));
					} else {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(
								ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.AukeratuEzOsatutakoBidaia"));
					}
				} else {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.Erroraukera"));
				}
			}

		});

		this.getContentPane().add(jButtonErreklamatu, BorderLayout.EAST);

		// Itxi botoia
		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Close"));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		this.getContentPane().add(jButtonClose, BorderLayout.SOUTH);

	}

	private void laguntzailea(int pos, Booking booking, double prez, DefaultTableModel model, JLabel lblErrorea, String username) {
		if (taula.getValueAt(pos, 4).equals("Erreklamazioa")) {
			Erreklamazioa(pos, booking, prez, model, lblErrorea, username);
		} else if (taula.getValueAt(pos, 4).equals("Ez aurkeztua")) {
			Ezaurkeztua(pos, booking, prez, model, lblErrorea, username);
		} else {
			lblErrorea.setForeground(Color.RED);
			lblErrorea.setText(ResourceBundle.getBundle("Etiquetas")
					.getString("BezeroGUI.AukeratuEzOsatutakoBidaia"));
		}
    }
	private void Erreklamazioa(int pos, Booking booking, double prez, DefaultTableModel model, JLabel lblErrorea, String username) {
			Traveler traveler = booking.getTraveler();

			booking.setStatus("Complained");
			appFacadeInterface.updateBooking(booking);

			traveler.setIzoztatutakoDirua(traveler.getIzoztatutakoDirua() - prez);
			appFacadeInterface.updateTraveler(traveler);

			appFacadeInterface.gauzatuEragiketa(traveler.getUsername(), prez, true);
			appFacadeInterface.addMovement(traveler, "UnfreezeNotComplete", prez);

			Driver driver = appFacadeInterface.getDriver(username);
			driver.setErreklamaKop(driver.getErreklamaKop() + 1);

			lblErrorea.setText(
					ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.ComplaintAccepted"));
			lblErrorea.setForeground(Color.BLACK);

			model.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Complained"), pos, 3);
			model.setValueAt("", pos, 4);
	}
	private void Ezaurkeztua(int pos, Booking booking, double prez, DefaultTableModel model, JLabel lblErrorea, String username) {
		Driver driver = booking.getRide().getDriver();
		Traveler traveler = booking.getTraveler();

		booking.setStatus("Complained");
		appFacadeInterface.updateBooking(booking);

		traveler.setIzoztatutakoDirua(traveler.getIzoztatutakoDirua() - prez);
		traveler.setErreklamaKop(traveler.getErreklamaKop() + 1);
		appFacadeInterface.updateTraveler(traveler);

		appFacadeInterface.gauzatuEragiketa(driver.getUsername(), prez, true);
		appFacadeInterface.addMovement(traveler, "UnfreezeCompleteT", 0);
		appFacadeInterface.addMovement(driver, "UnfreezeCompleteD", prez);
		lblErrorea.setText(
				ResourceBundle.getBundle("Etiquetas").getString("BezeroGUI.ComplaintComplete"));
		lblErrorea.setForeground(Color.BLACK);

		model.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Complained"), pos, 3);
		model.setValueAt("", pos, 4);
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

}