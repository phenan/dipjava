package com.phenan.service;

import com.phenan.service_task.RepositoryTask;
import com.phenan.service_task.ServiceTask;

import java.util.Optional;

public class UserNameFetchService implements Service<Integer, Optional<String>> {
    @Override
    public ServiceTask<Optional<String>> run(Integer userId) {
        return RepositoryTask.UserRepository.select(userId).map((userOpt) -> userOpt.map(user -> user.name));
    }
}
