package service;

import domain.Position;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.BoardSnapshot;
import domain.board.BoardSnapshots;
import domain.board.BoardStates;
import domain.board.TableSetting;
import domain.country.CountryType;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import domain.piece.PieceType;
import dto.GameInfo;
import dto.PositionState;
import dto.TurnHistory;
import infrastructure.TransactionManager;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repository.GameInfoRepository;
import repository.PositionHistoryRepository;
import repository.PositionStateRepository;
import repository.TurnHistoryRepository;
import view.InputParser;

public class JanggiService {
    private static final String NOT_EXIST_BOARD = "[ERROR] 현재 저장된 보드가 없습니다.";
    private static final String NOT_FOUND_BOARD = "[ERROR] 해당 번호의 board가 존재하지 않습니다.";

    private final GameInfoRepository gameInfoRepository;
    private final PositionStateRepository positionStateRepository;
    private final PositionHistoryRepository positionHistoryRepository;
    private final TurnHistoryRepository turnHistoryRepository;
    private final TransactionManager transactionManager;

    public JanggiService(
            GameInfoRepository gameInfoRepository,
            PositionStateRepository positionStateRepository,
            PositionHistoryRepository positionHistoryRepository,
            TurnHistoryRepository turnHistoryRepository,
            TransactionManager transactionManager) {
        this.gameInfoRepository = gameInfoRepository;
        this.positionStateRepository = positionStateRepository;
        this.positionHistoryRepository = positionHistoryRepository;
        this.turnHistoryRepository = turnHistoryRepository;
        this.transactionManager = transactionManager;
    }

    public List<Integer> readAllGameInfoIds() {
        List<GameInfo> gameInfos = transactionManager.transaction(gameInfoRepository::findAllGameInfos);
        if (gameInfos.isEmpty()) {
            throw new IllegalArgumentException(NOT_EXIST_BOARD);
        }
        return gameInfos.stream()
                .map(GameInfo::id)
                .toList();
    }

    public int makeBoard(TableSetting choTableSetting, TableSetting hanTableSetting) {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.create(choTableSetting, hanTableSetting);
        return transactionManager.transaction(connection -> {
            int gameInfoId = gameInfoRepository.saveGameInfo(connection);
            initBoardState(board.getPieceInfos(), gameInfoId, connection);
            return gameInfoId;
        });
    }

    private void initBoardState(PieceInfos pieceInfos, int gameInfoId, Connection connection) {
        for (Position position : pieceInfos.getKeys()) {
            positionStateRepository.savePositionState(position, pieceInfos.get(position), gameInfoId, connection);
        }
    }

    public int findBoardId(String input) {
        int boardId = InputParser.parseBoardId(input);
        if (!readAllGameInfoIds().contains(boardId)) {
            throw new IllegalArgumentException(NOT_FOUND_BOARD);
        }
        return boardId;
    }

    public Board readBoard(int gameInfoId) {
        return transactionManager.transaction(connection -> {
            GameInfo gameInfo = gameInfoRepository.findGameInfoById(gameInfoId, connection);
            List<PositionState> positionStates = positionStateRepository.findAllPositionStatesByGameInfoId(gameInfoId,
                    connection);
            BoardStates boardStates = readPositionStates(positionStates);
            BoardSnapshots boardSnapshots = loadBoardSnapshots(gameInfoId, connection);
            return new Board(boardStates, boardSnapshots, CountryType.valueOf(gameInfo.turn()));
        });
    }

    private BoardStates readPositionStates(List<PositionState> positionStates) {
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

    private BoardSnapshots loadBoardSnapshots(int gameInfoId, Connection connection) {
        BoardSnapshots boardSnapshots = new BoardSnapshots();
        for (TurnHistory turnHistory : loadTurnHistories(gameInfoId, connection)) {
            List<PositionState> positionHistories = positionHistoryRepository.findPositionHistoriesByTurnHistoryId(
                    turnHistory.id(), connection);
            BoardSnapshot boardSnapshot = makeBoardSnapshot(positionHistories, turnHistory.turn());
            boardSnapshots.addBoardSnapshot(boardSnapshot);
        }
        return boardSnapshots;
    }

    private List<TurnHistory> loadTurnHistories(int gameInfoId, Connection connection) {
        return turnHistoryRepository.findTurnHistoriesByGameInfoId(gameInfoId, connection);
    }

    private BoardSnapshot makeBoardSnapshot(List<PositionState> positionHistories, String countryTurn) {
        Map<Position, PieceInfo> pieceInfos = new HashMap<>();
        for (PositionState positionHistory : positionHistories) {
            Position position = new Position(positionHistory.x(), positionHistory.y());

            PieceType pieceType = PieceType.valueOf(positionHistory.pieceType());
            CountryType countryType = CountryType.valueOf(positionHistory.countryType());
            PieceInfo pieceInfo = new PieceInfo(pieceType, countryType);

            pieceInfos.put(position, pieceInfo);
        }
        return new BoardSnapshot(new PieceInfos(pieceInfos), CountryType.valueOf(countryTurn));
    }

    public TableSetting makeTableSetting(String input) {
        String tableNames = InputParser.parseTableSetting(input);
        return TableSetting.from(tableNames);
    }

    public Position makePosition(String input) {
        List<Integer> positions = InputParser.parsePosition(input);
        return new Position(positions.get(0), positions.get(1));
    }

    public void movePiece(Board board, Position from, Position to, int gameInfoId) {
        board.movePiece(from, to);
        BoardSnapshot boardSnapshot = new BoardSnapshot(board.getPieceInfos(), board.getTurn());
        board.addBoardSnapshot(boardSnapshot);
        transactionManager.transaction(connection -> {
            int turnHistoryId = turnHistoryRepository.saveTurnHistory(gameInfoId, board.getTurn(), connection);
            positionHistoryRepository.savePositionHistory(board.getPieceInfos(), turnHistoryId, connection);
            changePositionStateToAndFrom(from, to, board.getPieceInfos(), gameInfoId, connection);
            board.changeTurn();
            gameInfoRepository.updateGameInfo(board.getTurn(), gameInfoId, connection);
        });
    }

    private void changePositionStateToAndFrom(Position from, Position to, PieceInfos pieceInfos, int gameInfoId,
                                              Connection connection) {
        if (isEmptyPosition(to, gameInfoId, connection)) {
            positionStateRepository.savePositionState(to, pieceInfos.get(to), gameInfoId, connection);
            positionStateRepository.deletePositionStateByPosition(from, gameInfoId, connection);
            return;
        }
        positionStateRepository.updatePositionState(to, pieceInfos.get(to), gameInfoId, connection);
        positionStateRepository.deletePositionStateByPosition(from, gameInfoId, connection);
    }

    private boolean isEmptyPosition(Position position, int gameInfoId, Connection connection) {
        PositionState positionState = positionStateRepository.findPositionStateByPosition(position, gameInfoId,
                connection);
        return positionState == null;
    }

    public void deleteAllByGameInfoId(int gameInfoId) {
        transactionManager.transaction(connection -> {
            positionStateRepository.deleteAllPositionStatesByGameInfoId(gameInfoId, connection);
            for (TurnHistory turnHistory : loadTurnHistories(gameInfoId, connection)) {
                positionHistoryRepository.deletePositionHistoriesByTurnHistoryId(turnHistory.id(), connection);
            }
            turnHistoryRepository.deleteAllTurnHistoriesByGameInfoId(gameInfoId, connection);
            gameInfoRepository.deleteGameInfo(gameInfoId, connection);
        });
    }
}
