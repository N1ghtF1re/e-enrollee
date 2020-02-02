package men.brakh.enrollment.domain;

import java.io.Serializable;

/**
 * Base entity
 * @param <T> Identifier's type
 */
public interface BaseEntity<T> extends Serializable {
    T getId();
    void setId(T id);
}
