package com.igianesini.goldenraspberry.services;

import com.igianesini.goldenraspberry.domain.Movie;
import com.igianesini.goldenraspberry.domain.Producer;
import com.igianesini.goldenraspberry.dto.AwardIntervalDTO;
import com.igianesini.goldenraspberry.dto.AwardIntervalResponseDTO;
import com.igianesini.goldenraspberry.repositories.MovieRepository;
import com.igianesini.goldenraspberry.repositories.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class AwardService {

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private MovieRepository movieRepository;

    public AwardIntervalResponseDTO getAwardIntervals() {
        List<Movie> savedMovies = movieRepository.findAll();

        List<Producer> producers = producerRepository.findProducersWithMultipleWinningMovies();

        List<AwardIntervalDTO> allIntervals = producers.stream()
                .flatMap(this::calculateIntervalsForProducer)
                .toList();

        int minInterval = findMinInterval(allIntervals);
        int maxInterval = findMaxInterval(allIntervals);

        List<AwardIntervalDTO> minIntervals = filterIntervalsByValue(allIntervals, minInterval);
        List<AwardIntervalDTO> maxIntervals = filterIntervalsByValue(allIntervals, maxInterval);

        return new AwardIntervalResponseDTO(
                minIntervals,
                maxIntervals
        );
    }

    private Stream<AwardIntervalDTO> calculateIntervalsForProducer(Producer producer) {
        List<Integer> winnerYears = getSortedWinnerYears(producer);

        return IntStream.range(0, winnerYears.size() - 1)
                .mapToObj(i -> createAwardIntervalDTO(producer, winnerYears, i));
    }

    private List<Integer> getSortedWinnerYears(Producer producer) {
        return producer.getMovies().stream()
                .filter(Movie::isWinner)
                .map(Movie::getYear)
                .sorted()
                .toList();
    }

    private AwardIntervalDTO createAwardIntervalDTO(Producer producer, List<Integer> winnerYears, int index) {
        int previousWin = winnerYears.get(index);
        int followingWin = winnerYears.get(index + 1);
        int interval = followingWin - previousWin;
        return new AwardIntervalDTO(producer.getName(), interval, previousWin, followingWin);
    }

    private int findMinInterval(List<AwardIntervalDTO> intervals) {
        return intervals.stream()
                .mapToInt(AwardIntervalDTO::getInterval)
                .min()
                .orElse(0);
    }

    private int findMaxInterval(List<AwardIntervalDTO> intervals) {
        return intervals.stream()
                .mapToInt(AwardIntervalDTO::getInterval)
                .max()
                .orElse(0);
    }

    private List<AwardIntervalDTO> filterIntervalsByValue(List<AwardIntervalDTO> intervals, int value) {
        return intervals.stream()
                .filter(interval -> interval.getInterval() == value)
                .collect(Collectors.toList());
    }
}
