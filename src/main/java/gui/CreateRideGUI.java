package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Car;
import domain.Driver;
import domain.OriginDestinationWhen;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private Driver driver;
	private JTextField fieldOrigin = new JTextField();
	private JTextField fieldDestination = new JTextField();

	private JLabel jLabelOrigin = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private JLabel jLabelSeats = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.NumberOfSeats"));
	private JLabel jLabRideDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private JLabel jLabelPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Price"));
	private JLabel lblNewLabelcars = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Cars"));

	private JComboBox<Integer> comboBoxSeats = new JComboBox<Integer>();
	private JTextField jTextFieldPrice = new JTextField();

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();

	@SuppressWarnings("unused")
	private List<Date> datesWithEventsCurrentMonth;

	private static BLFacade appFacadeInterface;

	private JComboBox<String> comboBoxcars;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public CreateRideGUI(String username) {

		CreateRideGUI.setBussinessLogic(DriverGUI.getBusinessLogic());

		this.driver = appFacadeInterface.getDriver(username);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));

		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelSeats.setBounds(new Rectangle(10, 155, 173, 20));
		comboBoxSeats.setBounds(new Rectangle(139, 155, 60, 20));

		jLabelPrice.setBounds(new Rectangle(10, 186, 173, 20));
		jTextFieldPrice.setBounds(new Rectangle(139, 186, 60, 20));

		jCalendar.setBounds(new Rectangle(300, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(100, 263, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(275, 263, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 214, 305, 20));
		jLabelMsg.setForeground(Color.red);

		jLabelError.setBounds(new Rectangle(10, 232, 320, 20));
		jLabelError.setForeground(Color.red);

		taula_null_ezarri();
		

		datesWithEventsCurrentMonth = appFacadeInterface.getThisMonthDatesWithRides("a", "b", jCalendar.getDate());

		jLabRideDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabRideDate.setBounds(298, 16, 140, 25);
		getContentPane().add(jLabRideDate);

		jLabelDestination.setBounds(6, 81, 61, 16);
		getContentPane().add(jLabelDestination);

		fieldOrigin.setBounds(100, 53, 130, 26);
		getContentPane().add(fieldOrigin);
		fieldOrigin.setColumns(10);

		fieldDestination.setBounds(100, 81, 130, 26);
		getContentPane().add(fieldDestination);
		fieldDestination.setColumns(10);

		lblNewLabelcars.setBounds(10, 122, 88, 14);
		getContentPane().add(lblNewLabelcars);

		comboBoxcars = new JComboBox<>();

		for (Car kotxe : appFacadeInterface.getDriver(username).getCars()) {
			comboBoxcars.addItem(kotxe.getMatrikula());
		}

		/*comboBoxSeats.removeAllItems();
		String matr = (String) comboBoxcars.getSelectedItem();
		int eserKopKotxe = appFacadeInterface.getKotxeByMatrikula(matr).getEserlekuak();
		for (int i = eserKopKotxe; i >= 1; i--) {
			comboBoxSeats.addItem(i);
		}*/

		comboBoxcars.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matr = (String) comboBoxcars.getSelectedItem();
				int eserKopKotxe = appFacadeInterface.getKotxeByMatrikula(matr).getEserlekuak();
				comboBoxSeats.removeAllItems();
				// Kotxea lortu matrikularekin eta zerrendan gehitu eserleku kopuruak
				for (int i = eserKopKotxe; i >= 1; i--) {
					comboBoxSeats.addItem(i);
				}
			}
		});
		comboBoxcars.setBounds(100, 122, 130, 22);
		getContentPane().add(comboBoxcars);
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//			
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					@SuppressWarnings("unused")
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de
							// marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar.setCalendar(calendarAct);

					}
					jCalendar.setCalendar(calendarAct);
					int offset = jCalendar.getCalendar().get(Calendar.DAY_OF_WEEK);

					if (Locale.getDefault().equals(new Locale("es")))
						offset += 4;
					else
						offset += 5;
					@SuppressWarnings("unused")
					Component o = (Component) jCalendar.getDayChooser().getDayPanel()
							.getComponent(jCalendar.getCalendar().get(Calendar.DAY_OF_MONTH) + offset);
				}
			}
		});

	}

	private void jButtonCreate_actionPerformed(ActionEvent e) {
		jLabelMsg.setText("");
		String error = field_Errors();
		if (error != null)
			jLabelMsg.setText(error);
		else
			try {
				BLFacade facade = MainGUI.getBusinessLogic();
				int inputSeats = (int) comboBoxSeats.getSelectedItem();
				float price = Float.parseFloat(jTextFieldPrice.getText());

				OriginDestinationWhen odw = new OriginDestinationWhen(fieldOrigin.getText(), fieldDestination.getText(),
						UtilDate.trim(jCalendar.getDate()));
				@SuppressWarnings("unused")
				Ride r = facade.createRide(odw, inputSeats, price, driver.getUsername());
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideCreated"));

			} catch (RideMustBeLaterThanTodayException | RideAlreadyExistException e1) {
				jLabelMsg.setText(e1.getMessage());
			}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private String field_Errors() {

		try {
			if ((fieldOrigin.getText().length() == 0) || (fieldDestination.getText().length() == 0)
					|| (jTextFieldPrice.getText().length() == 0))
				return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
			else {

				int inputSeats = (int) comboBoxSeats.getSelectedItem();

				if (inputSeats <= 0) {
					return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.SeatsMustBeGreaterThan0");
				} else {
					float price = Float.parseFloat(jTextFieldPrice.getText());
					if (price <= 0)
						return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.PriceMustBeGreaterThan0");

					else
						return null;

				}
			}
		} catch (java.lang.NumberFormatException e1) {

			return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorNumber");
		} catch (Exception e1) {

			e1.printStackTrace();
			return null;

		}
	}
	
	
	public void taula_null_ezarri() {
		
		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);
		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jLabelOrigin, null);
		this.getContentPane().add(jCalendar, null);
		this.getContentPane().add(comboBoxSeats, null);
		this.getContentPane().add(jLabelSeats, null);
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(jTextFieldPrice, null);

	}
	
}
