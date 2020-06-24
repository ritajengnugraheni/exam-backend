package com.purwadika.movie.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purwadika.movie.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
