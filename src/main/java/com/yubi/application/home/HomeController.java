package com.yubi.application.home;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

	@Inject
	ApplicationContext context;

	@RequestMapping("/")
	public String shop() {
		return "redirect:/shop";
	}

	@RequestMapping(value = "/sitemap.xml", produces = "application/xml")
	public @ResponseBody
	String sitemap() throws IOException {
		Resource resource = context.getResource("classpath:sitemap.xml");
		InputStream is = resource.getInputStream();

		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer, null);
		return writer.toString();
	}

	
	/**
	 * No robots.txt file is needed at this stage. However Google will want a 404 if there
	 * isn't one. Don't want a 301 to the standard error page.
	 */
	@RequestMapping("/robots.txt")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void robotsTxt() {}
}
