package org.bitbuck.jadams;

import java.util.stream.Stream;

/**
 * Created by Joe on 8/30/2015.
 */
public class ForwardingStreamImpl<T> implements ForwardingStream<T> {

    final private Stream<T> delegate;

    public ForwardingStreamImpl(Stream<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Stream<T> getDelegate() {
        return delegate;
    }

    
    @Override
    public <R> ForwardingStream<R> fromDelegate(Stream<R> stream) {
        if (stream instanceof ForwardingStream){
            return (ForwardingStream<R>) stream;
        }
        return new ForwardingStreamImpl<R>(stream);
    }
}
