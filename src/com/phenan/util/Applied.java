package com.phenan.util;

public interface Applied<Witness extends HigherKind, T> {
    // FutureH<T> <: Applied<FutureH.__, T> なので、これは安全ではない。
    // これが可能であるのは Higher<FutureH.__, T> の実装が FutureH<T> のみであるため。
    // Java で Higher kinded type をエミュレーションする都合上仕方ない制限か。
    static <T> Future<T> toFuture(Applied<Future.__, T> applied) {
        return (Future<T>) applied;
    }
}
