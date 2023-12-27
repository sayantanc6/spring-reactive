package dummy.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import dummy.document.Product;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>{

}
