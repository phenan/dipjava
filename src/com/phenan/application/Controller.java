package com.phenan.application;

import com.phenan.repository.Repositories;
import com.phenan.service.Service;
import com.phenan.service.UserNameFetchService;
import com.phenan.util.Applied;
import com.phenan.util.Future;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Controller {
    private final Repositories repositories;
    private final UserNameFetchService userNameFetchService;

    public Controller() {
        repositories = new Repositories();
        userNameFetchService = new UserNameFetchService();
    }

    public CompletableFuture<Optional<String>> fetchUserName(int userId) {
        return runService(userNameFetchService, userId);
    }

    private <In, Out> CompletableFuture<Out> runService(Service<In, Out> service, In in) {
        return Applied.toFuture(service.run(in).run(repositories, Future.monad)).instance;
    }
}
