package org.quarkus.samples.petclinic.system;

import org.quarkus.samples.petclinic.model.Person;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.FormParam;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User extends Person {

	@Column(name = "email", unique = true)
	@NotEmpty
	@FormParam("email")
	public String email;

	@Column(name = "password")
	@NotEmpty
	@FormParam("password")
	public String password;

	public static Collection<User> findByEmail(String name) {
		return User.list("email", name);
	}

	public User attach() {
		return getEntityManager().merge(this);
	}

	/**
	 * Adds a new user to the database
	 * 
	 * @param username the username
	 * @param password the unencrypted password (it will be encrypted with bcrypt)
	 */
	public static void add(String email, String password) {
		User user = new User();
		user.firstName = email;
		user.lastName = email;
		user.email = email;
		Log.info("user password stored: " + password);
		user.password = BcryptUtil.bcryptHash(password);
		Log.info("user password encrypted: " + user.password);
		user.persist();
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}

}