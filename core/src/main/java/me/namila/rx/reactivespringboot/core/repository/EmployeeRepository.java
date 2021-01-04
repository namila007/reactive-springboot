package me.namila.rx.reactivespringboot.core.repository;

import me.namila.rx.reactivespringboot.core.model.EmployeeModel;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends GenericRepository<EmployeeModel,String> {

}
