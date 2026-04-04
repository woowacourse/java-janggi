package domain;

import java.util.Objects;

public record MoveCandidate(Position source, Destinations destinations) {

    public MoveCandidate {
        Objects.requireNonNull(source, "출발 지점은 필수입니다.");
        Objects.requireNonNull(destinations, "목적지 정보는 필수입니다.");
    }

    public void validate(Position target) {
        destinations.validateDestinations(target);
    }
}
