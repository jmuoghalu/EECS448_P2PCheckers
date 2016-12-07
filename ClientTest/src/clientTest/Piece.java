package clientTest;

import java.io.Serializable;

/**
 * Piece holds information about a single piece. This object will be retained
 * even after the piece has moved into a new square.
 */
public class Piece implements Serializable {
	private PieceType type;
	private Player player;

	/**
	 * Create a single, "nothing" piece.
	 */
	public Piece() {
		this(PieceType.EMPTY, Player.NONE);
	}

	/**
	 * Create a piece from a type and player
	 * 
	 * @param type
	 *            The type to use for this piece.
	 * @param player
	 *            The player that owns the piece.
	 */
	public Piece(PieceType type, Player player) {
		this.type = type;
		this.player = player;
	}

	/**
	 * Get the type of this piece.
	 * 
	 * @return The type of this piece.
	 */
	public PieceType getType() {
		return this.type;
	}

	/**
	 * Upgrade this piece to a king.
	 */
	public void king() {
		this.type = PieceType.KING;
	}

	/**
	 * Get the player that owns this piece.
	 * 
	 * @return The player that owns the piece.
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * See if this piece matches a provided piece.
	 * 
	 * @param b
	 *            The piece that may have the same attributes as this piece.
	 * @return Whether the piece matches.
	 */
	public boolean matches(Piece b) {
		// if it quacks like a duck...
		return this.type == b.type && this.player == b.player;
	}
}
