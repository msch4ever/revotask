package com.los.revotask.persistence;

import java.util.List;

public interface Dao <T> {

    long save(T t);
    T findById(Class<T> c, long id);
    List<T> getAll(Class<T> c);
    void update(T t);
    void delete(T t);
}
