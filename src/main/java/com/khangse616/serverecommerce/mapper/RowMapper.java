package com.khangse616.serverecommerce.mapper;

public interface RowMapper<T, S> {
    T mapRow(S s);
}
