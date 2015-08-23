package org.bitbuck.jadams;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by Joe on 8/22/2015.
 */
public class GroupByCollector<K,V> implements Collector<V, Map<K,List<V>>,Map<K,List<V>>>{
    final private Function<V,K> mapFunction;
    final private Function<K,List<V>> newEntry=(k)->(new LinkedList<>());
    final private Set<Characteristics> characteristics=EnumSet.of(Characteristics.UNORDERED);


    public GroupByCollector(Function<V, K> mapFunction) {
        this.mapFunction = mapFunction;
    }

    @Override
    public Supplier<Map<K, List<V>>> supplier() {
        return ()->new ConcurrentHashMap<>();
    }

    @Override
    public BiConsumer<Map<K, List<V>>, V> accumulator() {
        return (Map<K, List<V>> map,V newVal)->{
            K key=mapFunction.apply(newVal);
            map.computeIfAbsent(key, newEntry);
            map.get(key).add(newVal);
        };
    }

    @Override
    public BinaryOperator<Map<K, List<V>>> combiner() {
        return (mapToReturn,otherMap)->{
            otherMap.entrySet().forEach((entry)->{
                K key=entry.getKey();
                mapToReturn.computeIfAbsent(key,newEntry);
                mapToReturn.get(key).addAll(otherMap.get(key));
            });
            return mapToReturn;
        };
    }

    @Override
    public Function<Map<K, List<V>>, Map<K, List<V>>> finisher() {
        return (map)->(new HashMap<>(map));
    }

    @Override
    public Set<Characteristics> characteristics() {
        return characteristics;
    }
}
