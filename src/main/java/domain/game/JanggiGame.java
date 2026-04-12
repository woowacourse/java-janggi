package domain.game;

import domain.board.Board;
import domain.board.SangSetup;
import domain.game.exception.GameEndedException;
import domain.game.exception.GameErrorMessage;
import domain.game.exception.GameIdAlreadyExistsException;
import domain.game.exception.InvalidGameIdException;
import domain.game.exception.InvalidTurnException;
import domain.pieces.Piece;
import domain.pieces.PieceType;
import domain.pieces.Side;
import domain.pieces.exception.NoPieceException;
import domain.pieces.exception.PieceErrorMessage;
import domain.position.Position;

public class JanggiGame {

    private Long gameId;
    private Board board;
    private Turn currentTurn = Turn.start();
    private GameResult gameResult = GameResult.running();
    private final GameScoreCalculator gameScoreCalculator = new GameScoreCalculator();

    public JanggiGame(Board board) {
        this(null, board);
    }

    private JanggiGame(Long gameId, Board board) {
        this.gameId = gameId;
        this.board = board;
    }

    public static JanggiGame of(SangSetup choSangSetup, SangSetup hanSangSetup) {
        Board choBoard = choSangSetup.initialize(Side.CHO);
        Board hanBoard = hanSangSetup.initialize(Side.HAN);
        return new JanggiGame(choBoard.merge(hanBoard));
    }

    public static JanggiGame restore(Long gameId, Board board, Side currentTurn, GameResult gameResult) {
        JanggiGame janggiGame = new JanggiGame(gameId, board);
        janggiGame.currentTurn = Turn.from(currentTurn);
        janggiGame.gameResult = gameResult;
        return janggiGame;
    }

    public void move(Position departure, Position destination) {
        validateGameNotEnded();

        Piece departurePiece = board.pieces().get(departure);
        Piece destinationPiece = board.pieces().get(destination);

        if(departurePiece.isEmpty()){
            throw new NoPieceException(PieceErrorMessage.NO_PIECE);
        }

        if (currentTurn.isNotCurrentTurnPiece(departurePiece)) {
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

    public Long gameId() {
        return gameId;
    }

    public void assignGameId(Long gameId) {
        if (this.gameId != null) {
            throw new GameIdAlreadyExistsException(GameErrorMessage.GAME_ID_ALREADY_EXISTS);
        }
        if (gameId == null) {
            throw new InvalidGameIdException(GameErrorMessage.GAME_ID_REQUIRED);
        }
        this.gameId = gameId;
    }

    public GameResult gameResult() {
        return gameResult;
    }

    public GameScore calculateScore() {
        return gameScoreCalculator.calculate(board);
    }

    public GameScore finishByScore() {
        GameScore gameScore = calculateScore();
        gameResult = gameResult.finishByScore(gameScore);
        return gameScore;
    }
}
