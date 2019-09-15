package men.brakh.abiturient.model;

public interface BaseEntity<T> extends Cloneable {
    T getId();
    void setId(T id);

    BaseEntity clone();
}
