package com.unsky.myblog.pojo;

/**
 * @author UNSKY
 * @date 2022/3/14 20:16
 */
public class AdminUser {
    private Integer adminUserId;

    private String loginUserName;

    private String loginPassword;

    private String nickName;

    private Byte locked;

    public AdminUser(Integer adminUserId, String loginUserName, String loginPassword, String nickName, Byte locked) {
        this.adminUserId = adminUserId;
        this.loginUserName = loginUserName;
        this.loginPassword = loginPassword;
        this.nickName = nickName;
        this.locked = locked;
    }

    public AdminUser() {
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "adminUserId=" + adminUserId +
                ", loginUserName='" + loginUserName + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", nickName='" + nickName + '\'' +
                ", locked=" + locked +
                '}';
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Byte getLocked() {
        return locked;
    }

    public void setLocked(Byte locked) {
        this.locked = locked;
    }
}
