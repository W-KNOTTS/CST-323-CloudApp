package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Marks this class as a web controller, capable of handling requests.
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); // Creates a logger for this class.
    private final UserRepository userRepository; // Injects the UserRepository for database operations.

    //initializes the controller with UserRepository.
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.debug("UserController initialized"); // Logs initialization.
    }

    // Handles requests to the "/all" path to list all users.
    @GetMapping("/all")
    public String listUsers(Model model) {
        logger.debug("Handling request to list all users"); // Log the action of listing all.
        List<User> users = userRepository.findAll(); // get all users from the database.
        model.addAttribute("users", users); // Adds users to the model to be displayed.
        return "users"; // Returns the view.
    }

    //request to search users by name.
    @GetMapping("/search")
    public String searchUsers(@RequestParam("name") String name, Model model) {
        logger.debug("Handling search for users with name: {}", name); // Log the action for search.
        List<User> searchResults = userRepository.findByNameContainingIgnoreCase(name); // Searches users by name.
        model.addAttribute("users", searchResults); // Adds search results to the model.
        return "users"; // Returns the "users" view.
    }

    // add a new user.
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        logger.debug("Showing form to add a new user"); // Log the action of adding user.
        User user = new User(); // create empty user object.
        model.addAttribute("user", user); // Add the empty user to the model.
        return "add-user"; // Returns add-user view.
    }

    //form submission to add a new user.
    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        logger.debug("Adding a new user: {}", user); // Log the action.
        userRepository.save(user); // save the user to the database.
        return "redirect:/all"; // Redirects to the list users page.
    }

    //update an existing user.
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        logger.debug("Showing form to update user with id: {}", id); // Log the action of the update.
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)); // gets user or exception if not found.
        model.addAttribute("user", user); // Adds the user to the model.
        return "update"; // Returns view.
    }

    //form submission to update an existing user.
    @PostMapping("/update")
    public String updateUser(User user) {
        logger.debug("Updating user: {}", user); // Log the action.
        userRepository.save(user); // Saves the updated user to the database.
        return "redirect:/all"; // redirects to the list users page.
    }

    //request to delete an existing user.
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        logger.debug("Deleting user with id: {}", id); // Log the action.
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)); // Fetches the user or throws exception if not found.
        userRepository.delete(user); // Deletes the user from the database.
        return "redirect:/all"; // Redirects to the list users page.
    }
}
