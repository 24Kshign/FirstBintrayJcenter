package com.mifengkong.frtools.app;


import com.mifengkong.frtools.util.FRDebugMode;

/**
 *
 */
public class FRServletAddress {

    private String onlineAddress;   //线上地址
    private String onlineAddressRelease; //预发
    private String offlineAddress;  //线下地址

    private String cashAdvanceOnlineAddress;     //卡猫线上地址
    private String cashAdvanceOnlineAddressRelease; //预发
    private String cashAdvanceOfflineAddress;   //卡猫线下地址

    private String mocoAddress;

    public String getMocoAddress() {
        return mocoAddress;
    }

    public void setMocoAddress(String mocoAddress) {
        this.mocoAddress = mocoAddress;
    }

    private static final class SingletonHolder {
        private static final FRServletAddress INSTANCE = new FRServletAddress();
    }

    public static FRServletAddress getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private FRServletAddress() {
    }

    public String getOnlineAddress() {
        return onlineAddress;
    }

    public void setOnlineAddress(String onlineAddress) {
        this.onlineAddress = onlineAddress;
    }

    public String getOfflineAddress() {
        return offlineAddress;
    }


    public void setOfflineAddress(String offlineAddress) {
        this.offlineAddress = offlineAddress;
    }

    public String getCashAdvanceOnlineAddress() {
        return cashAdvanceOnlineAddress;
    }

    public void setCashAdvanceOnlineAddress(String cashAdvanceOnlineAddress) {
        this.cashAdvanceOnlineAddress = cashAdvanceOnlineAddress;
    }

    public String getCashAdvanceOfflineAddress() {
        return cashAdvanceOfflineAddress;
    }

    public void setCashAdvanceOfflineAddress(String cashAdvanceOfflineAddress) {
        this.cashAdvanceOfflineAddress = cashAdvanceOfflineAddress;
    }

    public String getOnlineAddressRelease() {
        return onlineAddressRelease;
    }

    public void setOnlineAddressRelease(String onlineAddressRelease) {
        this.onlineAddressRelease = onlineAddressRelease;
    }

    public String getCashAdvanceOnlineAddressRelease() {
        return cashAdvanceOnlineAddressRelease;
    }

    public void setCashAdvanceOnlineAddressRelease(String cashAdvanceOnlineAddressRelease) {
        this.cashAdvanceOnlineAddressRelease = cashAdvanceOnlineAddressRelease;
    }

    public String getServletAddress() {
        if (FRDebugMode.isDebugMode()) {
            return offlineAddress;   //测试
        } else if (FRDebugMode.isPreMode()) {
            return onlineAddressRelease;   //预发
        }
        return onlineAddress;   //正式
    }

    public String getCashAdvanceServletAddress() {
        if (FRDebugMode.isDebugMode()) {
            return cashAdvanceOfflineAddress;   //测试
        } else if (FRDebugMode.isPreMode()) {
            return cashAdvanceOnlineAddressRelease;   //预发
        }
        return cashAdvanceOnlineAddress; //正式
    }
}