package men.brakh.abiturient.model;

public interface ParentAware<T> extends BaseEntity<T> {
    T getParentId();
}
