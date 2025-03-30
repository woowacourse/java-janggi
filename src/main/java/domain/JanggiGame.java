package domain;

import dao.BoardDao;
import domain.board.Board;
import domain.board.BoardPoint;
import domain.board.Score;
import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.Elephant;
import domain.pieces.General;
import domain.pieces.Guard;
import domain.pieces.Horse;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import dto.MovementResponseDto;
import dto.SwitchPlayerTurnRequestDto;
import execptions.JanggiArgumentException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JanggiGame {

    private final Board board;
    private final List<Player> players;
    private final BoardDao boardDao;

    public JanggiGame() {
        this.boardDao = new BoardDao();
        this.board = boardDao.getBoard();
        this.players = boardDao.getPlayers();
    }

    public Map<BoardPoint, Piece> getBoard() {
        return board.getLocations();
    }

    public void move(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        Player currentPlayer = players.stream()
                .filter(Player::isTurn)
                .findFirst()
                .orElseThrow(() -> new JanggiArgumentException("턴을 가진 플레이어가 존재하지 않습니다."));

        board.movePiece(startBoardPoint, arrivalBoardPoint, currentPlayer.getTeam());
        boardDao.saveMovementResult(new MovementResponseDto(startBoardPoint, arrivalBoardPoint));

        List<SwitchPlayerTurnRequestDto> switchPlayerTurnRequestDtos = switchTurn();
        boardDao.saveSwitchedTurn(switchPlayerTurnRequestDtos);
    }

    private List<SwitchPlayerTurnRequestDto> switchTurn() {
        List<SwitchPlayerTurnRequestDto> switchPlayerTurnRequestDtos = new ArrayList<>();
        for (Player player : players) {
            player.switchTurn();
            switchPlayerTurnRequestDtos.add(new SwitchPlayerTurnRequestDto(player.getTeam(), player.isTurn()));
        }
        return switchPlayerTurnRequestDtos;
    }

    private Board generateBoard() {
        final Map<BoardPoint, Piece> locations = new HashMap<>();

        locations.putAll(generateLocationsForHan());
        locations.putAll(generateLocationsForCho());

        return new Board(locations);
    }

    public boolean isGeneralDied() {
        return board.isGeneralDied();
    }

    public Score calculateScore() {
        return new Score(board.calculateScoreOf(Team.HAN), board.calculateScoreOf(Team.CHO));
    }

    private Map<BoardPoint, Piece> generateLocationsForHan() {
        final Map<BoardPoint, Piece> locations = new HashMap<>();

        locations.put(new BoardPoint(6, 0), new Soldier(Team.HAN));
        locations.put(new BoardPoint(6, 2), new Soldier(Team.HAN));
        locations.put(new BoardPoint(6, 4), new Soldier(Team.HAN));
        locations.put(new BoardPoint(6, 6), new Soldier(Team.HAN));
        locations.put(new BoardPoint(6, 8), new Soldier(Team.HAN));

        locations.put(new BoardPoint(9, 0), new Chariot(Team.HAN));
        locations.put(new BoardPoint(9, 8), new Chariot(Team.HAN));

        locations.put(new BoardPoint(7, 1), new Cannon(Team.HAN));
        locations.put(new BoardPoint(7, 7), new Cannon(Team.HAN));

        locations.put(new BoardPoint(9, 1), new Elephant(Team.HAN));
        locations.put(new BoardPoint(9, 7), new Elephant(Team.HAN));

        locations.put(new BoardPoint(9, 2), new Horse(Team.HAN));
        locations.put(new BoardPoint(9, 6), new Horse(Team.HAN));

        locations.put(new BoardPoint(8, 4), new General(Team.HAN));
        locations.put(new BoardPoint(9, 3), new Guard(Team.HAN));
        locations.put(new BoardPoint(9, 5), new Guard(Team.HAN));

        return locations;
    }

    private Map<BoardPoint, Piece> generateLocationsForCho() {
        final Map<BoardPoint, Piece> locations = new HashMap<>();

        locations.put(new BoardPoint(3, 0), new Soldier(Team.CHO));
        locations.put(new BoardPoint(3, 2), new Soldier(Team.CHO));
        locations.put(new BoardPoint(3, 4), new Soldier(Team.CHO));
        locations.put(new BoardPoint(3, 6), new Soldier(Team.CHO));
        locations.put(new BoardPoint(3, 8), new Soldier(Team.CHO));

        locations.put(new BoardPoint(0, 0), new Chariot(Team.CHO));
        locations.put(new BoardPoint(0, 8), new Chariot(Team.CHO));

        locations.put(new BoardPoint(2, 1), new Cannon(Team.CHO));
        locations.put(new BoardPoint(2, 7), new Cannon(Team.CHO));

        locations.put(new BoardPoint(0, 1), new Elephant(Team.CHO));
        locations.put(new BoardPoint(0, 7), new Elephant(Team.CHO));

        locations.put(new BoardPoint(0, 2), new Horse(Team.CHO));
        locations.put(new BoardPoint(0, 6), new Horse(Team.CHO));

        locations.put(new BoardPoint(1, 4), new General(Team.CHO));
        locations.put(new BoardPoint(0, 3), new Guard(Team.CHO));
        locations.put(new BoardPoint(0, 5), new Guard(Team.CHO));

        return locations;
    }
}
