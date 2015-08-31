package org.bitbucket.jadams13.map;

import org.bitbucket.jadams13.map.IndexByCollector;
import org.junit.Test;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by Joe on 8/29/2015.
 */
public class IndexByCollectorTest {

    @Test
    public void testIndexBy(){
        Map<Integer,Integer> map=Stream.of(new Integer(0), new Integer(1),new Integer(2)).collect(IndexByCollector.indexBy(Function.identity()));
        assertEquals(map.size(),3);
        assertEquals(map.get(new Integer(0)),new Integer(0));
        assertEquals(map.get(new Integer(1)),new Integer(1));
        assertEquals(map.get(new Integer(2)),new Integer(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrows(){
        Map<Integer,Integer> map=Stream.of(new Integer(0), new Integer(0),new Integer(2)).collect(IndexByCollector.indexBy(Function.identity()));
    }
}
