package by.itacademy.javaenterprise.knyazev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TransactionRequiredException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import by.itacademy.javaenterprise.knyazev.dao.exceptions.DAOException;
import by.itacademy.javaenterprise.knyazev.entities.Teacher;

public class TeachersDAOTests {
	private EntityManager entityManagerMock;
	private EntityTransaction entityTransactionMock;
	private TeachersDAO teachersDAO;

	@BeforeEach
	public void setUpBeforeEachTest() {
		entityManagerMock = Mockito.mock(EntityManager.class);
		entityTransactionMock = Mockito.mock(EntityTransaction.class);
		teachersDAO = new TeachersDAO(entityManagerMock);
	}

	@Test
	public void whenSaveTeacher() throws DAOException {
		Teacher teacher = new Teacher();
		teacher.setId(25L);

		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);

		Long result = teachersDAO.save(teacher);

		assertNotNull(result);
		assertEquals(25L, result);
		Mockito.verify(entityManagerMock, times(1)).persist(teacher);
	}

	@Test
	public void whenSaveNullTeacher() {

		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);

		assertThrows(DAOException.class, () -> teachersDAO.save(null), "DAOException was expected");
	}

	@Test
	public void whenSaveThrowEntityExistsException() throws DAOException {

		Teacher teacher = new Teacher();

		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		Mockito.doThrow(EntityExistsException.class).when(entityManagerMock).persist(Mockito.eq(teacher));

		Long result = teachersDAO.save(teacher);

		assertNull(result);
		Mockito.verify(entityManagerMock, times(1)).persist(teacher);
	}

	@Test
	public void whenSaveThrowIllegalArgumentException() throws DAOException {

		Teacher teacher = new Teacher();

		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		Mockito.doThrow(IllegalArgumentException.class).when(entityManagerMock).persist(Mockito.eq(teacher));

		Long result = teachersDAO.save(teacher);

		assertNull(result);
		Mockito.verify(entityManagerMock, times(1)).persist(teacher);
	}

	@Test
	public void whenSaveThrowTransactionRequiredException() throws DAOException {

		Teacher teacher = new Teacher();

		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		Mockito.doThrow(TransactionRequiredException.class).when(entityManagerMock).persist(Mockito.eq(teacher));

		Long result = teachersDAO.save(teacher);

		assertNull(result);
		Mockito.verify(entityManagerMock, times(1)).persist(teacher);
		;
	}

	@Test
	public void whenFindTeacher() throws DAOException {
		Teacher teacher = new Teacher();
		teacher.setId(9L);

		Long idForQuery = 9L;

		Mockito.when(entityManagerMock.find(Mockito.<Class<Teacher>>any(), Mockito.eq(idForQuery))).thenReturn(teacher);

		assertEquals(teacher, teachersDAO.find(idForQuery));
	}

	@Test
	public void whenFindTeacherOnNull() {
		assertThrows(DAOException.class, () -> teachersDAO.find(null), "DAOException was expected");
	}

	@Test
	public void whenFindTeacherOnIdBelowZero() {
		Long idForQuery = -12L;

		assertThrows(DAOException.class, () -> teachersDAO.find(idForQuery), "DAOException was expected");
	}

	@Test
	public void whenFindTeacherTrowIllegalArgumentException() throws DAOException {
		Long idForQuery = 135666L;

		Mockito.when(entityManagerMock.find(Mockito.<Class<Teacher>>any(), Mockito.eq(idForQuery)))
				.thenThrow(IllegalArgumentException.class);

		assertNull(teachersDAO.find(idForQuery));
	}
}
