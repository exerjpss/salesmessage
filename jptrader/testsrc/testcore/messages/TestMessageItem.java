package testcore.messages;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import core.adjustment.AdjustmentItem;
import core.constants.ConstSet;
import core.constants.ErrorCodes;
import core.messages.MessageItem;
import core.product.ProductItemList;
import core.sales.SaleItem;
import core.sales.SaleList;
import core.transactions.IActionTransaction;
import core.transactions.ITransaction;

public class TestMessageItem {

	@Test
	public void testSetMessage() 
	{
		MessageItem msgitem = new MessageItem();
		int errval = msgitem.setMessage ("This is a message string");
		assertTrue ("TestMessageItem setting string return code " + errval,errval==ErrorCodes.OK);
		assertTrue ("TestMessageItem *This is a message string* vs " +  msgitem.getMessage(),"This is a message string".equals(msgitem.getMessage()));
	}
	
	/*
	 * Try setting a larger message than allowed
	 */
	@Test
	public void testSetExcessMessage() 
	{
		MessageItem msgitem = new MessageItem();
		int errval = msgitem.setMessage ("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
		assertTrue ("TestMessageItem exceed string limit return code "  + errval,errval!=0);
		assertTrue ("TestMessageItem exceed string limit string set " +  msgitem.getMessage(),msgitem.getMessage()==null);
	}

	@Test
	public void testProcessSaleMessage() 
	{
		MessageItem msgitem = new MessageItem();
		int errval = msgitem.setMessage ("type=Sale#product=Apples#value=12");
		assertTrue ("TestMessageItem set processsalemessage return code "  + errval,errval==ErrorCodes.OK);

		// check valid message is processed
		errval = msgitem.processMessage();
		assertTrue ("TestMessageItem process processsalemessage return code "  + errval,errval==ErrorCodes.OK);
		
		// get linked list of transactions
		
	}
	
	/*
	 * Test a sale message being added, and retrieve list to check correct items present and set
	 */
	@Test
	public void testProcessSaleMessage2() 
	{
		ProductItemList.Reset();
		MessageItem msgitem = new MessageItem();
		int errval = msgitem.setMessage ("type=Sale#product=Apples#AmouNt=12");
		assertTrue ("TestMessageItem set processsalemessage2 return code "  + errval,errval==ErrorCodes.OK);

		// check valid message is processed
		errval = msgitem.processMessage();
		assertTrue ("TestMessageItem process processsalemessage2 return code "  + errval,errval==ErrorCodes.OK);
		
		assertTrue ("TestMessageItem.processsalemessage2 transactionlist size " + msgitem.getTransactionList().size(), msgitem.getTransactionList().size()!=0);

		ITransaction transaction=null;
		for (int n=0;n<msgitem.getTransactionList().size();n++)
		{
			transaction = msgitem.getTransactionList().get(n);
			assertTrue ("TestMessageItem.processsalemessage2 saleitem found",transaction instanceof SaleItem);
			SaleItem saleitem = (SaleItem)transaction;
			assertTrue ("TestMessageItem.processsalemessage2 saleitem.product " + saleitem.getProduct(),saleitem.getProduct()==1);
			assertTrue ("TestMessageItem.processsalemessage2 saleitem.value " + saleitem.getValue(),saleitem.getValue()==12);
						
		}
		
		// get linked list of transactions
		
	}
	/*
	 * Test multiple sale message being added, and retrieve list to check correct items present and set
	 * Message will have two correctly formatted sales
	 */
	@Test
	public void testProcessSaleMessage3() 
	{
		MessageItem msgitem = new MessageItem();
		int [] amounts = {12,24,13};
		int [] products = {1,2,1};
		int errval = msgitem.setMessage ("type=Sale#product=Apples#AmouNt="+amounts[0]+"#type=Sale#product=Oranges#AmouNt="+amounts[1] +"#type=Sale#product=Apples#AmouNt="+amounts[2]);
		assertTrue ("TestMessageItem set processsalemessage3 return code "  + errval,errval==ErrorCodes.OK);

		// check valid message is processed
		errval = msgitem.processMessage();
		assertTrue ("TestMessageItem process processsalemessage3 return code "  + errval,errval==ErrorCodes.OK);
		
		assertTrue ("TestMessageItem.processsalemessage3 transactionlist size " + msgitem.getTransactionList().size(), msgitem.getTransactionList().size()!=0);

		ITransaction transaction=null;
		for (int n=0;n<msgitem.getTransactionList().size();n++)
		{
			transaction = msgitem.getTransactionList().get(n);
			assertTrue ("TestMessageItem.processsalemessage3 saleitem found",transaction instanceof SaleItem);
			SaleItem saleitem = (SaleItem)transaction;
			assertTrue ("TestMessageItem.processsalemessage3 saleitem.getValid " + saleitem.isValid(),saleitem.isValid()==true);
			assertTrue ("TestMessageItem.processsalemessage3 saleitem.product " + saleitem.getProduct(),saleitem.getProduct()==products[n]);
			assertTrue ("TestMessageItem.processsalemessage3 saleitem.value " + saleitem.getValue(),saleitem.getValue()==amounts[n]);
						
		}
		
		// get linked list of transactions
		
	}
	/*
	 * Test multiple sale message being added
	 * message two is invalid, 
	 * and retrieve list to check correct items present and set
	 * Message will have two correctly formatted sales
	 */
	@Test
	public void testProcessSaleMessage4() 
	{
		MessageItem msgitem = new MessageItem();
		int [] amounts = {12,24,13};
		int [] products = {1,0,1};
		boolean [] valid = {true,false,true};
		
		int errval = msgitem.setMessage ("type=Sale#product=Apples#AmouNt="+amounts[0]+"#type=Sale#productunknown=Oranges#AmouNt="+amounts[1] +"#type=Sale#product=Apples#AmouNt="+amounts[2]);
		assertTrue ("TestMessageItem set processsalemessage4 return code "  + errval,errval==ErrorCodes.OK);

		// check valid message is processed
		errval = msgitem.processMessage();
		assertTrue ("TestMessageItem process processsalemessage4 return code "  + errval,errval==ErrorCodes.OK);
		
		assertTrue ("TestMessageItem.processsalemessage4 transactionlist size " + msgitem.getTransactionList().size(), msgitem.getTransactionList().size()==3);

		ITransaction transaction=null;
		for (int n=0;n<msgitem.getTransactionList().size();n++)
		{
			transaction = msgitem.getTransactionList().get(n);
			assertTrue ("TestMessageItem.processsalemessage4 saleitem found",transaction instanceof SaleItem);
			SaleItem saleitem = (SaleItem)transaction;
			assertTrue ("TestMessageItem.processsalemessage4 saleitem.getValid " + saleitem.isValid(),saleitem.isValid()==valid[n]);
			assertTrue ("TestMessageItem.processsalemessage4 saleitem.product " + saleitem.getProduct(),saleitem.getProduct()==products[n]);
			assertTrue ("TestMessageItem.processsalemessage4 saleitem.value " + saleitem.getValue(),saleitem.getValue()==amounts[n]);
						
		}
		
		// get linked list of transactions
		
	}

	/*
	 * Test multiple valid adjustment messages
	 */
	@Test
	public void testProcessAdjustMessageWithValidInputs() 
	{
		MessageItem msgitem = new MessageItem();
		int [] amounts = {12,24,13};
		int [] products = {1,2,1};
		int [] adjust = {ConstSet.ADJ_NADD,ConstSet.ADJ_NSUB,ConstSet.ADJ_NMUL};
		int errval = msgitem.setMessage ("type=AdjuStment#product=Apples#AmouNt="+amounts[0]+"#adjustmenttype=ADD##type=Adjustment#adjustmenttype=SUB#product=Oranges#AmouNt="+amounts[1] +"#type=Adjustment#product=Apples#adjustmenttype=MUL#AmouNt="+amounts[2]);

		assertTrue ("TestMessageItem set processAdjustmentmessage1 return code "  + errval,errval==ErrorCodes.OK);

		
		
		// check valid message is processed
		errval = msgitem.processMessage();
		assertTrue ("TestMessageItem process processAdjustmentmessage1 return code "  + errval,errval==ErrorCodes.OK);
		
		assertTrue ("TestMessageItem.processAdjustmentmessage1 transactionlist size " + msgitem.getTransactionList().size(), msgitem.getTransactionList().size()!=0);

		ITransaction transaction=null;
		for (int n=0;n<msgitem.getTransactionList().size();n++)
		{
			transaction = msgitem.getTransactionList().get(n);
			

			assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem found",transaction instanceof AdjustmentItem);
			AdjustmentItem adjitem = (AdjustmentItem)transaction;
			assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.product "  + adjitem.getProduct(),adjitem.getProduct()==products[n]);
			assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.value " + adjitem.getAdjustmentval(),adjitem.getAdjustmentval()==amounts[n]);
			assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.type " + adjitem.getAdjustmenttype(),adjitem.getAdjustmenttype()==adjust[n]);
			assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.getValid " + adjitem.isValid(),adjitem.isValid()==true);
						
		}
		
		// get linked list of transactions
		
	}

	/*
	 * Test multiple valid adjustment messages
	 */
	@Test
	public void testProcessAdjustMessageWithErrors() 
	{
		MessageItem msgitem = new MessageItem();
		int [] amounts = {12,24,13};
		int [] products = {0,2,1};
		boolean [] valid = {false,true,false};
		int [] adjust = {ConstSet.ADJ_NADD,ConstSet.ADJ_NSUB,0};
		int errval = msgitem.setMessage ("type=AdjuStment#products=Apples#AmouNt="+amounts[0]+"#adjustmenttype=ADD##type=Adjustment#adjustmenttype=SUB#product=Oranges#AmouNt="+amounts[1] +"#type=Adjustment#product=Apples#AmouNt="+amounts[2]);

		assertTrue ("TestMessageItem set processAdjustmentmessage1 return code "  + errval,errval==ErrorCodes.OK);

		
		
		// check valid message is processed
		errval = msgitem.processMessage();
		assertTrue ("TestMessageItem process processAdjustmentmessage1 return code "  + errval,errval==ErrorCodes.OK);
		
		assertTrue ("TestMessageItem.processAdjustmentmessage1 transactionlist size " + msgitem.getTransactionList().size(), msgitem.getTransactionList().size()!=0);

		ITransaction transaction=null;
		for (int n=0;n<msgitem.getTransactionList().size();n++)
		{
			transaction = msgitem.getTransactionList().get(n);
			

			assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem found",transaction instanceof AdjustmentItem);
			AdjustmentItem adjitem = (AdjustmentItem)transaction;
			assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.product "  + adjitem.getProduct(),adjitem.getProduct()==products[n]);
			assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.value " + adjitem.getAdjustmentval(),adjitem.getAdjustmentval()==amounts[n]);
			assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.type " + adjitem.getAdjustmenttype(),adjitem.getAdjustmenttype()==adjust[n]);
			assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.getValid " + adjitem.isValid(),adjitem.isValid()==valid[n]);
						
		}
		
		// get linked list of transactions
		
	}


	@Test
	public void testProcessAdjustMessageWithValidSale() 
	{
		MessageItem msgitem = new MessageItem();
		int [] amounts = {78,12,24,13};
		int [] products = {1,1,2,1};
		int [] adjust = {0,ConstSet.ADJ_NADD,ConstSet.ADJ_NSUB,ConstSet.ADJ_NMUL};
		int errval = msgitem.setMessage ("type=Sale#product=Apples#AmouNt="+amounts[0]+"#type=AdjuStment#product=Apples#AmouNt="+amounts[1]+"#adjustmenttype=ADD##type=Adjustment#adjustmenttype=SUB#product=Oranges#AmouNt="+amounts[2] +"#type=Adjustment#product=Apples#adjustmenttype=MUL#AmouNt="+amounts[3]);

		assertTrue ("TestMessageItem set processAdjustmentmessage1 return code "  + errval,errval==ErrorCodes.OK);

		
		
		// check valid message is processed
		errval = msgitem.processMessage();
		assertTrue ("TestMessageItem process processAdjustmentmessage1 return code "  + errval,errval==ErrorCodes.OK);
		
		assertTrue ("TestMessageItem.processAdjustmentmessage1 transactionlist size " + msgitem.getTransactionList().size(), msgitem.getTransactionList().size()!=0);

		ITransaction transaction=null;
		for (int n=0;n<msgitem.getTransactionList().size();n++)
		{
			transaction = msgitem.getTransactionList().get(n);
			if (transaction instanceof AdjustmentItem)
			{
				AdjustmentItem adjitem = (AdjustmentItem)transaction;
				assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.product "  + adjitem.getProduct(),adjitem.getProduct()==products[n]);
				assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.value " + adjitem.getAdjustmentval(),adjitem.getAdjustmentval()==amounts[n]);
				assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.type " + adjitem.getAdjustmenttype(),adjitem.getAdjustmenttype()==adjust[n]);
				assertTrue ("TestMessageItem.processAdjustmentmessage1 adjitem.getValid " + adjitem.isValid(),adjitem.isValid()==true);
			}
			if (transaction instanceof SaleItem)
			{
				SaleItem saleitem = (SaleItem)transaction;
				assertTrue ("TestMessageItem.processsalemessage3 saleitem.getValid " + saleitem.isValid(),saleitem.isValid()==true);
				assertTrue ("TestMessageItem.processsalemessage3 saleitem.product " + saleitem.getProduct(),saleitem.getProduct()==products[n]);
				assertTrue ("TestMessageItem.processsalemessage3 saleitem.value " + saleitem.getValue(),saleitem.getValue()==amounts[n]);

			}
		}
		
	
	}
	
	/*
	 * Testing adjustment onto a sale all entered via message string
	 */

	@Test
	public void testProcessAdjustMessageWithValidSaleActionedOnSale() 
	{
		MessageItem msgitem = new MessageItem();
		int [] amounts = {78,12,24,13};
		int [] newamounts = {1170,0,0,0};
		int [] products = {1,1,2,1};
		int [] adjust = {0,ConstSet.ADJ_NADD,ConstSet.ADJ_NSUB,ConstSet.ADJ_NMUL};
		int errval = msgitem.setMessage ("type=Sale#product=Apples#AmouNt="+amounts[0]+"#type=AdjuStment#product=Apples#AmouNt="+amounts[1]+"#adjustmenttype=ADD##type=Adjustment#adjustmenttype=SUB#product=Oranges#AmouNt="+amounts[2] +"#type=Adjustment#product=Apples#adjustmenttype=MUL#AmouNt="+amounts[3]);
		SaleList salelist = new SaleList();
		assertTrue ("TestMessageItem set processAdjustmentmessage1 return code "  + errval,errval==ErrorCodes.OK);

		
		
		// check valid message is processed
		errval = msgitem.processMessage();
		assertTrue ("TestMessageItem process processAdjustmentmessage1 return code "  + errval,errval==ErrorCodes.OK);
		
		assertTrue ("TestMessageItem.processAdjustmentmessage1 transactionlist size " + msgitem.getTransactionList().size(), msgitem.getTransactionList().size()!=0);

		salelist.addTransactionsToList(msgitem.getTransactionList());
		
		//ITransaction transaction=null;
		//
		//for (int n=0;n<msgitem.getTransactionList().size();n++)
		//{
		//	transaction = msgitem.getTransactionList().get(n);
		//	if (transaction instanceof SaleItem)
		//	{
		//		
		//		SaleItem saleitem = (SaleItem)transaction;
		//		salelist.add(saleitem);
		//	}
		//	if (transaction instanceof AdjustmentItem)
		//	{
		//		IActionTransaction iatransaction = (AdjustmentItem) transaction;
		//		salelist.addAction(iatransaction);
		//	}
		//	
		//}
		
		for (int n=0;n<salelist.getSize();n++)
		{
		
			SaleItem saleitem = salelist.getIndex(n);
			// expect only one
			
				
				assertTrue ("TestMessageItem.processsalemessage3 saleitem.getValid " + saleitem.isValid(),saleitem.isValid()==true);
				assertTrue ("TestMessageItem.processsalemessage3 saleitem.product " + saleitem.getProduct(),saleitem.getProduct()==products[n]);
				assertTrue ("TestMessageItem.processsalemessage3 saleitem " + n + ".value " + saleitem.getValue(),saleitem.getValue()==newamounts[n]);
				
		}
		
	
	}
	
}
