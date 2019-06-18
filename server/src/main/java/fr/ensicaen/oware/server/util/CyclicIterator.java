package fr.ensicaen.oware.server.util;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CyclicIterator<T> implements Iterator<T> {

    private final List<T> list;
    private Iterator<T> iterator;

    public CyclicIterator(List<T> list) {
        this.list = list;
        this.iterator = list.iterator();
    }

    public CyclicIterator startsAt(int x) {
        if (x < 0 || this.list.size() < x) {
            throw new IndexOutOfBoundsException();
        }

        // Ignore the first x values
        for (; x > 0; --x) {
            this.next();
        }

        return this;
    }

    @Override
    public boolean hasNext() {
        return !this.list.isEmpty();
    }

    @Override
    public T next() {
        T nextElement;

        if (!this.hasNext()) {
            throw new NoSuchElementException();
        } else if (this.iterator.hasNext()) {
            nextElement = this.iterator.next();
        } else {
            this.iterator = this.list.iterator();
            nextElement = this.iterator.next();
        }

        return nextElement;
    }

    @Override
    public void remove() {
        this.iterator.remove();
    }

}
