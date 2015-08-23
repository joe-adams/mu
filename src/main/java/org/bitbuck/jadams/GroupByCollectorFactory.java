package org.bitbuck.jadams;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by Joe on 8/23/2015.
 */
public class GroupByCollectorFactory {

    final private static Set<Collector.Characteristics> characteristics= EnumSet.of(Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH);
    final private static Function newEntry=Util.functionFromSupplier(ArrayList::new);
    final private static Supplier supplier=HashMap::new;

    public static <K,V> Collector<V, Map<K,List<V>>,Map<K,List<V>>> get(Function<V,K> mapFunction){
        return new MuCollecterImpl.Builder<V, Map<K,List<V>>,Map<K,List<V>>>()
                .setAccumulator(accumulator(mapFunction))
                .setCharacteristics(characteristics)
                .setCombiner(GroupByCollectorFactory::combiner)
                .setFinisher(Function.identity())
                .setSupplier(supplier).build();
    }


    private static <K,V> BiConsumer<Map<K, List<V>>, V> accumulator(Function<V,K> mapFunction) {
        return (Map<K, List<V>> map,V newVal)->{
            K key=mapFunction.apply(newVal);
            map.computeIfAbsent(key, newEntry);
            map.get(key).add(newVal);
        };
    }

    private static <K,V> Map<K, List<V>> combiner(Map<K, List<V>> mapToReturn,Map<K, List<V>> otherMap) {
        otherMap.entrySet().forEach((entry) -> {
            K key = entry.getKey();
            mapToReturn.computeIfAbsent(key, newEntry);
            mapToReturn.get(key).addAll(otherMap.get(key));
        });
        return mapToReturn;
    }

}
