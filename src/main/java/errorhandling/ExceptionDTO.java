package errorhandling;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "Exeption", description = "Message containing error code and description of error.")
public class ExceptionDTO {

    public ExceptionDTO(int code, String description) {
        this.code = code;
        this.message = description;
    }
    private int code;
    private String message;

    @Schema(description = "http Error Code")
    public int getCode() {
        return code;
    }

    @Schema(description = "ErrorMsg")
    public String getMessage() {
        return message;
    }

}
