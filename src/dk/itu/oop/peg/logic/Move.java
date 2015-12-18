package dk.itu.oop.peg.logic;

import java.awt.Point;

public class Move
{
	private final Point pointA;
	private final Point pointB;
	
	public Move(Point pointA, Point pointB)
	{
		this.pointA = pointA;
		this.pointB = pointB;
	}

	public Point getPointA()
	{
		return pointA;
	}

	public Point getPointB()
	{
		return pointB;
	}
}
