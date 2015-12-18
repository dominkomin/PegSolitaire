package dk.itu.oop.peg.logic;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;

import dk.itu.oop.peg.gui.PegSolitareSwing;
import dk.itu.oop.peg.model.BoardElement;

public class PegSolitareGui extends PegSolitare
{
	private final PegSolitareSwing gui;
	private final AbstractAction moveAction;

	public PegSolitareGui(int[][] gameBoard)
	{
		super(gameBoard);
		moveAction = new MoveAction(this);
		AbstractAction saveAction = new SaveAction(this);
		AbstractAction loadAction = new LoadAction(this);
		AbstractAction solveAction = new SolveAction(this);
		AbstractAction newGameAction = new NewGameAction(this);
		AbstractAction exitAction = new ExitAction(this);
		AbstractAction displaySolutionAction = new DisplaySolutionAction(this);
		gui = new PegSolitareSwing(board.getBoardElements(), moveAction,
				saveAction, loadAction, solveAction, displaySolutionAction,
				newGameAction, exitAction);
	}

	public void play()
	{
		gui.setVisible(true);
	}

	public boolean moveExecute(Point pointA, Point pointB)
	{
		if (super.move(pointA, pointB))
		{
			if (CheckIfWin())
			{
				gui.showNotification("You won a game!");
			} else if (!isMovePossible())
			{
				gui.showNotification("You lose a game!");
			}
		} else
		{
			gui.showNotification("Invalid move.");
			return false;
		}
		return true;
	}

	public BoardElement getSelectedItem()
	{
		return board.getBoardElements().stream()
				.filter(boardElement -> boardElement.isSelected() == true)
				.findFirst().orElse(null);
	}

	@Override
	public void save()
	{
		try
		{
			super.save();
		} catch (IOException e)
		{
			gui.showNotification("Cannot save your game.");
		}
		gui.showNotification("Game saved.");
	}

	@Override
	public void startNewGame()
	{
		super.startNewGame();
		gui.paintBoard(board.getBoardElements(), moveAction);
	}

	@Override
	public void load()
	{
		try
		{
			super.load();
			gui.paintBoard(board.getBoardElements(), moveAction);
		} catch (IOException | ClassNotFoundException e)
		{
			gui.showNotification("Cannot load your game.");
		}
		gui.showNotification("Game loaded.");
	}

	@Override
	public void exit()
	{
		gui.setVisible(false);
		super.exit();
	}

	public void displaySolution()
	{
		if (solveBacktrack.size() == 0)
		{
			gui.showNotification("No solution ready.");
			return;
		}
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		Runnable helloRunnable = new Runnable()
		{
			int j = 0;

			public void run()
			{
				final int i = j++;
				moveExecute(solveBacktrack.get(i).getPointA(), solveBacktrack
						.get(i).getPointB());
				if (i == solveBacktrack.size() - 1)
					executor.shutdown();
			}
		};
		executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
	}

	public boolean solveExecute()
	{
		List<BoardElement> boardElementsCopy = new ArrayList<BoardElement>();
		board.getBoardElements()
				.forEach(
						element -> boardElementsCopy.add((BoardElement) element
								.clone()));
		board.getBoardElements().forEach(
				element -> element.setNotifyChanges(false));
		if (super.solve())
		{
			gui.showNotification("Solved.");
		} else
		{
			gui.showNotification("Not solved.");
		}
		board.setBoardElements(boardElementsCopy);
		gui.paintBoard(board.getBoardElements(), moveAction);
		return super.solved;
	}
}
