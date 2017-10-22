package db;

import java.util.List;

import domain.Person;

public interface PersonDb {

	Person get(String personId);

	List<Person> getAll();

	void add(Person person);

	void update(Person person);

	void delete(String personId);

}