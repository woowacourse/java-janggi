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
import infrastructure.TransactionManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import repository.GameInfoRepository;
import repository.PositionHistoryRepository;
import repository.PositionStateRepository;

public class JanggiService {
    private final GameInfoRepository gameInfoRepository;
    private final PositionStateRepository positionStateRepository;
    private final PositionHistoryRepository positionHistoryRepository;
    private final TransactionManager transactionManager;

    public JanggiService(
            GameInfoRepository gameInfoRepository,
            PositionStateRepository positionStateRepository,
            PositionHistoryRepository positionHistoryRepository,
            TransactionManager transactionManager) {
        this.gameInfoRepository = gameInfoRepository;
        this.positionStateRepository = positionStateRepository;
        this.positionHistoryRepository = positionHistoryRepository;
        this.transactionManager = transactionManager;
    }

    public List<Integer> readAllGameInfoIds() {
        List<GameInfo> gameInfos = transactionManager.transaction(gameInfoRepository::findAllGameInfos);
        if (gameInfos.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 현재 저장된 보드가 없습니다.");
        }
        return gameInfos.stream()
                .map(GameInfo::id)
                .toList();
    }

    public int insertGameInfo() {
        return transactionManager.transaction(gameInfoRepository::saveGameInfo);
    }

    public void updateGameInfo(CountryType countryType, Map<CountryType, Double> scores, int id) {
        transactionManager.transaction(connection -> {
            gameInfoRepository.updateGameInfo(countryType, scores, id, connection);
        });
    }

    public CountryType readCountryTurn(int id) {
        GameInfo gameInfo = transactionManager.transaction(connection -> {
            return gameInfoRepository.findGameInfoById(id, connection);
        });
        return CountryType.valueOf(gameInfo.turn());
    }

    public Board readBoard(int id) {
        GameInfo gameInfo = transactionManager.transaction(connection -> {
            return gameInfoRepository.findGameInfoById(id, connection);
        });
        BoardStates boardStates = readPositionStates(id);
        return new Board(boardStates, gameInfo.cho_score(), gameInfo.han_score());
    }

    private BoardStates readPositionStates(int gameInfoId) {
        List<PositionState> positionStates = transactionManager.transaction(connection -> {
            return positionStateRepository.findAllPositionStatesByGameInfoId(gameInfoId, connection);
        });
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
        transactionManager.transaction(connection -> {
            gameInfoRepository.deleteGameInfo(id, connection);
        });
    }

    public void insertPositionState(Position position, PieceInfo pieceInfo, int gameInfoId) {
        transactionManager.transaction(connection -> {
            positionStateRepository.savePositionState(position, pieceInfo, gameInfoId, connection);
        });
    }

    public void changePositionStateToAndFrom(Position from, Position to, PieceInfos pieceInfos, int gameInfoId) {
        if (isEmptyPosition(to, gameInfoId)) {
            transactionManager.transaction(connection -> {
                positionStateRepository.savePositionState(to, pieceInfos.get(to), gameInfoId, connection);
                positionStateRepository.deletePositionStateByPosition(from, gameInfoId, connection);
            });
            return;
        }
        transactionManager.transaction(connection -> {
            positionStateRepository.updatePositionState(to, pieceInfos.get(to), gameInfoId, connection);
            positionStateRepository.deletePositionStateByPosition(from, gameInfoId, connection);
        });
    }

    public boolean isEmptyPosition(Position position, int gameInfoId) {
        PositionState positionState = transactionManager.transaction(connection -> {
            return positionStateRepository.findPositionStateByPosition(position, gameInfoId, connection);
        });
        return positionState == null;
    }

    public void deleteAllPositionStates(int gameInfoId) {
        transactionManager.transaction(connection -> {
            positionStateRepository.deleteAllPositionStatesByGameInfoId(gameInfoId, connection);
        });
    }

    public void insertPositionHistory(PieceInfos pieceInfos, int gameInfoId, CountryType turn) {
        transactionManager.transaction(connection -> {
            positionHistoryRepository.savePositionHistory(pieceInfos, gameInfoId, turn, connection);
        });
    }

    public BoardSnapshots loadPositionHistories(int gameInfoId) {
        List<PositionHistory> positionHistories = transactionManager.transaction(connection -> {
            return positionHistoryRepository.findPositionHistoriesByGameInfoId(gameInfoId, connection);
        });
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
        transactionManager.transaction(connection -> {
            positionHistoryRepository.deletePositionHistoriesByGameInfoId(gameInfoId, connection);
        });
    }
}
