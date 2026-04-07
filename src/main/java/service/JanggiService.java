package service;

import domain.Position;
import domain.board.Board;
import domain.board.BoardSnapshot;
import domain.board.BoardSnapshots;
import domain.board.BoardStates;
import domain.country.CountryType;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import domain.piece.PieceType;
import dto.GameInfo;
import dto.PositionHistory;
import dto.PositionState;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import repository.JanggiRepository;

public class JanggiService {
    private final JanggiRepository janggiRepository;

    public JanggiService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public List<Integer> readAllGameInfoIds() {
        List<GameInfo> gameInfos = janggiRepository.findAllGameInfos();
        if (gameInfos.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 현재 저장된 보드가 없습니다.");
        }
        return gameInfos.stream()
                .map(GameInfo::id)
                .toList();
    }

    public int insertGameInfo() {
        return janggiRepository.saveGameInfo();
    }

    public void updateGameInfo(CountryType countryType, Map<CountryType, Double> scores, int id) {
        janggiRepository.updateGameInfo(countryType, scores, id);
    }

    public CountryType readCountryTurn(int id) {
        GameInfo gameInfo = janggiRepository.findGameInfoById(id);
        return CountryType.valueOf(gameInfo.turn());
    }

    public Board readBoard(int id) {
        GameInfo gameInfo = janggiRepository.findGameInfoById(id);
        BoardStates boardStates = readPositionStates(id);
        return new Board(boardStates, gameInfo.cho_score(), gameInfo.han_score());
    }

    private BoardStates readPositionStates(int gameInfoId) {
        List<PositionState> positionStates = janggiRepository.findAllPositionStatesByGameInfoId(gameInfoId);
        Map<Position, Piece> boardStates = new HashMap<>();
        for (PositionState positionState : positionStates) {
            Position position = new Position(positionState.x(), positionState.y());
            String pieceType = positionState.pieceType();
            CountryType countryType = CountryType.valueOf(positionState.countryType());
            Piece piece = PieceFactory.valueOf(pieceType).create(countryType);
            boardStates.put(position, piece);
        }
        return new BoardStates(boardStates);
    }

    public void deleteGameInfo(int id) {
        janggiRepository.deleteGameInfo(id);
    }

    public void insertPositionState(Position position, PieceInfo pieceInfo, int gameInfoId) {
        janggiRepository.savePositionState(position, pieceInfo, gameInfoId);
    }

    public void changePositionStateToAndFrom(Position from, Position to, PieceInfos pieceInfos, int gameInfoId) {
        if (isEmptyPosition(to, gameInfoId)) {
            janggiRepository.savePositionState(to, pieceInfos.get(to), gameInfoId);
            janggiRepository.deletePositionStateByPosition(from, gameInfoId);
            return;
        }
        janggiRepository.updatePositionState(to, pieceInfos.get(to), gameInfoId);
        janggiRepository.deletePositionStateByPosition(from, gameInfoId);
    }

    public boolean isEmptyPosition(Position position, int gameInfoId) {
        PositionState positionState = janggiRepository.findPositionStateByPosition(position, gameInfoId);
        return positionState == null;
    }

    public void deleteAllPositionStates(int gameInfoId) {
        janggiRepository.deleteAllPositionStatesByGameInfoId(gameInfoId);
    }

    public void insertPositionHistory(PieceInfos pieceInfos, int gameInfoId, CountryType turn) {
        janggiRepository.savePositionHistory(pieceInfos, gameInfoId, turn);
    }

    public BoardSnapshots loadPositionHistories(int gameInfoId) {
        List<PositionHistory> positionHistories = janggiRepository.findPositionHistoriesByGameInfoId(gameInfoId);
        Map<Integer, List<PositionHistory>> groupingBoardSnapshots = positionHistories.stream()
                .collect(Collectors.groupingBy(PositionHistory::id));
        BoardSnapshots boardSnapshots = new BoardSnapshots();
        for (List<PositionHistory> groupingPositionHistories : groupingBoardSnapshots.values()) {
            boardSnapshots.addBoardSnapshot(makeBoardSnapshots(groupingPositionHistories));
        }
        return boardSnapshots;
    }

    public BoardSnapshot makeBoardSnapshots(List<PositionHistory> positionHistories) {
        Map<Position, PieceInfo> pieceInfos = new HashMap<>();
        CountryType turn = null;
        for (PositionHistory positionHistory : positionHistories) {
            Position position = new Position(positionHistory.x(), positionHistory.y());
            PieceType pieceType = PieceType.valueOf(positionHistory.pieceType());
            turn = CountryType.valueOf(positionHistory.countryType());
            pieceInfos.put(position, new PieceInfo(pieceType, turn));
        }
        return new BoardSnapshot(new PieceInfos(pieceInfos), turn);
    }

    public void deleteAllPositionHistoriesInBoard(int gameInfoId) {
        janggiRepository.deletePositionHistoriesByGameInfoId(gameInfoId);
    }
}
