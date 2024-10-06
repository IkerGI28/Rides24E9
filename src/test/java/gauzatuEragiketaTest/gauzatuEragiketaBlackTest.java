package gauzatuEragiketaTest;

import static org.junit.Assert.*;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;

public class gauzatuEragiketaBlackTest {
	private DataAccess sut = new DataAccess();
	private int amount = 20;
	Driver driverTest = new Driver("iker", "123");
	
	
	@Test
	public void test1() {
		try {
			sut.open();
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			boolean emaitza  = sut.gauzatuEragiketa(driverTest.getUsername(), amount, false);
			assertEquals(false, emaitza);
		}catch(Exception e) {
			sut.close();
		}finally {
            sut.deleteUser(driverTest);
            sut.close();
		}
	}
	
	@Test
	public void test2() {
		try {
			sut.open();
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			driverTest.setMoney(40);
			boolean emaitza = sut.gauzatuEragiketa(driverTest.getUsername(), amount, false);
			assertEquals(true, emaitza);
		} catch (Exception e) {
			sut.close();
		} finally {
			sut.deleteUser(driverTest);
			sut.close();
		}
	}

	@Test
	public void test3() {
		try {
			sut.open();
			boolean emaitza = sut.gauzatuEragiketa(null, amount, true);
			assertEquals(false, emaitza);
		} catch (Exception e) {
			sut.close();
		}finally {
			sut.deleteUser(driverTest);
			sut.close();
		}

	}
	@Test
	public void test4() {
		try {
			sut.open();
			driverTest.setMoney(20);
			sut.addDriver(driverTest.getUsername(), driverTest.getPassword());
			boolean emaitza = sut.gauzatuEragiketa(driverTest.getUsername(), -10, true);
			assertEquals(true, emaitza);
		} catch (Exception e) {
			sut.close();
		}finally {
			sut.deleteUser(driverTest);
			sut.close();
		}

	}
	
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
