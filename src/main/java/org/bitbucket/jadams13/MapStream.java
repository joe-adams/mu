package org.bitbucket.jadams13;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Created by Joe on 8/29/2015.
 */
public class MapStream<K,V> extends ForwardingStreamImpl<Map.Entry<K,V>> {

    public MapStream(Stream<Map.Entry<K, V>> delegate) {
        super(delegate);
    }

    private <R,S> MapStream<R,S> toMapStream(Stream<Map.Entry<R,S>> stream){
        if (stream instanceof MapStream){
            return (MapStream<R,S>) stream;
        }
        return new MapStream<R,S>(stream);
    }


    public Map<K,V> collectMap(Supplier<Map<K,V>> supplier){
        return getDelegate().collect(IndexByCollector.mapFromEntries(supplier));
    }

    public Map<K,List<V>> collectMapList(Supplier<Map<K,List<V>>> supplier){
        return getDelegate().collect(MapCollector.mapFromEntries(supplier));
    }


    public MapStream<K, V> filterKey(Predicate<? super K> predicate) {
        return toMapStream(getDelegate().filter(keyPredicate(predicate)));
    }

    public MapStream<K, V> filterValue(Predicate<? super V> predicate) {
        return toMapStream(getDelegate().filter(valuePredicate(predicate)));
    }
    
    @Override
    public MapStream<K, V> filter(Predicate<? super Map.Entry<K, V>> predicate) {
    	return toMapStream(getDelegate().filter(predicate));
    }

    public <R,S> MapStream<R,S> mapAsMapStream(Function<? super Map.Entry<K, V>, ? extends Map.Entry<R, S>> function) {
        return toMapStream(getDelegate().map(function));
    }

    public <R> MapStream<R,V> mapKey(Function<? super  K, ? extends R> function) {
        return toMapStream(getDelegate().map((entry) -> new AbstractMap.SimpleEntry<>(function.apply(entry.getKey()), entry.getValue())));
    }

    public <R> MapStream<K,R> mapValue(Function<? super  V, ? extends R> function) {
        return toMapStream(getDelegate().map((entry) -> new AbstractMap.SimpleEntry<>(entry.getKey(), function.apply(entry.getValue()))));
    }


    @Override
    public MapStream<K, V> distinct() {
        return toMapStream(getDelegate().distinct());
    }

    public MapStream<K, V> distinctValues() {
       return this.mapAsMapStream(ValueEqualedEntry::new).distinct().mapAsMapStream(entry -> new AbstractMap.SimpleEntry(entry));
    }

    @Override
    public MapStream<K, V> sorted() {
        return toMapStream(getDelegate().sorted());
    }

    @Override
    public MapStream<K, V> sorted(Comparator<? super Map.Entry<K, V>> comparator) {
        return toMapStream(getDelegate().sorted(comparator));
    }

    public MapStream<K, V>  sortedByKey(Comparator<? super K> comparator) {
        return toMapStream(getDelegate().sorted(Comparator.comparing(entry -> entry.getKey(), comparator)));
    }

    public MapStream<K, V>  sortedByValue(Comparator<? super V> comparator) {
        return toMapStream(getDelegate().sorted(Comparator.comparing(entry -> entry.getValue(), comparator)));
    }

    @Override
    public MapStream<K, V> peek(Consumer<? super Map.Entry<K, V>> consumer) {
        return toMapStream(getDelegate().peek(consumer));
    }

    @Override
    public MapStream<K, V>  limit(long l) {
        return toMapStream(getDelegate().limit(l));
    }

    @Override
    public MapStream<K, V> skip(long l) {
        return toMapStream(getDelegate().skip(l));
    }

    public boolean anyMatchKey(Predicate<? super K> predicate) {
        return getDelegate().anyMatch(keyPredicate(predicate));
    }

    public boolean anyMatchValue(Predicate<? super V> predicate) {
        return getDelegate().anyMatch(valuePredicate(predicate));
    }

    public boolean allMatchKey(Predicate<? super K> predicate) {
        return getDelegate().allMatch(keyPredicate(predicate));
    }

    public boolean allMatchValue(Predicate<? super V> predicate) {
        return getDelegate().allMatch(valuePredicate(predicate));
    }

    public boolean noneMatchKey(Predicate<? super K> predicate) {
        return getDelegate().noneMatch(keyPredicate(predicate));
    }

    public boolean noneMatchValue(Predicate<? super V> predicate) {
        return getDelegate().noneMatch(valuePredicate(predicate));
    }

    @Override
    public Optional<Map.Entry<K, V>> findFirst() {
        return getDelegate().findFirst();
    }

    @Override
    public Optional<Map.Entry<K, V>> findAny() {
        return getDelegate().findAny();
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return getDelegate().iterator();
    }

    @Override
    public Spliterator<Map.Entry<K, V>> spliterator() {
        return getDelegate().spliterator();
    }

    @Override
    public boolean isParallel() {
        return getDelegate().isParallel();
    }

    @Override
    public MapStream<K, V> sequential() {
        return toMapStream(getDelegate().sequential());
    }

    @Override
    public MapStream<K, V> parallel() {
        return toMapStream(getDelegate().parallel());
    }

    @Override
    public MapStream<K, V> unordered() {
        return toMapStream(getDelegate().unordered());
    }

    @Override
    public MapStream<K, V> onClose(Runnable runnable) {
        return toMapStream(getDelegate().onClose(runnable));
    }


    private static <K, V> Predicate<? super Map.Entry<K, V>> keyPredicate(Predicate<? super K> keyPredicate){
        return (entry)->keyPredicate.test(entry.getKey());
    }

    private static <K, V> Predicate<? super Map.Entry<K, V>> valuePredicate(Predicate<? super V> valuePredicate){
        return (entry)->valuePredicate.test(entry.getValue());
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
}
