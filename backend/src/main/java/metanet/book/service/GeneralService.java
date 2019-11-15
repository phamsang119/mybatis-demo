package metanet.book.service;

import java.io.Serializable;
import java.util.List;

public interface GeneralService<PK extends Serializable, T> {
    List<T> getAll(int page, int limit);

    T saveOne(T object);

    void saveMany(List<T> list);

    T updateOne(T object);

    void updateMany(List<T> list);

    void deleteOne(PK pk);

    T getOne(PK pk);

    long count();
}
