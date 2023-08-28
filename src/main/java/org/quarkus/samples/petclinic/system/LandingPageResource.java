package org.quarkus.samples.petclinic.system;

import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class LandingPageResource {

	@Inject
	TemplatesLocale templates;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public TemplateInstance get() {
		return templates.login(null, null);
	}

}