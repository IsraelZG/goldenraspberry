package com.igianesini.goldenraspberry;

import com.igianesini.goldenraspberry.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoldenRaspberryApplication implements CommandLineRunner {

	@Autowired
	private MovieService movieService;

	public static void main(String[] args) {
		SpringApplication.run(GoldenRaspberryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		movieService.loadMovieData();
	}
}
