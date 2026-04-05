import domain.board.Board;
import domain.place.piece.Piece;
import domain.place.piece.Side;
import domain.player.Player;
import domain.player.Players;
import domain.position.Position;
import java.util.List;
import java.util.Optional;

public class Janggi {

    private final Players players;
    private final Board board;
    private Player currentPlayer;

    public Janggi(Players players, Board board) {
        this.players = players;
        this.board = board;
        this.currentPlayer = players.getPlayerBySide(Side.CHO);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<List<String>> getBoardFormat() {
        return board.getFormatBoard();
    }

    public void move(Position from, Position to) {
        Piece piece = getRequiredPiece(from);
        validateOwnPiece(piece);
        validateDestinationNotOccupiedBySameSide(piece, to);

        board.move(from, to);
        changeTurn();
    }

    private Piece getRequiredPiece(Position position) {
        return board.findPiece(position)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 현재 위치에 기물이 없습니다."));
    }

    private void validateOwnPiece(Piece piece) {
        if (!piece.isSameSide(currentPlayer.getSide())) {
            throw new IllegalArgumentException("[ERROR] 선택하신 피스가 같은 편 피스가 아닙니다.");
        }
    }

    private void validateDestinationNotOccupiedBySameSide(Piece piece, Position to) {
        Optional<Piece> target = board.findPiece(to);

        if (target.isPresent() && piece.isSameSide(target.get())) {
            throw new IllegalArgumentException("[ERROR] 이동하실 위치에 같은 편 기물이 존재합니다.");
        }
    }

    private void changeTurn() {
        if (currentPlayer.getSide() == Side.CHO) {
            currentPlayer = players.getPlayerBySide(Side.HAN);
            return;
        }
        currentPlayer = players.getPlayerBySide(Side.CHO);
    }
}
