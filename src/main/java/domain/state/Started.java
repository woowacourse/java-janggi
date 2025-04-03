package domain.state;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceColor;
import java.util.List;

public abstract class Started implements State {

    public static final double RED_HANDICAP = 1.5;
    public static final double BLUE_HANDICAP = 0;

    protected final Board board;

    protected Started(Board board) {
        this.board = board;
    }

    @Override
    public double getRedTeamScore() {
        return calculateTeamScore(PieceColor.RED, RED_HANDICAP);
    }

    @Override
    public double getBlueTeamScore() {
        return calculateTeamScore(PieceColor.BLUE, BLUE_HANDICAP);
    }

    @Override
    public Board getBoard() {
        return board;
    }

    private Double calculateTeamScore(PieceColor teamColor, double handicap) {
        List<Piece> remainPieces = board.getPieceByColor(teamColor);

        return remainPieces.stream()
                .map(Piece::getPieceScore)
                .reduce(handicap, Double::sum);
    }

    @Override
    public State startGame(Board board, PieceColor pieceColor) {
        throw new UnsupportedOperationException("게임이 이미 진행중입니다.");
    }
}
