package domain.constant;

import domain.Position;
import java.util.Arrays;
import java.util.List;

public enum InitMaSangPosition {
    CHO(Country.CHO, List.of(
            Position.create(1, 2), Position.create(1, 3), Position.create(1, 7), Position.create(1, 8)
    )),
    HAN(Country.HAN, List.of(
            Position.create(10, 2), Position.create(10, 3), Position.create(10, 7), Position.create(10, 8)
    ));

    private final Country country;
    private final List<Position> positions;

    InitMaSangPosition(Country country, List<Position> positions) {
        this.country = country;
        this.positions = positions;
    }

    public static List<Position> getPositionsByCountry(Country country) {
        return Arrays.stream(values())
                .filter(maSang -> maSang.country == country)
                .findAny()
                .map(maSang -> maSang.positions)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 나라입니다."));
    }
}
