package org.bitbuck.jadams;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Created by Joe on 8/29/2015.
 */
public class MapStream<K,V> implements Stream<Map.Entry<K,V>> {
    private final Stream<Map.Entry<K,V>> delegate;

    public MapStream(Stream<Map.Entry<K, V>> delegate) {
        this.delegate = delegate;
    }

    public static<K,V>  MapStream<K,V> toMapStream(Stream<Map.Entry<K, V>> delegate) {
        if (delegate instanceof MapStream){
            return (MapStream)delegate;
        }
        return new MapStream(delegate);
    }

    public static <K,V> MapStream<K,V> fromMap(Map<K,V> map){

        return new MapStream(map.entrySet().stream());
    }

    public Map<K,V> collect(Supplier<Map<K,V>> supplier){
        return this.delegate.collect(IndexByCollector.mapFromEntries(supplier));
    }

    public Stream<Map.Entry<K, V>> getDelegate() {
        return delegate;
    }

    @Override
    public MapStream<K, V> filter(Predicate<? super Map.Entry<K, V>> predicate) {
        return new MapStream<>(delegate.filter(predicate));
    }

    public MapStream<K, V> filterKey(Predicate<? super K> predicate) {
        return toMapStream(delegate.filter((entry)->predicate.test(entry.getKey())));
    }

    public MapStream<K, V> filterValue(Predicate<? super V> predicate) {
        return toMapStream(delegate.filter((entry)->predicate.test(entry.getValue())));
    }

    @Override
    public <R> Stream<R> map(Function<? super Map.Entry<K, V>, ? extends R> function) {
        return delegate.map(function);
    }

    public <R,S> MapStream<R,S> mapAsMapStream(Function<? super Map.Entry<K, V>, ? extends Map.Entry<R, S>> function) {
        return toMapStream(delegate.map(function));
    }

    public <R> MapStream<R,V> mapKey(Function<? super  K, ? extends R> function) {
        return toMapStream(delegate.map((entry)->new AbstractMap.SimpleEntry<>(function.apply(entry.getKey()),entry.getValue())));
    }

    public <R> MapStream<K,R> mapValue(Function<? super  V, ? extends R> function) {
        return toMapStream(delegate.map((entry)->new AbstractMap.SimpleEntry<>(entry.getKey(),function.apply(entry.getValue()))));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super Map.Entry<K, V>> toIntFunction) {
        return delegate.mapToInt(toIntFunction);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super Map.Entry<K, V>> toLongFunction) {
        return delegate.mapToLong(toLongFunction);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super Map.Entry<K, V>> toDoubleFunction) {
        return delegate.mapToDouble(toDoubleFunction);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super Map.Entry<K, V>, ? extends Stream<? extends R>> function) {
        return delegate.flatMap(function);
    }

    @Override
    public IntStream flatMapToInt(Function<? super Map.Entry<K, V>, ? extends IntStream> function) {
        return delegate.flatMapToInt(function);
    }

    @Override
    public LongStream flatMapToLong(Function<? super Map.Entry<K, V>, ? extends LongStream> function) {
        return delegate.flatMapToLong(function);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super Map.Entry<K, V>, ? extends DoubleStream> function) {
        return delegate.flatMapToDouble(function);
    }

    @Override
    public MapStream<K, V> distinct() {
        return toMapStream(delegate.distinct());
    }

    private static class ValueEqualedEntry<K,V> extends AbstractMap.SimpleEntry<K,V>{

        private final Map.Entry<? extends K, ? extends V> entry;
        public ValueEqualedEntry(Map.Entry<? extends K, ? extends V> entry) {
            super(entry);
            this.entry=entry;
        }

        @Override
        public boolean equals(Object obj){
            if (!(obj instanceof Map.Entry)){
                return false;
            }
            return this.getValue().equals(((Map.Entry) obj).getValue());
        }

        @Override
        public int hashCode(){
            return this.getValue().hashCode();
        }
    }

