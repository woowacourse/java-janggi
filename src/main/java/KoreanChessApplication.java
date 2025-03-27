import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import piece.Piece;
import piece.Pieces;
import piece.initiate.InitiateJanggiTeamPieces;
import piece.initiate.TableSetting;
import piece.player.PlayerPieces;
import piece.player.Team;
import piece.position.JanggiPosition;
import save.JanggiConnection;
import save.JanggiSaveService;

public class KoreanChessApplication {

    private static final int PLAYER_SIZE = 2;
    private static final Map<Integer, Team> turnTable;
    private static final JanggiSaveService janggiSaveService = new JanggiSaveService(new JanggiConnection());

    static {
        turnTable = Map.of(0, Team.RED, 1, Team.BLUE);
    }

    public static void main(String[] args) {
        GameView gameView = new GameView();
        PlayerPieces playerPieces = initiatePieces(gameView);
        playKoreanChess(playerPieces, gameView);
    }

    private static PlayerPieces initiatePieces(GameView gameView) {
        if (janggiSaveService.isPreviousGameExist()) {
            return initiatePiecesFromPreviousGame();
        }
        Map<Team, TableSetting> teamTableSetting = inputTableSetting(gameView);
        Map<Team, Pieces> teamPieces = new InitiateJanggiTeamPieces(teamTableSetting).janggiInitiatePieces();
        return new PlayerPieces(teamPieces);
    }

    private static PlayerPieces initiatePiecesFromPreviousGame() {
        Pieces previousPieces = janggiSaveService.loadPieces();
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
            loseTeam = turnResult.loseTeam();
            turn = turnResult.nextTurn();
        }
        Team team = loseTeam;
        gameView.printWinner(team.opposite());
        printPlayersScore(playerPieces, gameView);
        janggiSaveService.resetJanggi();
    }

    private static int initiateTurn() {
        Optional<Integer> previousTurn = janggiSaveService.getPreviousTurn();
        if (previousTurn.isPresent()) {
            return previousTurn.get() + 1;
        }
        return 0;
    }

    private static TurnResult playKoreanChess(PlayerPieces playerPieces, GameView gameView, int turn) {
        try {
            playTurn(playerPieces, gameView, turn);
            Team kingDeadTeam = playerPieces.kingDeadTeam();
            janggiSaveService.saveJanggi(playerPieces, turn, determineCurrentPlayTeam(turn));
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
