package iterator;

import java.util.List;

public class DepartCitiesExtendedIterator implements ExtendedIterator {

	List<Object> departCities;
	int position = 0;
	
	public DepartCitiesExtendedIterator(List<Object> cities) {
		this.departCities = cities;
	}
	
	//return the actual	element	and	go to the previous
	public Object previous() {
		String city = (String) departCities.get(position);
		position--;
		return city;
	}
	
	//true if there	is a previous element
	public boolean hasPrevious() {
		return position >= 0;
	}
	
	//It is	placed in the first element
	public void goFirst() {
		this.position = 0;
	}
	
	//It is	placed in the last element
	public void goLast() {
		this.position = departCities.size() - 1;
	}
	
	public boolean hasNext() {
		return position < departCities.size();
	}
	
	public Object next() {
		String city = (String) departCities.get(position);
		position++;
		return city;
	}
	
}
