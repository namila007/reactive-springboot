package me.namila.rx.reactivespringboot.core.repository;

import me.namila.rx.reactivespringboot.core.model.DepartmentModel;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends GenericRepository<DepartmentModel, String> {

}
