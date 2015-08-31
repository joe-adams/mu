package org.bitbucket.jadams13.util;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Created by Joe on 8/30/2015.
 */
public interface ForwardingStream<T> extends Stream<T> {

    Stream<T> getDelegate();

    <R> ForwardingStream<R> fromDelegate(Stream<R> stream);

    default <R> ForwardingStream<R> mapAsForwardingStream(Function<? super T, ? extends R> function) {
        return fromDelegate(getDelegate().map(function));
    }

    @Override
    default ForwardingStream<T> filter(Predicate<? super T> predicate) {
        return fromDelegate(getDelegate().filter(predicate));
    }

    @Override
    default <R> Stream<R> map(Function<? super T, ? extends R> function) {
        return getDelegate().map(function);
    }

    @Override
    default IntStream mapToInt(ToIntFunction<? super T> toIntFunction) {
        return getDelegate().mapToInt(toIntFunction);
    }

    @Override
    default LongStream mapToLong(ToLongFunction<? super T> toLongFunction) {
        return getDelegate().mapToLong(toLongFunction);
    }

    @Override
    default DoubleStream mapToDouble(ToDoubleFunction<? super T> toDoubleFunction) {
        return getDelegate().mapToDouble(toDoubleFunction);
    }

    @Override
    default <R> ForwardingStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> function) {
        return fromDelegate(getDelegate().flatMap(function));
    }

    @Override
    default IntStream flatMapToInt(Function<? super T, ? extends IntStream> function) {
        return getDelegate().flatMapToInt(function);
    }

    @Override
    default LongStream flatMapToLong(Function<? super T, ? extends LongStream> function) {
        return getDelegate().flatMapToLong(function);
    }

    @Override
    default DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> function) {
        return getDelegate().flatMapToDouble(function);
    }

    @Override
    default ForwardingStream<T> distinct() {
        return fromDelegate(getDelegate().distinct());
    }

    @Override
    default ForwardingStream<T>  sorted() {
        return fromDelegate(getDelegate().sorted());
    }

    @Override
    default ForwardingStream<T> sorted(Comparator<? super T> comparator) {
        return fromDelegate(getDelegate().sorted(comparator));
    }

    @Override
    default ForwardingStream<T> peek(Consumer<? super T> consumer) {
        return fromDelegate(getDelegate().peek(consumer));
    }

    @Override
    default ForwardingStream<T> limit(long l) {
        return fromDelegate(getDelegate().limit(l));
    }

    @Override
    default ForwardingStream<T> skip(long l) {
        return fromDelegate(getDelegate().skip(l));
    }

    @Override
    default void forEach(Consumer<? super T> consumer) {
        getDelegate().forEach(consumer);
    }

    @Override
    default void forEachOrdered(Consumer<? super T> consumer) {
        getDelegate().forEachOrdered(consumer);
    }

    @Override
    default Object[] toArray() {
        return getDelegate().toArray();
    }

    @Override
    default <A> A[] toArray(IntFunction<A[]> intFunction) {
        return getDelegate().toArray(intFunction);
    }

    @Override
    default T reduce(T t, BinaryOperator<T> binaryOperator) {
        return getDelegate().reduce(t, binaryOperator);
    }

    @Override
    default Optional<T> reduce(BinaryOperator<T> binaryOperator) {
        return getDelegate().reduce(binaryOperator);
    }

    @Override
    default <U> U reduce(U u, BiFunction<U, ? super T, U> biFunction, BinaryOperator<U> binaryOperator) {
        return getDelegate().reduce(u, biFunction, binaryOperator);
    }

    @Override
    default <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> biConsumer, BiConsumer<R, R> biConsumer1) {
        return getDelegate().collect(supplier, biConsumer, biConsumer1);
    }

    @Override
    default <R, A> R collect(Collector<? super T, A, R> collector) {
        return getDelegate().collect(collector);
    }

    @Override
    default Optional<T> min(Comparator<? super T> comparator) {
        return getDelegate().min(comparator);
    }

    @Override
    default Optional<T> max(Comparator<? super T> comparator) {
        return getDelegate().max(comparator);
    }

    @Override
    default long count() {
        return getDelegate().count();
    }

    @Override
    default boolean anyMatch(Predicate<? super T> predicate) {
        return getDelegate().anyMatch(predicate);
    }

    @Override
    default boolean allMatch(Predicate<? super T> predicate) {
        return getDelegate().allMatch(predicate);
    }

    @Override
    default boolean noneMatch(Predicate<? super T> predicate) {
        return getDelegate().noneMatch(predicate);
    }

    @Override
    default Optional<T> findFirst() {
        return getDelegate().findFirst();
    }

    @Override
    default Optional<T> findAny() {
        return getDelegate().findAny();
    }


    @Override
    default Iterator<T> iterator() {
        return getDelegate().iterator();
    }

    @Override
    default Spliterator<T> spliterator() {
        return getDelegate().spliterator();
    }

    @Override
    default boolean isParallel() {
        return getDelegate().isParallel();
    }

    @Override
    default ForwardingStream<T> sequential() {
        return fromDelegate(getDelegate().sequential());
    }

    @Override
    default ForwardingStream<T> parallel() {
        return fromDelegate(getDelegate().parallel());
    }

    @Override
    default ForwardingStream<T> unordered() {
        return fromDelegate(getDelegate().unordered());
    }

    @Override
    default ForwardingStream<T> onClose(Runnable runnable) {
        return fromDelegate(getDelegate().onClose(runnable));
    }

    @Override
    default void close() {
        getDelegate().close();
    }
}
