package id.ac.ui.cs.advprog.cart.service;

import id.ac.ui.cs.advprog.cart.dto.UserResponse;

import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<UserResponse> getUsernameWithToken(String token);
    CompletableFuture<Boolean> isAdmin(String token);
}
