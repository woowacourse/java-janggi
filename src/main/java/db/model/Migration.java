package db.model;

public record Migration(int version, String description, String resourcePath) {
}
