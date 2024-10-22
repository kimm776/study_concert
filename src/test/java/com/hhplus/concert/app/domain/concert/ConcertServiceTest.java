package com.hhplus.concert.app.domain.concert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ConcertOptionRepository concertOptionRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ConcertService concertService;

    @BeforeEach
    public void setup() {
    }

    @DisplayName("[예외] 콘서트 날짜 조회 : 존재하지 않는 콘서트 테스트")
    @Test
    public void testGetAvailableDates_ConcertNotFound() {
        // given
        Long tokenId = 1L;
        Long concertId = -11L;

        given(concertRepository.findById(concertId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> concertService.getAvailableDates(concertId, tokenId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("콘서트가 존재하지 않습니다.");
    }

    @DisplayName("[정상] 콘서트 날짜 조회")
    @Test
    public void testGetAvailableDates_Success() {
        // given
        Long tokenId = 1L;
        Long concertId = 11L;
        List<ConcertOption> expectedConcertOptions = new ArrayList<>();

        given(concertRepository.findById(concertId)).willReturn(Optional.of(new Concert(concertId, "테스트 콘서트", 10000.0)));
        given(concertOptionRepository.findByConcertIdAndReservationDateAfter(concertId)).willReturn(expectedConcertOptions);

        // when
        List<ConcertOption> actualConcertOptions = concertService.getAvailableDates(concertId, tokenId);

        // then
        assertEquals(expectedConcertOptions, actualConcertOptions);

    }

    @DisplayName("[예외] 콘서트 좌석 조회 : 존재하지 않는 콘서트 옵션 테스트")
    @Test
    void testGetAvailableSeats_ConcertOptionNotFound() {
        // given
        Long tokenId = 1L;
        Long concertOptionId = -111L;

        given(concertOptionRepository.findById(concertOptionId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> concertService.getAvailableSeats(concertOptionId, tokenId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 콘서트 옵션이 존재하지 않습니다.");
    }

    @DisplayName("[예외] 콘서트 좌석 조회 : 존재하지 않는 좌석 테스트")
    @Test
    void testGetAvailableSeats_NoSeatsAvailable() {
        // given
        Long tokenId = 1L;
        Long concertId = 11L;
        Long concertOptionId = 111L;

        ConcertOption concertOption = new ConcertOption(concertId, LocalDateTime.now(), 50);
        given(concertOptionRepository.findById(concertOptionId)).willReturn(Optional.of(concertOption));
        given(seatRepository.findByConcertOptionId(concertOptionId)).willReturn(new ArrayList<>());

        // when & then
        assertThatThrownBy(() -> concertService.getAvailableSeats(concertOptionId, tokenId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 콘서트에 대한 좌석이 존재하지 않습니다.");
    }

    @DisplayName("[예외] 콘서트 좌석 조회 : 모든 좌석이 점유된 경우")
    @Test
    void testGetAvailableSeats_AllSeatsOccupied() {
        // given
        Long tokenId = 1L;
        Long concertId = 11L;
        Long concertOptionId = 111L;

        ConcertOption concertOption = new ConcertOption(concertId, LocalDateTime.now(), 2);
        List<Seat> occupiedSeats = new ArrayList<>();
        occupiedSeats.add(new Seat(concertOptionId, 1L, "R1", SeatStatus.OCCUPIED, LocalDateTime.now()));
        occupiedSeats.add(new Seat(concertOptionId, 2L, "R2", SeatStatus.OCCUPIED, LocalDateTime.now()));

        given(concertOptionRepository.findById(concertOptionId)).willReturn(Optional.of(concertOption));
        given(seatRepository.findByConcertOptionId(concertOptionId)).willReturn(occupiedSeats);

        // when & then
        assertThatThrownBy(() -> concertService.getAvailableSeats(concertOptionId, tokenId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 콘서트의 잔여 좌석이 없습니다.");
    }

    @DisplayName("[예외] 좌석 예약 : 존재하지 않는 좌석 테스트")
    @Test
    void testReserveSeat_SeatNotFound() {
        Long userId = 1L;
        Long seatId = 11L;
        Long concertId = 111L;

        // Given
        given(seatRepository.findById(seatId)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> concertService.reserveSeat(seatId, userId, concertId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("좌석이 존재하지 않습니다.");
    }

    @DisplayName("[예외] 좌석 예약 : 임시배정된 좌석 테스트")
    @Test
    void testReserveSeat_SeatAlreadyOccupied() {
        Long userId = 1L;
        Long seatId = 11L;
        Long concertId = 111L;
        Long concertOptionId = 1111L;

        // Given
        Seat seat = new Seat(concertOptionId, 2L, "R1", SeatStatus.OCCUPIED, null);
        given(seatRepository.findById(seatId)).willReturn(Optional.of(seat));

        // When & Then
        assertThatThrownBy(() -> concertService.reserveSeat(seatId, userId, concertId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 선택된 좌석입니다.");
    }

}

