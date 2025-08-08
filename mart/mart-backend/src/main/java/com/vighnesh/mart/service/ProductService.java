package com.vighnesh.mart.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vighnesh.mart.handler.MartException;
import com.vighnesh.mart.pojo.Product;
import com.vighnesh.mart.repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Transactional
	public void addProduct(Product product) throws MartException {
		if (product.getName() == null || product.getName().trim().isEmpty()) {
			throw new MartException("Product name cannot be null or empty");
		}
		if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
			throw new MartException("Product description cannot be null or empty");
		}
		if (product.getPrice() == null) {
			throw new MartException("Product price cannot be null or empty");
		}
		productRepository.addProduct(product);
	}
	
	@Transactional
	public List<Product> getAllProducts() {
		return productRepository.getProduct(new Product());
	}
	
	@Transactional
	public Product getProduct(Product product) throws MartException {
		if (product.getProduct_id() <= 0 && product.getName()==null) {
			throw new MartException("Invalid product");
		}		
		List<Product> prod = productRepository.getProduct(product);
		if (prod.size()==0) {
			throw new MartException("Product doesn't exist");
		}
		return prod.get(0);
	}
	
	@Transactional
	public BigDecimal getProductPriceById(int productId) throws MartException {
		if (productId <= 0) {
			throw new MartException("Invalid product");
		}		
		Product temp = new Product();
		temp.setProduct_id(productId);
		List<Product> prod = productRepository.getProduct(temp);
		if (prod.size()==0) {
			throw new MartException("Product doesn't exist");
		}
		return prod.get(0).getPrice();
	}

	@Transactional
	public void updateProduct(Product product) throws MartException {
		if (product.getProduct_id()<=0) {
			throw new MartException("Product ID invalid");
		}
		if (product.getDescription() != null && product.getDescription().trim().isEmpty()) {
			throw new MartException("Product description cannot be null or empty");
		}
		if (product.getPrice() != null && (product.getPrice().compareTo(BigDecimal.ZERO)<=0) ) {
			throw new MartException("Product price cannot be <= 0");
		}
		productRepository.updateProduct(product);
	}
	
	@Transactional
	public void deleteProduct(Product product) throws MartException {
		if (product.getProduct_id()<=0) {
			throw new MartException("Product ID invalid");
		}
		product.setStatus(Product.Status.INACTIVE);
		productRepository.updateProduct(product);
	}
}
