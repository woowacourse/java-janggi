import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import persistence.JanggiGamePersistence;
import persistence.MySQLConnection;
import persistence.PersistenceFailException;
import piece.Piece;
import piece.Pieces;
import piece.initiate.InitiateJanggiTeamPieces;
import piece.initiate.TableSetting;
import piece.player.PlayerPieces;
import piece.player.Team;
import piece.position.JanggiPosition;

public class KoreanChessApplication {

    private static final String GAME_SAVE_NOT_AVAILABLE = "저장 기능이 비활성화 되었습니다. 게임은 여전히 진행하실 수 있습니다";

    private static final int PLAYER_SIZE = 2;
    private static final Map<Integer, Team> turnTable;
    private static JanggiGamePersistence janggiGamePersistence;

    static {
        turnTable = Map.of(0, Team.RED, 1, Team.BLUE);
    }

    public static void main(String[] args) {
        GameView gameView = new GameView();
        initiateJanggiService(gameView);
        PlayerPieces playerPieces = initiatePieces(gameView);
        playKoreanChess(playerPieces, gameView);
    }

    private static void initiateJanggiService(GameView gameView) {
        try {
            janggiGamePersistence = new JanggiGamePersistence(new MySQLConnection());
        } catch (PersistenceFailException e) {
            gameView.printCanNotApplySave(e.getMessage());
        }
    }

    private static PlayerPieces initiatePieces(GameView gameView) {
        try {
            return initiatePiecesFromPreviousGame();
        } catch (PersistenceFailException e) {
            gameView.printStartFromUserInput();
        }
        return userInputInitiatePieces(gameView);
    }

    private static PlayerPieces userInputInitiatePieces(GameView gameView) {
        Map<Team, TableSetting> teamTableSetting = inputTableSetting(gameView);
        Map<Team, Pieces> teamPieces = new InitiateJanggiTeamPieces(teamTableSetting).janggiInitiatePieces();
        return new PlayerPieces(teamPieces);
    }

    private static PlayerPieces initiatePiecesFromPreviousGame() {
        if (janggiGamePersistence == null) {
            throw new PersistenceFailException(GAME_SAVE_NOT_AVAILABLE);
        }
        Pieces previousPieces = janggiGamePersistence.loadPieces();
        Map<Team, Pieces> teamPieces = new InitiateJanggiTeamPieces(previousPieces).janggiInitiatePieces();
        return new PlayerPieces(teamPieces);
    }

    private static Map<Team, TableSetting> inputTableSetting(GameView gameView) {
        Map<Team, TableSetting> tableSetting = new HashMap<>();
        for (Team team : Team.playableTeams()) {
            TableSetting selectTableSetting = gameView.inputTableSetting(team);
            tableSetting.put(team, selectTableSetting);
        }
        return tableSetting;
    }

    private static void playKoreanChess(PlayerPieces playerPieces, GameView gameView) {
        int turn = initiateTurn();
        Team loseTeam = Team.EMPTY;
        while (loseTeam == Team.EMPTY) {
            TurnResult turnResult = playKoreanChess(playerPieces, gameView, turn);

            boolean isNextTurn = turnResult.nextTurn() != turn;
            saveJanggi(playerPieces, turn, gameView, isNextTurn);

            loseTeam = turnResult.loseTeam();
            turn = turnResult.nextTurn();
        }
        Team team = loseTeam;
        gameView.printWinner(team.opposite());
        printPlayersScore(playerPieces, gameView);
        resetJanggi(gameView);
    }

    private static void resetJanggi(GameView gameView) {
        try {
            janggiGamePersistence.resetJanggi();
        } catch (PersistenceFailException e) {
            gameView.printError(e.getMessage());
        }
    }

    private static void saveJanggi(PlayerPieces playerPieces, int turn, GameView gameView, boolean isNextTurn) {
        if (!isNextTurn) {
            return;
        }
        if (janggiGamePersistence == null) {
            return;
        }
        try {
            janggiGamePersistence.saveJanggi(playerPieces, turn, determineCurrentPlayTeam(turn));
        } catch (PersistenceFailException e) {
            gameView.printError(e.getMessage());
        }
    }

    private static int initiateTurn() {
        if (janggiGamePersistence == null) {
            return 0;
        }
        try {
            Optional<Integer> previousTurn = janggiGamePersistence.findPreviousTurn();
            return previousTurn.map(turn -> turn + 1).orElse(0);
        } catch (PersistenceFailException e) {
            return 0;
        }
    }

    private static TurnResult playKoreanChess(PlayerPieces playerPieces, GameView gameView, int turn) {
        try {
            playTurn(playerPieces, gameView, turn);
            Team kingDeadTeam = playerPieces.kingDeadTeam();
            return new TurnResult(turn + 1, kingDeadTeam);
        } catch (IllegalArgumentException e) {
            gameView.printError(e.getMessage());
        }
        return new TurnResult(turn, Team.EMPTY);
    }

    private static void playTurn(PlayerPieces playerPieces, GameView gameView, int turn) {
        Pieces allPieces = playerPieces.allPieces();
        Map<JanggiPosition, Piece> positionPieces = positionPieces(allPieces);
        gameView.printJanggiBoard(positionPieces);
        Team team = determineCurrentPlayTeam(turn);
        gameView.printPlayer(team);
        gameView.printTurn(turn);
        JanggiPosition selectPiecePosition = gameView.inputSelectPiece();
        JanggiPosition selectPosition = gameView.inputPiecePosition();
        playerPieces.placePhase(team, selectPiecePosition, selectPosition);
    }

    private static Team determineCurrentPlayTeam(int turn) {
        return turnTable.get(turn % PLAYER_SIZE);
    }

    public static Map<JanggiPosition, Piece> positionPieces(Pieces pieces) {
        List<Piece> allPieces = pieces.getPieces();
        Map<JanggiPosition, Piece> playerBoard = new HashMap<>();
        for (Piece piece : allPieces) {
            playerBoard.put(piece.position(), piece);
        }
        return playerBoard;
    }

    public static void printPlayersScore(PlayerPieces playerPieces, GameView gameView) {
        Map<Team, Integer> playerScores = playerPieces.getPlayerScores();
        for (Team team : playerScores.keySet()) {
            gameView.printPlayerScore(team, playerScores.get(team));
        }
    }
}
