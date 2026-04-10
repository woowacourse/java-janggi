package domain.game;

import domain.board.Board;
import domain.board.BoardPiece;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import java.util.Map;
import java.util.stream.Collectors;

public class LegalMoveAnalyzer {
    private static final String SELF_CHECK_ERROR_MESSAGE = "[ERROR] 자신의 장군이 공격받는 수는 둘 수 없습니다.";

    private final ThreatAnalyzer threatAnalyzer = new ThreatAnalyzer();

    public void validate(Board board, Position from, Position to) {
        Camp movingCamp = board.findBy(from).camp();
        Board simulatedBoard = simulate(board);

        simulatedBoard.movePiece(from, to);

        validateThreatened(simulatedBoard, movingCamp);
    }

    private void validateThreatened(Board simulatedBoard, Camp movingCamp) {
        if (threatAnalyzer.isInCheck(simulatedBoard, movingCamp)) {
            throw new IllegalArgumentException(SELF_CHECK_ERROR_MESSAGE);
        }
    }

    private Board simulate(Board board) {
        Map<Position, Piece> copiedBoard = board.pieces().stream()
                .collect(Collectors.toMap(
                        BoardPiece::position,
                        boardPiece -> new Piece(boardPiece.camp(), boardPiece.pieceType())
                ));

        return new Board(copiedBoard);
    }
}
