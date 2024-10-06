package gauzatuEragiketaTest;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.User;

public class gauzatuEragiketaBlackMockTest {


	static DataAccess sut;
	protected MockedStatic<Persistence> persistanceMock;
	
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;
	
	int amount = 20;
	Driver driverTest = new Driver("iker", "123");
	
	@Before
	 public void init() {
		MockitoAnnotations.openMocks(this);
		persistanceMock = Mockito.mockStatic(Persistence.class);
		persistanceMock.when(() ->
		Persistence.createEntityManagerFactory(Mockito.any())).thenReturn(entityManagerFactory);
		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		sut=new DataAccess(db);
	 }
	@After
	public void tearDown() {
		persistanceMock.close();
	 }
	
	//erabiltzailea datubasean eta atera nahi den dirua, daukana bainan handiagoa da
	@Test
	public void test1() {
		try {
			sut.open();
			TypedQuery<User> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(driverTest);
			sut.gauzatuEragiketa(driverTest.getUsername(), 10, true);
			boolean emaitza = sut.gauzatuEragiketa(driverTest.getUsername(), 30, false);
			assertEquals(true, emaitza);
		} catch (Exception e) {
			sut.close();
		} finally {
			sut.deleteUser(driverTest);
			sut.close();
		}
	}
	
	//erabiltzailea datubasean eta dirua egoki ateratzen du
	@Test
	public void test2() {
		try {
			sut.open();
			TypedQuery<User> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(driverTest);
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
		}

	}
	
	//depositatu nahi den dirua negatiboa da
	@Test
	public void test4() {
		try {
			sut.open();
			TypedQuery<User> query = Mockito.mock(TypedQuery.class);
			Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
			Mockito.when(query.getSingleResult()).thenReturn(driverTest);
			sut.gauzatuEragiketa(driverTest.getUsername(), 20, true);
			boolean emaitza = sut.gauzatuEragiketa(driverTest.getUsername(), -10, true);
			assertEquals(true, emaitza);
		} catch (Exception e) {
			sut.close();
		}

	}
	
	//erabiltzailea ez dago datubasean
	@Test
	public void test5() {
		int amount = 20;
		Driver driverTest = new Driver("gorka@gmail.com", "123");
		try {
			sut.open();
			driverTest.setMoney(20);
			Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(null);
			boolean emaitza = sut.gauzatuEragiketa(driverTest.getUsername(), amount, true);
			assertEquals(false, emaitza);
			sut.close();
		} catch (Exception e) {
			sut.close();
		}
	}
}
