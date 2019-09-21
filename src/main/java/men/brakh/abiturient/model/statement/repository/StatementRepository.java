package men.brakh.abiturient.model.statement.repository;

import men.brakh.abiturient.model.statement.Statement;
import men.brakh.abiturient.repository.CRUDRepository;

import java.util.List;

public interface StatementRepository extends CRUDRepository<Statement, Integer> {
    List<Statement> findByAbiturientId(Integer abiturientId);
}
