package com.hhplus.concert.app.domain.concert;

import com.hhplus.concert.app.infrastructure.concert.ConcertEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Concert {

    private Long id;
    private String title;
    private double price;

    public static Concert fromEntity(ConcertEntity e) {
        return new Concert(e.getId(), e.getTitle(), e.getPrice());
    }

    public ConcertEntity toEntity() {
        return new ConcertEntity(id, title, price);
    }

}
