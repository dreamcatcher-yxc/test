package com.example.demo.repository;

import java.util.List;

public interface SpecialRepository<T> {

    List<T> runQuerySql(String sql, Object... parameters);

}
