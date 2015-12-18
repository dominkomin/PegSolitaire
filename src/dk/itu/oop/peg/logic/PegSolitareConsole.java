package dk.itu.oop.peg.logic;

import java.awt.Point;
import java.util.InputMismatchException;
import java.util.Scanner;

import dk.itu.oop.peg.common.DirectionList;
import dk.itu.oop.peg.model.BoardElement;
import dk.itu.oop.peg.model.BoardElementType;
import dk.itu.oop.peg.model.Direction;

public class PegSolitareConsole extends PegSolitare
{
	public PegSolitareConsole(int[][] gameBoard)
	{
		super(gameBoard);
	}

	public void play()
	{
		Scanner in = new Scanner(System.in);
		print();
		int direction = 0;
		Point pointA = new Point();
		Point pointB = new Point();
		while (true)
		{
			boolean goodInput = false;
			try
			{
				System.out.print("Enter x: ");
				pointA.x = in.nextInt();
				System.out.print("Enter y: ");
				pointA.y = in.nextInt();
				System.out
						.print("Enter direction 0-left, 1-top, 2-right, 3-bottm: ");
				direction = in.nextInt();
				int newX = getDestination(pointA.x,
						DirectionList.enumList[direction], Direction.Right,
						Direction.Left);
				int newY = getDestination(pointA.y,
						DirectionList.enumList[direction], Direction.Top,
						Direction.Bottom);
				pointB = new Point(newX, newY);
				goodInput = true;
			} catch (InputMismatchException e)
			{
				System.out.println("Bad input, try again.");
				in.next();
			} catch (ArrayIndexOutOfBoundsException e)
			{
				System.out.println("Bad input, try again.");
				in.next();
			}
			
			if (!goodInput)
			{
				continue;
			}
			
			if (!move(pointA, pointB))
				System.out.println("Invalid move, try again!");
			if (CheckIfWin())
			{
				System.out.println("You won!");
				in.close();
				continue;
			} else if (!isMovePossible())
			{
				System.out.println("You lose, no possible moves!");
				in.close();
				continue;
			}
			print();
		}
	}

	private void print()
	{
		int rowNumber = 0;
		for (BoardElement boardElement : board.getBoardElements())
		{
			if (boardElement.getPosition().y != rowNumber)
			{
				System.out.println();
				rowNumber = boardElement.getPosition().y;
			}
			String toDisplay = "";
			if (boardElement.getElementType() == BoardElementType.Empty)
				toDisplay = "0";
			else if (boardElement.getElementType() == BoardElementType.Inactive)
				toDisplay = " ";
			else if (boardElement.getElementType() == BoardElementType.Occupied)
				toDisplay = "X";
			System.out.print(toDisplay);
		}
		System.out.println();
		System.out.println();
	}
}
