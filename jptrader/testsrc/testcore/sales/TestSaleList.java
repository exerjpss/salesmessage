package testcore.sales;

import static org.junit.Assert.*;

import org.junit.Test;

import output.IOutput;
import output.SalesConsoleOut;
import testoutput.TestIOutput;

import core.adjustment.AdjustmentItem;
import core.constants.ConstSet;
import core.product.ProductItemList;
import core.sales.SaleItem;
import core.sales.SaleList;

public class TestSaleList 
{

	
	/*
	 * adding saleitem obejcts to the queue
	 */
	@Test
	public void testSaleItemObjectsIfValid() 
	{
		SaleList salelist = new SaleList ();
		SaleItem saleitem = new SaleItem();
		
		/*
		 * Add three invalid objects
		 */
		salelist.add(saleitem);  
		salelist.add(saleitem);
		salelist.add(saleitem);
		
		/*
		 * Add one valid object
		 */
		saleitem.setProductInfo("Apple");
		saleitem.addParameter(ConstSet.PARAM_AMOUNT, "2");
		salelist.add(saleitem);
		
		/*
		 * Confirm number of valid objects
		 */
		
		int n = salelist.getSalesCount();
		assertTrue ("TestSaleList addingobjects count = " + n,n==1);
		
	}
	
	/*
	 * Procedure for repetitive actions
	 */
	private SaleList InitialiseWithThreeObjects (SaleList salelist)
	{
		SaleItem saleitem;
		
		/*
		 * Add some invalid objects
		 */
		saleitem = new SaleItem();
		salelist.add(saleitem);
		saleitem = new SaleItem();
		salelist.add(saleitem);
		saleitem = new SaleItem();
		salelist.add(saleitem);
		saleitem = new SaleItem();
		
		/*
		 * Add an apple
		 */
		saleitem.setProductInfo("Apple");
		saleitem.addParameter(ConstSet.PARAM_AMOUNT, "2");
		salelist.add(saleitem);
		
		/*
		 * Add an orange
		 */
		saleitem = new SaleItem();
		saleitem.setProductInfo("Oranges");
		saleitem.addParameter(ConstSet.PARAM_AMOUNT, "5");
		salelist.add(saleitem);
		
		/*
		 * add another apple
		 */
		saleitem = new SaleItem();
		saleitem.setProductInfo("Apple");
		saleitem.addParameter(ConstSet.PARAM_AMOUNT, "7");
		salelist.add(saleitem);
		return salelist;
	}

	@Test
	public void testAddMultipleSaleItemObjectsIfValid() 
	{
		/*
		 * Initialisation
		 */
		SaleList salelist = new SaleList ();
		/*
		 * Add some objects
		 */
		salelist = InitialiseWithThreeObjects(salelist);
		int n = salelist.getSalesCount();
		assertTrue ("TestSaleList addingobjects count = " + n,n==3);
	}
	
	private SaleList AddOneAdjustments (SaleList salelist)
	{
		AdjustmentItem adjitem = new AdjustmentItem();
		adjitem.setProductInfo("Apple");
		adjitem.setAdjustmenttype(ConstSet.ADJ_NADD);
		adjitem.setAdjustmentval(5);
		salelist.addAction(adjitem);
		return salelist;
	}
	private SaleList AddSecondAdjustment (SaleList salelist)
	{
		AdjustmentItem adjitem = new AdjustmentItem();
		adjitem.setProductInfo("Oranges");
		adjitem.setAdjustmenttype(ConstSet.ADJ_NMUL);
		adjitem.setAdjustmentval(4);
		salelist.addAction(adjitem);
		return salelist;

	}
	/*
	 * Add some objects to the list
	 * Then apply some adjustments
	 * Verify objects in the list and adjustments
	 */
	@Test
	public void testSaleItemObjectsIfValidAndApplyAdjustment() 
	{
		/*
		 * Initialisation
		 */
		SaleList salelist = new SaleList ();
		SaleItem saleitem;
		/*
		 * Add some objects
		 */
		salelist = InitialiseWithThreeObjects(salelist);
				
		salelist = AddOneAdjustments(salelist);

		// +5
		saleitem = salelist.getIndex(0);
		assertTrue ("TestSaleList apply adjustment unit 1 7 vs " + saleitem.getValue(),saleitem.getValue()==7);
		
		// +0
		saleitem = salelist.getIndex(1);
		assertTrue ("TestSaleList apply adjustment unit 2 5 vs " + saleitem.getValue(),saleitem.getValue()==5);
		
		// +5
		saleitem = salelist.getIndex(2);
		assertTrue ("TestSaleList apply adjustment unit 3 12 vs " + saleitem.getValue(),saleitem.getValue()==12);
		
		salelist = AddSecondAdjustment(salelist);
		// --
		saleitem = salelist.getIndex(0);
		assertTrue ("TestSaleList apply adjustment unit 1 7 vs " + saleitem.getValue(),saleitem.getValue()==7);
		
		// *4
		saleitem = salelist.getIndex(1);
		assertTrue ("TestSaleList apply adjustment unit 2 20 vs " + saleitem.getValue(),saleitem.getValue()==20);
		
		// --
		saleitem = salelist.getIndex(2);
		assertTrue ("TestSaleList apply adjustment unit 3 12 vs " + saleitem.getValue(),saleitem.getValue()==12);

	}
		
