package FabioGilardi.U5W2D5.services;

import FabioGilardi.U5W2D5.entities.Device;
import FabioGilardi.U5W2D5.exceptions.BadRequestException;
import FabioGilardi.U5W2D5.exceptions.NotFoundException;
import FabioGilardi.U5W2D5.payloads.DeviceDTO;
import FabioGilardi.U5W2D5.repositories.DeviceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class DeviceService {

    @Autowired
    private DeviceDAO deviceDAO;

    public Page<Device> findAll(int number, int size, String sortBY) {
        Pageable pageable = PageRequest.of(number, size, Sort.by(sortBY));
        return deviceDAO.findAll(pageable);
    }

    public Device save(DeviceDTO payload) {
        if (!payload.status().toLowerCase().equals("assigned")) {
            Device newDevice = new Device(payload.type(), payload.status());
            return deviceDAO.save(newDevice);
        } else throw new BadRequestException("Device cannot be created as assigned in type");
    }

    public Device findById(long id) {
        return deviceDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Device findByIdAndUpdate(long id, DeviceDTO payload) {
        if (!payload.status().toLowerCase().equals("assigned")) {
            Device found = this.findById(id);
            found.setType(payload.type());
            found.setStatus(payload.status());
            deviceDAO.save(found);
            return found;
        } else throw new BadRequestException("Device cannot be created as assigned in type");
    }

    public void findByIdAndDelete(long id) {
        Device found = this.findById(id);
        deviceDAO.delete(found);
    }
}
