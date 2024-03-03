package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository; //Importing the JpaRepository interface

import java.util.List; //Importing the List interface

// extend JpaRepository to handle User and ID
public interface UserRepository extends JpaRepository<User, Long> 
{
    //finding users by name from the users entered string with ignoring case
    List<User> findByNameContainingIgnoreCase(String name);
}
