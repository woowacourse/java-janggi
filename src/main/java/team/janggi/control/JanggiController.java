package team.janggi.control;

import java.util.List;
import team.janggi.domain.JanggiFormation;
import team.janggi.domain.JanggiGame;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.Board;
import team.janggi.domain.board.BoardFactory;
import team.janggi.entity.Game;
import team.janggi.infra.H2ConnectionManager;
import team.janggi.infra.TransactionManager;
import team.janggi.repository.BoardPieceRepository;
import team.janggi.repository.GameRepository;
import team.janggi.service.GameService;
import team.janggi.util.Parser;
import team.janggi.view.ConsoleInputView;
import team.janggi.view.ConsoleOutputView;

public class JanggiController {
    private final ConsoleOutputView consoleOutputView;
    private final ConsoleInputView consoleInputView;
    private final GameService gameService;

    public JanggiController() {
        this.consoleOutputView = new ConsoleOutputView();
        this.consoleInputView = new ConsoleInputView();
        this.gameService = new GameService(new GameRepository(), new BoardPieceRepository(),
                new TransactionManager(new H2ConnectionManager()));
    }

    public void runJanggiGame(JanggiGame game) {
        while (!game.isGameOver()) {
            printCurrentScores(game.getBoard());
            consoleOutputView.print(game.getBoard());
            Team currentTurn = game.getCurrentTurn();
            try {
                String input = consoleInputView.readCommand(currentTurn);

                if (input.equals("save")) {
                    String gameName = consoleInputView.readGameName();
                    gameService.saveGame(gameName, currentTurn, game.getBoard());
                    consoleOutputView.printSavedGame(gameName);
                    return;
                }

                final Position from = getSourcePosition(input);
                final Position to = getDestinationPosition(currentTurn);

                game.doTurn(from, to);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        consoleOutputView.printWinner(game.getCurrentTurn());
    }

    public void run() {
        int option = consoleInputView.readStartOption();
        if (option == 1) {
            JanggiGame loadGame = loadAndResumeGame();
            runJanggiGame(loadGame);
            return;
        }
        JanggiGame janggiGame = startNewGame();
        runJanggiGame(janggiGame);
    }

    public JanggiGame startNewGame() {
        final Board board = createBoard();
        board.initBoard();

        Team currentTurn = Team.CHO;
        return new JanggiGame(board, currentTurn);
    }

    private JanggiGame loadAndResumeGame() {
        List<Game> games = gameService.getGames();
        consoleOutputView.printGames(games);

        if (games.isEmpty()) {
            consoleOutputView.printNoSavedGames();
            return startNewGame();
        }

        int selectedId = getSelectedGameId(games);

        if (selectedId == games.size() + 1) {
            return startNewGame();
        }

        Board board = gameService.loadBoard(selectedId);
        Team currentTurn = gameService.loadTurn(selectedId);
        return new JanggiGame(board, currentTurn);
    }


    private int getSelectedGameId(List<Game> games) {
        while (true) {
            try {
                int selectedId = consoleInputView.readGameId();
                if (selectedId == games.size() + 1) {
                    return selectedId;
                }
                games.stream()
                        .filter(game -> game.getId() == selectedId)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 게임 번호입니다."));
                return selectedId;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printCurrentScores(Board board) {
        consoleOutputView.printScore(Team.CHO, board.getScore(Team.CHO));
        consoleOutputView.printScore(Team.HAN, board.getScore(Team.HAN));
    }

    private Board createBoard() {
        final JanggiFormation choSetup = getChoFormation();
        final JanggiFormation hanSetup = getHanFormation();

        final BoardFactory boardFactory = new BoardFactory(choSetup, hanSetup);
        return new Board(boardFactory);
    }

    private JanggiFormation getChoFormation() {
        while (true) {
            try {
                int choNumber = consoleInputView.readChoFormation();
                return JanggiFormation.from(choNumber);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private JanggiFormation getHanFormation() {
        while (true) {
            try {
                int hanNumber = consoleInputView.readHanFormation();
                return JanggiFormation.from(hanNumber);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Position getSourcePosition(String input) {
        List<Integer> positions = Parser.parseByDelimiter(input, "[ERROR] 좌표는 숫자 형식이어야 합니다.");
        return Position.of(positions.get(0), positions.get(1));
    }

    private Position getDestinationPosition(Team currentTurn) {
        while (true) {
            try {
                List<Integer> destinationPosition = consoleInputView.readDestinationPosition(currentTurn);
                return Position.of(destinationPosition.get(0), destinationPosition.get(1));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
