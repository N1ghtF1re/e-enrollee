package men.brakh.enrollment.model;

import java.io.Serializable;

/**
 * Base entity
 * @param <T> Identifier's type
 */
public interface BaseEntity<T> extends Cloneable, Serializable {
    T getId();
    void setId(T id);

    BaseEntity clone();
}
