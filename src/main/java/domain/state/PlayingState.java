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
import java.util.Optional;

public class PlayingState implements GameState {

    private static final CheckDetector checkDetector = new CheckDetector();
    private static final CheckmateDetector checkmateDetector = new CheckmateDetector();
    private static final BikjangDetector bikjangDetector = new BikjangDetector();

    @Override
    public GameStateName stateName() {
        return GameStateName.PLAYING;
    }

    @Override
    public GameState handle(JanggiGame game, Command command) {
        game.move(command.toCoordinate());
        return resolveAfterMove(game);
    }

    private GameState resolveAfterMove(JanggiGame game) {
        Board board = game.getBoard().orElseThrow();
        Team current = game.getCurrentTeam();
        Team opponent = game.getEnemy();

        return checkOpponentDefeated(board, current, opponent)
                .or(() -> checkBoardEndCondition(board))
                .orElseGet(() -> resolveNoLegalMoves(game, board, current, opponent));
    }

    private Optional<GameState> checkOpponentDefeated(Board board, Team current, Team opponent) {
        if (!board.hasGeneral(opponent) || checkmateDetector.isCheckmate(board, opponent)) {
            return Optional.of(new EndGameState(GameResult.winOf(current)));
        }
        return Optional.empty();
    }

    private Optional<GameState> checkBoardEndCondition(Board board) {
        if (bikjangDetector.isBikjang(board)) {
            return Optional.of(new BikjangState());
        }
        if (isBothInsufficient(board)) {
            return Optional.of(endByScore(board));
        }
        return Optional.empty();
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
        Board board = game.getBoard().orElseThrow();
        Turn turn = game.getTurn();
        Team currentTeam = turn.team();

        if (checkDetector.isInCheck(board, currentTeam)) {
            outputView.printCheckMessage(currentTeam);
        }

        outputView.printBoard(board, turn);
        outputView.printPieceMovement(turn);
    }
}
