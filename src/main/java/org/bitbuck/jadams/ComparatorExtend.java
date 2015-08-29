package org.bitbuck.jadams;

import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Created by Joe on 8/29/2015.
 */
public interface ComparatorExtend<T> extends Comparator<T> {

    default  Comparator<? extends Comparable<T>> naturalNullsFirst(){
        return Comparator.nullsFirst(Comparator.naturalOrder());
    }

    default  Comparator<? extends Comparable<T>> naturalNullsLast(){
        return Comparator.nullsLast(Comparator.naturalOrder());
    }

    default Comparator<T> compare(Stream<Comparator<T>> comparatorStream){
        return (T t1,T t2)->{
          Optional<Integer> compare=comparatorStream.map(comparator -> comparator.compare(t1, t2)).filter(x->x!=0).findFirst();
            if(compare.isPresent()){
                return compare.get();
            }
            return 0;
        };
    }

    default  Comparator<T> compare(Collection<Comparator<T>> comparators){
        return compare(comparators.stream());
    }

    default <T> Comparator<Optional<T>> emptyFirst(Comparator<? super T> comparator) {

        return (opt1,opt2)->{
            if (opt1==opt2){
                return 0;
            }
            if (!opt1.isPresent()){
                return -1;
            }
            if (!opt2.isPresent()){
                return 1;
            }
            return comparator.compare(opt1.get(),opt2.get());
        };
    }

    default <T> Comparator<Optional<T>> emptyLast(Comparator<? super T> comparator) {

        return (opt1,opt2)->{
            if (opt1==opt2){
                return 0;
            }
            if (!opt1.isPresent()){
                return 1;
            }
            if (!opt2.isPresent()){
                return -1;
            }
            return comparator.compare(opt1.get(),opt2.get());
        };
    }
}
