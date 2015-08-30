package org.bitbucket.jadams13;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by Joe on 8/30/2015.
 */
public class ComparatorExtendTest {

    private static class C{
        private final String one;
        private final int two;
        private final String three;

        public C(String one, int two, String three) {
            this.one = one;
            this.two = two;
            this.three = three;
        }

        public String getOne() {
            return one;
        }

        public Integer getTwo() {
            return two;
        }

        public String getThree() {
            return three;
        }
    }

    @Test
    public void testCompareByKeys(){
        C first=new C("a",1,"a");
        C second=new C("a",1,"b");
        C third=new C("a",2,"a");
        C fourth=new C("b",1,"a");

        Stream<Function<C,Comparable>> stream=Stream.of(((C c) -> c.getOne()), ((C d) -> d.getTwo()), ((C d) -> d.getThree()));
        Comparator<C> comparator=ComparatorExtend.compareByKeys(stream);
        //Comparator<C> comparator=ComparatorExtend.compareByKeys(Stream.of(((C c) -> c.getOne()), ((C d) -> d.getTwo()), ((C d) -> d.getThree())));
        List<C> cs=Stream.of(second,third,first,fourth).sorted(comparator).collect(Collectors.toList());
        assertEquals(cs.get(0),first);
        assertEquals(cs.get(1),second);
        assertEquals(cs.get(2),third);
        assertEquals(cs.get(3),fourth);
    }
}
