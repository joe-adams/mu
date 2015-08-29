package org.bitbuck.jadams;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * Created by Joe on 8/23/2015.
 */
public class GroupByCollector {


    public static <K,V> Collector<V, ?,Map<K,List<V>>> groupBy(Function<V, K> mapFunction){
        return getBuilder(mapFunction).build();
    }

    /*public static <K,V> Collector<V, ?,Map<K,Integer>> countBy(Function<V, K> mapFunction){
        Map m=new HashMap<>();

        return getBuilderNoFinisher(mapFunction)
                .setFinisher((map) -> {
                    m
                })
                .build();
    }*/

    public static <K,V> MuCollecterImpl.Builder getBuilder(Function<V,K> mapFunction){
        return new MuCollecterImpl.Builder<V, Map<K,List<V>>,Map<K,List<V>>>()
                .setAccumulator(accumulator(mapFunction))
                .setSupplier(HashMap::new)
                .setCombiner(GroupByCollector::combiner)
                .setFinisher(Function.identity())
                .addCharacteristic(Collector.Characteristics.IDENTITY_FINISH)
                .addCharacteristic(Collector.Characteristics.UNORDERED);
    }


    private static <K,V> BiConsumer<Map<K, List<V>>, V> accumulator(Function<V,K> mapFunction) {
        return (Map<K, List<V>> map,V newVal)->{
            K key=mapFunction.apply(newVal);
            map.computeIfAbsent(key, Util.function(ArrayList::new));
            map.get(key).add(newVal);
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
