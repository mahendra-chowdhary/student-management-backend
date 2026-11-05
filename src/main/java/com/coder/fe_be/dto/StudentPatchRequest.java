
package com.coder.fe_be.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class StudentPatchRequest {

    @Size(min = 3, max = 50,
          message = "Name must contain 3 to 50 characters")
    private String name;

    @Email(message = "Please enter a valid email address")
    private String email;

    @Size(min = 2, max = 50,
          message = "Course must contain 2 to 50 characters")
    private String course;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
