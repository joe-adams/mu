package org.bitbucket.jadams13.Collectors;

import java.util.Set;
import java.util.stream.Collector;


/**
 * Created by Joe on 9/1/2015.
 */
public class CharacteristicsDescriberImpl implements CharacteristicsDescriber {
    final private Set<Collector.Characteristics> included;
    final private Set<Collector.Characteristics> excluded;


    public CharacteristicsDescriberImpl(Set<Collector.Characteristics> included, Set<Collector.Characteristics> excluded) {
        this.included = included;
        this.excluded = excluded;
    }

    @Override
    public Set<Collector.Characteristics> included() {
        return included;
    }

    @Override
    public Set<Collector.Characteristics> excluded() {
        return excluded;
    }
}
