package janggi.dto;

import janggi.domain.piece.Camp;
import janggi.view.format.CampFormat;

public record CampDto(
        String name,
        String color
) {
    public static CampDto from(Camp camp) {
        CampFormat campFormat = CampFormat.from(camp);
        return new CampDto(campFormat.getName(), campFormat.getColor());
    }
}
