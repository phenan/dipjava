package com.phenan.repository;

import com.phenan.repository_request.RepositoryRequest;
import com.phenan.repository_request.UserRepositoryRequest;
import com.phenan.util.Future;
import com.phenan.util.Applied;

public class Repositories implements RepositoryRequest.RequestDispatcher<Future.__> {
    private final UserRepository userRepository;

    public Repositories() {
        this.userRepository = new UserRepository();
    }

    @Override
    public <T> Applied<Future.__, T> dispatch(RepositoryRequest<T> request) {
        return request.dispatchRepository(this);
    }

    @Override
    public <T> Applied<Future.__, T> dispatchRepository(UserRepositoryRequest<T> request) {
        return request.dispatchRequest(userRepository);
    }
}
