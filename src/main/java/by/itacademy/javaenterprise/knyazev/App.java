package by.itacademy.javaenterprise.knyazev;

import by.itacademy.javaenterprise.knyazev.dao.TeachersDAO;
import by.itacademy.javaenterprise.knyazev.entities.Teacher;
import by.itacademy.javaenterprise.knyazev.entities.TeacherDetails;
import by.itacademy.javaenterprise.knyazev.jpa.JpaUtil;
import by.itacademy.javaenterprise.knyazev.servises.InformationService;

public class App {
	private static InformationService informationService;
	
	public static void main(String[] args) {
		Long id = 2L;

		TeachersDAO teachersDAO = new TeachersDAO(JpaUtil.getInstance().getEntityManager());
		informationService = new InformationService(teachersDAO);

		saveTeacherInfo();
		informationService.printTeacherEmailOnId(id);
		updateTeacherInfo();
		deleteTeacherInfo();

		teachersDAO.closeEntityManager();
		JpaUtil.getInstance().closeEntityManagerFactory();
	}

	public static void saveTeacherInfo() {
		Teacher teacher = new Teacher();
		teacher.setName("Ленок");
		teacher.setAge(22);
		teacher.setTeacherDetails(new TeacherDetails());

		teacher.getTeacherDetails().setSubject("Биология");
		teacher.getTeacherDetails().setSchoolNumber(18);
		teacher.getTeacherDetails().setEMail("lenok@mail.ru");

		informationService.saveTeacher(teacher);
	}

	public static void updateTeacherInfo() {
		Long id = 4L;

		Teacher teacher = informationService.getTeacherOnId(id);
		
		teacher.setAge(25);
		System.out.println(teacher.getTeacherDetails().getId());

		informationService.updateTecherInformation(teacher);
	}

	public static void deleteTeacherInfo() {
		Long id = 3L;

		Teacher teacher = informationService.getTeacherOnId(id);

		informationService.deleteTecherInformation(teacher);
	}

}