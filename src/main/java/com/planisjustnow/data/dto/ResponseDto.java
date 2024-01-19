package com.planisjustnow.data.dto;

public class ResponseDto {
    String messageTitle;
    String messageDetail;

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
    public ResponseDto(String messageTitle,String messageDetail){
        this.messageTitle = messageTitle;
        this.messageDetail = messageDetail;
    }
}
