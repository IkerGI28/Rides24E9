package gauzatuEragiketaTest;

import static org.junit.Assert.*;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;

public class gauzatuEragiketaBlackTest {
	private DataAccess sut = new DataAccess();
	private int amount = 20;
	Driver driverTest = new Driver("iker", "123");
	
	//erabiltzailea datubasean dago eta atera nahi den dirua, daukana bainan handiagoa da
	@Test
	public void test1() {
		try {
			sut.open();
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			sut.gauzatuEragiketa(driverTest.getUsername(), 10, true);
			boolean emaitza  = sut.gauzatuEragiketa(driverTest.getUsername(), 30, false);
			assertEquals(true, emaitza);
		}catch(Exception e) {
			sut.close();
		}finally {
            sut.deleteUser(driverTest);
            sut.close();
		}
	}
	
	//erabiltzailea datubasean eta dirua egoki ateratzen du
	@Test
	public void test2() {
		try {
			sut.open();
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			sut.gauzatuEragiketa(driverTest.getUsername(), 40, true);
			boolean emaitza = sut.gauzatuEragiketa(driverTest.getUsername(), amount, false);
			assertEquals(true, emaitza);
		} catch (Exception e) {
			sut.close();
		} finally {
			sut.deleteUser(driverTest);
			sut.close();
		}
	} 
	//erabiltzailea null da
	@Test
	public void test3() {
		try {
			sut.open();
			boolean emaitza = sut.gauzatuEragiketa(null, 10, true);
			assertEquals(false, emaitza);
		} catch (Exception e) {
			sut.close();
		}finally {
			sut.deleteUser(driverTest);
			sut.close();
		}

	}
	//depositatu nahi den dirua negatiboa da
	@Test
	public void test4() {
		try {
			sut.open();
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			sut.gauzatuEragiketa(driverTest.getUsername(), 20, true);
			boolean emaitza = sut.gauzatuEragiketa(driverTest.getUsername(), -10, true);
			assertEquals(true, emaitza);
		} catch (Exception e) {
			sut.close();
		}finally {
			sut.deleteUser(driverTest);
			sut.close();
		}

	}
	//erabiltzailea ez dago datubasean
	@Test
	public void test5() {
		try {
			sut.open();
			boolean emaitza = sut.gauzatuEragiketa("iker", amount, true);
			assertEquals(false, emaitza);
		} catch (Exception e) {
			sut.close();
		}finally {
			sut.deleteUser(driverTest);
			sut.close();
		}

	}
}
