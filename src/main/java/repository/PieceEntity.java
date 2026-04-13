package repository;

record PieceEntity(
        long gameId,
        int row,
        int file,
        String type,
        String side
) {
}
