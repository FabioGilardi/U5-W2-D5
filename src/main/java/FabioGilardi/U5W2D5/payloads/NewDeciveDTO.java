package FabioGilardi.U5W2D5.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewDeciveDTO(
        @NotEmpty(message = "type is mandatory")
        String type
) {
}
