package men.brakh.enrollment.model;

public interface ParentAware<T> extends BaseEntity<T> {
    T getParentId();
}
