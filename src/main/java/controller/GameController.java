package controller;

import java.util.List;
import java.util.Optional;

import model.board.*;
import model.game.GameSession;
import model.game.JanggiGame;
import model.move.Move;
import model.position.Position;
import repository.GameRepository;
import repository.GameRestorer;
import repository.GameSnapshot;
import repository.SavedGame;
import view.InputHandler;
import view.InputView;
import view.OutputView;

public class GameController {
    private final BoardInitializer boardInitializer;
    private final GameRepository gameRepository;

    public GameController(BoardInitializer boardInitializer, GameRepository gameRepository) {
        this.boardInitializer = boardInitializer;
        this.gameRepository = gameRepository;
    }

    public void start() {
        GameSession session = loadGameSession();
        Board board = session.board();
        JanggiGame game = session.game();

        OutputView.printBoard(board);
        while (true) {
            runGame(game, board);

            if (game.isFinished()) {
                printResult(board, game);
                gameRepository.clear();
                break;
            }
            saveGame(game, board);
        }
    }

    private GameSession loadGameSession() {
        Optional<SavedGame> savedGame = gameRepository.find();
        if (savedGame.isPresent()) {
            OutputView.printLoadedGameMessage();
            OutputView.printLine();
            return GameRestorer.restore(savedGame.get());
        }
        OutputView.printNewGameMessage();
        return newGameSession();
    }

    private GameSession newGameSession() {
        Board board = new Board();
        init(board);
        JanggiGame game = new JanggiGame(board);
        return new GameSession(board, game);
    }

    private void saveGame(JanggiGame game, Board board) {
        SavedGame savedGame = GameSnapshot.from(game, board);
        gameRepository.save(savedGame);
    }

    private void init(Board board) {
        ArrangementType choType = readArrangementType(Country.CHO);
        OutputView.printLine();
        ArrangementType hanType = readArrangementType(Country.HAN);

        boardInitializer.initialize(board, choType, hanType);
    }

    private ArrangementType readArrangementType(Country country) {
        OutputView.printArrangeCountry(country);
        return InputHandler.retry(() ->
                ArrangementType.from(InputView.readArrangement(country)));
    }

    private void runGame(JanggiGame game, Board board) {
        OutputView.printPositionCountry(game.turn());

        InputHandler.retry(() -> {
            List<Integer> startList = InputView.readStartPosition();
            List<Integer> endList = InputView.readEndPosition();
            Position from = Position.of(startList.get(0), startList.get(1));
            Position to = Position.of(endList.get(0), endList.get(1));
            Move move = new Move(from, to);
            game.move(move);
            OutputView.printBoard(board);
            return null;
        });
    }

    private void printResult(Board board, JanggiGame game) {
        OutputView.printWinner(game.winner());
        OutputView.printScore(Country.CHO, board.calculateScore(Country.CHO));
        OutputView.printScore(Country.HAN, board.calculateScore(Country.HAN));
    }
}
