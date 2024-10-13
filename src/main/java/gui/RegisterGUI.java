package gui;

import javax.swing.*;
import businessLogic.BLFacade;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class RegisterGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField fieldUser = new JTextField();
    private JPasswordField fieldPass = new JPasswordField(); 
    private JComboBox<String> comboBoxType = new JComboBox<String>();

    private JLabel jLabelError = new JLabel();
    private JLabel jLabelUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Username"));
    private JLabel jLabelPass = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Pass"));
    private JLabel jLabelType = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Type"));
    private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
    private JButton jButtonRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Register"));

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
    
    public RegisterGUI() {
    	
    	RegisterGUI.setBussinessLogic(MainGUI.getBusinessLogic());
    	
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(400, 300));
        this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Register"));
        this.setResizable(false);
        
        comboBoxType.addItem(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver"));
        comboBoxType.addItem(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Traveler"));
        
        jLabelUser.setBounds(39, 35, 100, 20);
        fieldUser.setBounds(149, 32, 150, 26);
        jLabelPass.setBounds(39, 86, 100, 20);
        fieldPass.setBounds(149, 83, 150, 26);
        jLabelType.setBounds(39, 134, 100, 20);
        comboBoxType.setBounds(149, 131, 150, 26);
        jButtonClose.setBounds(230, 200, 100, 30);
        jButtonRegister.setBounds(70, 200, 120, 30);
        
        this.getContentPane().add(jLabelUser);
        this.getContentPane().add(fieldUser);
        this.getContentPane().add(jLabelPass);
        this.getContentPane().add(fieldPass);
        this.getContentPane().add(jLabelType);
        this.getContentPane().add(comboBoxType);
        this.getContentPane().add(jButtonClose);
        getContentPane().add(jButtonRegister);
        
        
        jLabelError.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelError.setBounds(0, 169, 394, 20);
        getContentPane().add(jLabelError);
        
        

        jButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonClose_actionPerformed(e);
            }
        });
        
        jButtonRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = fieldUser.getText();
                String password = new String(fieldPass.getPassword());
                String userType = (String) comboBoxType.getSelectedItem();

                if (username.isEmpty() || password.isEmpty()) {
                    jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Error"));
                    return;
                }
                boolean added=false;
                if(userType.equals(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver")))
                	added = appFacadeInterface.addUser(username, password,0);
                else if (userType.equals(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Traveler")))
                	added = appFacadeInterface.addUser(username, password,1);
                
                if (added) {
                	jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Ondo"));
                    fieldUser.setText("");
                    fieldPass.setText("");
                } else {
                	jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Gaizki"));
                }
            }
        });
    }

    private void jButtonClose_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
}