package dk.itu.oop.peg;

import dk.itu.oop.peg.common.Playable;
import dk.itu.oop.peg.logic.PegSolitareGui;

/*
 * Solution partialy based on:
 * http://stackoverflow.com/questions/929773/calculating-the-distance-between-two-points
 * http://java2n.blogspot.dk/2012/10/peg-solitaire-in-java.html
 * http://stackoverflow.com/questions/18001087/jpanel-removeall-doesnt-get-rid-of-previous-components
 * http://stackoverflow.com/questions/1726115/performing-an-action-when-an-jmenuitem-is-clicked
 * http://java.dzone.com/articles/how-find-point-coordinates
 * http://stackoverflow.com/questions/929773/calculating-the-distance-between-two-points
 * http://stackoverflow.com/questions/412380/combine-paths-in-java
 * http://stackoverflow.com/questions/2592642/type-safety-unchecked-cast-from-object
 * http://stackoverflow.com/questions/3572160/infinite-looptry-catch-with-exceptions
 * http://alvinalexander.com/java/java-action-abstractaction-actionlistener
 * http://alvinalexander.com/java/java-command-design-pattern-in-java-examples
 * https://scatteredcode.wordpress.com/2011/11/24/from-c-to-java-events/
 * http://stackoverflow.com/questions/23696317/java-8-find-first-element-by-predicate
 * http://stackoverflow.com/questions/4206586/java-array-of-enum-values
 * http://stackoverflow.com/questions/12908412/print-hello-world-every-x-seconds
 * https://blackflux.wordpress.com/2014/04/30/peg-solitaire-brute-force/
 * http://stackoverflow.com/questions/26421688/recursive-backtracking-algorithm-not-solving-some-cases
 * and my previous soltion from last OOP exam 
 * 
 * I was trying to keep track of every page that I used. There are probably
 * many others which probably I am not aware of.
 */

public class Player
{
	public static final int[][] englishPeg =
	{
	{ 0, 0, 1, 1, 1, 0, 0 },
	{ 0, 0, 1, 1, 1, 0, 0 },
	{ 1, 1, 1, 1, 1, 1, 1 },
	{ 1, 1, 1, 2, 1, 1, 1 },
	{ 1, 1, 1, 1, 1, 1, 1 },
	{ 0, 0, 1, 1, 1, 0, 0 },
	{ 0, 0, 1, 1, 1, 0, 0 } };

	public static void main(String[] args) throws InterruptedException
	{
		Playable game = new PegSolitareGui(englishPeg);
		game.play();
	}
}
