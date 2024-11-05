package Adapter;

import domain.Driver;
import domain.Ride;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DriverAdapter extends AbstractTableModel{
	protected Driver driver;
	protected String[] columnNames = new String[] { "from", "to", "Date", "places","price" };
	public DriverAdapter(Driver driver) {
		this.driver = driver;
	}
	@Override
	public int getRowCount() {
		return driver.getCreatedRides().size();
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
     }
	
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ride Ride = driver.getCreatedRides().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return (Object) Ride.getFrom();
		
		case 1:
			return (Object) Ride.getTo();
		case 2:
            return (Object) Ride.getDate();
		case 3:
			return (Object) Ride.getnPlaces();
		case 4:
			return (Object) Ride.getPrice();
		}
		return null;
	}
}