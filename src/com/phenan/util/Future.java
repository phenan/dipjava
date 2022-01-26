package com.phenan.util;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class Future<T> implements Applied<Future.__, T> {
    public interface __ extends HigherKind {}

    public Future(CompletableFuture<T> instance) {
        this.instance = instance;
    }

    public final CompletableFuture<T> instance;

    public static Monad<Future.__> monad = new Monad<>() {
        @Override
        public <A> Applied<__, A> pure(A value) {
            return new Future<>(CompletableFuture.completedFuture(value));
        }
        @Override
        public <A, B> Function<Applied<__, A>, Applied<__, B>> flatMap(Function<A, Applied<__, B>> function) {
            return (higher) -> new Future<>(Applied.toFuture(higher).instance.thenCompose((a) -> Applied.toFuture(function.apply(a)).instance));
        }
        @Override
        public <A, B> Function <Applied<__, A>, Applied<__, B>> map(Function<A, B> function) {
            return (higher) -> new Future<>(Applied.toFuture(higher).instance.thenApplyAsync(function));
        }
    };
}
