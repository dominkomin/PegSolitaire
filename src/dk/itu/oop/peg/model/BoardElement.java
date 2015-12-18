package dk.itu.oop.peg.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Observable;

public class BoardElement extends Observable implements Serializable, Cloneable
{
	private static final long serialVersionUID = 1L;
	private BoardElementType elementType;
	private boolean isSelected = false;
	private Point position;
	private boolean notifyChanges = true;

	public BoardElement(int xPosition, int yPosition,
			BoardElementType elementType)
	{
		position = new Point(xPosition, yPosition);
		this.elementType = elementType;
	}

	public boolean isNotifyChanges()
	{
		return notifyChanges;
	}

	public void setNotifyChanges(boolean notifyChanges)
	{
		this.notifyChanges = notifyChanges;
	}

	public boolean isSelected()
	{
		return isSelected;
	}

	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
		if (notifyChanges)
		{
			setChanged();
			notifyObservers(isSelected);
		}
	}

	public BoardElementType getElementType()
	{
		return elementType;
	}

	public void setElementType(BoardElementType elementType)
	{
		this.elementType = elementType;
		if (notifyChanges)
		{
			setChanged();
			notifyObservers(elementType);
		}
	}

	public Point getPosition()
	{
		return position;
	}

	public Object clone()
	{
		try
		{
			return super.clone();
		} catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
