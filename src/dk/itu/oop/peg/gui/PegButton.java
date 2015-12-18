package dk.itu.oop.peg.gui;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import dk.itu.oop.peg.model.BoardElement;
import dk.itu.oop.peg.model.BoardElementType;

public class PegButton extends JButton implements Observer
{
	private static final long serialVersionUID = 1L;
	final private BoardElement boardElement;

	public PegButton(AbstractAction moveAction, BoardElement boardElement)
	{
		super(moveAction);
	
		if (boardElement.getElementType() == BoardElementType.Inactive)
			setVisible(false);
		drawLabel(boardElement.getElementType());
		this.boardElement = boardElement;
	}
	
	public BoardElement getBoardElement()
	{
		return boardElement;
	}

	private void drawLabel(BoardElementType elementType)
	{
		if (elementType == BoardElementType.Empty)
			setText("O");
		else if (elementType == BoardElementType.Occupied)
			setText("X");
	}

	@Override
	public void update(Observable o, Object arg)
	{	
		if (arg instanceof BoardElementType)
			drawLabel((BoardElementType)arg);
		else if (arg instanceof Boolean)
		{
			if (boardElement.isSelected())
				setBackground(Color.RED);
			else
				setBackground(null);
		}
	}
}
