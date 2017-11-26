package core.sales;

import java.util.Collections;
import java.util.LinkedList;

import core.product.ProductItemList;
import core.transactions.IActionTransaction;
import core.transactions.ITransaction;

import output.IOutput;

/*
 * Object to hold, add, process SaleItems
 */

public class SaleList 
{

	// Keeping sales as simple linkedlist
	
	private LinkedList <SaleItem> sales = new LinkedList <SaleItem> ();
	private int count = 0;

	public int add(int product, int amount) 
	{
		int errval = 0;
		
		SaleItem saleitem = new SaleItem();
		errval = saleitem.initSale(product, amount);
		if (errval==0)
		{
			// Simply add this to the list
			sales.add(saleitem);
		}
		
		return errval;
	}
	
	/*
	 * Iterate through list
	 * searching for object
	 * 
	 */
	
	
	public int getSalesCount() 
	{
		
		return sales.size();
	}


	/*
	 * This procedure is specifically for testing
	 * return null if out of bounds
	 */
	public SaleItem getIndex(int index) 
	{
		try 
		{ 
			return sales.get(index);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * Simply add saleitem to list
	 */
	
	public void add(SaleItem saleitem) 
	{
		if (saleitem !=null)
			if (saleitem.isValid())
				sales.addLast(saleitem);
		
	}
	
	/*
	 * No multi threading at present, otherwise would
	 * have to consider the integrity of the iteration
	 * and alteration
	 */
	
	public void addAction (IActionTransaction transaction)
	{
		int productid = transaction.getValidForProduct();
		for (int n=0;n<sales.size();n++)
			if (sales.get(n).getProduct()==productid)
			{
				sales.get(n).actionAdjustment(transaction);
				
			}
	}

	public int getSize() 
	{
		return sales.size();
	}
	
	public void addTransactionsToList (LinkedList<ITransaction> transactionlist)
	{
		for (int n=0;n<transactionlist.size();n++)
		{
			ITransaction transaction = transactionlist.get(n);
			if (transaction instanceof IActionTransaction)
			{
				addAction ((IActionTransaction) transaction);
			}
			if (transaction instanceof SaleItem)
				add ((SaleItem)transaction);
			
		}
	}
	
	/*
	 * I would prefer having a sorted list seperate from the historical record
	 * but for now just sorting the current list
	 */
	public void sortList ()
	{
		Collections.sort (sales,new SaleItemCompare());
		
	}
	
	public void outputSales (IOutput output)
	{
		int lastProduct = -1;
		
		int qty = 0;
		int totalvalue = 0;
		boolean print = false;
		for (int n=0;n<sales.size();n++)
		{
			
			if (lastProduct != sales.get(n).getProduct())
			{
				if (print)
				{
					output.println(ProductItemList.getProductName(lastProduct), "" + qty, "" + totalvalue);
					
				}
				print = true;
				lastProduct = sales.get(n).getProduct();
				qty = sales.get(n).getQty();
				totalvalue = sales.get(n).getValue() * qty;
			}
			else
			{
				qty = qty + sales.get(n).getQty();
				totalvalue = totalvalue + sales.get(n).getValue() * sales.get(n).getQty();

			}
		}
		if (print)
		{
			output.println(ProductItemList.getProductName(lastProduct), "" + qty, "" + totalvalue);
			output.println();
			
		}

	}

	public void outputSalesDetail(IOutput io) 
	{
		io.printlnAdj();
		for (int n=0;n<sales.size();n++)
		{
			sales.get(n).outputSale (io);
			sales.get(n).outputActions (io);
			
		}
		
	}
	
}
