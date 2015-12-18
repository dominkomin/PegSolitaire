package dk.itu.oop.peg.logic;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dk.itu.oop.peg.Player;
import dk.itu.oop.peg.common.DirectionList;
import dk.itu.oop.peg.common.Playable;
import dk.itu.oop.peg.common.SerializationManager;
import dk.itu.oop.peg.model.Board;
import dk.itu.oop.peg.model.BoardElement;
import dk.itu.oop.peg.model.BoardElementType;
import dk.itu.oop.peg.model.Direction;

public abstract class PegSolitare implements Playable
{
	protected Board board;
	private final SerializationManager<Board> serializationManager;
	
	// To limit algorithm when board is impossible to solve.
	private int moveCounter = 0;
	private int moveLimit = 8_000_000;
	// Used in slove algorithm.
	protected boolean solved;
	private List<BoardElement> occupiedElements;
	protected List<Move> solveBacktrack = new ArrayList<Move>();

	public PegSolitare(int[][] gameBoard)
	{
		this.board = new Board(gameBoard);
		serializationManager = new SerializationManager<Board>();
	}

	public abstract void play();
	
	protected void exit()
	{
		System.exit(0);
	}
	
	protected void startNewGame()
	{
		board = new Board(Player.englishPeg);
	}
	
	private Point getMiddlePoint(Point pointA, Point pointB)
	{
		return new Point((pointA.x + pointB.x) / 2, (pointA.y + pointB.y) / 2);
	}

	protected void undoMove(Point pointA, Point pointB)
	{
		board.setElementTypeIfPresent(pointA, BoardElementType.Occupied);
		board.setElementTypeIfPresent(pointB, BoardElementType.Empty);
		board.setElementTypeIfPresent(getMiddlePoint(pointA, pointB),
				BoardElementType.Occupied);
	}

	protected boolean move(Point pointA, Point pointB)
	{
		if (isValidMove(pointA, pointB))
		{
			board.setElementTypeIfPresent(pointA, BoardElementType.Empty);
			board.setElementTypeIfPresent(pointB, BoardElementType.Occupied);
			board.setElementTypeIfPresent(getMiddlePoint(pointA, pointB),
					BoardElementType.Empty);
			return true;
		}
		return false;
	}

	protected int getDestination(int value, Direction direction,
			Direction addDirection, Direction minusDirection)
	{
		if (direction == addDirection)
			return value + 2;
		else if (direction == minusDirection)
			return value - 2;
		return value;
	}

	private boolean isValidMove(Point pointA, Point pointB)
	{
		return board.checkElementType(pointA, BoardElementType.Occupied)
				&& board.checkElementType(pointB, BoardElementType.Empty)
				&& getDistance(pointA, pointB) == 2
				&& board.checkElementType(getMiddlePoint(pointA, pointB),
						BoardElementType.Occupied);
	}

	private double getDistance(Point pointA, Point pointB)
	{
		return Math.sqrt(Math.pow(pointA.x - pointB.x, 2)
				+ Math.pow(pointA.y - pointB.y, 2));
	}

	protected boolean CheckIfWin()
	{
		List<BoardElement> occupiedElements = board.getOccupiedElements();
		return occupiedElements.size() == 1
				&& occupiedElements.get(0).getPosition().x == board.getCentralElement().getPosition().x
				&& occupiedElements.get(0).getPosition().y == board.getCentralElement().getPosition().y;
	}

	protected boolean isMovePossible()
	{
		List<BoardElement> occupiedElements = board.getOccupiedElements();
		for (BoardElement boardElement : occupiedElements)
		{
			for (Direction direction : DirectionList.enumList)
			{
				Point pointB = new Point(getDestination(
						boardElement.getPosition().x, direction,
						Direction.Right, Direction.Left), getDestination(
						boardElement.getPosition().y, direction, Direction.Top,
						Direction.Bottom));
				if (isValidMove(boardElement.getPosition(), pointB))
				{
					return true;
				}
			}
		}
		return false;
	}

	public void save() throws IOException
	{
		serializationManager.SerializeBoard("PegSolitare", board);
	}

	public void load() throws IOException, ClassNotFoundException
	{
		board = serializationManager.DeserializeBoard("PegSolitare");
	}

	/*
	 * Algorithm is not perfect, it always finds a solution to initial board but in some cases it does not work even for solvable boards.
	 * I hope for exercise porpouse it is enough.
	 */
	protected boolean solve()
	{
		occupiedElements = board.getOccupiedElements();
		if (occupiedElements.size() == 1
				&& occupiedElements.get(0).getPosition().x == 3
				&& occupiedElements.get(0).getPosition().y == 3)
		{
			solved = true;
			return solved;
		// Finish algorithm in case of unsolvable board layout.
		} else if (moveCounter > moveLimit)
			return false;
		else
		{
			for (BoardElement boardElement : occupiedElements)
			{
				for (Direction direction : DirectionList.enumList)
				{
					Point pointB = new Point(getDestination(
							boardElement.getPosition().x, direction,
							Direction.Right, Direction.Left), getDestination(
							boardElement.getPosition().y, direction,
							Direction.Top, Direction.Bottom));
					if (this.move(boardElement.getPosition(), pointB))
					{
						++moveCounter;
						// Keep track of moves.
						solveBacktrack.add(new Move(boardElement.getPosition(),
								pointB));
						solve();
						if (!solved)
						{
							// Keep track of moves.
							solveBacktrack.remove(solveBacktrack.size() - 1);
							undoMove(boardElement.getPosition(), pointB);
						}
					}
				}
			}
		}
		return solved;
	}
}
