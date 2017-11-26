package core.sales;

import java.util.LinkedList;

import core.constants.ConstSet;
import core.product.ProductItem;
import core.product.ProductItemList;
import core.transactions.IActionTransaction;
import core.transactions.ITransaction;

import output.IOutput;

/* 
 * Hold the sale record as received
 * Prefer to have a product as a product
 * So limits can be established
 * 
 * SaleItem will also hold a list of Adjustment Transactions applied on a saleitem
 * 
 */

public class SaleItem extends ProductItem implements ITransaction, ISalesOutput
{
	
	// number of sales, for message type 2
	// default will be 1, unless specified
	
	private int qty = 1;
	
	// value of sale and whether this parameter has been set
	private int amount = 0;
	private boolean amountset = false;
	
	// Keep a track of adjustment actions applied to this object
	private LinkedList<IActionTransaction> actions = new LinkedList<>();
	
	// Not needed any more
	public int initSale (int product, int amount)
	{
		int err=0;

		setProduct(product);
		setAmount(amount);
		return err;
	}
	// Not needed any more
	public int initSale(int product, int amount, int qty) 
	{
		setQty(qty);
		initSale (product,amount);
		return 0;
	}
	

	/*
	 * Std getters
	 */
	public int getValue()   {return amount;};
	public int getQty ()    {return qty;};
	
	/*
	 * STD setters
	 * can add checkers like qty should be 1 or greater
	 * amount should be positive
	 * Will depend on specification of what is allowed
	 */
	
	private void setAmount (int value) 
	{
		amount=value;
		amountset=true;
	}
	private void setQty(int value) 
	{
		qty=value;
	}

	/*
	 * ITransaction interface
	 * Requires the class to know what parameters to accept and how to process them
	 * Parameter name supplied as String
	 * Value also supplied as String
	 * 
	 * This will call static productlist and add/get the product id
	 * 
	 * The procedure also sets whether the parameters have been set
	 * 
	 */
	@Override
	public void addParameter(String key, String value) 
	{
		if ((key!=null) && (value!=null))
		{
			if (key.toUpperCase().equals(ConstSet.PARAM_AMOUNT))
			{
				try {
					int val = Integer.valueOf(value);
					setAmount(val);
				} catch (Exception e)
				{
					// Log Exception ? 
				}
			}
			if (key.toUpperCase().equals(ConstSet.PARAM_QTY))
			{
				try {
					int val = Integer.valueOf(value);
					setQty (val);
				} catch (Exception e)
				{
					// Log Exception ? 
				}
			}
			if (key.toUpperCase().equals(ConstSet.PARAM_PRODUCT))
			{
				setProductInfo (value);
			}
			
		}
	}

	/*
	 * Returns whether the object has been correctly set
	 * 
	 */
	@Override
	public boolean isValid() 
	{
		return amountset && productset;
	}
	
	/*
	 * Given an action, apply the action on the current value
	 * and accept the result
	 * Store the action
	 */
	
	public void actionAdjustment (IActionTransaction transaction)
	{
		int val = transaction.applyAction(getValue());
		// should have some checking but we don't know the specs yet
		setAmount(val);
		this.actions.add(transaction);
	}
	
	/*
	 * Getters
	 */
	public LinkedList<IActionTransaction> getActions() 
	{
		return actions;
	}
	
	/*
	 * For output
	 * 
	 */
	@Override
	public String getNameString() 
	{
		return getProductInfo();
	}
	@Override
	public String getTotalValueString() 
	{
		return "" + (getValue() * getQty());
	}
	@Override
	public String getValueString() 
	{
		return "" + getValue() ;
	}
	@Override
	public String getQtyString() 
	{
		return "" +  getQty();
	}
	
	@Override
	public String getAllAsString() 
	{
		return getNameString() + " " + getValueString() + " " + getQtyString() + " " + getTotalValueString();
	}
	
	/*
	 * Passed an output send the relevant information to the output channel
	 */
	public void outputSale(IOutput io) 
	{
		io.println(ProductItemList.getProductName(getProduct()), getQtyString(), getTotalValueString(), getValueString());
	
	}
	/*
	 * Passed an output send the actions on this saleitem
	 */
	public void outputActions(IOutput io) 
	{
		for (int n=0;n<actions.size();n++)
		{
			actions.get(n).outputDetails(io);
		}
	}
	


}
