package by.itacademy.javaenterprise.knyazev.dao;

import java.util.List;

import by.itacademy.javaenterprise.knyazev.dao.exceptions.ExceptionDAO;

public interface DAO<T> {
	Long save(T object) throws ExceptionDAO;
	T find(Long id) throws ExceptionDAO;
	List<T>findAll() throws ExceptionDAO;
	void update(T object) throws ExceptionDAO;
	void delete(T object) throws ExceptionDAO;
}
