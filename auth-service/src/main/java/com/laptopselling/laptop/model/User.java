package com.laptopselling.laptop.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "user-security")
@Data
public class User {
    @Id
    private Long id;
    private String username;
    private String password;

    private String fullName;
    private String address;
    private String email;
    private String phoneNumber;
    private int inBlackList = 0;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date birthDay;
    private Boolean gender; // true: male, false: female
    private String role; // [user / transportStaff / productAdmin/ orderAdmin / webAdmin / superAdmin]

    private String employeeCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date createdDate;
    private int year;
    private int month;
    private int day;

}
