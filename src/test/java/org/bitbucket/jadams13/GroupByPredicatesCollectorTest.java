package org.bitbucket.jadams13;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Joe on 8/23/2015.
 */
public class GroupByPredicatesCollectorTest {

	@Test
    public void testGroupByPredicates(){
        List<Predicate<Integer>> preds=new ArrayList<>();
        Predicate<Integer> even=(i)->i%2==0;
        Predicate<Integer> odd=even.negate();
        preds.add(even);
        preds.add(odd);
        preds.stream();
        Map<Predicate<Integer>, List<Integer>> map= IntStream.rangeClosed(1, 10).boxed().collect(GroupByPredicatesCollector.get(preds));
        assertEquals(map.get(even).size(),5);
        assertEquals(map.get(odd).size(),5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGroupByPredicatesException(){
        List<Predicate<Integer>> preds=new ArrayList<>();
        Predicate<Integer> even=(i)->i%2==0;
        Predicate<Integer> odd=even.negate();
        preds.add(even);
        Map<Predicate<Integer>, List<Integer>> map= IntStream.rangeClosed(1, 10).boxed().collect(GroupByPredicatesCollector.get(preds));
        assertEquals(map.get(even).size(), 5);
        assertEquals(map.get(odd).size(), 5);
    }
}
