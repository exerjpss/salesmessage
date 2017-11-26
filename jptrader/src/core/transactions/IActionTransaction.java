package core.transactions;

import output.IOutput;

/*
 * Aimed at applying adjustments on a value and returning the new value
 */

public interface IActionTransaction 
{
	abstract int applyAction (int value);
	abstract int getValidForProduct();
	abstract void outputDetails (IOutput io);

}
