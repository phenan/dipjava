package com.phenan.repository;

import com.phenan.model.User;
import com.phenan.repository_request.UserRepositoryRequest;
import com.phenan.util.Future;
import com.phenan.util.Applied;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class UserRepository implements UserRepositoryRequest.Handler<Future.__> {

    private final Map<Integer, User> userMap;

    public UserRepository() {
        userMap = new HashMap<>();
        userMap.put(1, new User(1, "user name"));
    }

    @Override
    public Applied<Future.__, Optional<User>> handle(UserRepositoryRequest.SelectUserRequest selectUserRequest) {
        if (userMap.containsKey(selectUserRequest.id)) {
            return new Future<>(CompletableFuture.supplyAsync(() -> Optional.of(userMap.get(selectUserRequest.id))));
        } else {
            return new Future<>(CompletableFuture.completedFuture(Optional.empty()));
        }
    }

    @Override
    public Applied<Future.__, Collection<User>> handle(UserRepositoryRequest.SelectAllUserRequest selectAllUserRequest) {
        return new Future<>(CompletableFuture.supplyAsync(userMap::values));
    }
}
