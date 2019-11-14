package metanet.book.service;

import java.io.Serializable;
import java.util.List;

public interface GeneralService<PK extends Serializable, T> {
    List<T> getAll(int page, int limit);

    T saveOne(T book);

    T updateOne(T book);

    void deleteOne(PK pk);

    T getOne(PK pk);
}
