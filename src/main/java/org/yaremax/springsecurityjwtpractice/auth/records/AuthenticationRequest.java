package org.yaremax.springsecurityjwtpractice.auth.records;

public record AuthenticationRequest(String email,
                                    String password) {
}