	private SaleList addAnotherSaleAndThenAnotherAdjustment (SaleList salelist)
	{
		/* 
		 * Add another object
		 * Previous adjustments will not apply
		 */
		
		SaleItem saleitem = new SaleItem();
		saleitem.setProductInfo("Apple");
		saleitem.addParameter(ConstSet.PARAM_AMOUNT, "9");
		salelist.add(saleitem);
		
		/*
		 * Add another adjustment
		 */
		AdjustmentItem adjitem = new AdjustmentItem();
		adjitem.setProductInfo("Apple");
		adjitem.setAdjustmenttype(ConstSet.ADJ_NSUB);
		adjitem.setAdjustmentval(3);
		salelist.addAction(adjitem);
		return salelist;
	}
	

	/*
	 * Add objects, and adjustments
	 * then add another object and adjustment
	 */
	@Test
	public void testSaleItemObjectsIfValidAndApplyAdjustmentAndAddSaleAndAddAdjust() 
	{
		/*
		 * Initialisation
		 */
		SaleList salelist = new SaleList ();
		SaleItem saleitem;
		/*
		 * Add some objects
		 */
		salelist = InitialiseWithThreeObjects(salelist);
		salelist = AddOneAdjustments(salelist);
		salelist = AddSecondAdjustment(salelist);
		salelist = addAnotherSaleAndThenAnotherAdjustment(salelist);

		saleitem = salelist.getIndex(0);
		assertTrue ("TestSaleList apply adjustment unit 1 4 vs " + saleitem.getValue(),saleitem.getValue()==4);
		
		// *4
		saleitem = salelist.getIndex(1);
		assertTrue ("TestSaleList apply adjustment unit 2 20 vs " + saleitem.getValue(),saleitem.getValue()==20);
		
		// --
		saleitem = salelist.getIndex(2);
		assertTrue ("TestSaleList apply adjustment unit 3 9 vs " + saleitem.getValue(),saleitem.getValue()==9);

		// --
		saleitem = salelist.getIndex(3);
		assertTrue ("TestSaleList apply adjustment unit 3 6 vs " + saleitem.getValue(),saleitem.getValue()==6);

	}
	
	/*
	 * Add objects, and adjustments
	 * then add another object and adjustment
	 * Sortlist
	 */
	@Test
	public void testSaleItemObjectsIfValidAndApplyAdjustmentAndAddSaleAndAddAdjustAndSort() 
	{
		/*
		 * Initialisation
		 */
		SaleList salelist = new SaleList ();
		SaleItem saleitem;
		/*
		 * Add some objects
		 */
		salelist = InitialiseWithThreeObjects(salelist);
		salelist = AddOneAdjustments(salelist);
		salelist = AddSecondAdjustment(salelist);
		salelist = addAnotherSaleAndThenAnotherAdjustment(salelist);
		salelist.sortList();
		saleitem = salelist.getIndex(0);
		assertTrue ("TestSaleList apply adjustment unit 1 Apple vs " + ProductItemList.getProductName(saleitem.getProduct()),ProductItemList.getProductName(saleitem.getProduct()).equalsIgnoreCase("Apple"));
		
		// *4
		saleitem = salelist.getIndex(1);
		assertTrue ("TestSaleList apply adjustment unit 2 Apple vs " + ProductItemList.getProductName(saleitem.getProduct()),ProductItemList.getProductName(saleitem.getProduct()).equalsIgnoreCase("Apple"));
		
		// --
		saleitem = salelist.getIndex(2);
		assertTrue ("TestSaleList apply adjustment unit 3 Apple vs " + ProductItemList.getProductName(saleitem.getProduct()),ProductItemList.getProductName(saleitem.getProduct()).equalsIgnoreCase("Apple"));

		// --
		saleitem = salelist.getIndex(3);
		assertTrue ("TestSaleList apply adjustment unit 4 Orange vs " + ProductItemList.getProductName(saleitem.getProduct()),ProductItemList.getProductName(saleitem.getProduct()).equalsIgnoreCase("Oranges"));

	}
	
