package domain;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Person {
	private String userid;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String salt;

	public Person(String userid, String email, String password, String firstName, String lastName) {
		setSalt();
		setUserid(userid);
		setEmail(email);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	public Person() {
		setSalt();
	}

	public Person(String userid, String email, String password, String firstName, String lastName, String salt) {
		setSalt(salt);
		setUserid(userid);
		setEmail(email);
		this.password = password;
		setFirstName(firstName);
		setLastName(lastName);
	}	

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		if(userid.isEmpty()){
			throw new IllegalArgumentException("No userid given");
		}
		this.userid = userid;
	}

	public void setEmail(String email) {
		if(email.isEmpty()){
			throw new IllegalArgumentException("No email given");
		}
		if (!isValidEmailAddress(email)) {
			throw new IllegalArgumentException("Email not valid");
		}
		this.email = email;
	}

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
	
	public String getEmail() {
		return email;
	}
	
	public boolean isCorrectPassword(String password) {
		if(password.isEmpty()){
			throw new IllegalArgumentException("No password given");
		}
		//hash password parameter
		String hashedPassword = this.hashPassword(password);
		System.out.println("Hashed pw para: " + hashedPassword);
		//compare with (hashed) password property, return boolean
		return this.password.equals(hashedPassword);
	}

	public void setPassword(String password) {
		if(password.isEmpty()){
			throw new IllegalArgumentException("No password given");
		}
		this.password = this.hashPassword(password);
		System.out.println("Setting PW first time: " + this.password);
	}
	
	public String getPassword() {
		//returns hashed password
		return password;
	}
	
	private String hashPassword(String password) {
		System.out.println("pw before hash: " + password);
		//create MessageDigest
		MessageDigest crypt;
		try {
			crypt = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			throw new DomainException(e.getMessage(), e);
		}
		//reset
		crypt.reset();
		
		//update seed
		try {
			System.out.println("Salt: " + this.getSalt());
			crypt.update(this.getSalt().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			throw new DomainException(e1.getMessage(), e1);
		} 
		//update password
		byte[] passwordBytes;
		try {
			passwordBytes = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new DomainException(e.getMessage(), e);
		}
		crypt.update(passwordBytes);
		//digest
		byte[] digest = crypt.digest();
		//convert to String and return hashed password
		System.out.println("pw after hash: " + new BigInteger(1, digest).toString(16));
		return new BigInteger(1, digest).toString(16);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName.isEmpty()){
			throw new IllegalArgumentException("No firstname given");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName.isEmpty()){
			throw new IllegalArgumentException("No last name given");
		}
		this.lastName = lastName;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setSalt() {
		//create SecureRandom
		SecureRandom random = new SecureRandom();
		//generate seed (of 20 bytes)
		byte[] seed = random.generateSeed(20);
		//update seed
		String salt = new BigInteger(1, seed).toString();
		this.salt = salt;
	}

	@Override
	public String toString(){
		return getFirstName() + " " + getLastName() + ": " + getUserid() + ", " + getEmail();
	}	
}
