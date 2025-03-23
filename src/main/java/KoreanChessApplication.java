import java.util.Map;
import piece.InitiateJanggiTeamPieces;
import piece.Piece;
import piece.Pieces;
import piece.PlayerPieces;
import piece.Position;
import piece.PositionPieces;
import piece.Team;

public class KoreanChessApplication {

    private static final Map<Integer, Team> turnTable;
    private static final int PLAYER_SIZE = 2;

    static {
        turnTable = Map.of(0, Team.RED, 1, Team.BLUE);
    }

    public static void main(String[] args) {
        GameView gameView = new GameView();
        Map<Team, Pieces> teamPieces = new InitiateJanggiTeamPieces().janggiInitiatePieces();
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        gameView.printChangePieceNotImplement();

        playKoreanChess(playerPieces, gameView);
    }

    private static void playKoreanChess(PlayerPieces playerPieces, GameView gameView) {
        int turn = 0;
        while (playerPieces.kingDeadTeam().isEmpty()) {
            try {
                playTurn(playerPieces, gameView, turn);
                turn = (turn + 1) % PLAYER_SIZE;
            } catch (IllegalArgumentException e) {
                gameView.printError(e.getMessage());
            }
        }
    }

    private static void playTurn(PlayerPieces playerPieces, GameView gameView, int turn) {
        Pieces allPieces = playerPieces.allPieces();
        PositionPieces board = new PositionPieces(allPieces);
        Map<Position, Piece> positionPieces = board.positionPieces();
        gameView.printJanggiBoard(positionPieces);
        Team team = turnTable.get(turn);
        gameView.printTurn(team);
        Position selectPiecePosition = gameView.inputSelectPiece();
        Position selectPosition = gameView.inputPiecePosition();
        playerPieces.move(team, selectPiecePosition, selectPosition);
    }
}
