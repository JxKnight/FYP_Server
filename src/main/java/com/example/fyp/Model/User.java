package com.example.fyp.Model;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_firstname")
    private String firstName;
    @Column(name = "user_lastname")
    private String lastName;
    @Column(name = "user_ic", unique = true)
    private String userIc;
    @Column(name = "user_contact")
    private String userContact;
    @Column(name = "user_address")
    private String userAddress;
    @Column(name = "user_Role")
    private String userRole;
    @Column(name = "user_PostCode")
    private String userPostCode;
    @Column(name = "user_State")
    private String userState;
    @Column(name = "user_pic")
    private String userPic;
    @Column(name = "user_firstEntry")
    private String userFirstEntry;

    public User() {
    }

    public User(String userIc) {
        this.userIc = userIc;
    }

    public User(String userPassword, String firstName, String lastName, String userIc, String userContact, String userAddress, String userRole, String userPostCode, String userState, String userPic) {
        this.userPassword = userPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userIc = userIc;
        this.userContact = userContact;
        this.userAddress = userAddress;
        this.userRole = userRole;
        this.userPostCode = userPostCode;
        this.userState = userState;
        this.userPic = userPic;
    }

    public User(String userPassword, String userIc, String userContact, String userFirstEntry,String userRole) {
        this.userPassword = userPassword;
        this.userIc = userIc;
        this.userContact = userContact;
        this.userFirstEntry = userFirstEntry;
        this.userRole = userRole;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserPostCode() {
        return userPostCode;
    }

    public void setUserPostCode(String userPostCode) {
        this.userPostCode = userPostCode;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserFirstEntry() {
        return userFirstEntry;
    }

    public void setUserFirstEntry(String userFirstEntry) {
        this.userFirstEntry = userFirstEntry;
    }
}