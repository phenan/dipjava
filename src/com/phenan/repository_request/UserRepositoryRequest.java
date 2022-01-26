package com.phenan.repository_request;

import com.phenan.model.User;
import com.phenan.util.HigherKind;
import com.phenan.util.Applied;

import java.util.Collection;
import java.util.Optional;

public interface UserRepositoryRequest<T> extends RepositoryRequest<T> {
    default <H extends HigherKind> Applied<H, T> dispatchRepository(RequestDispatcher<H> dispatcher) {
        return dispatcher.dispatchRepository(this);
    }

    <H extends HigherKind> Applied<H, T> dispatchRequest(Handler<H> handler);

    interface Handler<H extends HigherKind> {
        Applied<H, Optional<User>> handle(SelectUserRequest selectUserRequest);
        Applied<H, Collection<User>> handle(SelectAllUserRequest selectAllUserRequest);
    }

    class SelectUserRequest implements UserRepositoryRequest<Optional<User>> {
        public final int id;

        public SelectUserRequest(int id) {
            this.id = id;
        }

        public <H extends HigherKind> Applied<H, Optional<User>> dispatchRequest(Handler<H> handler) {
            return handler.handle(this);
        }
    }

    class SelectAllUserRequest implements UserRepositoryRequest<Collection<User>> {
        private SelectAllUserRequest() {}

        public <H extends HigherKind> Applied<H, Collection<User>> dispatchRequest(Handler<H> handler) {
            return handler.handle(this);
        }
    }
}
