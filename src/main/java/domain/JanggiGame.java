package domain;

import domain.piece.Piece;

import java.util.Map;

public class JanggiGame {
    private static final double DUM_SCORE = 1.5;

    private final JanggiBoard board;
    private Country currTurn;

    public JanggiGame(Map<JanggiCoordinate, Piece> initBoard, Country currTurn) {
        this.board = new JanggiBoard(initBoard);
        this.currTurn = currTurn;
    }

    public void movePlayerPiece(JanggiCoordinate from, JanggiCoordinate to) {
        Piece piece = board.findPieceByCoordinate(from);
        validatePieceMove(piece, from, to);
        board.movePiece(from, to);
        convertPlayerTurn();
    }

    public boolean isGameOver() {
        return !board.isChoGungAlive() || !board.isHanGungAlive();
    }

    public Country getWinner() {
        if (board.isHanGungAlive()) {
            return Country.HAN;
        }
        return Country.CHO;
    }

    public double getCountryScore(Country country) {
        if (country == Country.HAN) {
            return board.getPieceScoreSum(country) + DUM_SCORE;
        }
        return board.getPieceScoreSum(country);
    }

    private void validatePieceMove(Piece piece, JanggiCoordinate from, JanggiCoordinate to) {
        validatePlayerTurnPiece(from, piece.getCountry());
        piece.validateDestination(board, from, to);
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
