package dummy.exception;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ErrorInfo {

	private String errorMessage;
    private Integer errorCode;
    private LocalDateTime timestamp;
}
