package com.planisjustnow.data.dto;

public class FriendDto {
    String from;
    String to;
    Integer is_friend;

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public Integer getIs_friend() { return is_friend; }

    public void setFrom(String from) { this.from = from; }
    public void setTo(String to) { this.to = to; }
    public void setIs_friend(Integer is_friend) { this.is_friend = is_friend; }
}
