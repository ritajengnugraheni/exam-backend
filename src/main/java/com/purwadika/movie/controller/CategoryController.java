package com.purwadika.movie.controller;

import java.util.List;

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
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired 
	private MovieRepo movieRepo;
	
	@PostMapping("/addCategory")
	public Category addBodyCategory(@RequestBody Category category) {
		category.setId(0);
		Category saveBodyCategory = categoryRepo.save(category);
		return saveBodyCategory;
	}
	
	@GetMapping("/allCategory")
	public Iterable<Category> getAllCatgory() {
		Iterable<Category> viewCategory = categoryRepo.findAll();
		return viewCategory;
	}
	
	@PutMapping("/editBodyCategory/{idCategory}")
	public Category editBodyCategory(@RequestBody Category category,@PathVariable int idCategory ) {
		Category findCategory = categoryRepo.findById(idCategory).get();
		
		category.setId(idCategory);
		category.setMovie(findCategory.getMovie());
		return categoryRepo.save(category);
	}
	
	@DeleteMapping("/{idCategory}/{idMovie}")
	public void deleteRelasi (@PathVariable int idCategory, @PathVariable int idMovie) {
		Movie findMovie = movieRepo.findById(idMovie).get();
		
		Category findCategory=categoryRepo.findById(idCategory).get();
		
		findMovie.getCategory().forEach(category -> {
			if(category.getId()== idCategory) {
			List<Movie> categoryMovie= category.getMovie();
			categoryMovie.remove(findMovie);
			categoryRepo.save(category);
			}
		});
		
	
	}
	
	
	
}
