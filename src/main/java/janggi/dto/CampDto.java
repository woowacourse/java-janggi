package janggi.dto;

import janggi.domain.piece.Camp;
import janggi.formatter.CampFormatter;

public record CampDto(String camp) {

    public static CampDto from(Camp camp) {
        return new CampDto(CampFormatter.format(camp));
    }
}
