package dummy.controller; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import dummy.entity.Product;
import dummy.exception.ProductNotFoundException;
import dummy.model.ProductModel;
import dummy.repo.ProductRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
//@Slf4j  // used for logging
@Validated // to validate path variables and request parameters
@RestController
public class ProductController{
	
	@Autowired
	ProductRepository prodrepo;

	@Autowired
	Gson gson;

	@PostMapping("/addProduct")
	public Mono<ResponseEntity<ProductModel>> saveOrUpdateProduct(@Valid @RequestBody ProductModel productModel) {
		prodrepo.save(gson.fromJson(gson.toJson(productModel), Product.class)) 
			    .doOnSuccess(p -> Mono.just(new ResponseEntity<ProductModel>(productModel,HttpStatus.OK)))
			    .doOnError(e -> Mono.just(new ResponseEntity<String>("error while saving product",HttpStatus.INTERNAL_SERVER_ERROR)))
			    .log();

		return Mono.just(new ResponseEntity<ProductModel>(productModel,HttpStatus.OK)).log(); 
	}
 
	@GetMapping("/getProduct/{productid}")  
	public Mono<ResponseEntity<ProductModel>> findProductById(@PathVariable @Pattern(regexp = "^([0-9]|[1-9][0-9]|100)$",message = "must be between 1 to 100") String productid) {
		
		Mono<Product> proMono = prodrepo.findById(productid)
										.switchIfEmpty(Mono.error(new ProductNotFoundException(productid))) 
										.log();
		return Mono.just(new ResponseEntity<ProductModel>(gson.fromJson(gson.toJson(proMono), ProductModel.class),HttpStatus.OK));
	} 

	@GetMapping("/getProducts") 
	public Flux<ResponseEntity<ProductModel>> findAllProducts() { 
		Flux<Product> proFlux = prodrepo.findAll()
								//		.switchIfEmpty(p -> log.info("products not found"))
										.switchIfEmpty(Mono.error(new ProductNotFoundException("all")))
										.log();
		return Flux.just(new ResponseEntity<ProductModel>(gson.fromJson(gson.toJson(proFlux), ProductModel.class),HttpStatus.OK));
	} 

	@DeleteMapping("/deleteProduct/{productid}")
	public Mono<ResponseEntity<String>> deleteById(@PathVariable @Pattern(regexp = "^([0-9]|[1-9][0-9]|100)$",message = "must be between 1 to 100") String productid) {
		prodrepo.deleteById(productid)
			    .switchIfEmpty(Mono.error(new ProductNotFoundException(productid)))
			    .log();
		return Mono.just(new ResponseEntity<String>("successfully deleted product by id : "+productid,HttpStatus.OK)); 
	}

	@DeleteMapping("/deleteProducts") 
	public Mono<ResponseEntity<String>> deleteAll() {
		prodrepo.deleteAll()
			    .switchIfEmpty(Mono.error(new ProductNotFoundException("all")))
			    .log();
		return Mono.just(new ResponseEntity<String>("all products successfully deleted",HttpStatus.OK));
	}
      }
