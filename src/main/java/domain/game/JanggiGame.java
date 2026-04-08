package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.FormationType;
import domain.piece.Piece;
import domain.position.Position;

public class JanggiGame {
    private Turn turn;
    private final Board board;

    public JanggiGame(Turn turn, Board board) {
        this.turn = turn;
        this.board = board;
    }

    public static JanggiGame of(FormationType choFormation, FormationType hanFormation) {
        return new JanggiGame(Turn.first(), BoardFactory.create(choFormation, hanFormation));
    }

    public void move(Position source, Position destination) {
        validateTurn(source);
        board.move(source, destination);
        turn = turn.next();
    }

    private void validateTurn(Position source) {
        Piece sourcePiece = board.pieceAt(source);

        if (!sourcePiece.isNotEmpty()) {
            throw new IllegalArgumentException("빈 칸을 선택하셨습니다.");
        }

        if (!sourcePiece.belongsTo(turn.current())) {
            throw new IllegalArgumentException("현재 턴의 기물이 아닙니다.");
        }
    }

    public boolean isRunning() {
        return true; // TODO: 종료 조건 구현
    }

    public Team currentTurn() {
        return turn.current();
    }

    public Board getBoard() {
        return board;
    }
}
