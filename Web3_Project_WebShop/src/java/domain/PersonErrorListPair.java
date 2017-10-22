/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;

/**
 *
 * @author Marie
 */
public class PersonErrorListPair {
    
    private Person person;
    private ArrayList<String> errors;

    public PersonErrorListPair(Person person, ArrayList<String> errors) {
        this.person = person;
        this.errors = errors;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }
    
}
