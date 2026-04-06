package db.session;

public record Session<T>(
        T payload,
        int id
) {
}
