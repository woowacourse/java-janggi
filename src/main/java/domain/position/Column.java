package domain.position;

public record Column(int value) {

    public Column add(int value) {
        return new Column(this.value + value);
    }

    public boolean isColumnInRange(int start, int end) {
        return value >= start && value <= end;
    }

    public Column getUpper(Column target) {
        if (value > target.value) {
            return this;
        }
        return target;
    }

    public Column getLowerValue(Column target) {
        if (value < target.value) {
            return this;
        }
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Column column = (Column) o;
        return value == column.value;
    }

}
