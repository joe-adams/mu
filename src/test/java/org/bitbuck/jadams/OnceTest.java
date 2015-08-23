package org.bitbuck.jadams;

import jdk.nashorn.internal.codegen.CompilerConstants;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by Joe on 8/22/2015.
 */
public class OnceTest {

    private static class Dummy{

    }

    @Test
    public void testOnce(){
        Supplier<Dummy> supplier=()->new Dummy();
        Once<Dummy> once=new Once(supplier);
        Dummy d1=once.get();
        Dummy d2=once.get();
        Dummy d3=once.get();
        assertTrue(d1 == d2);
        assertTrue(d3 == d2);
        assertNotNull(d1);
        assertTrue(d1 instanceof Dummy);
    }


    @Test
    public void testConcurrent(){
        Supplier<Dummy> supplier=()->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                fail();
            }
            return new Dummy();
        };
        Once<Dummy> once=new Once(supplier);
        ExecutorService taskExecutor = Executors.newFixedThreadPool(4);
        List<Callable<Dummy>> callables=new ArrayList<>();
        for (int i=0;i<5;i++){
            callables.add(()->once.get());
        }
        try {
            List<Future<Dummy>> futures=taskExecutor.invokeAll(callables);
            Set<Dummy> dummies=futures.stream().map((future) -> {
                try {
                    return future.get();
                } catch (InterruptedException|ExecutionException ex) {
                    ex.printStackTrace();
                    fail();
                    return null;
                }
            }).collect(Collectors.toSet());
            assertEquals(dummies.size(),1);
            assertTrue(dummies.iterator().next() instanceof Dummy);
        } catch (InterruptedException e) {
            assertTrue(false);
        }
    }
}
