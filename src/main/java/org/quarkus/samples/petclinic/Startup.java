package org.quarkus.samples.petclinic;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.quarkus.samples.petclinic.system.User;

import io.quarkus.runtime.StartupEvent;

@Singleton
public class Startup {
	@Transactional
	public void loadUsers(@javax.enterprise.event.Observes StartupEvent evt) {
		// reset and load all test users
		User.deleteAll();
		User.add("admin@petclinic.com", "Admin");
		User.add("user@petclinic.com", "User");
	}
}