package org.bitbucket.jadams13;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by Joe on 8/22/2015.
 */
public class Once<R> implements Supplier<R>{
    final private Supplier<R> supplier;
    private Optional<R> value;

    public Once(Supplier<R> supplier){
        this.supplier=supplier;
        this.value=Optional.empty();
    }

    public R get(){
        if (value.isPresent()){
            return value.get();
        }
        return syncGet();
    }

    private synchronized R syncGet(){
        if (value.isPresent()){
            return value.get();
        }
        value=Optional.ofNullable(supplier.get());
        return value.get();
    }
}
