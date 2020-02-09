package men.brakh.enrollment.application.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


@Data
public class SearchRequest implements Specification {
  private List<Condition> filters;
  private Sort sortBy;
  private Integer page = 0;
  private Integer pageSize = 15;

  @Override
  public Predicate toPredicate(final Root root,
                               final CriteriaQuery criteriaQuery,
                               final CriteriaBuilder criteriaBuilder) {
    final List<Predicate> predicates = buildPredicates(root, criteriaQuery, criteriaBuilder);

    addSort(root, criteriaQuery, criteriaBuilder);

    if (predicates.size() > 1) {
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    } else if (predicates.size() == 1) {
      return predicates.get(0);
    } else {
      return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
  }

  public Pageable getPageable() {
    return PageRequest.of(page, pageSize);
  }

  private void addSort(final Root root,
                         final CriteriaQuery criteriaQuery,
                         final CriteriaBuilder criteriaBuilder) {
    if (sortBy != null) {
      Order order = null;

      final Path path = root.get(sortBy.getField());

      switch (sortBy.getType()) {
        case ASC:
          order = criteriaBuilder.asc(path);
          break;
        case DESC:
          order = criteriaBuilder.desc(path);
          break;
      }
      criteriaQuery.orderBy(order);
    }
  }

  private List<Predicate> buildPredicates(final Root root,
                                          final CriteriaQuery criteriaQuery,
                                          final CriteriaBuilder criteriaBuilder) {
    if (filters == null) {
      return Collections.emptyList();
    }

    List<Predicate> predicates = new ArrayList<>();
    filters.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
    return predicates;
  }



  private Predicate buildPredicate(final Condition condition,
                                   final Root root,
                                   final CriteriaQuery criteriaQuery,
                                   final CriteriaBuilder criteriaBuilder) {

    switch (condition.getComparison()) {
      case eq:
        return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
      case gt:
        return buildGreaterThanPredicate(condition, root, criteriaQuery, criteriaBuilder);
      case lt:
        return buildLessThanPredicate(condition, root, criteriaQuery, criteriaBuilder);
      case ne:
        return buildNotEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
      case ct:
        return buildContainsPredicate(condition, root, criteriaQuery, criteriaBuilder);
      case isnull:
        return buildIsNullPredicate(condition, root, criteriaQuery, criteriaBuilder);
      case in:
        throw new IllegalArgumentException("Not supported");
    }

    throw new RuntimeException();
  }

  private Predicate buildEqualsPredicateToCriteria(Condition condition,
                                                   Root root,
                                                   CriteriaQuery criteriaQuery,
                                                   CriteriaBuilder cb) {

    return cb.equal(root.get(condition.getField()), condition.getValue());
  }

  private Predicate buildNotEqualsPredicateToCriteria(Condition condition,
                                                      Root root,
                                                      CriteriaQuery criteriaQuery,
                                                      CriteriaBuilder cb) {
    return cb.notEqual(root.get(condition.getField()), condition.getValue());

  }

  @SuppressWarnings("unchecked")
  private Predicate buildGreaterThanPredicate(Condition condition,
                                              Root root,
                                              CriteriaQuery criteriaQuery,
                                              CriteriaBuilder cb) {
    if (condition.getType() == Type.date) {
      return cb.greaterThan((Path<Date>) root.get(condition.getField()), (Date) condition.getValue());
    } else {
      return cb.gt(root.get(condition.getField()), (Number) condition.getValue());
    }
  }

  @SuppressWarnings("unchecked")
  private Predicate buildLessThanPredicate(Condition condition,
                                              Root root,
                                              CriteriaQuery criteriaQuery,
                                              CriteriaBuilder cb) {
    if (condition.getType() == Type.date) {
      return cb.lessThan((Path<Date>) root.get(condition.getField()), (Date) condition.getValue());
    } else {
      return cb.lt(root.get(condition.getField()), (Number) condition.getValue());
    }
  }

  @SuppressWarnings("unchecked")
  private Predicate buildContainsPredicate(Condition condition,
                                           Root root,
                                           CriteriaQuery criteriaQuery,
                                           CriteriaBuilder cb) {
    if (condition.getType() != Type.string) {
      throw new IllegalArgumentException("Contains criteria can be used only with strings");
    }

    final String valueString = condition.getValue().toString().replaceAll("%", "~%");

    return cb.like(root.get(condition.getField()), "%" + valueString + "%", '~');
  }

  private Predicate buildIsNullPredicate(Condition condition,
                                         Root root,
                                         CriteriaQuery criteriaQuery,
                                         CriteriaBuilder cb) {
    return cb.isNull(root.get(condition.getField()));
  }
}