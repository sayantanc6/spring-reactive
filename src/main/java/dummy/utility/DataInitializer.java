package dummy.utility;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import dummy.document.Product;
import dummy.repo.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
 
	@Autowired
	ProductRepository prodrepo;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) { 
		
		List<Product> products = IntStream.range(0, 100).boxed()
				// PK is to be mentioned as a unique value, otherwise it'll auto generate ObjectId with unique ID.
				//		.map(i -> Product.builder().name("product"+i).price(Math.random()).quantity(i).build())
						.map(i -> Product.builder().productid(i.toString()).name("product"+i).price(Math.random()).quantity(i).build())
						.collect(Collectors.toList()); 
		 
		prodrepo.deleteAll()
			     .thenMany(Flux.just(products).flatMap(p -> this.prodrepo.saveAll(p))) 
			     .log().subscribe(null,null,() -> log.info("initialization done...")); 
	}
}
