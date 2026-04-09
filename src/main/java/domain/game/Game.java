package domain.game;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Position;
import domain.board.SetUp;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Optional;

public class Game {
    private final Board board;
    private Camp currentTurn;
    private boolean finished;

    public static Game start(SetUp choSetUp, SetUp hanSetUp) {
        return new Game(choSetUp, hanSetUp);
    }

    public static Game restore(Board board, Camp currentTurn, boolean finished) {
        return new Game(board, currentTurn, finished);
    }

    private Game(SetUp choSetUp, SetUp hanSetUp) {
        this(new Board(BoardInitializer.init(choSetUp, hanSetUp)), Camp.CHO, false);
    }

    private Game(Board board, Camp currentTurn, boolean finished) {
        this.board = board;
        this.currentTurn = currentTurn;
        this.finished = finished;
    }

    public Board board() {
        return board;
    }

    public Camp currentTurn() {
        return currentTurn;
    }

    public void playMove(Position from, Position to) {
        Piece piece = board.findBy(from);
        validateTurn(piece);

        Optional<Piece> capturedPiece = board.movePiece(from, to);

        finished = capturedPiece
                .map(target -> target.type() == PieceType.GENERAL)
                .orElse(false);

        if (finished) {
            return;
        }

        changeTurn();
    }

    public void passTurn() {
        changeTurn();
    }

    private void validateTurn(Piece piece) {
        if (piece.camp() != currentTurn) {
            throw new IllegalArgumentException("[ERROR] 현재 턴의 기물만 움직일 수 있습니다.");
        }
    }

    private void changeTurn() {
        currentTurn = currentTurn.opponent();
    }

    public boolean isFinished() {
        return finished;
    }

    public int scoreOf(Camp camp) {
        return board.scoreOf(camp);
    }
}
