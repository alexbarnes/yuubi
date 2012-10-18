package com.yubi.application.product;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;

@Controller
@RequestMapping("/product/image")
public class ProductImageController {

	private final ProductAccess productAccess;

	@Inject
	public ProductImageController(ProductAccess productAccess) {
		super();
		this.productAccess = productAccess;
	}

	@RequestMapping("/display/{id}")
	public ResponseEntity<byte[]> show(@PathVariable("id") long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_GIF);
		return new ResponseEntity<byte[]>(
				productAccess.loadImage(id).getImage(), 
				headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/add/{code}", method = RequestMethod.POST)
	public Model add(@PathVariable("code") String code, MultipartFile file,
			String description) throws IOException {

		Product product = productAccess.load(code);

		if (code.isEmpty() || file == null) {
			Model model = new Model(ScreenMode.ENQUIRE, "product", "product",
					product);
			model.addObject("showpopup", true);
			return model;
		}

		ProductImage image = product.addImage();
		image.setImage(file.getBytes());
		image.setDescription(description);

		productAccess.save(product);

		return new Model("redirect:/product/view/" + code);
	}

	
	@RequestMapping("/remove/{id}")
	public void remove(@PathVariable("id") long id) {
		productAccess.deleteImage(id);
	}

}
