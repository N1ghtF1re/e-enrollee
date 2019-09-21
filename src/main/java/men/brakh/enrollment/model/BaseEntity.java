package men.brakh.enrollment.model;

public interface BaseEntity<T> extends Cloneable {
    T getId();
    void setId(T id);

    BaseEntity clone();
}
