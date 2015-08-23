package org.bitbuck.jadams;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * Created by Joe on 8/23/2015.
 */
public class IndexByCollector {

    final private static Set<Collector.Characteristics> characteristics= EnumSet.of(Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH);

    public static <K,V> Collector<V, Map<K,V>,Map<K,V>> get(Function<V,K> mapFunction){
        return new MuCollecterImpl.Builder<V, Map<K,V>,Map<K,V>>()
                .setAccumulator(accumulator(mapFunction))
                .setCharacteristics(characteristics)
                .setCombiner(IndexByCollector::combiner)
                .setFinisher(Function.identity())
                .setSupplier(HashMap::new).build();
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
