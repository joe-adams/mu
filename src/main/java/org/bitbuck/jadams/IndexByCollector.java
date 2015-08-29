package org.bitbuck.jadams;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by Joe on 8/23/2015.
 */
public class IndexByCollector {


    public static <K,V> Collector<V, ?,Map<K,V>> indexBy(Function<V, K> mapFunction){
        return getBuilder(mapFunction)
                .build();
    }

    public static <K,V> Collector<Map.Entry<K,V>,?,Map<K,V>> mapFromEntries(Supplier<Map<K,V>> supplier){
        return new MuCollecterImpl.Builder<Map.Entry<K,V>, Map<K,V>,Map<K,V>>()
                .setAccumulator(IndexByCollector::mapAccumulator)
                .setSupplier(supplier)
                .setCombiner(IndexByCollector::combiner)
                .setFinisher(Function.identity())
                .addCharacteristic(Collector.Characteristics.UNORDERED)
                .addCharacteristic(Collector.Characteristics.IDENTITY_FINISH).build();
    }

    private static <K,V> void mapAccumulator(Map<K, V> map, Map.Entry<K, V> entry){
        addKVorThrow(map,entry.getKey(),entry.getValue());
    }


    public static <K,V> MuCollecterImpl.Builder getBuilder(Function<V,K> mapFunction){
        return new MuCollecterImpl.Builder<V, Map<K,V>,Map<K,V>>()
                .setAccumulator(accumulator(mapFunction))
                .setSupplier(HashMap::new)
                .setCombiner(IndexByCollector::combiner)
                .setFinisher(Function.identity())
                .addCharacteristic(Collector.Characteristics.IDENTITY_FINISH)
                .addCharacteristic(Collector.Characteristics.UNORDERED);
    }


    private static <K,V> BiConsumer<Map<K, V>, V> accumulator(Function<V,K> mapFunction) {
        return (Map<K, V> map,V newVal)->{
            K key=mapFunction.apply(newVal);
            addKVorThrow(map, key, newVal);
        };
    }

    private static <K,V> Map<K, V> combiner(Map<K, V> mapToReturn,Map<K, V> otherMap) {
        otherMap.entrySet().forEach((entry) -> {
            addKVorThrow(mapToReturn,entry.getKey(),entry.getValue());
        });
        return mapToReturn;
    }

    private static <K,V> void addKVorThrow(Map<K,V> map,K key,V value){
        if (map.containsKey(key)){
            throw new IllegalArgumentException("duplicate keys in indexByCollector");
        }
        map.put(key,value);
    }

}
