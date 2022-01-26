package com.phenan.service_task;

import com.phenan.model.User;
import com.phenan.repository_request.UserRepositoryRequest;

import java.util.Optional;

public class RepositoryTask {
    public static class UserRepository {
        public static ServiceTask<Optional<User>> select(int userId) {
            return new ServiceTask.Process<>(new UserRepositoryRequest.SelectUserRequest(userId));
        }
    }
}
