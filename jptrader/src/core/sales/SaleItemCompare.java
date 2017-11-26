package core.sales;

import java.util.Comparator;

import core.transactions.ITransaction;

public class SaleItemCompare implements Comparator<ITransaction> 
{
	
	@Override
	public int compare(ITransaction it1, ITransaction it2) 
	{
		SaleItem s1 = (SaleItem) it1;
		SaleItem s2 = (SaleItem) it2;
		return s1.getProduct() - s2.getProduct();
	}

}
