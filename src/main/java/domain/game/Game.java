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
    private Optional<Camp> winner;

    public static Game start(SetUp choSetUp, SetUp hanSetUp) {
        return new Game(choSetUp, hanSetUp);
    }

    public static Game restore(Board board, Camp currentTurn, boolean finished) {
        return new Game(board, currentTurn, findWinner(currentTurn, finished));
    }

    private Game(SetUp choSetUp, SetUp hanSetUp) {
        this(new Board(BoardInitializer.init(choSetUp, hanSetUp)), Camp.CHO, Optional.empty());
    }

    private Game(Board board, Camp currentTurn, Optional<Camp> winner) {
        this.board = board;
        this.currentTurn = currentTurn;
        this.winner = winner;
    }

    public Board board() {
        return board;
    }

    public Camp currentTurn() {
        return currentTurn;
    }

    public TurnResult playMove(Position from, Position to) {
        Camp movingCamp = currentTurn;
        Camp opponentCamp = movingCamp.opponent();
        Piece piece = board.findBy(from);
        validateTurn(piece);

        Optional<Piece> capturedPiece = board.movePiece(from, to);
        Optional<Camp> determinedWinner = findWinner(capturedPiece, movingCamp);
        Optional<Camp> checkedCamp = findCheckedCamp(determinedWinner, opponentCamp);

        winner = determinedWinner;
        changeTurnIfGameContinues(determinedWinner, opponentCamp);

        return new TurnResult(
                capturedPiece.map(Piece::type),
                checkedCamp,
                determinedWinner
        );
    }

    public TurnResult passTurn() {
        changeTurn();
        return TurnResult.empty();
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
        return winner.isPresent();
    }

    public int scoreOf(Camp camp) {
        return board.scoreOf(camp);
    }

    private Optional<Camp> findWinner(Optional<Piece> capturedPiece, Camp movingCamp) {
        return capturedPiece
                .filter(target -> target.type() == PieceType.GENERAL)
                .map(ignored -> movingCamp);
    }

    private Optional<Camp> findCheckedCamp(Optional<Camp> winner, Camp opponentCamp) {
        if (winner.isPresent()) {
            return Optional.empty();
        }

        ThreatAnalyzer threatAnalyzer = new ThreatAnalyzer();

        if (threatAnalyzer.isInCheck(board, opponentCamp)) {
            return Optional.of(opponentCamp);
        }

        return Optional.empty();
    }

    private void changeTurnIfGameContinues(Optional<Camp> winner, Camp opponentCamp) {
        if (winner.isEmpty()) {
            currentTurn = opponentCamp;
        }
    }

    private static Optional<Camp> findWinner(Camp currentTurn, boolean finished) {
        if (finished) {
            return Optional.of(currentTurn);
        }

        return Optional.empty();
    }
}
