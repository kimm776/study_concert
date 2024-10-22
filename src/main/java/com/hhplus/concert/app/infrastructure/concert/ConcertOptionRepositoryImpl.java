package com.hhplus.concert.app.infrastructure.concert;

import com.hhplus.concert.app.domain.concert.ConcertOption;
import com.hhplus.concert.app.domain.concert.ConcertOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertOptionRepositoryImpl implements ConcertOptionRepository {

    private final ConcertOptionJpaRepository concertOptionJpaRepository;

    @Override
    public List<ConcertOption> findByConcertIdAndReservationDateAfter(Long concertId) {
        List<ConcertOptionEntity> entities = concertOptionJpaRepository.findByConcertIdAndReservationDateAfter(concertId, LocalDateTime.now());
        return entities.stream()
                .map(ConcertOption::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConcertOption> findById(Long concertOptionId) {
        return concertOptionJpaRepository.findById(concertOptionId)
                .map(ConcertOption::fromEntity);
    }

}
