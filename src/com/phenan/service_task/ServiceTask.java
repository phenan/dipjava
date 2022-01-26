package com.phenan.service_task;

import com.phenan.repository_request.RepositoryRequest;
import com.phenan.util.HigherKind;
import com.phenan.util.Applied;
import com.phenan.util.Monad;

import java.util.function.Function;

public interface ServiceTask<R> {
    <H extends HigherKind> Applied<H, R> run(RepositoryRequest.RequestDispatcher<H> dispatcher, Monad<H> monad);

    default <U> ServiceTask<U> flatMap(Function<R, ServiceTask<U>> function) {
        return new FlatMapped<>(this, function);
    }

    default <U> ServiceTask<U> map(Function<R, U> function) {
        return new FlatMapped<>(this, (r) -> new Return<>(function.apply(r)));
    }

    class Return<R> implements ServiceTask<R> {
        final R value;

        public Return(R value) {
            this.value = value;
        }

        public <H extends HigherKind> Applied<H, R> run(RepositoryRequest.RequestDispatcher<H> dispatcher, Monad<H> monad) {
            return monad.pure(value);
        }
    }

    class Process<T> implements ServiceTask<T> {
        final RepositoryRequest<T> request;

        public Process(RepositoryRequest<T> request) {
            this.request = request;
        }

        @Override
        public <H extends HigherKind> Applied<H, T> run(RepositoryRequest.RequestDispatcher<H> dispatcher, Monad<H> monad) {
            return dispatcher.dispatch(request);
        }
    }

    class FlatMapped<T, R> implements ServiceTask<R> {
        final ServiceTask<T> serviceTask;
        final Function<T, ServiceTask<R>> continuation;

        public FlatMapped(ServiceTask<T> serviceTask, Function<T, ServiceTask<R>> continuation) {
            this.serviceTask = serviceTask;
            this.continuation = continuation;
        }

        @Override
        public <H extends HigherKind> Applied<H, R> run(RepositoryRequest.RequestDispatcher<H> dispatcher, Monad<H> monad) {
            return monad.<T, R>flatMap((t) -> continuation.apply(t).run(dispatcher, monad)).apply(serviceTask.run(dispatcher, monad));
        }
    }
}
