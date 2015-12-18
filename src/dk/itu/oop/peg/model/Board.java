package dk.itu.oop.peg.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dk.itu.oop.peg.common.BoardElementTypeList;

public class Board implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<BoardElement> boardElements;


	final private BoardElement centralElement;

	public Board(int[][] board)
	{
		int y = board.length - 1;
		List<BoardElement> tempBoardElements = new ArrayList<BoardElement>();
		for (int[] boardRow : board)
		{
			for (int boardRowItem = 0; boardRowItem < boardRow.length; boardRowItem++)
				tempBoardElements
						.add(new BoardElement(
								boardRowItem,
								y,
								BoardElementTypeList.enmuList[boardRow[boardRowItem]]));
			--y;
		}
		boardElements = tempBoardElements;
		centralElement = boardElements
				.stream()
				.filter(element -> element.getElementType() == BoardElementType.Empty)
				.findFirst().orElse(null);
	}
	
	public List<BoardElement> getBoardElements()
	{
		return boardElements;
	}

	public void setBoardElements(List<BoardElement> boardElements)
	{
		this.boardElements = boardElements;
	}
	
	public BoardElement getCentralElement()
	{
		return centralElement;
	}

	private BoardElement findBoardElement(Point point)
	{
		return boardElements
				.stream()
				.filter(boardElement -> boardElement.getPosition().x == point.x
						&& boardElement.getPosition().y  == point.y).findFirst()
				.orElse(null);
	}

	public void setElementTypeIfPresent(Point point, BoardElementType type)
	{
		BoardElement element = findBoardElement(point);
		if (null != element)
			element.setElementType(type);
	}

	public boolean checkElementType(Point point, BoardElementType type)
	{
		BoardElement element = findBoardElement(point);
		return null != element && element.getElementType() == type;
	}

	public List<BoardElement> getOccupiedElements()
	{
		return boardElements
				.stream()
				.filter(element -> element.getElementType() == BoardElementType.Occupied)
				.collect(Collectors.toList());
	}
}
