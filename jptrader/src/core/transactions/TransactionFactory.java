package core.transactions;

import core.adjustment.AdjustmentItem;
import core.constants.ConstSet;
import core.sales.SaleItem;

public class TransactionFactory 
{
	public static ITransaction generateTransaction (String type)
	{
		if (type==null) return null;
		
		if (type.toUpperCase().equals(ConstSet.TYPE_SALE))
			return new SaleItem ();
		if (type.toUpperCase().equals(ConstSet.TYPE_ADJUSTMENT))
		    return new AdjustmentItem ();
		
		return null;
			
	}

}
