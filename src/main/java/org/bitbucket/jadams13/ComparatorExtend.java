package org.bitbucket.jadams13;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by Joe on 8/29/2015.
 */
public interface ComparatorExtend {

    static <T extends Comparable<? super T>> Comparator<T> naturalNullsFirst(){
        return Comparator.nullsFirst(Comparator.naturalOrder());
    }

    static  <T extends Comparable<? super T>> Comparator<T>  naturalNullsLast(){
        return Comparator.nullsLast(Comparator.naturalOrder());
    }

    static <T> Comparator<Optional<T>> emptyFirst(Comparator<? super T> comparator) {
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

    static <T> Comparator<Optional<T>> emptyLast(Comparator<? super T> comparator) {
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

    static <T extends Comparable<? super T>> Comparator<Optional<T>> naturalEmptyFirst(){
        return ComparatorExtend.emptyFirst(Comparator.naturalOrder());
    }

    static  <T extends Comparable<? super T>> Comparator<Optional<T>>  naturalEmptyLast(){
        return ComparatorExtend.emptyLast(Comparator.naturalOrder());
    }

    static <T> Comparator<T> compare(Stream<Comparator<T>> comparatorStream){
        return comparatorStream.reduce((c1,c2)->c1.thenComparing(c2)).orElse((c1, c2) -> 0);
    }

    //More flexible with types than Comparator.andThen
    static <T> BiFunction<Comparator<? super T>,Comparator<? super T>,Comparator<T>> compareThen(){
        return (c1,c2)->{
            Comparator<T> comparator1=(Comparator<T>)c1;
            Comparator<T> comparator2=(Comparator<T>)c2;
            return comparator1.thenComparing((comparator2));
        };
    }


    static <T>  Comparator<T> compare(Collection<Comparator<T>> comparators){
        return compare(comparators.stream());
    }


    static <T> Comparator<T> compareByKeys(Stream<Function<T,Comparable>> functionStream){
        return functionStream.map((Function<T, Comparable> function) -> {
            return (Comparator<T>)(T t1, T t2) -> {
                Comparable c1 = function.apply(t1);
                Comparable c2 = function.apply(t2);
                return c1.compareTo(c2);
            };
        }).reduce((c1,c2)->c1.thenComparing(c2)).orElse((c1, c2) -> 0);
    }

}
