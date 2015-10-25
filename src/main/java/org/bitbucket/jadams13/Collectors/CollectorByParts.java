package org.bitbucket.jadams13.Collectors;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by Joe on 9/1/2015.
 */
public class CollectorByParts<T,A,R> implements Collector<T,A,R> {

    final private Reducer<T,A> reducer;
    final private Finisher<A,R> finisher;
    final private Set<Collector.Characteristics> characteristics;

    public CollectorByParts(Reducer<T, A> reducer, Finisher<A, R> finisher,CharacteristicsDescriber extraChacertisticsDescriber) {
        this.reducer = reducer;
        this.finisher = finisher;
        characteristics=CharacteristicsDescriber.make(reducer,finisher,extraChacertisticsDescriber);
    }

    public CollectorByParts(Reducer<T, A> reducer, Finisher<A, R> finisher) {
        this(reducer,finisher,CharacteristicsDescriber.NONE);
    }

    public static <T,A> Collector<T,A,A> collectorWithIdentityFinish(Reducer<T,A> reducer){
        return new CollectorByParts<T, A, A>(reducer,IdentityFinisher.finisher());
    }

    @Override
    public Supplier<A> supplier() {
        return reducer.supplier();
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return reducer.accumulator();
    }

    @Override
    public BinaryOperator<A> combiner() {
        return reducer.combiner();
    }

    @Override
    public Function<A, R> finisher() {
        return finisher.get();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return characteristics;
    }
}
