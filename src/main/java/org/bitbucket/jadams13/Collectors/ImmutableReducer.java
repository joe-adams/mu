package org.bitbucket.jadams13.Collectors;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * Created by Joe on 9/1/2015.
 */
public interface ImmutableReducer<T,A> extends Reducer<T, A>{

    default BiConsumer<A, T> accumulator(){
        return this::accumulatorImpl;
    }

    default Supplier<A> supplier(){
        return this::supplierImpl;
    }

    default BinaryOperator<A> combiner(){
        return this::combinerImpl;
    }

    void accumulatorImpl(A a,T t);

    A supplierImpl();

    A combinerImpl(A a1,A a2);

}
