package domain;

import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private final Player choPlayer;
    private final Player hanPlayer;

    public Game(Board board, Player choPlayer, Player hanPlayer) {
        this.board = board;
        this.choPlayer = choPlayer;
        this.hanPlayer = hanPlayer;
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }

    public boolean isOver() {
        return board.isGameOver();
    }

    public List<Position> getPossibleDestinations(Position position) {
        // TODO: Players에게 현재 턴인 플레이어의 진영을 물어보고, 사용자가 선택한 position의 진영을 비교해서 다르면 에러 발생
        List<Position> destinations = board.getPossibleDestinations(position);
        if (destinations.isEmpty()) {
            throw new IllegalArgumentException("갈 수 있는 목적지가 없습니다. 다른 기물을 선택하세요.");
        }
        return destinations;
    }

    public void movePiece(Position from, Position to) {
        board.movePiece(from, to);
    }

    public String nextTurn() {
        if (choPlayer.isTurn()) {
            choPlayer.changeTurn();
            hanPlayer.changeTurn();
            return Side.HAN.getName();
        }

        choPlayer.changeTurn();
        hanPlayer.changeTurn();
        return Side.CHO.getName();
    }
}
