package org.bitbucket.jadams13;

import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Joe on 8/22/2015.
 */
public class MemoizedFunctionTest {

    private static class Dummy{
        private final String string;
        Dummy(String string){
            this.string=string;
        }

        @Override
        public boolean equals(Object o2){
            if (!(o2 instanceof Dummy)){
                return false;
            }
            return this.string.equals(((Dummy)o2).string);
        }

        @Override
        public int hashCode(){
            return string.hashCode();
        }

    }

    @Test
    public void testMemoizedFunction(){
        Function<Dummy,Dummy> function=Function.identity();
        MemoizedFunction<Dummy,Dummy> memo=new MemoizedFunction(function);
        Dummy a=new Dummy("a");
        Dummy a2=new Dummy("a");
        Dummy b=new Dummy("b");
        assertTrue(a!=a2&&a.equals(a2)&&!a.equals(b));
        Dummy answerA=memo.apply(a);
        Dummy answerA2=memo.apply(a2);
        Dummy answerB=memo.apply(b);
        assertTrue(answerA==answerA2);
        assertNotEquals(answerA,answerB);


    }
}
