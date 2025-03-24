package domain;

import domain.piece.Piece;

import java.util.Map;

public class JanggiGame {
    private final JanggiBoard board;
    private Country currTurn;

    public JanggiGame(Map<JanggiCoordinate, Piece> initBoard) {
        board = new JanggiBoard(initBoard);
        currTurn = Country.CHO;
    }

    public void movePlayerPiece(JanggiCoordinate from, JanggiCoordinate to) {
        Piece piece = board.findPieceByCoordinate(from);
        validatePlayerTurnPiece(from, piece.getCountry());
        piece.validateMove(board, from, to);
        board.movePiece(from, to);

        convertPlayerTurn();
    }

    private void validatePlayerTurnPiece(JanggiCoordinate from, Country pieceCountry) {
        if (pieceCountry != currTurn) {
            throw new IllegalArgumentException("[ERROR] 플레이어의 기물이 아닙니다.");
        }
    }

    private void convertPlayerTurn() {
        if (currTurn == Country.CHO) {
            currTurn = Country.HAN;
            return;
        }
        currTurn = Country.CHO;
    }

    public Country getCurrTurn() {
        return currTurn;
    }

    public JanggiBoard getBoard() {
        return board;
    }
}
