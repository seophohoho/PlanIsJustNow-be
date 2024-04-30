package com.planisjustnow.data.dto;

public class ResponseDto {
    String messageTitle;
    String messageDetail;
    Object data;
    Object userInfo;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    public Object getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Object userInfo) {
        this.userInfo = userInfo;
    }

    public ResponseDto(String messageTitle, String messageDetail, Object data,Object userInfo){
        this.messageTitle = messageTitle;
        this.messageDetail = messageDetail;
        this.data = data;
        this.userInfo = userInfo;
    }
}