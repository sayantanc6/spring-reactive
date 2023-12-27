package dummy.model;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Component
@Data
public class ProductModel {

	@NotNull
	@Pattern(regexp = "^([0-9]|[1-9][0-9]|100)$",message = "must be between 1 to 100")
	private String productid;
	
	@NotNull
	private String name;
	
	@NotNull
	private double price;
	
	@NotNull
	private int quantity;
}
