package dk.itu.oop.peg.gui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dk.itu.oop.peg.model.BoardElement;

public class PegSolitareSwing extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public PegSolitareSwing(List<BoardElement> gameBoard,
			AbstractAction moveAction, AbstractAction saveAction,
			AbstractAction loadAction, AbstractAction solveAction,
			AbstractAction displaySolutionAction, AbstractAction newGameAction,
			AbstractAction exitAction)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setSize(500, 500);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenuItem mntmNewGame = new JMenuItem(newGameAction);
		mntmNewGame.setText("New Game");
		mnFile.add(mntmNewGame);
		JMenuItem mntmSaveGame = new JMenuItem(saveAction);
		mntmSaveGame.setText("Save");
		mnFile.add(mntmSaveGame);
		JMenuItem mntmLoadGame = new JMenuItem(loadAction);
		mntmLoadGame.setText("Load");
		mnFile.add(mntmLoadGame);
		JMenuItem mntmExit = new JMenuItem(exitAction);
		mntmExit.setText("Exit");
		mnFile.add(mntmExit);
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		JMenuItem mntmFindSolution = new JMenuItem(solveAction);
		mntmFindSolution.setText("Find Solution");
		mnTools.add(mntmFindSolution);
		JMenuItem mntmDisplaySolution = new JMenuItem(displaySolutionAction);
		mntmDisplaySolution.setText("Display solution");
		mnTools.add(mntmDisplaySolution);
		paintBoard(gameBoard, moveAction);
	}

	public void paintBoard(List<BoardElement> gameBoard,
			AbstractAction moveAction)
	{
		BoardElement lastElement = gameBoard.get(gameBoard.size() - 1);
		contentPane.setLayout(new GridLayout(lastElement.getPosition().x + 1,
				lastElement.getPosition().y + 1));
		contentPane.removeAll();
		contentPane.revalidate();
		contentPane.repaint();
		for (BoardElement boardElement : gameBoard)
		{
			PegButton button = new PegButton(moveAction, boardElement);
			boardElement.addObserver(button);
			contentPane.add(button);
		}
	}

	public void showNotification(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}
}
