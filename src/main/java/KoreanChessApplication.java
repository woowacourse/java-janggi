import java.util.Map;
import piece.Board;
import piece.InitiateJanggiTeamPieces;
import piece.Piece;
import piece.Pieces;
import piece.Position;
import piece.Team;
import piece.TeamPieces;

public class KoreanChessApplication {

    private static final Map<Integer, Team> turnTable;
    private static final int PLAYER_SIZE = 2;

    static {
        turnTable = Map.of(0, Team.RED, 1, Team.BLUE);
    }

    public static void main(String[] args) {
        GameView gameView = new GameView();
        TeamPieces teamPieces = new TeamPieces(new InitiateJanggiTeamPieces());
        gameView.printChangePieceNotImplement();

        playKoreanChess(teamPieces, gameView);
    }

    private static void playKoreanChess(TeamPieces teamPieces, GameView gameView) {
        int turn = 0;
        while (!teamPieces.isKingDead()) {
            try {
                playTurn(teamPieces, gameView, turn);
                turn = (turn + 1) % PLAYER_SIZE;
            } catch (IllegalArgumentException e) {
                gameView.printError(e.getMessage());
            }
        }
    }

    private static void playTurn(TeamPieces teamPieces, GameView gameView, int turn) {
        Pieces allPieces = teamPieces.allPieces();
        Board board = new Board(allPieces);
        Map<Position, Piece> positionPieces = board.positionPieces();
        gameView.printJanggiBoard(positionPieces);
        Team team = turnTable.get(turn);
        gameView.printTurn(team);
        Position selectPiecePosition = gameView.inputSelectPiece();
        Position selectPosition = gameView.inputPiecePosition();
        teamPieces.move(team, selectPiecePosition, selectPosition);
    }
}
