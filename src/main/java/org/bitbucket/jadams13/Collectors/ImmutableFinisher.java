package org.bitbucket.jadams13.Collectors;

import java.util.function.Function;

/**
 * Created by Joe on 9/1/2015.
 */
public interface ImmutableFinisher<A,R> extends Finisher<A,R> {
    default Function<A,R> get(){
        return this::finisherImpl;
    }

    R finisherImpl(A a);
}
