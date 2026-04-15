package controller;

import domain.board.formation.FormationType;

public final class FormationConverter {

    private FormationConverter() {}

    public static FormationType convert(int formationType) {
        if (formationType == 1) return FormationType.LEFT_GIWMA;
        if (formationType == 2) return FormationType.RIGHT_GIWMA;
        if (formationType == 3) return FormationType.WONANGMA;
        if (formationType == 4) return FormationType.YANGGWIMA;

        throw new IllegalArgumentException("올바르지 않은 상차림 번호입니다.");
    };

}
