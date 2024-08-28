package com.igianesini.goldenraspberry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igianesini.goldenraspberry.domain.Movie;
import com.igianesini.goldenraspberry.domain.Producer;
import com.igianesini.goldenraspberry.domain.Studio;
import com.igianesini.goldenraspberry.dto.AwardIntervalDTO;
import com.igianesini.goldenraspberry.dto.AwardIntervalResponseDTO;
import com.igianesini.goldenraspberry.repositories.MovieRepository;
import com.igianesini.goldenraspberry.repositories.ProducerRepository;
import com.igianesini.goldenraspberry.repositories.StudioRepository;
import com.igianesini.goldenraspberry.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProducerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
        producerRepository.deleteAll();
        studioRepository.deleteAll();
    }

    @Test
    void testGetAwardIntervals() throws Exception {
        Producer producer1 = new Producer("Producer One");
        Producer producer2 = new Producer("Producer Two");
        Producer producer3 = new Producer("Producer Three");
        Producer producer4 = new Producer("Producer Four");

        producerRepository.saveAll(Set.of(producer1, producer2, producer3, producer4));

        Studio studio1 = new Studio("Studio 1");

        studioRepository.save(studio1);

        Movie movie1 = new Movie(1995, "Movie 1", true, Set.of(studio1), Set.of(producer1, producer2));
        Movie movie2 = new Movie(1995, "Movie 2", false, Set.of(studio1), Set.of(producer3, producer4));
        Movie movie3 = new Movie(1996, "Movie 3", true, Set.of(studio1), Set.of(producer1, producer3));
        Movie movie4 = new Movie(1996, "Movie 4", false, Set.of(studio1), Set.of(producer3, producer4));
        Movie movie5 = new Movie(2000, "Movie 5", true, Set.of(studio1), Set.of(producer1, producer4));
        Movie movie6 = new Movie(2000, "Movie 6", false, Set.of(studio1), Set.of(producer3));
        Movie movie7 = new Movie(2020, "Movie 7", true, Set.of(studio1), Set.of(producer2, producer4));
        Movie movie8 = new Movie(2020, "Movie 8", false, Set.of(studio1), Set.of(producer4));
        Movie movie9 = new Movie(2021, "Movie 9", true, Set.of(studio1), Set.of(producer3, producer4));
        Movie movie10 = new Movie(2021, "Movie 10", false, Set.of(studio1), Set.of(producer1, producer2));

        Set<Movie> movies = Set.of(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9, movie10);
        movieRepository.saveAll(movies);

        mockMvc.perform(get("/api/producers/award-intervals"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().json("{" +
                        "'min':[" +
                        "{" +
                        "'producer':'Producer One'," +
                        "'interval':1," +
                        "'previousWin':1995," +
                        "'followingWin':1996" +
                        "}," +
                        "{" +
                        "'producer':'Producer Four'," +
                        "'interval':1," +
                        "'previousWin':2020," +
                        "'followingWin':2021" +
                        "}]," +
                        "'max':[" +
                        "{" +
                        "'producer':'Producer Two'," +
                        "'interval':25," +
                        "'previousWin':1995," +
                        "'followingWin':2020" +
                        "}," +
                        "{" +
                        "'producer':'Producer Three'," +
                        "'interval':25," +
                        "'previousWin':1996," +
                        "'followingWin':2021" +
                        "}]" +
                        "}"));
    }

    @Test
    void testAwardIntervalsWithStandardCSVFile() throws Exception {
        movieService.loadMovieData();

        MvcResult result = mockMvc.perform(get("/api/producers/award-intervals"))
                .andExpect(status().isOk())
                .andReturn();

        AwardIntervalResponseDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), AwardIntervalResponseDTO.class);

        assertEquals(1, response.getMin().size());
        AwardIntervalDTO min =  response.getMin().getFirst();
        assertEquals("Joel Silver", min.getProducer());
        assertEquals(1, min.getInterval());
        assertEquals(1990, min.getPreviousWin());
        assertEquals(1991, min.getFollowingWin());

        assertEquals(1, response.getMax().size());
        AwardIntervalDTO max = response.getMax().getFirst();
        assertEquals("Matthew Vaughn", max.getProducer());
        assertEquals(13, max.getInterval());
        assertEquals(2002, max.getPreviousWin());
        assertEquals(2015, max.getFollowingWin());
    }
}
