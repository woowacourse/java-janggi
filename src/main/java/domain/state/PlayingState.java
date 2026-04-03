package domain.state;

import domain.board.Board;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Team;
import domain.rule.BikjangDetector;
import domain.rule.CheckDetector;
import domain.rule.CheckmateDetector;
import domain.setup.Command;
import io.OutputView;

public class PlayingState implements GameState {

    private final CheckDetector checkDetector = new CheckDetector();
    private final CheckmateDetector checkmateDetector = new CheckmateDetector();
    private final BikjangDetector bikjangDetector = new BikjangDetector();

    @Override
    public GameState handle(JanggiGame game, Command command) {
        game.move(command.toCoordinate());
        return resolveAfterMove(game);
    }

    private GameState resolveAfterMove(JanggiGame game) {
        Board board = game.getBoard();
        Team current = game.getCurrentTeam();
        Team opponent = game.getEnemy();

        if (!board.hasGeneral(opponent)) {
            return new EndGameState(GameResult.winOf(current));
        }
        if (checkmateDetector.isCheckmate(board, opponent)) {
            return new EndGameState(GameResult.winOf(current));
        }
        if (bikjangDetector.isBikjang(board)) {
            return new BikjangState();
        }
        if (isBothInsufficient(board)) {
            return endByScore(board);
        }
        return resolveNoLegalMoves(game, board, current, opponent);
    }

    private GameState resolveNoLegalMoves(JanggiGame game, Board board, Team current, Team opponent) {
        if (!checkmateDetector.hasNoLegalMoves(board, opponent)) {
            return this;
        }
        if (checkmateDetector.hasNoLegalMoves(board, current)) {
            return endByScore(board);
        }
        game.nextTurn();
        return this;
    }

    private boolean isBothInsufficient(Board board) {
        return board.hasInsufficientPieces(Team.HAN) && board.hasInsufficientPieces(Team.CHO);
    }

    private EndGameState endByScore(Board board) {
        return new EndGameState(GameResult.fromScore(
                board.calculateScore(Team.HAN), board.calculateScore(Team.CHO)));
    }

    @Override
    public void display(JanggiGame game, OutputView outputView) {
        Board board = game.getBoard();
        Turn turn = game.getTurn();
        Team currentTeam = turn.team();

        if (checkDetector.isInCheck(board, currentTeam)) {
            outputView.printCheckMessage(currentTeam);
        }

        outputView.printBoard(board, turn);
        outputView.printPieceMovement(turn);
    }
}
