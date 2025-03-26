import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piece.Piece;
import piece.Pieces;
import piece.initiate.InitiateJanggiTeamPieces;
import piece.initiate.TableSetting;
import piece.player.PlayerPieces;
import piece.player.Team;
import piece.position.JanggiPosition;

public class KoreanChessApplication {

    private static final Map<Integer, Team> turnTable;
    private static final int PLAYER_SIZE = 2;

    static {
        turnTable = Map.of(0, Team.RED, 1, Team.BLUE);
    }

    public static void main(String[] args) {
        GameView gameView = new GameView();
        Map<Team, TableSetting> teamTableSetting = inputTableSetting(gameView);
        Map<Team, Pieces> teamPieces = new InitiateJanggiTeamPieces(teamTableSetting).janggiInitiatePieces();
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        playKoreanChess(playerPieces, gameView);
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
        int turn = 0;
        Team loseTeam = Team.EMPTY;
        while (loseTeam == Team.EMPTY) {
            TurnResult turnResult = playKoreanChess(playerPieces, gameView, turn);
            loseTeam = turnResult.loseTeam();
            turn = turnResult.nextTurn() % PLAYER_SIZE;
        }
        Team team = loseTeam;
        gameView.printWinner(team.opposite());
        printPlayersScore(playerPieces, gameView);
    }

    private static TurnResult playKoreanChess(PlayerPieces playerPieces, GameView gameView, int turn) {
        try {
            playTurn(playerPieces, gameView, turn);
            Team kingDeadTeam = playerPieces.kingDeadTeam();
            return new TurnResult(turn + 1 % PLAYER_SIZE, kingDeadTeam);
        } catch (IllegalArgumentException e) {
            gameView.printError(e.getMessage());
        }
        return new TurnResult(turn, Team.EMPTY);
    }

    private static void playTurn(PlayerPieces playerPieces, GameView gameView, int turn) {
        Pieces allPieces = playerPieces.allPieces();
        Map<JanggiPosition, Piece> positionPieces = positionPieces(allPieces);
        gameView.printJanggiBoard(positionPieces);
        Team team = turnTable.get(turn);
        gameView.printTurn(team);
        JanggiPosition selectPiecePosition = gameView.inputSelectPiece();
        JanggiPosition selectPosition = gameView.inputPiecePosition();
        playerPieces.placePhase(team, selectPiecePosition, selectPosition);
    }

    public static Map<JanggiPosition, Piece> positionPieces(Pieces pieces) {
        List<Piece> allPieces = pieces.getPieces();
        Map<JanggiPosition, Piece> playerBoard = new HashMap<>();
        for (Piece piece : allPieces) {
            playerBoard.put(piece.getPosition(), piece);
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
