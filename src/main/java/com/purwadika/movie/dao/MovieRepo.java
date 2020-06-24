package com.purwadika.movie.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purwadika.movie.entity.Movie;

public interface MovieRepo extends JpaRepository<Movie, Integer> {

}
