package testcore.transactions;

import static org.junit.Assert.*;

import org.junit.Test;

import core.adjustment.AdjustmentItem;
import core.constants.ConstSet;
import core.sales.SaleItem;
import core.transactions.ITransaction;
import core.transactions.TransactionFactory;

public class TestTransactionFactory {

	@Test
	public void testGenerateSaleItem() 
	{
		ITransaction transaction = TransactionFactory.generateTransaction(ConstSet.TYPE_SALE);
		assertTrue ("TestTransactionFactory test salegeneration", transaction instanceof SaleItem);
	}

	@Test
	public void testGenerateAdjustmentItem() 
	{
		ITransaction transaction = TransactionFactory.generateTransaction(ConstSet.TYPE_ADJUSTMENT);
		assertTrue ("TestTransactionFactory test adjustment", transaction instanceof AdjustmentItem);
	}		
		@Test
		public void testGenerateErrortem() 
	{
	    ITransaction transaction = TransactionFactory.generateTransaction("Random");
		assertTrue ("TestTransactionFactory test random", transaction==null);
		
		transaction = TransactionFactory.generateTransaction("");
		assertTrue ("TestTransactionFactory test blank", transaction==null);
		
		transaction = TransactionFactory.generateTransaction(null);
		assertTrue ("TestTransactionFactory test null", transaction==null);
	}	

}
