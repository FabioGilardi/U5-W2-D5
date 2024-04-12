package FabioGilardi.U5W2D5.payloads;

import jakarta.validation.constraints.Min;

public record DeviceAssignementDTO(
        @Min(value = 1, message = "employee id is mandatory")
        long employeeId
) {
}
