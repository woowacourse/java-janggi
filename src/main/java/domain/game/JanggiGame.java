package domain.game;

import domain.board.Board;
import domain.board.SangSetup;
import domain.game.exception.GameEndedException;
import domain.game.exception.GameErrorMessage;
import domain.game.exception.InvalidTurnException;
import domain.pieces.Piece;
import domain.pieces.PieceType;
import domain.pieces.Side;
import domain.position.Position;

public class JanggiGame {
    private Board board;
    private Turn currentTurn = Turn.start();
    private GameResult gameResult = GameResult.running();
    private final GameScoreCalculator gameScoreCalculator = new GameScoreCalculator();

    public JanggiGame(Board board) {
        this.board = board;
    }

    public static JanggiGame of(SangSetup choSangSetup, SangSetup hanSangSetup) {
        Board choBoard = choSangSetup.initialize(Side.CHO);
        Board hanBoard = hanSangSetup.initialize(Side.HAN);
        return new JanggiGame(choBoard.merge(hanBoard));
    }

    public void move(Position departure, Position destination) {
        validateGameNotEnded();

        Piece destinationPiece = board.pieces().get(destination);

        if (currentTurn.isNotCurrentTurnPiece(board.pieces().get(departure))) {
            throw new InvalidTurnException(currentTurn.errorMessage());
        }

        board = board.move(departure, destination);

        if (destinationPiece.getType() == PieceType.GUNG) {
            gameResult = GameResult.ended(currentTurn.side());
            return;
        }

        currentTurn = currentTurn.next();
    }

    private void validateGameNotEnded() {
        if (gameResult.isEnded()) {
            throw new GameEndedException(GameErrorMessage.GAME_ALREADY_ENDED);
        }
    }

    public Side currentTurn() {
        return currentTurn.side();
    }

    public Board board() {
        return board;
    }

    public GameResult gameResult() {
        return gameResult;
    }

    public GameScore calculateScore() {
        return gameScoreCalculator.calculate(board);
    }
}
