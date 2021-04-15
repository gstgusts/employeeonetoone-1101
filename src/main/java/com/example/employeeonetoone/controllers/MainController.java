package com.example.employeeonetoone.controllers;

import com.example.employeeonetoone.data.Address;
import com.example.employeeonetoone.data.Employee;
import com.example.employeeonetoone.data.EmployeeRepository;
import com.example.employeeonetoone.models.EmployeeAddressModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping("")
    public String getEmployees(Model model) {
        var repo = new EmployeeRepository();

        model.addAttribute("employees", repo.getEmployees());
        model.addAttribute("confirmDelete", 0);

        return "employee_list";
    }

    @GetMapping("/confirm/{id}")
    public String confirmDelete(@PathVariable int id, Model model) {
        var repo = new EmployeeRepository();

        model.addAttribute("employees", repo.getEmployees());
        model.addAttribute("confirmDelete", id);

        return "employee_list";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteItem(@PathVariable int id) {
        var repo = new EmployeeRepository();

        var dataToDelete = repo.getEmployeeById(id);

        if(dataToDelete != null) {
            repo.delete(dataToDelete);
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/add")
    public String addEmployee(Model model) {

       var repo = new EmployeeRepository();

        model.addAttribute("addresses", repo.getAddresses());
        return "employees_addedit";
    }

    @PostMapping("/add")
    public ModelAndView saveEmployee(@ModelAttribute("EmployeeAddressModel") EmployeeAddressModel data) {

        var repo = new EmployeeRepository();

        var employee = new Employee(0, data.getFirstName(), data.getLastName(), data.getSalary(), null);

        Address address = null;

        if(data.getUseExistingAddress() && data.getAddressId() != 0) {
            address = repo.getById(data.getAddressId());
        } else {
            address = new Address(0, data.getStreet(), data.getCity(), data.getState(), data.getZip());

            repo.save(address);
        }

        employee.setAddress(address);

        repo.save(employee);

        return new ModelAndView("redirect:/");
    }
}
