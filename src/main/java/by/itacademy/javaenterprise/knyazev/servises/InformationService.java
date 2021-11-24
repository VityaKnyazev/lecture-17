package by.itacademy.javaenterprise.knyazev.servises;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.itacademy.javaenterprise.knyazev.dao.TeachersDAO;
import by.itacademy.javaenterprise.knyazev.dao.exceptions.DAOException;
import by.itacademy.javaenterprise.knyazev.entities.Teacher;

public class InformationService {
	private TeachersDAO teachersDAO;
	private static final Logger logger = LoggerFactory.getLogger(InformationService.class);

	public InformationService(TeachersDAO teachersDAO) {
		this.teachersDAO = teachersDAO;
	}

	public void saveTeacher(Teacher teacher) {
		try {
			teachersDAO.save(teacher);
			logger.info("Successfully saved teacher object.");
		} catch (DAOException e) {
			logger.error("Error in Long saveTeacher(Teacher teacher) method: " + e.getMessage());
		}

	}

	public Teacher getTeacherOnId(Long id) {
		try {
			return teachersDAO.find(id);
		} catch (DAOException e) {
			logger.error("Error: " + e.getMessage());
		}
		return null;
	}

	public void printTeacherEmailOnId(Long id) {
		try {
			logger.info("Teacher eMail: " + teachersDAO.find(id).getTeacherDetails().getEmail());
		} catch (DAOException e) {
			logger.error("Error in String getTeacherEmailOnId(Long id) method: " + e.getMessage());
		}
	}

	public void printTeachersInformation() {
		List<Teacher> teachers = new ArrayList<>();

		teachers = teachersDAO.findAll();
		teachers.stream().forEach(t -> logger.info(t.toString()));

	}

	public void updateTecherInformation(Teacher teacher) {
		try {
			teachersDAO.update(teacher);
			logger.info("Successfully updated teacher information");
		} catch (DAOException e) {
			logger.error("Error on updateTecherInformation(Teacher teacher) method", e);
		}
		
	}

	public void deleteTecherInformation(Teacher teacher) {
		try {
			teachersDAO.delete(teacher);
			logger.info("Successfully deleted teacher information");
		} catch (DAOException e) {
			logger.error("Error on deleteTecherInformation(Teacher teacher) method", e);
		}
		
	}

}