package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Person;

public class PersonDbInMemory implements PersonDb {
	private Map<String, Person> persons = new HashMap<String, Person>();
	
	public PersonDbInMemory () {
		Person administrator = new Person("admin", "admin@ucll.be", "t", "Ad", "Ministrator");
		add(administrator);
	}
	
	/* (non-Javadoc)
	 * @see db.PersonDb#get(java.lang.String)
	 */
	@Override
	public Person get(String personId){
		if(personId == null){
			throw new DbException("No id given");
		}
		return persons.get(personId);
	}
	
	/* (non-Javadoc)
	 * @see db.PersonDb#getAll()
	 */
	@Override
	public List<Person> getAll(){
		return new ArrayList<Person>(persons.values());	
	}

	/* (non-Javadoc)
	 * @see db.PersonDb#add(domain.Person)
	 */
	@Override
	public void add(Person person){
		if(person == null){
			throw new DbException("No person given");
		}
		if (persons.containsKey(person.getUserid())) {
			throw new DbException("User already exists");
		}
		persons.put(person.getUserid(), person);
	}
	
	/* (non-Javadoc)
	 * @see db.PersonDb#update(domain.Person)
	 */
	@Override
	public void update(Person person){
		if(person == null){
			throw new DbException("No person given");
		}
		if(!persons.containsKey(person.getUserid())){
			throw new DbException("No person found");
		}
		persons.put(person.getUserid(), person);
	}
	
	/* (non-Javadoc)
	 * @see db.PersonDb#delete(java.lang.String)
	 */
	@Override
	public void delete(String personId){
		if(personId == null){
			throw new DbException("No id given");
		}
		persons.remove(personId);
	}
}
