package domain.board;

import domain.Country;
import domain.Path;
import domain.Position;
import domain.TableSetting;
import domain.piece.PieceInfo;
import java.util.Map;

public class Board {
    private static final String NOT_MY_PIECE = "[ERROR] 본인 진영의 기물이 아닙니다.";

    private final BoardStates boardStates;

    public Board(TableSetting choTableSetting, TableSetting hanTableSetting) {
        this.boardStates = new BoardStates(choTableSetting, hanTableSetting);
    }

    public void validateFromPosition(Position from, Country country) {
        if (boardStates.getPieceCountry(from) != country) {
            throw new IllegalArgumentException(NOT_MY_PIECE);
        }
    }

    public void move(Position from, Position to) {
        Path path = boardStates.getPiecePath(from, to);

        boolean canMove = boardStates.canMovePiece(from, path);
        if (canMove) {
            boardStates.changeState(from, to);
        }
    }

    public Map<Position, PieceInfo> getPieceInfos() {
        return boardStates.getPieceInfos();
    }
}
