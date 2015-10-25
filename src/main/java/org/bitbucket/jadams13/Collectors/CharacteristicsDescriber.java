package org.bitbucket.jadams13.Collectors;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Joe on 8/31/2015.
 */
public interface CharacteristicsDescriber {
    default Set<Collector.Characteristics> included(){
        return EnumSet.noneOf(Collector.Characteristics.class);
    }

    default Set<Collector.Characteristics> excluded(){
        return EnumSet.noneOf(Collector.Characteristics.class);
    }

    enum None implements CharacteristicsDescriber{
        NONE
    }

    CharacteristicsDescriber NONE=None.NONE;

    static Set<Collector.Characteristics> make(CharacteristicsDescriber... describers){
        Set<Collector.Characteristics> included=Arrays.stream(describers).map(CharacteristicsDescriber::included).flatMap(Collection::stream).collect(Collectors.toSet());
        Set<Collector.Characteristics> excluded=Arrays.stream(describers).map(CharacteristicsDescriber::excluded).flatMap(Collection::stream).collect(Collectors.toSet());
        Set<Collector.Characteristics> test=EnumSet.copyOf(included);
        test.retainAll(excluded);
        if (test.size()>0){
            throw new IllegalArgumentException("CharacteristicsDescribers contradicted each other!");
        }
        return EnumSet.copyOf(included);
    }
}
