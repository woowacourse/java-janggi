package domain;

import domain.coordinate.Position;
import domain.piece.PieceType;

import java.util.Map;

public class GameEndJudge {

    public boolean isGameOver(Map<Position, CellSnapshot> boardPieces) {
        return boardPieces.values().stream()
                .filter(cell -> cell.type() == PieceType.KING)
                .map(CellSnapshot::side)
                .distinct()
                .count() < 2;
    }

    public Side getWinner(Map<Position, CellSnapshot> boardPieces) {
        if (!isGameOver(boardPieces)) {
            throw new IllegalStateException("게임이 종료되지 않았습니다.");
        }
        return boardPieces.values().stream()
                .filter(cell -> cell.type() == PieceType.KING)
                .map(CellSnapshot::side)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("게임이 종료되지 않았습니다."));
    }
}