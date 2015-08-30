package org.bitbucket.jadams13;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Joe on 8/23/2015.
 */
public interface Util {
    public static <K,V> Function<K,V> function(Supplier<V> supplier){
        return (i)->supplier.get();
    }
}
