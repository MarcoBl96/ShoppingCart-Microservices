
package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;


@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {
	private final ProductRepository productRepository;

	@RequestMapping("/getAllProducts")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@RequestMapping("/{productId}")
	public Optional<Product> getProductById(@PathVariable(value = "productId") Long productId) {

		System.out.println("Get Product Details");
		return productRepository.findById(productId);

	}

	@PostMapping("/admin/delete/{productId}")
	public void deleteProduct(@PathVariable(value = "productId") Long productId) {

		// Here the Path class is used to refer the path of the file

		Path path = Paths.get("C:/Users/Ismail/workspace/ShoppingCart/src/main/webapp/WEB-INF/resource/images/products/"
				+ productId + ".jpg");

		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		productRepository.deleteById(productId);
		return;
		// http://localhost:8080/shoppingCart/getAllProducts
	}

	@PostMapping(value = "/admin/product/addProduct")
	@ResponseStatus(HttpStatus.CREATED)
	public Product addProduct(@Valid @RequestBody Product product) {
		System.out.println("Add product funktion");
		System.out.println("Produkt: " + product.getProductName());
		return productRepository.save(product);
	}

	@PutMapping(value = "/admin/product/editProduct/{productId}")
	public void updateProduct(@PathVariable("productId") @Min(1) long productId, @Valid @RequestBody Product productRequest) {
		final Optional<Product> product = productRepository.findById(productId);
		final Product productModel = product.orElseThrow(() -> new ResourceNotFoundException("Product "+productId+" not found"));

		// This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
		productModel.setProductId(productRequest.getProductId());
		productModel.setProductPrice(productRequest.getProductPrice());
		productModel.setProductName(productRequest.getProductName());
		productModel.setProductDescription(productRequest.getProductDescription());
		productModel.setProductImage(productRequest.getProductImage());
		productModel.setUnitStock(productRequest.getUnitStock());
		productModel.setProductManufacturer(productRequest.getProductManufacturer());
		productModel.setProductCategory(productRequest.getProductCategory());
		log.info("Saving product {}", productModel);
		productRepository.save(productModel);
	}

	//API for Performance Test
	@RequestMapping("/calcPi/threads/{threads}")
	public String getPi(@PathVariable(value = "threads") int threads) throws JsonProcessingException {

		CalcPi calcPi = new CalcPi();

		double pi = calcPi.calcPi(threads);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(pi);
		return json;
	}

	//API for Performance Test
	@RequestMapping("/helloworld")
	public String getHelloWorld() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString("Hello World");
		return json;
	}
}
