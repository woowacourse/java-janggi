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
    LEFT_ELEPHANT(1, 2, 3, 7, 8, 3, 2, 8, 7),
    RIGHT_ELEPHANT(2, 3, 2, 8, 7, 2, 3, 7, 8),
    INNER_ELEPHANT(3, 3, 2, 7, 8, 3, 2, 7, 8),
    OUTER_ELEPHANT(4, 2, 3, 8, 7, 2, 3, 8, 7);

    private final int choice;
    private final int hanLeftElephantX;
    private final int hanLeftHorseX;
    private final int hanRightElephantX;
    private final int hanRightHorseX;
    private final int choLeftElephantX;
    private final int choLeftHorseX;
    private final int choRightElephantX;
    private final int choRightHorseX;
    private static final int HAN_BASE_ROW = BoardRange.MIN_Y;
    private static final int CHO_BASE_ROW = BoardRange.MAX_Y;

    OpeningFormation(int choice, int hanLeftElephantX, int hanLeftHorseX, int hanRightElephantX,
                     int hanRightHorseX, int choLeftElephantX, int choLeftHorseX,
                     int choRightElephantX, int choRightHorseX) {
        this.choice = choice;
        this.hanLeftElephantX = hanLeftElephantX;
        this.hanLeftHorseX = hanLeftHorseX;
        this.hanRightElephantX = hanRightElephantX;
        this.hanRightHorseX = hanRightHorseX;
        this.choLeftElephantX = choLeftElephantX;
        this.choLeftHorseX = choLeftHorseX;
        this.choRightElephantX = choRightElephantX;
        this.choRightHorseX = choRightHorseX;
    }

    public static OpeningFormation from(int choice) {
        return Arrays.stream(values())
                .filter(openingFormation -> openingFormation.choice == choice)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 차림 선택입니다."));
    }

    public void initializeHan(Map<Position, Piece> board) {
        place(board, Team.HAN, HAN_BASE_ROW, hanLeftElephantX, hanLeftHorseX, hanRightElephantX, hanRightHorseX);
    }

    public void initializeCho(Map<Position, Piece> board) {
        place(board, Team.CHO, CHO_BASE_ROW, choLeftElephantX, choLeftHorseX, choRightElephantX, choRightHorseX);
    }

    private void place(Map<Position, Piece> board, Team team, int y, int leftElephantX, int leftHorseX,
                       int rightElephantX, int rightHorseX) {
        board.put(new Position(leftElephantX, y), new ElephantPiece(team, new ElephantStrategy()));
        board.put(new Position(rightElephantX, y), new ElephantPiece(team, new ElephantStrategy()));
        board.put(new Position(leftHorseX, y), new HorsePiece(team, new HorseStrategy()));
        board.put(new Position(rightHorseX, y), new HorsePiece(team, new HorseStrategy()));
    }
}
