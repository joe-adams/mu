package org.bitbuck.jadams;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * Created by Joe on 8/23/2015.
 */
public class GroupByCollector {

    final private static Set<Collector.Characteristics> characteristics= EnumSet.of(Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH);

    public static <K,V> Collector<V, Map<K,List<V>>,Map<K,List<V>>> get(Function<V,K> mapFunction){
        return new MuCollecterImpl.Builder<V, Map<K,List<V>>,Map<K,List<V>>>()
                .setAccumulator(accumulator(mapFunction))
                .setCharacteristics(characteristics)
                .setCombiner(GroupByCollector::combiner)
                .setFinisher(Function.identity())
                .setSupplier(HashMap::new).build();
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
