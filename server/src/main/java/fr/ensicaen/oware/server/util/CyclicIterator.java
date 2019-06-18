package fr.ensicaen.oware.server.util;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CyclicIterator<T> implements Iterator<T> {

    private int position;

    private final List<T> list;

    public CyclicIterator(List<T> list) {
        this(list, 0);
    }

    public CyclicIterator(List<T> list, int position) {
        this.position = position - 1;
        this.list = list;
    }

    public boolean hasNext() {
        return !this.list.isEmpty();
    }

    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        } else {
            this.position = this.nextIndex();
            return this.list.get(this.position);
        }
    }

    public boolean hasPrevious() {
        return !this.list.isEmpty();
    }

    public T previous() {
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        } else {
            this.position = this.previousIndex();
            return this.list.get(this.position);
        }
    }

    public T current() {
        return this.list.get(this.position);
    }

    public int nextIndex() {
        if (this.position == this.list.size() - 1) {
            return 0;
        } else {
            return this.position + 1;
        }
    }

    public int previousIndex() {
        if (this.position <= 0) {
            return this.list.size() - 1;
        } else {
            return this.position - 1;
        }
    }

}
