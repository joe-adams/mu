package org.bitbucket.jadams13.Collectors;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collector;

/**
 * Created by Joe on 9/1/2015.
 */
public class IdentityFinisher<A> implements ImmutableFinisher<A,A>{

    private IdentityFinisher(){

    }
    @Override
    public A finisherImpl(A a){
        return a;
    }

    public Set<Collector.Characteristics> included(){
        return EnumSet.of(Collector.Characteristics.IDENTITY_FINISH);
    }

    private static IdentityFinisher<?> FINISHER=new IdentityFinisher();

    public static <A> IdentityFinisher<A> finisher(){
        return (IdentityFinisher<A>)FINISHER;
    }
}
