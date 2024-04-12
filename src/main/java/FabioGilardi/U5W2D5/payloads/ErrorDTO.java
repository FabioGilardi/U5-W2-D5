package FabioGilardi.U5W2D5.payloads;

import java.time.LocalDateTime;

public record ErrorDTO(
        String message,
        LocalDateTime timestamp) {
}
