package org.yaremax.springsecurityjwtpractice.auth.records;

public record LoginRequest(String email,
                           String password) {
}
