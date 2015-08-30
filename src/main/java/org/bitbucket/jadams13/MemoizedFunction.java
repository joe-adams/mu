package org.bitbucket.jadams13;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Created by Joe on 8/22/2015.
 */
public class MemoizedFunction<T,R> implements Function<T,R> {
    private final ConcurrentMap<T,R> map=new ConcurrentHashMap<>();
    private final Function<T,R> function;

    public MemoizedFunction(Function<T, R> function) {
        this.function = function;
    }

    @Override
    public R apply(T t) {
       map.computeIfAbsent(t,function);
        return map.get(t);
    }

}
