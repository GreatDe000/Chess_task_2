package move;

/**
 * Класс, описывающий ход.
 */
public class Move {

/**
 * Поле fromRow.
 */
    private final int fromRow;
/**
 * Поле fromCol.
 */
    private final int fromCol;
/**
 * Поле toRow.
 */
    private final int toRow;
/**
 * Поле toCol.
 */
    private final int toCol;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

/**
 * Возвращает значение fromrow.
 * @return результат выполнения
 */
    public int getFromRow() {
        return fromRow;
    }

/**
 * Возвращает значение fromcol.
 * @return результат выполнения
 */
    public int getFromCol() {
        return fromCol;
    }

/**
 * Возвращает значение torow.
 * @return результат выполнения
 */
    public int getToRow() {
        return toRow;
    }

/**
 * Возвращает значение tocol.
 * @return результат выполнения
 */
    public int getToCol() {
        return toCol;
    }
}
