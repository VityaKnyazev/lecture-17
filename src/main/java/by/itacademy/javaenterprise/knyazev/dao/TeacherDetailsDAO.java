package by.itacademy.javaenterprise.knyazev.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.itacademy.javaenterprise.knyazev.dao.exceptions.ExceptionDAO;
import by.itacademy.javaenterprise.knyazev.dao.exceptions.TeacherDetailsExceptionDAO;
import by.itacademy.javaenterprise.knyazev.entities.TeacherDetails;

public class TeacherDetailsDAO extends AbstractDAO<TeacherDetails> {
	private static final String SELECT_ALL = "SELECT d FROM teacher_details d";

	private static final Logger logger = LoggerFactory.getLogger(TeacherDetailsDAO.class);

	public TeacherDetailsDAO(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Long save(TeacherDetails teacherDetails) throws TeacherDetailsExceptionDAO {
		if (teacherDetails != null) {
			try {
				entityManager.getTransaction().begin();
				entityManager.persist(teacherDetails);
				entityManager.getTransaction().commit();
				return teacherDetails.getId();
			} catch (RuntimeException e) {
				entityManager.getTransaction().rollback();
				logger.error("Transaction on method int save(TeacherDetails teacherDetails) failed: " + e.getMessage()
						+ "With name of class exception: " + e.getClass().getCanonicalName());
				return null;
			}
		} else {
			throw new TeacherDetailsExceptionDAO(
					"Expected TeacherDetails object. Null was given in method Long save(TeacherDetails teacherDetails)");
		}
	}

	@Override
	public TeacherDetails find(Long id) throws TeacherDetailsExceptionDAO {

		if (id == null || id < 0L) {
			throw new TeacherDetailsExceptionDAO(
					"Id should be not null and above zero in method TeacherDetails find(Long id)");
		}

		try {
			return entityManager.find(TeacherDetails.class, id);
		} catch (IllegalArgumentException e) {
			logger.error("Can't get TeacherDetails object on id=" + id + " on method TeacherDetails find(Integer id): "
					+ e.getMessage() + "with exception class name: " + e.getClass().getCanonicalName());
		}

		return null;
	}

	@Override
	public List<TeacherDetails> findAll() throws TeacherDetailsExceptionDAO {
		List<TeacherDetails> teachersDetails = new ArrayList<>();

		try {
			teachersDetails = entityManager.createNamedQuery(SELECT_ALL, TeacherDetails.class).getResultList();
			if (teachersDetails.isEmpty()) {
				throw new TeacherDetailsExceptionDAO(
						"Error in method List<TeacherDetails> findAll(), no entries were found in database");
			}
		} catch (IllegalStateException | IllegalArgumentException | PersistenceException e) {
			logger.error("Error in method List<TeacherDetails> findAll(): " + e.getMessage()
					+ " from class exception name: " + e.getClass().getCanonicalName());
		}

		return teachersDetails;
	}
	
	@Override
	public void update(TeacherDetails teacherDetails) throws TeacherDetailsExceptionDAO {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(teacherDetails);
			entityManager.getTransaction().commit();
		} catch (IllegalStateException | IllegalArgumentException | PersistenceException e) {
			entityManager.getTransaction().rollback();
			logger.error("Error in method update(TeacherDetails teacherDetails): " + e.getMessage()
					+ " from class exception name: " + e.getClass().getCanonicalName());
			throw new TeacherDetailsExceptionDAO(
					"Error can't merge object on method void update(TeacherDetails teacherDetails)", e);
		}
	}

	@Override
	public void delete(TeacherDetails teacherDetails) throws ExceptionDAO {
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(teacherDetails);
			entityManager.getTransaction().commit();
		} catch (IllegalStateException | IllegalArgumentException | PersistenceException e) {
			entityManager.getTransaction().rollback();
			logger.error("Error in method void delete(TeacherDetails teacherDetails): " + e.getMessage()
					+ " from class exception name: " + e.getClass().getCanonicalName());
			throw new TeacherDetailsExceptionDAO(
					"Error can't remove object on method void delete(TeacherDetails teacherDetails)", e);
		}
	}
}