package org.bitbucket.jadams13.util;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by Joe on 8/23/2015.
 */
public class MuCollecterImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        public static <T, A, R> Builder<T, A, R> builder(){
            return new Builder<>();
        }

        private MuCollecterImpl(Builder<T,A,R> builder) {
            this.supplier = builder.supplier;
            this.accumulator = builder.accumulator;
            this.combiner = builder.combiner;
            this.finisher = builder.finisher;
            this.characteristics = builder.characteristics;
        }

        public static class Builder<T, A, R>{
            private Supplier<A> supplier;
            private BiConsumer<A, T> accumulator;
            private BinaryOperator<A> combiner;
            private Function<A, R> finisher;
            private Set<Characteristics> characteristics= EnumSet.noneOf(Characteristics.class);

            public Builder<T, A, R> setSupplier(Supplier<A> supplier) {
                this.supplier = supplier;
                return this;
            }

            public Builder<T, A, R> setAccumulator(BiConsumer<A, T> accumulator) {
                this.accumulator = accumulator;
                return this;
            }

            public Builder<T, A, R> setCombiner(BinaryOperator<A> combiner) {
                this.combiner = combiner;
                return this;
            }

            public Builder<T, A, R> setFinisher(Function<A, R> finisher) {
                this.finisher = finisher;
                return this;
            }

            public Builder<T, A, R> setCharacteristics(Set<Characteristics> characteristics) {
                this.characteristics = characteristics;
                return this;
            }

            public Builder<T, A, R> addCharacteristic(Characteristics characteristic) {
                this.characteristics.add(characteristic);
                return this;
            }

            public void clearCharacteristics(){
                this.characteristics= EnumSet.noneOf(Characteristics.class);
            }

            public MuCollecterImpl<T, A, R> build(){
                return new MuCollecterImpl<>(this);
            }
        }

        public BiConsumer<A, T> accumulator() {
            return this.accumulator;
        }

        public Supplier<A> supplier() {
            return this.supplier;
        }

        public BinaryOperator<A> combiner() {
            return this.combiner;
        }

        public Function<A, R> finisher() {
            return this.finisher;
        }

        public Set<Characteristics> characteristics() {
            return this.characteristics;
        }

}
