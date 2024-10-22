package com.hhplus.concert.app.infrastructure.concert;

import com.hhplus.concert.app.domain.concert.Seat;
import com.hhplus.concert.app.domain.concert.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> findByConcertOptionId(Long concertOptionId) {
        List<SeatEntity> entities = seatJpaRepository.findByConcertOptionId(concertOptionId);
        return entities.stream()
                .map(Seat::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Seat> findById(Long seatId) {
        return seatJpaRepository.findById(seatId)
                .map(Seat::fromEntity);
    }

    @Override
    public void saveSeat(Seat seat) {
        seatJpaRepository.save(seat.toEntity());
    }

}