    public MapStream<K, V> distinctValues() {
       return this.mapAsMapStream(ValueEqualedEntry::new).distinct().mapAsMapStream(entry->new AbstractMap.SimpleEntry(entry));
    }

    @Override
    public MapStream<K, V> sorted() {
        return toMapStream(delegate.sorted());
    }

    @Override
    public MapStream<K, V>  sorted(Comparator<? super Map.Entry<K, V>> comparator) {
        return toMapStream(delegate.sorted(comparator));
    }

    public MapStream<K, V>  sortedByKey(Comparator<? super K> comparator) {
        return toMapStream(delegate.sorted(Comparator.comparing(entry->entry.getKey(),comparator)));
    }

    public MapStream<K, V>  sortedByValue(Comparator<? super V> comparator) {
        return toMapStream(delegate.sorted(Comparator.comparing(entry -> entry.getValue(), comparator)));
    }

    @Override
    public MapStream<K, V> peek(Consumer<? super Map.Entry<K, V>> consumer) {
        return toMapStream(delegate.peek(consumer));
    }

    @Override
    public MapStream<K, V>  limit(long l) {
        return toMapStream(delegate.limit(l));
    }

    @Override
    public MapStream<K, V>  skip(long l) {
        return toMapStream(delegate.skip(l));
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
        delegate.forEach(consumer);
    }

    @Override
    public void forEachOrdered(Consumer<? super Map.Entry<K, V>> consumer) {
        delegate.forEachOrdered(consumer);
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> intFunction) {
        return delegate.toArray(intFunction);
    }

    @Override
    public Map.Entry<K, V> reduce(Map.Entry<K, V> kvEntry, BinaryOperator<Map.Entry<K, V>> binaryOperator) {
        return delegate.reduce(kvEntry, binaryOperator);
    }

    @Override
    public Optional<Map.Entry<K, V>> reduce(BinaryOperator<Map.Entry<K, V>> binaryOperator) {
        return delegate.reduce(binaryOperator);
    }

    @Override
    public <U> U reduce(U u, BiFunction<U, ? super Map.Entry<K, V>, U> biFunction, BinaryOperator<U> binaryOperator) {
        return delegate.reduce(u, biFunction, binaryOperator);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super Map.Entry<K, V>> biConsumer, BiConsumer<R, R> biConsumer1) {
        return delegate.collect(supplier, biConsumer, biConsumer1);
    }

    @Override
    public <R, A> R collect(Collector<? super Map.Entry<K, V>, A, R> collector) {
        return delegate.collect(collector);
    }

    @Override
    public Optional<Map.Entry<K, V>> min(Comparator<? super Map.Entry<K, V>> comparator) {
        return delegate.min(comparator);
    }

    @Override
    public Optional<Map.Entry<K, V>> max(Comparator<? super Map.Entry<K, V>> comparator) {
        return delegate.max(comparator);
    }

    @Override
    public long count() {
        return delegate.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super Map.Entry<K, V>> predicate) {
        return delegate.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super Map.Entry<K, V>> predicate) {
        return delegate.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super Map.Entry<K, V>> predicate) {
        return delegate.noneMatch(predicate);
    }

    @Override
    public Optional<Map.Entry<K, V>> findFirst() {
        return delegate.findFirst();
    }

    @Override
    public Optional<Map.Entry<K, V>> findAny() {
        return delegate.findAny();
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return delegate.iterator();
    }

    @Override
    public Spliterator<Map.Entry<K, V>> spliterator() {
        return delegate.spliterator();
    }

    @Override
    public boolean isParallel() {
        return delegate.isParallel();
    }

    @Override
    public Stream<Map.Entry<K, V>> sequential() {
        return delegate.sequential();
    }

    @Override
    public Stream<Map.Entry<K, V>> parallel() {
        return delegate.parallel();
    }

    @Override
    public Stream<Map.Entry<K, V>> unordered() {
        return delegate.unordered();
    }

    @Override
    public Stream<Map.Entry<K, V>> onClose(Runnable runnable) {
        return delegate.onClose(runnable);
    }

    @Override
    public void close() {
        delegate.close();
    }
}
