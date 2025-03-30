package view;

import piece.Country;
import position.LineDirection;

public record LineSettingDto(Country country, LineDirection direction) {
}
