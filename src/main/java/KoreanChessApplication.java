import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import piece.Piece;
import piece.Pieces;
import piece.PlayerPieces;
import piece.Team;
import piece.initiate.InitiateJanggiTeamPieces;
import piece.initiate.TableSetting;
import piece.position.Position;

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
        for (Team team : Team.values()) {
            TableSetting selectTableSetting = gameView.inputTableSetting(team);
            tableSetting.put(team, selectTableSetting);
        }
        return tableSetting;
    }

    private static void playKoreanChess(PlayerPieces playerPieces, GameView gameView) {
        int turn = 0;
        Optional<Team> loseTeam = playerPieces.kingDeadTeam();
        while (loseTeam.isEmpty()) {
            TurnResult turnResult = playKoreanChess(playerPieces, gameView, turn);
            turn = turnResult.nextTurn() % PLAYER_SIZE;
        }
        playTurn(playerPieces, gameView, turn);
        Team team = loseTeam.get();
        gameView.printWinner(team.opposite());
    }

    private static TurnResult playKoreanChess(PlayerPieces playerPieces, GameView gameView, int turn) {
        try {
            playTurn(playerPieces, gameView, turn);
            return new TurnResult(turn + 1 % 2, playerPieces.kingDeadTeam());
        } catch (IllegalArgumentException e) {
            gameView.printError(e.getMessage());
        }
        return new TurnResult(turn, Optional.empty());
    }

    private static void playTurn(PlayerPieces playerPieces, GameView gameView, int turn) {
        Pieces allPieces = playerPieces.allPieces();
        Map<Position, Piece> positionPieces = positionPieces(allPieces);
        gameView.printJanggiBoard(positionPieces);
        Team team = turnTable.get(turn);
        gameView.printTurn(team);
        Position selectPiecePosition = gameView.inputSelectPiece();
        Position selectPosition = gameView.inputPiecePosition();
        playerPieces.move(team, selectPiecePosition, selectPosition);
    }

    public static Map<Position, Piece> positionPieces(Pieces pieces) {
        List<Piece> allPieces = pieces.getPieces();
        Map<Position, Piece> playerBoard = new HashMap<>();
        for (Piece piece : allPieces) {
            playerBoard.put(piece.getPosition(), piece);
        }
        return playerBoard;
    }
}
