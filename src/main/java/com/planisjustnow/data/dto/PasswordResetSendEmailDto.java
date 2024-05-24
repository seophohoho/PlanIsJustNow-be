package com.planisjustnow.data.dto;

public class PasswordResetSendEmailDto {
        String userId; // user_id -> userId로 수정 (변수이름수정)
        String email;

        // Getter, Setter 메서드
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


}
