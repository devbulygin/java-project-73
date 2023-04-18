package hexlet.code.repository;


//@Repository
//public interface TaskRepository extends JpaRepository<Task, Long>, QuerydslPredicateExecutor<Task> {
//
//}

import com.querydsl.core.types.dsl.StringPath;
import hexlet.code.model.QTask;
import hexlet.code.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringExpression;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, QuerydslPredicateExecutor<Task>,
        QuerydslBinderCustomizer<QTask> {

    @Override
    default void customize(QuerydslBindings bindings, QTask task) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>)
                StringExpression::containsIgnoreCase);
        bindings.excluding(task.id);
        bindings.bind(task.labels.any().name).first((path, value) -> path.containsIgnoreCase(value));
    }
}
