package hu.bankblaze.bankblaze.controller;

import hu.bankblaze.bankblaze.model.Employee;
import hu.bankblaze.bankblaze.model.Permission;
import hu.bankblaze.bankblaze.service.AdminService;
import hu.bankblaze.bankblaze.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;


    private PermissionService permissionService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAllClerks(Model model) {
        model.addAttribute("employees", adminService.getAllClerks());
        model.addAttribute("admins", adminService.getAllAdmins());
        return "admin";
    }

    @PostMapping("/update")
    public String getAllClerks(@RequestParam("employeeId") Long id,
                               @RequestParam("retailCheckbox") Boolean forRetail,
                               @RequestParam("corporateCheckbox") Boolean forCorporate,
                               @RequestParam("tellerCheckbox") Boolean forTeller,
                               @RequestParam("premiumCheckbox") Boolean forPremium) {
        permissionService.modifyForRetail(id, forRetail);
        permissionService.modifyForCorporate(id, forCorporate);
        permissionService.modifyForTellers(id, forTeller);
        permissionService.modifyForPremium(id, forPremium);
        return "redirect:/admin";
    }

    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        model.addAttribute("admins", adminService.getAllAdmins());
        return "statistics";
    }

    @GetMapping("/desk")
    public String setDesks(Model model) {
        model.addAttribute("admins", adminService.getAllAdmins());
        return "desk";
    }

    @PostMapping("/desk")
    public String setDesks(@RequestParam("employeeId") Long id) {
        return "redirect:/admin/desk";
    }

    @GetMapping("/registration")
    public String createEmployee(Model model) {
        model.addAttribute("admins", adminService.getAllAdmins());
        model.addAttribute("newEmployee", new Employee());
        return "registration";
    }

    @PostMapping("/registration")
    public String createEmployee(@ModelAttribute("newEmployee") Employee employee,
                                 @RequestParam("defaultRole") String defaultRole) {
        employee.setRole(String.valueOf(defaultRole));
        adminService.saveAdmin(employee);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdminById(id);
        return "redirect:/admin";
    }

    @PostMapping("update/{id}")
    public String updatePermission(@PathVariable("id") Integer id, @ModelAttribute("permission") Permission update) {
        permissionService.savePermisson(update);
        return "redirect:/admin/" + id;
    }


}