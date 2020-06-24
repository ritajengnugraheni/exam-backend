package com.purwadika.movie.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purwadika.movie.dao.CategoryRepo;
import com.purwadika.movie.dao.MovieRepo;
import com.purwadika.movie.entity.Category;
import com.purwadika.movie.entity.Movie;

@RestController 
@RequestMapping("/movies")
public class MovieController {

	@Autowired 
	private MovieRepo movieRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@PostMapping("/createMovieOnly")
	public Movie createBodyMovie(@RequestBody Movie movie) {
		movie.setId(0);
		return movieRepo.save(movie);
	}
	
	@PutMapping("/editBodyMovie")
	public Movie editBodyMovie(@RequestBody Movie movie) {
		Optional<Movie> findMovie = movieRepo.findById(movie.getId());
		
		if (findMovie.get()== null) {
			throw new RuntimeException("Not Found");
		}
		return movieRepo.save(movie);
	}
	
	@GetMapping("/readMovie")
	public Iterable<Movie> readMovie (){
		Iterable<Movie> viewMovie = movieRepo.findAll();
		return viewMovie;
	}
	
	// delete movie aja tapi relasi tidak 
	@DeleteMapping("/deleteBodyMovie/{id}")
	public void deleteBodyMovie(@PathVariable int id ) {
		Optional<Movie> findMovie = movieRepo.findById(id);
		
		if (findMovie.toString()=="Option.empty") {
			throw new RuntimeException("Not Found");
		}
		
		movieRepo.deleteById(id);
	}
	
	
//	@DeleteMapping("/{movieId}")
//	public void deleteMovieAndRelasi(@PathVariable int movieId) {
//		Movie findMovie = movieRepo.findById(movieId).get();
//		
//		findMovie.setCategory(null);
//		
//		movieRepo.save(findMovie);
//		movieRepo.deleteById(movieId);
//	}
//	
	
	// delete movie with relasi without category 
	@DeleteMapping("/{movieId}")
	public void deleteMovieAndRelasi(@PathVariable int movieId) {
		Movie findMovie = movieRepo.findById(movieId).get();
		
		findMovie.getCategory().forEach(category -> {
			List<Movie> categoryMovie= category.getMovie();
			categoryMovie.remove(findMovie);
			categoryRepo.save(category);
		});
		
		findMovie.setCategory(null);
		movieRepo.deleteById(movieId);
	}
	
	@PostMapping("/{movieId}/category/{categoryId}")
	public Movie addCategoryToMovie (@PathVariable int categoryId, @PathVariable int movieId) {
		Category findCategory = categoryRepo.findById(categoryId).get();
		
		Movie findMovie= movieRepo.findById(movieId).get();
		
		findMovie.getCategory().add(findCategory);
		return movieRepo.save(findMovie);
	}
	
}
