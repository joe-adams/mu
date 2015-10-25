package org.bitbucket.jadams13.Collectors;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by Joe on 8/31/2015.
 */
public interface Reducer<T, A> extends CharacteristicsDescriber {

    BiConsumer<A, T> accumulator();

    Supplier<A> supplier();

    BinaryOperator<A> combiner();


}
