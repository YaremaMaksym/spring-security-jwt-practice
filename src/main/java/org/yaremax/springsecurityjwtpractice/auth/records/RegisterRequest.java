package org.yaremax.springsecurityjwtpractice.auth.records;

public record RegisterRequest(String firstName,
                              String lastName,
                              String email,
                              String password) {
}