	/*
	 * Add objects, and adjustments
	 * then add another object and adjustment
	 * verify report summary and detailed match
	 */
	@Test
	public void testSaleItemObjectsIfValidAndApplyAdjustmentAndAddSaleAndAddAdjustTestReport() 
	{
		ProductItemList.Reset();
		SaleItem saleitem;
		SaleList salelist = new SaleList();
		AdjustmentItem adjitem;
		
		/*
		 * Initialise object
		 */
		salelist = InitialiseWithThreeObjects(salelist);
		
		salelist = AddOneAdjustments(salelist);
		salelist = AddSecondAdjustment(salelist);
		salelist = addAnotherSaleAndThenAnotherAdjustment(salelist);
		
		// no need to test substract too, already tested elsewhere
		salelist.sortList();
		TestIOutput io = new TestIOutput();
		salelist.outputSales(io);
		salelist.outputSalesDetail(io);
		
		IOutput io1 = new SalesConsoleOut();
		salelist.outputSales(io1);
		salelist.outputSalesDetail(io1);
		
		int n=0;
		String header = "TestSaleList testing report";
		assertTrue (header + " Sorted Apple " + io.getName(n), io.getName(n).equalsIgnoreCase("Apple") );
		assertTrue (header + " Sorted Apple qty " + io.getQty(n),io.getQty(n)==3);
		assertTrue (header + " Sorted Apple value" + io.getTotalvalue(n),io.getTotalvalue(n)==19);
		
		n++;	
		assertTrue (header + " Sorted Orange " + io.getName(n), io.getName(n).equalsIgnoreCase("Oranges") );
		assertTrue (header + " Sorted Oranges qty " + io.getQty(n),io.getQty(n)==1);
		assertTrue (header + " Sorted Oranges value" + io.getTotalvalue(n),io.getTotalvalue(n)==20);

		n++ ; // Apple unit
		
		n++;
		assertTrue (header + "Adjust Apple amount " + io.getAdjAmount(n), io.getAdjAmount(n)==5);
		assertTrue (header + "Adjust Apple type " + io.getAdjType(n), io.getAdjType(n).equals(ConstSet.ADJ_FULLNAME[ConstSet.ADJ_NADD]));
		
		n++;
		assertTrue (header + "Adjust Apple amount " + io.getAdjAmount(n), io.getAdjAmount(n)==3);
		assertTrue (header + "Adjust Apple type " + io.getAdjType(n), io.getAdjType(n).equals(ConstSet.ADJ_FULLNAME[ConstSet.ADJ_NSUB]));
		
		
		
		
		//salelist.outputSalesDetail(io2);
	}

	/*
	 * adding items using the product and amount
	 * No longer a key requirement, 
	 */
	@Test
	public void testAddTwoSaleItemsAndVerifyBoth() 
	{
		/*
		 * Initialise
		 */
		
		SaleList salelist = new SaleList ();
		
		


		/* 
		 * Add object 1
		 */
		int product = 1;
		int amount = 2;
		salelist.add (product,amount);
		
		
		/*
		 * Add object 2
		 */
		product=2;
		amount=4;
		salelist.add (product,amount);
		
		/*
		 * Verification
		 */
		
		/*
		 * Verify total items
		 */
		int salescount = salelist.getSalesCount ();
		assertTrue ("TestSaleList Number of saleitems 2 vs " + salescount, salescount == 2);
		
		/*
		 * Verify object 1
		 */
		SaleItem saleitem = salelist.getIndex (0);
		if (saleitem==null)
			fail("TestSaleList No items found in list");
		assertTrue ("TestSaleList item 0 saleitem.getProduct() = " + saleitem.getProduct() +
										"saleitem.getValue()   = " + saleitem.getValue() , 
										(saleitem.getProduct()==1) && (saleitem.getValue()==2));

		/*
		 * Verify object 2
		 */
		
		saleitem = salelist.getIndex (1);
		if (saleitem==null)
			fail("TestSaleList No items found in list");
		assertTrue ("TestSaleList item 0 saleitem.getProduct() = " + saleitem.getProduct() +
										"saleitem.getValue()   = " + saleitem.getValue() , 
										(saleitem.getProduct()==2) && (saleitem.getValue()==4));
		
		
	}

}
