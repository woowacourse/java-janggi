package janggi.dto;

import janggi.domain.piece.Camp;
import janggi.formatter.CampFormatter;

public record CampDto(String camp) {

    private static final String CHO_NAME = "초";

    public static CampDto from(Camp camp) {
        return new CampDto(CampFormatter.format(camp));
    }

    public boolean isCho() {
        return camp.equals(CHO_NAME);
    }
}
