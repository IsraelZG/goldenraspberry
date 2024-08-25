package com.igianesini.goldenraspberry.services;

import com.igianesini.goldenraspberry.domain.Movie;
import com.igianesini.goldenraspberry.domain.Producer;
import com.igianesini.goldenraspberry.domain.Studio;
import com.igianesini.goldenraspberry.repositories.MovieRepository;
import com.igianesini.goldenraspberry.repositories.ProducerRepository;
import com.igianesini.goldenraspberry.repositories.StudioRepository;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;
    private final StudioRepository studioRepository;

    @Autowired
    public MovieService(
            MovieRepository movieRepository,
            ProducerRepository producerRepository,
            StudioRepository studioRepository
    ) {
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository;
        this.studioRepository = studioRepository;
    }

    public void loadMovieData() {
        File resource = new File("movielist.csv");
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .build();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(resource))
                .withCSVParser(parser)
                .build()) {
            List<String[]> allRows = csvReader.readAll();
            allRows.removeFirst();

            Set<String> allStudiosNames = allRows.stream()
                    .flatMap(row -> Stream.of(row[2].split(",\\s*|\\sand\\s")))
                    .map(String::trim)
                    .collect(Collectors.toSet());
            Set<Studio> allStudios = allStudiosNames.stream()
                    .map(Studio::new)
                    .collect(Collectors.toSet());
            studioRepository.saveAll(allStudios);

            Set<String> allProducersNames = allRows.stream()
                    .flatMap(row -> Stream.of(row[3].split(",\\s*|\\sand\\s")))
                    .map(String::trim)
                    .collect(Collectors.toSet());
            Set<Producer> allProducers = allProducersNames.stream()
                    .map(Producer::new)
                    .collect(Collectors.toSet());
            producerRepository.saveAll(allProducers);

            Set<Movie> allMovies = allRows.stream()
                    .map(values -> {
                        Movie movie = new Movie();
                        movie.setYear(Integer.parseInt(values[0]));
                        movie.setTitle(values[1]);
                        movie.setStudios(parseStudios(values[2]));
                        movie.setProducers(parseProducers(values[3]));
                        movie.setWinner("yes".equalsIgnoreCase(values[4]));
                        return movie;
                    })
                    .collect(Collectors.toSet());
            movieRepository.saveAll(allMovies);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Studio> parseStudios(String studiosString) {
        List<String> studiosList = Arrays.asList(studiosString.split(",\\s*"));
        return new HashSet<>(studioRepository.findByNameIn(studiosList));
    }

    private Set<Producer> parseProducers(String producersString) {
        List<String> producersList = Arrays.asList(producersString.split(",\\s*|\\sand\\s"));
        return new HashSet<>(producerRepository.findByNameIn(producersList));
    }
}
