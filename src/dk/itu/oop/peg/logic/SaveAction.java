package dk.itu.oop.peg.logic;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class SaveAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private final PegSolitareGui guiLogic;

	public SaveAction(PegSolitareGui guiLogic)
	{
		this.guiLogic = guiLogic;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		guiLogic.save();
	}
}
