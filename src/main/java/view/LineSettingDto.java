package view;

import domain.piece.Country;
import domain.position.LineDirection;

public record LineSettingDto(Country country, LineDirection direction) {
}
