package com.example.reactivecouchbase.repository;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.example.reactivecouchbase.model.Employee;




@Repository
@N1qlPrimaryIndexed
@ViewIndexed(designDoc="employee",viewName="all")
public interface EmployeeRepository extends ReactiveCouchbaseRepository<Employee, Integer>{

}
