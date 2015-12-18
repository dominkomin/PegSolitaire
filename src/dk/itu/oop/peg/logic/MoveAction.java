package dk.itu.oop.peg.logic;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import dk.itu.oop.peg.gui.PegButton;
import dk.itu.oop.peg.model.BoardElement;

public class MoveAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private final PegSolitareGui guiLogic;

	public MoveAction(PegSolitareGui guiLogic)
	{
		this.guiLogic = guiLogic;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		PegButton clickedButton = (PegButton) arg0.getSource();
		BoardElement sourceElement = guiLogic.getSelectedItem();
		if (sourceElement == null)
		{
			clickedButton.getBoardElement().setSelected(true);
		} else
		{
			guiLogic.moveExecute(sourceElement.getPosition(),clickedButton.getBoardElement().getPosition());
			sourceElement.setSelected(false);
		}
	}
}
