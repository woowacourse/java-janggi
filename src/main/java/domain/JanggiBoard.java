package domain;

import domain.piece.*;

import domain.position.Position;
import java.util.Collections;
import java.util.Map;

public class JanggiBoard implements PieceProvider {

    private final Map<Position, Piece> janggiBoard;
    private Team turn;

    public JanggiBoard(JanggiBoardInitializer initializer) {
        this.janggiBoard = initializer.init();
        turn = Team.CHO;
    }

    public void move(Position from, Position to, Piece currentPiece) {
        janggiBoard.put(to, currentPiece);
        janggiBoard.put(from, new Blank());
        changeTurn();
    }

    public boolean isGameOver() {
        return janggiBoard.values().stream()
                .filter(piece -> piece.getPieceType() == PieceType.KING)
                .count() < 2;
    }

    public Map<Position, Piece> getJanggiBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }

    public Team getTurn() {
        return turn;
    }

    private void changeTurn() {
        if (Team.CHO == turn) {
            turn = Team.HAN;
            return;
        }
        turn = Team.CHO;
    }

    @Override
    public boolean isBlank(Position position) {
        Piece piece = janggiBoard.get(position);
        return piece instanceof Blank;
    }

    @Override
    public boolean isCannon(Position position) {
        Piece piece = janggiBoard.get(position);
        return piece instanceof Cannon;
    }

    @Override
    public Piece getPiece(Position position) {
        return janggiBoard.get(position);
    }
}
