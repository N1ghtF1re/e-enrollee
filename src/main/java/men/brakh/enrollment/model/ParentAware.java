package men.brakh.enrollment.model;

/**
 * Interface which have to implement child entities.
 * @param <T> Parent identifier's type.
 */
public interface ParentAware<T> extends BaseEntity<T> {
    T getParentId();
}
