package org.bitbuck.jadams;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * Created by Joe on 8/23/2015.
 */
public class GroupByPredicatesCollector {

    public static <V> Collector<V, Map<Predicate<V>,List<V>>,Map<Predicate<V>,List<V>>> get(List<Predicate<V>> predicates){
        Function<V,Predicate<V>> mapFunction=(v)->GroupByPredicatesCollector.getFromList(predicates,v);
        return GroupByCollector.get(mapFunction);
    }

    private static <V> Predicate<V> getFromList(List<Predicate<V>> predicates,V value){
        Optional<Predicate<V>> opt=predicates.stream().filter((Predicate<V> p)->p.test(value)).findFirst();
        if(!opt.isPresent()){
            throw new IllegalArgumentException("Value did not pass any predicate test");
        }
        return opt.get();
    }
}
