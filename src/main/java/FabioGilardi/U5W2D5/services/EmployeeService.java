package FabioGilardi.U5W2D5.services;

import FabioGilardi.U5W2D5.entities.Employee;
import FabioGilardi.U5W2D5.exceptions.BadRequestException;
import FabioGilardi.U5W2D5.exceptions.NotFoundException;
import FabioGilardi.U5W2D5.payloads.EmployeeDTO;
import FabioGilardi.U5W2D5.repositories.EmployeeDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EmployeeService {

    @Autowired
    private Cloudinary cloudinaryUploader;
    @Autowired
    private EmployeeDAO employeeDAO;

    public Page<Employee> findAll(int number, int size, String sortBY) {
        Pageable pageable = PageRequest.of(number, size, Sort.by(sortBY));
        return employeeDAO.findAll(pageable);
    }

    public Employee save(EmployeeDTO payload) {
        if (!employeeDAO.existsByEmail(payload.email()) && !employeeDAO.existsByUsername(payload.username())) {
            Employee newEmployee = new Employee(payload.username(), payload.name(), payload.surname(), payload.email());
            return employeeDAO.save(newEmployee);
        } else throw new BadRequestException("email/username has been already taken");
    }

    public Employee findById(long id) {
        return employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Employee findByIdAndUpdate(long id, EmployeeDTO payload) {
        Employee found = this.findById(id);
        found.setUsername(payload.username());
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());
        if (!found.getAvatar().contains("cloudinary")) found.setDeafaultAvatar();
        employeeDAO.save(found);
        return found;
    }

    public void findByIdAndDelete(long id) {
        Employee found = this.findById(id);
        employeeDAO.delete(found);
    }

    public Employee uploadImage(MultipartFile img, long blogPostId) throws IOException {
        Employee found = findById(blogPostId);
        String url = (String) cloudinaryUploader.uploader().upload(img.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        employeeDAO.save(found);
        return found;
    }
}
