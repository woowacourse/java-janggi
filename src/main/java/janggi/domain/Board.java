package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.side.Chu;
import janggi.domain.side.Han;
import janggi.domain.side.Team;
import janggi.dto.BoardSpot;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {

    private static final int FIRST_INDEX = 1;
    private static final int LAST_X_INDEX = 9;
    private static final int LAST_Y_INDEX = 10;

    private final Team chu;
    private final Team han;

    private Board(Team chu, Team han) {
        this.chu = chu;
        this.han = han;
    }

    public static Board createInitialBoard() {
        return new Board(Chu.createInitialChu(), Han.createInitialHan());
    }

    public List<BoardSpot> makeSpots() {
        List<BoardSpot> chuBoardSpots = chu.makeSpots();
        List<BoardSpot> hanBoardSpots = han.makeSpots();
        List<BoardSpot> boardSpots = new ArrayList<>(chuBoardSpots);
        boardSpots.addAll(hanBoardSpots);
        return boardSpots;
    }

    public Piece findPiece(List<String> parsedPiecePosition) {
        Position inputPosition = Position.makePosition(parsedPiecePosition);
        validateRange(inputPosition);
        return findPiece(inputPosition)
            .orElseThrow(() -> new IllegalArgumentException("입력한 위치에 기물이 없습니다."));
    }

    public Optional<Piece> findPiece(Position position) {
        Optional<Piece> chuPiece = chu.findPiece(position);
        if (chuPiece.isPresent()) {
            return chuPiece;
        }
        return han.findPiece(position);
    }

    private void validateRange(Position inputPosition) {
        int x = inputPosition.getX();
        int y = inputPosition.getY();
        if (isNotInRange(FIRST_INDEX, LAST_X_INDEX, x) || isNotInRange(FIRST_INDEX, LAST_Y_INDEX, y)) {
            throw new IllegalArgumentException("입력한 좌표가 장기판 범위 밖입니다.");
        }
    }

    private boolean isInRange(int start, int last, int index) {
        return index >= start && index <= last;
    }

    private boolean isNotInRange(int start, int last, int index) {
        return !isInRange(start, last, index);
    }
}
