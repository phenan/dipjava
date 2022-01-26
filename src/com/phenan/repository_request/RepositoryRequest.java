package com.phenan.repository_request;

import com.phenan.util.HigherKind;
import com.phenan.util.Applied;

public interface RepositoryRequest<T> {
    <H extends HigherKind> Applied<H, T> dispatchRepository(RequestDispatcher<H> router);

    interface RequestDispatcher<H extends HigherKind> {
        <T> Applied<H, T> dispatch(RepositoryRequest<T> request);
        <T> Applied<H, T> dispatchRepository(UserRepositoryRequest<T> request);
    }
}
