package pieces;

import board.Board;
import board.Cell;

/**
 * Класс, представляющий короля.
 */
public class King extends Piece {

    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    @Override
/**
 * Выполняет действие: isValidMove.
 * @param from параметр метода
 * @param to параметр метода
 * @param board параметр метода
 * @return результат выполнения
 */
    public boolean isValidMove(Cell from, Cell to, Board board) {
        int dx = Math.abs(from.getX() - to.getX());
        int dy = Math.abs(from.getY() - to.getY());

        return dx <= 1 && dy <= 1;
    }
}
