package org.quarkus.samples.petclinic.system;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import io.quarkus.qute.TemplateInstance;
import org.quarkus.samples.petclinic.owner.Owner;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;

@Path("/login")
public class LoginResource {

	@Inject
	TemplatesLocale templates;

	@Inject
	AppMessages messages;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public TemplateInstance get() {
		return templates.login(null, null);
	}

	@POST
	@Path("/authenticate")
	@Produces(MediaType.TEXT_HTML)
	/**
	 * Authenticate user credentials without exposing.
	 *
	 * @return
	 */
	public TemplateInstance authenticate(@FormParam("email") String email, @FormParam("password") String password) {
		final Error ERROR_USER_NOT_FOUND = new Error(messages.error_user_not_found());
		final Error ERROR_USER_CREDENTIALS_EMPTY = new Error(messages.error_user_credentials_empty());
		final Error ERROR_USER_CREDENTIALS_INCORRECT = new Error(messages.error_user_credentials_incorrect());
		final Error ERROR_USER_AUTHENTICATION_FAILED = new Error(messages.error_user_authentication_failed());
		try {
			email = email.trim();
			if (email.isEmpty() || password.isEmpty()) {
				return templates.login(email, ERROR_USER_CREDENTIALS_EMPTY);
			}
			Collection<User> hits = User.findByEmail(email);
			if (hits.isEmpty()) {
				return templates.login(email, ERROR_USER_NOT_FOUND);
			}
			User user = hits.stream().findFirst().get();
			if (BcryptUtil.matches(password, user.password)) {
				return templates.welcome(user);
			} else {
				return templates.login(email, ERROR_USER_CREDENTIALS_INCORRECT);
			}
		} catch (Exception e) {
			return templates.login(email, ERROR_USER_AUTHENTICATION_FAILED);
		}
	}
}