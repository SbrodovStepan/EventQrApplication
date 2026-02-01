package com.company.eventqrapplication.view.eventrequest;

import com.company.eventqrapplication.entity.User;

public class UserSelection {
    private User user;
    private boolean selected;
    private byte[] qrCode;

    public UserSelection(User user, boolean selected) {
        this.user = user;
        this.selected = selected;
    }

    // геттеры и сеттеры
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public String getLastName() {
        return user != null ? user.getLastName() : "";
    }

    public String getFirstName() {
        return user != null ? user.getFirstName() : "";
    }

    public String getMiddleName() {
        return user != null ? user.getMiddleName() : "";
    }

    public String getDepartment() {
        return user != null ? user.getDepartment() : "";
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }
}
