package com.phenan.util;

import java.util.function.Function;

public interface Monad <F extends HigherKind> {
    <A> Applied<F, A> pure(A value);
    <A, B> Function<Applied<F, A>, Applied<F, B>> flatMap(Function<A, Applied<F, B>> function);

    default <A, B> Function<Applied<F, A>, Applied<F, B>> map(Function<A, B> function) {
        return flatMap(a -> pure(function.apply(a)));
    }
}
