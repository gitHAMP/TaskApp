package com.hampcode.service;

import java.util.List;

public interface CrudService<T, ID> {

	T create(T entity);

	T update(ID id, T entity);

	List<T> getAll();

	T getOne(ID id);

	void deleteById(ID id);

}
