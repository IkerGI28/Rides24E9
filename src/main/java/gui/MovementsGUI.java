package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.Movement;
import domain.User;

import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovementsGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private static BLFacade appFacadeInterface;
	private JTable taula;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public MovementsGUI(String username) {
		setBussinessLogic(LoginGUI.getBusinessLogic());

		this.getContentPane().setLayout(new BorderLayout());
		this.setSize(new Dimension(600, 400));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.Movements"));
		this.setResizable(false);

		taula = new JTable();
		JScrollPane scrollPane = new JScrollPane(taula);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);

		taula.getTableHeader().setReorderingAllowed(false);
		taula.setColumnSelectionAllowed(false);
		taula.setRowSelectionAllowed(true);
		taula.setDefaultEditor(Object.class, null);

		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botoiaitxi();
				JFrame a = new MoneyGUI(username);
				a.setVisible(true);
			}
		});

		this.getContentPane().add(jButtonClose, BorderLayout.SOUTH);

		User user = appFacadeInterface.getUser(username);
		List<Movement> movementsList = appFacadeInterface.getAllMovements(user);

		String[] columnNames = { ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.Operation"),
				ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.Amount") };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		for (Movement movement : movementsList) {
			String eragiketaMota;
			if (movement.getEragiketa().equals("Deposit"))
				eragiketaMota = ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Deposit");
			else if (movement.getEragiketa().equals("Withdrawal"))
				eragiketaMota = ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Withdraw");
			else if (movement.getEragiketa().equals("BookFreeze"))
				eragiketaMota = ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Freeze");
			else if (movement.getEragiketa().equals("BookDeny"))
				eragiketaMota = ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.UnfreezeDeny");
			else if (movement.getEragiketa().equals("UnfreezeCompleteT"))
				eragiketaMota = ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.UnfreezeCompleteT");
			else if (movement.getEragiketa().equals("UnfreezeCompleteD"))
				eragiketaMota = ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.UnfreezeCompleteD");
			else if (movement.getEragiketa().equals("UnfreezeNotComplete"))
				eragiketaMota = ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.UnfreezeNotComplete");
			else
				eragiketaMota = ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Unknown");
			Object[] rowData = { eragiketaMota, movement.getKopurua() };
			model.addRow(rowData);
		}

		taula.setModel(model);
	}

	private void botoiaitxi() {
		this.setVisible(false);
	}
}
