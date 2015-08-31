package org.bitbucket.jadams13.function;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

/**
 * Created by Joe on 8/30/2015.
 */
public class DebounceTest {

    private static class Holder{
        private String s;
        public String get(){
            return s;
        }
        public void set(String s){
            this.s=s;
        }
    }

    @Test
    public void testDebouncer() throws InterruptedException {
        final Holder h=new Holder();
        Supplier<String> supplier=()->h.get();
        Debounce<String> debounce=new Debounce<>(supplier,100, TimeUnit.MILLISECONDS);
        h.set("A");
        String first=debounce.get();
        h.set("B");
        String second=debounce.get();
        Thread.sleep(110);
        String third=debounce.get();
        assertEquals(first,"A");
        assertEquals(second,"A");
        assertEquals(third,"B");
    }
}
