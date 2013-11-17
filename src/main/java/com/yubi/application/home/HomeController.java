package com.yubi.application.home;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.yubi.application.admin.ShopStatusService;

@Controller
public class HomeController {

	private final ShopStatusService shopStatusService;
	
	@Inject
	public HomeController(ShopStatusService shopStatusService) {
		super();
		this.shopStatusService = shopStatusService;
	}

	
	@RequestMapping("/")
	public String shop() {
		return "redirect:/shop";
	}

	
	@RequestMapping(value = "/sitemap.xml", produces = "application/xml")
	public @ResponseBody
	String sitemap() throws IOException {
		return shopStatusService.getSiteMap();
	}

	
	/**
	 * No robots.txt file is needed at this stage. However Google will want a 404 if there
	 * isn't one. Don't want a 301 to the standard error page.
	 */
	@RequestMapping("/robots.txt")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void robotsTxt() {}
}
