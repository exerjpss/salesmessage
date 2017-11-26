package core.adjustment;

import core.constants.ConstSet;
import core.product.ProductItem;
import core.transactions.IActionTransaction;
import core.transactions.ITransaction;
import output.IOutput;

/*
 * Based on a product
 * Stores adjustment messages for a product
 * 
 */
public class AdjustmentItem extends ProductItem implements ITransaction,IActionTransaction, IAdjustmentOutput
{

	// default type and value
	private int adjustmenttype=0;
	private int adjustmentval=0;
	// Whether the parameters have been set
	private boolean adjustmenttypeset = false;
	private boolean adjustmentvalset = false;
	
	/* Initialisation given a parameter and value */
	@Override
	public void addParameter(String key, String value) 
	{
		switch (key.toUpperCase())
		{
			case ConstSet.PARAM_ADJUSTMENT:
				switch (value.toUpperCase())
				{
					case ConstSet.ADJ_ADD :
						setAdjustmenttype (ConstSet.ADJ_NADD);
						break;
					case ConstSet.ADJ_MUL :
						setAdjustmenttype (ConstSet.ADJ_NMUL);
						break;
					case ConstSet.ADJ_SUB :
						setAdjustmenttype (ConstSet.ADJ_NSUB);
						break;
				}
				break;
			case ConstSet.PARAM_PRODUCT :
				setProductInfo(value);
				break;
			case ConstSet.PARAM_AMOUNT:
				int val = Integer.valueOf(value);
				setAdjustmentval(val);
				break;
			
		}
	}

	/* 
	 * If required parameters have been set
	 * and the object is valid
	 */
	@Override
	public boolean isValid() 
	{
		return adjustmentvalset && productset && adjustmenttypeset;
	}

	public int getAdjustmenttype() 
	{
		return adjustmenttype;
	}

	public void setAdjustmenttype(int adjustmenttype) 
	{
		// basic checking that minimum and maximum values have been set
		if ((adjustmenttype >= ConstSet.ADJ_MIN) &&  (adjustmenttype <= ConstSet.ADJ_MAX)) 
		{
			this.adjustmenttype = adjustmenttype;
			adjustmenttypeset = true;
		}
	}

	
	/*
	 * Currently no known limits
	 * Leaving as that, however limits should be introduced to prevent overflow issues
	 * 
	 * Note that adjustment value is INT. If multiplication requires a less than 1 number
	 * then that should revisited
	 * 
	 * Since we have a SUB/ADD operation assume value must be >0
	 */
	public int getAdjustmentval() 
	{
		return adjustmentval;
	}

	public void setAdjustmentval(int adjustmentval) 
	{
		if (adjustmentval >= 0)
		{
			this.adjustmentval = adjustmentval;
			adjustmentvalset = true;
		}
	}
	
	
	/*
	 * Need to apply the action to the original value
	 * and return the new value
	 * 
	 * There is no limit checking on the result or the input
	 * there should be
	 */
	@Override
	public int applyAction(int value) 
	{
		int newvalue = value;
		switch (adjustmenttype)
		{
			case ConstSet.ADJ_NADD:
				newvalue = getAdjustmentval() + value;
			break;
			/*
			 * Note could get a negative value : Is that possible or allowed
			 */
			case ConstSet.ADJ_NSUB:
				newvalue = value - getAdjustmentval();
			break;
			/*
			 * No checks on overflow or extra large numbers
			 */
			case ConstSet.ADJ_NMUL:
				newvalue = value * getAdjustmentval();
			break;
		}
		return newvalue;
	}

	@Override
	public int getValidForProduct() 
	{
		return getProduct();
		
	}

	@Override
	public String getNameString() 
	{
		return getProductInfo();
	}

	@Override
	public String getValueString() 
	{
		return Integer.toString(getAdjustmentval());
	}

	/*
	 * Get information for output strings
	 * 
	 */
	@Override
	public String getAdjustmentTypeString() 
	{
		if (getAdjustmenttype() < ConstSet.ADJ_FULLNAME.length)
			return ConstSet.ADJ_FULLNAME [getAdjustmenttype()];
		return "Unknown";
	}

	@Override
	public String getFullAsString() 
	{
		return getAdjustmentTypeString() + " " + getValueString() + " " + getNameString();
	}

	@Override
	public void outputDetails(IOutput io) 
	{
		io.printlnAdj( getAdjustmentTypeString(),getValueString());
		
	}

}
