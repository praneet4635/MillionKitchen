package com.t9l.millionkitchen.dao;

import java.io.Serializable;

public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int userId;
    private String userLandmark;
    private String username;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private String password;
    private String dob;
    private String phoneNumber;
    private String remoteImage;
    private String localImage;
    private String deviceId;
    private String deviceType;
    private String FbUserId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserLandmark() {
        return userLandmark;
    }

    public void setUserLandmark(String userLandmark) {
        this.userLandmark = userLandmark;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRemoteImage() {
        return remoteImage;
    }

    public void setRemoteImage(String remoteImage) {
        this.remoteImage = remoteImage;
    }

    public String getLocalImage() {
        return localImage;
    }

    public void setLocalImage(String localImage) {
        this.localImage = localImage;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getFbUserId() {
        return FbUserId;
    }

    public void setFbUserId(String fbUserId) {
        FbUserId = fbUserId;
    }
}