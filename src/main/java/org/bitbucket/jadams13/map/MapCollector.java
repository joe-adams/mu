package org.bitbucket.jadams13.map;

import org.bitbucket.jadams13.util.MuCollecterImpl;
import org.bitbucket.jadams13.util.Util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by Joe on 8/23/2015.
 */
public class MapCollector {

    public static <E,K,V> Collector<E, ?,Map<K,List<V>>> mapCollector(Function<E, K> keyFunction,Function<E,V>valueFunction){
        return mapCollector(keyFunction,valueFunction,HashMap<K,List<V>>::new);
    }

    public static <E,K,V> Collector<E, ?,Map<K,List<V>>> mapCollector(Function<E, K> keyFunction,Function<E,V>valueFunction,Supplier<Map<K,List<V>>> supplier){
        return getBuilder(keyFunction,valueFunction,supplier).build();
    }

    public static <K,V> Collector<V, Map<K, List<V>>, Map<K, Integer>> countBy(Function<V, K> keyFunction){
    	 Function<Map<K,List<V>>,Map<K,Integer>> finisher=(grouped)-> MapStream.fromMap(grouped).mapValue((l)->l.size()).collectMap(HashMap::new);
        return new MuCollecterImpl.Builder<V, Map<K,List<V>>,Map<K,Integer>>()
                .setAccumulator(accumulator(keyFunction, Function.identity()))
                .setSupplier(HashMap::new)
                .setCombiner(MapCollector::combiner)
                .setFinisher(finisher)
                .addCharacteristic(Collector.Characteristics.UNORDERED).build();
    }
    
    public static <K,V> Collector<V, ?,Map<K,List<V>>> groupBy(Function<V,K> keyFunction){
        return groupBy(keyFunction, HashMap<K, List<V>>::new);
    }
    public static <K,V> Collector<V, ?,Map<K,List<V>>> groupBy(Function<V,K> keyFunction,Supplier<Map<K,List<V>>> supplier){
        return mapCollector(keyFunction, Function.identity(),supplier);
    }

    public static <K,V> Collector<Map.Entry<K,V>, ?,Map<K,List<V>>>mapFromEntries(Supplier<Map<K,List<V>>> supplier){
        return mapCollector(((entry)->entry.getKey()),((entry)->entry.getValue()),supplier);
    }

    public static <E,K,V> MuCollecterImpl.Builder<E, ?,Map<K,List<V>>> getBuilder(Function<E,K> keyFunction,Function<E,V> valueFunction,Supplier<Map<K,List<V>>> supplier){
        return new MuCollecterImpl.Builder<E, Map<K,List<V>>,Map<K,List<V>>>()
                .setAccumulator(accumulator(keyFunction, valueFunction))
                .setSupplier(supplier)
                .setCombiner(MapCollector::combiner)
                .setFinisher(Function.identity())
                .addCharacteristic(Collector.Characteristics.IDENTITY_FINISH)
                .addCharacteristic(Collector.Characteristics.UNORDERED);
    }


    private static <E,K,V> BiConsumer<Map<K, List<V>>, E> accumulator(Function<E,K> keyFunction,Function<E,V> valueFunction) {
        return (Map<K, List<V>> map,E entry)->{
            K key=keyFunction.apply(entry);
            V value=valueFunction.apply(entry);
            map.computeIfAbsent(key, Util.function(ArrayList::new));
            map.get(key).add(value);
        };
    }

    private static <K,V> Map<K, List<V>> combiner(Map<K, List<V>> mapToReturn,Map<K, List<V>> otherMap) {
        otherMap.entrySet().forEach((entry) -> {
            K key = entry.getKey();
            mapToReturn.computeIfAbsent(key, Util.function(ArrayList::new));
            mapToReturn.get(key).addAll(otherMap.get(key));
        });
        return mapToReturn;
    }

}
