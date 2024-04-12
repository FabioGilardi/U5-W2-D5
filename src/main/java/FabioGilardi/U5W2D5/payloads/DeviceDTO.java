package FabioGilardi.U5W2D5.payloads;

import jakarta.validation.constraints.NotEmpty;

public record DeviceDTO(
        @NotEmpty(message = "status is mandatory")
        String status
) {
}
