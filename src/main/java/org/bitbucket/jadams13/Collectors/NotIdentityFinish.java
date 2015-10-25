package org.bitbucket.jadams13.Collectors;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collector;

/**
 * Created by Joe on 9/1/2015.
 */
public interface NotIdentityFinish<A,R> extends Finisher<A,R> {
    default Set<Collector.Characteristics> excluded(){
        return EnumSet.of(Collector.Characteristics.IDENTITY_FINISH);
    }
}
