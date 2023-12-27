package dummy.document;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

	/* 
	 * Mongo doesn't support and skip auto-increment logic with any other 
	 * database because of 
	 * slower performance
	 * memory consumption (extra storage of value) 
	 * sharding(database partition) 
	 * So as per acording to business requirement it's a developer's responsibility to make sure that PK is unique,
	 * for example we can use emailID, Timestamp, UUID or other custom logic adhering to business requirement.
	 * This is just for demo purpose.
	 * 
	 * Additional information,If you want to use a Long value then you need to assign the id field before saving!
	 * If you don't set the Long value for the @Id field then the server will create an ObjectId for the field, 
	 * and it is not compatible with the Long datatype declared in your class. 
	 * So when you read the document/entity it will complain about the data type conversion error.
	 * 
	 * */
	
	@NotNull
//	@Id // cumbersome due to the implicit ObjectId conversion
	@MongoId // no conversion attempts to any other type are made
	@Pattern(regexp = "^([0-9]|[1-9][0-9]|100)$",message = "must be between 1 to 100")
	private String productid;
	
	@NotNull
	private String name;
	
	@NotNull
	private double price;
	
	@NotNull
	private int quantity;
}
