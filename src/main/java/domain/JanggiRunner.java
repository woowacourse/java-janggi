package domain;

import domain.dao.BoardDao;
import domain.dao.GameStatusDao;
import domain.dao.PlayerDao;
import domain.participants.Player;
import domain.participants.Players;
import domain.participants.Usernames;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.TeamType;
import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.position.Position;
import java.util.Map;
import java.util.Optional;
import view.InputView;
import view.OutputView;

public class JanggiRunner {
    private static final String DEFAULT_ROOM_NAME = "GAME_ROOM";

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final BoardDao boardDao = new BoardDao();
    private final GameStatusDao gameStatusDao = new GameStatusDao();
    private final PlayerDao playerDao = new PlayerDao();

    public void run() {
        JanggiGame janggiGame = initializeGame();
        showInitializedBoardResult(janggiGame);
        startGame(janggiGame);
        deleteData();
    }

    private void showInitializedBoardResult(JanggiGame janggiGame) {
        Map<Position, Piece> alivePiecesInfo = janggiGame.getAlivePiecesInfo();
        outputView.printBoard(alivePiecesInfo);
        outputView.printScore(janggiGame.getScoreInfo());
    }

    private void startGame(JanggiGame janggiGame) {
        executeGame(janggiGame);
        showWinner(janggiGame);
    }

    private void executeGame(JanggiGame janggiGame) {
        TeamType nowTurn = janggiGame.getTurn();
        while (isGameInProgress(janggiGame)) {
            Player nowPlayer = janggiGame.findPlayerByTeam(nowTurn);
            Position startPosition = inputView.getStartPosition(nowPlayer);
            Position endPosition = inputView.getEndPosition(nowPlayer);
            janggiGame.movePiece(startPosition, endPosition);
            outputView.printBoard(janggiGame.getAlivePiecesInfo());
            outputView.printScore(janggiGame.getScoreInfo());
            nowTurn = janggiGame.getTurn();
            updateGameStatus(startPosition, endPosition, nowTurn);
        }
    }

    private void updateGameStatus(Position startPosition, Position endPosition, TeamType nowTurn) {
        boardDao.updateBoard(startPosition, endPosition);
        gameStatusDao.updateTurn(DEFAULT_ROOM_NAME, nowTurn);
    }

    private void deleteData() {
        playerDao.deletePlayer();
        boardDao.deleteBoard();
        gameStatusDao.deleteGame();
    }

    private void showWinner(JanggiGame janggiGame) {
        Player winner = janggiGame.findWinner();
        outputView.printWinner(winner);
    }

    private JanggiGame initializeGame() {
        Players players = createPlayers();
        GameStatus gameStatus = createGameStatus();
        Board board = createBoard(players);
        return new JanggiGame(players, board, gameStatus);
    }

    private Map<Position, Piece> createAllPieces(HorseElephantSetupStrategy firstPlayerStrategy,
                                                 HorseElephantSetupStrategy secondPlayerStrategy) {
        PieceFactory factory = new PieceFactory();
        return factory.createAllPieces(firstPlayerStrategy, secondPlayerStrategy);
    }

    private HorseElephantSetupStrategy chooseStrategy(String playerName) {
        String firstPlayerOption = inputView.getSetupNumber(playerName);
        return SetupOption.findSetupStrategy(firstPlayerOption);
    }

    private Players createPlayers() {
        Optional<Players> players = playerDao.findPlayers();
        if (players.isPresent()) {
            return players.get();
        }
        Usernames usernames = createUsernames();
        String startPlayerName = inputView.getStartPlayerName();
        Players newPlayers = Players.createFrom(usernames, startPlayerName);
        playerDao.savePlayers(newPlayers);
        return newPlayers;
    }

    private Board createBoard(Players players) {
        Optional<Board> board = boardDao.findBoard();
        if (board.isPresent()) {
            return board.get();
        }
        HorseElephantSetupStrategy choPlayerStrategy = chooseStrategy(players.getChoPlayerName());
        HorseElephantSetupStrategy hanPlayerStrategy = chooseStrategy(players.getHanPlayerName());
        Map<Position, Piece> allPieces = createAllPieces(choPlayerStrategy, hanPlayerStrategy);
        Board newBoard = new Board(allPieces);
        boardDao.save(newBoard);
        return newBoard;
    }

    private GameStatus createGameStatus() {
        Optional<GameStatus> gameStatus = gameStatusDao.findGameStatusByRoomName(DEFAULT_ROOM_NAME);
        if (gameStatus.isPresent()) {
            return gameStatus.get();
        }
        GameStatus newGameStatus = new GameStatus(DEFAULT_ROOM_NAME);
        gameStatusDao.save(newGameStatus);
        return newGameStatus;
    }

    private Usernames createUsernames() {
        String firstPlayerName = inputView.getFirstPlayerName();
        String secondPlayerName = inputView.getSecondPlayerName();
        return new Usernames(firstPlayerName, secondPlayerName);
    }

    private boolean isGameInProgress(JanggiGame janggiGame) {
        return !janggiGame.isFinished();
    }
}
