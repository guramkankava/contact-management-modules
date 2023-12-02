package com.github.guramkankava.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface PageableSearchService <T> {

    Page<T> findPage(Pageable pageable, T t);

}
