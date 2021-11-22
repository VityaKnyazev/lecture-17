package by.itacademy.javaenterprise.knyazev.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import by.itacademy.javaenterprise.knyazev.dao.exceptions.TeacherDetailsExceptionDAO;
import by.itacademy.javaenterprise.knyazev.entities.Teacher;
import by.itacademy.javaenterprise.knyazev.entities.TeacherDetails;

public class TeacherDetailsDAOTests {
	private EntityManager entityManagerMock;
	private EntityTransaction entityTransactionMock;
	private TeacherDetailsDAO teacherDetailsDAO;
	private TypedQuery<TeacherDetails> typedQueryMock;
		
	

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setUpBeforeEachTest() {
		entityManagerMock = Mockito.mock(EntityManager.class);		
		entityTransactionMock = Mockito.mock(EntityTransaction.class);
		typedQueryMock = Mockito.mock(TypedQuery.class);
		teacherDetailsDAO = new TeacherDetailsDAO(entityManagerMock);	
	}
	
	@Test
	public void whenSaveTeacherDetails() throws TeacherDetailsExceptionDAO {
		TeacherDetails teacherDetails = new TeacherDetails();
		teacherDetails.setId(18L);
		
		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
				
		Long result = teacherDetailsDAO.save(teacherDetails);
		
		assertNotNull(result);
		assertEquals(18L, result);
		Mockito.verify(entityManagerMock, times(1)).persist(teacherDetails);
	}
	
	@Test
	public void whenSaveNullTeacherDetails() {
		
		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
					
		assertThrows(TeacherDetailsExceptionDAO.class, () -> teacherDetailsDAO.save(null), "TeacherDetailsExceptionDAO was expected");
	}
	
	@Test
	public void whenSaveThrowEntityExistsException() throws TeacherDetailsExceptionDAO {
				
		TeacherDetails teacherDetails = new TeacherDetails();
		
		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);		
		Mockito.doThrow(EntityExistsException.class).when(entityManagerMock).persist(Mockito.eq(teacherDetails));
				
		Long result = teacherDetailsDAO.save(teacherDetails);
				
