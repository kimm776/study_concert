package com.hhplus.concert.app.infrastructure.concert;

import com.hhplus.concert.app.domain.concert.Concert;
import com.hhplus.concert.app.domain.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public Optional<Concert> findById(Long concertId) {
        return concertJpaRepository.findById(concertId)
                .map(Concert::fromEntity);
    }

}
