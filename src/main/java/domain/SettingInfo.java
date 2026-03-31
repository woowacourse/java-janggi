package domain;

import domain.position.Position;

public record SettingInfo(
        Position sang1,
        Position sang2,
        Position ma1,
        Position ma2
) {
}