		assertNull(result);		
		Mockito.verify(entityManagerMock, times(1)).persist(teacherDetails);
		
	}
	
	@Test
	public void whenSaveTrowIllegalArgumentException() throws TeacherDetailsExceptionDAO {
				
		TeacherDetails teacherDetails = new TeacherDetails();
		
		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		Mockito.doThrow(IllegalArgumentException.class).when(entityManagerMock).persist(Mockito.eq(teacherDetails));
				
		Long result = teacherDetailsDAO.save(teacherDetails);
		
		assertNull(result);
		Mockito.verify(entityManagerMock, times(1)).persist(teacherDetails);
	}
	
	@Test
	public void whenSaveThrowTransactionRequiredException() throws TeacherDetailsExceptionDAO {
				
		TeacherDetails teacherDetails = new TeacherDetails();
		
		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		Mockito.doThrow(TransactionRequiredException.class).when(entityManagerMock).persist(Mockito.eq(teacherDetails));
				
		Long result = teacherDetailsDAO.save(teacherDetails);
		
		assertNull(result);
		Mockito.verify(entityManagerMock, times(1)).persist(teacherDetails);
	}
	
	@Test
	public void whenFindTeacherDetails() throws TeacherDetailsExceptionDAO {
		TeacherDetails teacherDetails = new TeacherDetails();
		teacherDetails.setId(5L);
		
		Long idForQuery = 5L;
		
		Mockito.when(entityManagerMock.find(Mockito.<Class<TeacherDetails>>any(), Mockito.eq(idForQuery))).thenReturn(teacherDetails);
		
		assertEquals(teacherDetails, teacherDetailsDAO.find(idForQuery));
	}
	
	@Test
	public void whenFindTeacherDetailsOnNull() throws TeacherDetailsExceptionDAO {
		
		assertThrows(TeacherDetailsExceptionDAO.class, () -> teacherDetailsDAO.find(null), "TeacherDetailsExceptionDAO was expected");
	}
	
	@Test
	public void whenFindTeacherOnIdBelowZero() {
		Long idForQuery = -1L;
		
		assertThrows(TeacherDetailsExceptionDAO.class, () -> teacherDetailsDAO.find(idForQuery), "TeacherDetailsExceptionDAO was expected");
	}
	
	@Test
	public void whenFindTeacherTrowIllegalArgumentException() throws TeacherDetailsExceptionDAO {
		Long idForQuery = 12566L;
		
		Mockito.when(entityManagerMock.find(Mockito.<Class<TeacherDetails>>any(), Mockito.eq(idForQuery))).thenThrow(IllegalArgumentException.class);
		
		assertNull(teacherDetailsDAO.find(idForQuery));
	}
	
	//TODO THIS IS SECOND PART
	@Test
	public void whenFindAll() throws TeacherDetailsExceptionDAO {
		List<TeacherDetails> teachersDetails = new ArrayList<>();
		teachersDetails.add(new TeacherDetails());
		teachersDetails.stream().forEach(t -> {
			t.setId(5L);
			t.setEMail("Teacher@mail.ru");
			t.setSubject("Math");
			t.setSchoolNumber(18);
			t.setTeacher(new Teacher());
		});
		
		Mockito.when(entityManagerMock.createNamedQuery(Mockito.anyString(), Mockito.eq(TeacherDetails.class))).thenReturn(typedQueryMock);
		Mockito.when(typedQueryMock.getResultList()).thenReturn(teachersDetails);
		
		assertEquals(teachersDetails.get(0), teacherDetailsDAO.findAll().get(0));
	}
	
	@Test
	public void whenFindAllThrowTeacherDetailsExceptionDAO() {
		List<TeacherDetails> teachersDetails = new ArrayList<>();
		
		Mockito.when(entityManagerMock.createNamedQuery(Mockito.anyString(), Mockito.eq(TeacherDetails.class))).thenReturn(typedQueryMock);
		Mockito.when(typedQueryMock.getResultList()).thenReturn(teachersDetails);
		assertThrows(TeacherDetailsExceptionDAO.class, () -> teacherDetailsDAO.findAll(), "TeacherDetailsExceptionDAO was expected");
	}
	
	@Test
	public void whenFindAllThrowIllegalStateException() throws TeacherDetailsExceptionDAO {
		Mockito.when(entityManagerMock.createNamedQuery(Mockito.anyString(), Mockito.eq(TeacherDetails.class))).thenReturn(typedQueryMock);
		Mockito.when(typedQueryMock.getResultList()).thenThrow(new IllegalStateException());
		
		assertEquals(true, teacherDetailsDAO.findAll().isEmpty());
		Mockito.verify(entityManagerMock, times(1)).createNamedQuery(Mockito.anyString(), Mockito.eq(TeacherDetails.class));
		Mockito.verify(typedQueryMock, atLeastOnce()).getResultList();
	}
	
	@Test
	public void whenFindAllThrowIllegalArgumentException() throws TeacherDetailsExceptionDAO {
		Mockito.when(entityManagerMock.createNamedQuery(Mockito.anyString(), Mockito.eq(TeacherDetails.class))).thenThrow(IllegalArgumentException.class);
		
		assertEquals(true, teacherDetailsDAO.findAll().isEmpty());
		Mockito.verify(entityManagerMock, times(1)).createNamedQuery(Mockito.anyString(), Mockito.eq(TeacherDetails.class));
	}
	
	@Test
	public void whenFindAllThrowPersistenceException() throws TeacherDetailsExceptionDAO {
		Mockito.when(entityManagerMock.createNamedQuery(Mockito.anyString(), Mockito.eq(TeacherDetails.class))).thenReturn(typedQueryMock);
		Mockito.when(typedQueryMock.getResultList()).thenThrow(PersistenceException.class);
		
		assertEquals(true, teacherDetailsDAO.findAll().isEmpty());
		Mockito.verify(entityManagerMock, times(1)).createNamedQuery(Mockito.anyString(), Mockito.eq(TeacherDetails.class));
		Mockito.verify(typedQueryMock, times(1)).getResultList();
	}
	
	@Test
	public void whenUpdate() {
		TeacherDetails td = new TeacherDetails();
		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		
		assertDoesNotThrow(() -> teacherDetailsDAO.update(td));
		Mockito.verify(entityManagerMock, times(1)).merge(Mockito.eq(td));
	}
	
	@Test
	public void whenUpdateThrowTeacherDetailsExceptionDAO() {
		TeacherDetails td = new TeacherDetails();
		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		Mockito.when(entityManagerMock.merge(Mockito.eq(td))).thenThrow(PersistenceException.class);
		
		assertThrows(TeacherDetailsExceptionDAO.class, () -> teacherDetailsDAO.update(td), "TeacherDetailsExceptionDAO was expected");
		Mockito.verify(entityManagerMock, times(1)).merge(Mockito.eq(td));
	}
	
	@Test
	public void whenDelete() {
		TeacherDetails td = new TeacherDetails();
		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		
		assertDoesNotThrow(() -> teacherDetailsDAO.delete(td));
		Mockito.verify(entityManagerMock, times(1)).remove(Mockito.eq(td));
	}
	
	@Test
	public void whenDeleteThrowTeacherDetailsExceptionDAO() {
		TeacherDetails td = new TeacherDetails();
		Mockito.when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		Mockito.doThrow(IllegalArgumentException.class).when(entityManagerMock).remove(Mockito.eq(td));
		
		assertThrows(TeacherDetailsExceptionDAO.class, () -> teacherDetailsDAO.delete(td), "TeacherDetailsExceptionDAO was expected");
		Mockito.verify(entityManagerMock, times(1)).remove(Mockito.eq(td));
	}
}
