package com.vighnesh.mart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vighnesh.mart.handler.MartException;
import com.vighnesh.mart.pojo.Product;
import com.vighnesh.mart.pojo.ResponseObject;
import com.vighnesh.mart.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	private final ProductService productService;	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping("/addProduct")
	public ResponseObject createProduct(@RequestBody Product product) throws MartException {
		productService.addProduct(product);
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "Product created successfully");
		return responseObject;
	}
	
	@GetMapping("/getAllProducts")
	public ResponseObject getAllProducts() throws MartException{
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, productService.getAllProducts(), "Products retrived successfully");
		return responseObject;
	}
	
	@GetMapping("/getProduct/{productId}")
	public ResponseObject getProduct(@PathVariable int productId) throws MartException {
		Product temp = new Product();
		temp.setProduct_id(productId);
		Product product = productService.getProduct(temp);
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, product, "Product retrieved successfully");
		return responseObject;
	}
	
	@PutMapping("/updateProduct/{productId}")
	public ResponseObject updateProduct(@PathVariable int productId, @RequestBody Product updateRequest) throws MartException{
		updateRequest.setProduct_id(productId);	 
	 	productService.updateProduct(updateRequest);
	 	ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "Product updated successfully");
		return responseObject;
	}
	
	@DeleteMapping("/deleteProduct/{productId}")
	public ResponseObject deleteProduct(@PathVariable int productId) throws MartException{
		Product delProd = new Product();
		delProd.setProduct_id(productId);
		productService.deleteProduct(delProd);
	 	ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "Product deleted successfully");
		return responseObject;
	}
}
