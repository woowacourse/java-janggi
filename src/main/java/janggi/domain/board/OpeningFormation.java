package janggi.domain.board;

import janggi.domain.movestrategy.ElephantStrategy;
import janggi.domain.movestrategy.HorseStrategy;
import janggi.domain.piece.ElephantPiece;
import janggi.domain.piece.HorsePiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import java.util.Arrays;
import java.util.Map;

public enum OpeningFormation {
    LEFT_ELEPHANT(1, 2, 3, 7, 8),
    RIGHT_ELEPHANT(2, 3, 2, 8, 7),
    INNER_ELEPHANT(3, 3, 2, 7, 8),
    OUTER_ELEPHANT(4, 2, 3, 8, 7);

    private final int choice;
    private final int leftElephantX;
    private final int leftHorseX;
    private final int rightElephantX;
    private final int rightHorseX;

    OpeningFormation(int choice, int leftElephantX, int leftHorseX, int rightElephantX,
                     int rightHorseX) {
        this.choice = choice;
        this.leftElephantX = leftElephantX;
        this.leftHorseX = leftHorseX;
        this.rightElephantX = rightElephantX;
        this.rightHorseX = rightHorseX;
    }

    public static OpeningFormation from(int choice) {
        return Arrays.stream(values())
                .filter(openingFormation -> openingFormation.choice == choice)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 차림 선택 입니다."));
    }

    public void initialize(Map<Position, Piece> board, Team team, int y) {
        board.put(new Position(leftElephantX, y), new ElephantPiece(team, new
                ElephantStrategy()));
        board.put(new Position(rightElephantX, y), new ElephantPiece(team, new
                ElephantStrategy()));
        board.put(new Position(leftHorseX, y), new HorsePiece(team, new
                HorseStrategy()));
        board.put(new Position(rightHorseX, y), new HorsePiece(team, new
                HorseStrategy()));
    }
}
