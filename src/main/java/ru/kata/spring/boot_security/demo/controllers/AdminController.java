package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String getUser(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    @GetMapping("/new")
    public String createNewUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.getListRoles());
        return "new";
    }

    @PostMapping("/user")
    public String create(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model, Model roles) {
        roles.addAttribute("listRoles", roleService.getListRoles());
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/user/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.updateUser(id, user);
        return "redirect:/admin/user";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/user";
    }
}
