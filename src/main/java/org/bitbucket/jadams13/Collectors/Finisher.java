package org.bitbucket.jadams13.Collectors;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Joe on 9/1/2015.
 */
public interface Finisher<A,R> extends CharacteristicsDescriber, Supplier<Function<A,R>> {

}
