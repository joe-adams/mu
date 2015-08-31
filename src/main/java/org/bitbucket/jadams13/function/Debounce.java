package org.bitbucket.jadams13.function;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Joe on 8/30/2015.
 */
public class Debounce<R> implements Supplier<R> {
    private Optional<R> value;
    final private Supplier<R> supplier;
    final private int wait;
    final private TimeUnit timeUnit;
    final private ScheduledExecutorService executorService= new ScheduledThreadPoolExecutor(1);
    final private Runnable runnable=()->{
        value=Optional.empty();
    };

    public Debounce(Supplier<R> supplier,int wait,TimeUnit timeUnit){
        this.supplier=supplier;
        this.value=Optional.empty();
        this.wait=wait;
        this.timeUnit=timeUnit;
    }

    public R get(){
        Optional<R> opt=value;
        if (opt.isPresent()){
            return opt.get();
        }
        return syncGet();
    }

    private synchronized R syncGet(){
        if (value.isPresent()){
            return value.get();
        }
        value=Optional.ofNullable(supplier.get());
        executorService.schedule(runnable,wait, timeUnit);
        return value.get();
    }
}
