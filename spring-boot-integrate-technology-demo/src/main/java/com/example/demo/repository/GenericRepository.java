package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository <T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    Optional<T> findById(ID id);

    <S extends T> S save(S entity);
}