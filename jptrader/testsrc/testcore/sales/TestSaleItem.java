package testcore.sales;

import static org.junit.Assert.*;

import org.junit.Test;

import core.adjustment.AdjustmentItem;
import core.constants.ConstSet;
import core.product.ProductItemList;
import core.sales.SaleItem;

public class TestSaleItem {

	@Test
	public void testInitialSaleLoad() {
		
		SaleItem saleitem = new SaleItem ();
		int err = saleitem.initSale(4,9);
		assertTrue("testSaleItem initialise test",err==0);
		assertTrue("testSaleItem check initialise values product 4 vs =" + saleitem.getProduct(), saleitem.getProduct()==4);
		assertTrue("testSaleItem check initialise values amount 9 vs =" + saleitem.getValue(), saleitem.getValue()==9);
		assertTrue("testSaleItem check initialise values qty 1 vs =" + saleitem.getQty(), saleitem.getQty()==1);
		
		saleitem = new SaleItem ();
		err = saleitem.initSale(4,9,3);
		assertTrue("testSaleItem initialise test",err==0);
		assertTrue("testSaleItem check initialise values product 4 vs =" + saleitem.getProduct(), saleitem.getProduct()==4);
		assertTrue("testSaleItem check initialise values amount 9 vs =" + saleitem.getValue(), saleitem.getValue()==9);
		assertTrue("testSaleItem check initialise values qty 3 vs =" + saleitem.getQty(), saleitem.getQty()==3);

	}
	
	
	
	@Test
	public void testAddParameterAndWhenFilled() 
	{
		ProductItemList.Reset();
		SaleItem saleitem = new SaleItem ();
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.1 = " + saleitem.isValid(), saleitem.isValid()==false);
		
		saleitem.addParameter(ConstSet.PARAM_PRODUCT,"Apple" );
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.2 = " + saleitem.isValid(), saleitem.isValid()==false);
		
		saleitem.addParameter(ConstSet.PARAM_AMOUNT,"20" );
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.3 = " + saleitem.isValid(), saleitem.isValid()==true);
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.6 = " + saleitem.getQty(), saleitem.getQty()==1);
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.4 = " + saleitem.getProduct(), saleitem.getProduct()==1);
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.5 = " + saleitem.getValue(), saleitem.getValue()==20);
		
		
	}
	
	@Test
	public void testAddParameterAndWhenFilledWithErrors() 
	{
		SaleItem saleitem = new SaleItem ();
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.1 = " + saleitem.isValid(), saleitem.isValid()==false);
		
		saleitem.addParameter(ConstSet.PARAM_PRODUCT,"Apple" );
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.2 = " + saleitem.isValid(), saleitem.isValid()==false);
		
		saleitem.addParameter(ConstSet.PARAM_AMOUNT,"20OP" );
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.3 = " + saleitem.isValid(), saleitem.isValid()==false);
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.6 = " + saleitem.getQty(), saleitem.getQty()==1);
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.4 = " + saleitem.getProduct(), saleitem.getProduct()==1);
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.5 = " + saleitem.getValue(), saleitem.getValue()==0);

		saleitem.addParameter(ConstSet.PARAM_QTY,"89F" );
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.3 = " + saleitem.isValid(), saleitem.isValid()==false);
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.6 = " + saleitem.getQty(), saleitem.getQty()==1);
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.4 = " + saleitem.getProduct(), saleitem.getProduct()==1);
		assertTrue ("testsaleitem - testAddParameterAndWhenFilled.5 = " + saleitem.getValue(), saleitem.getValue()==0);

		
	}
	
	/*
	 * Create a saleitem, then an adjust item
	 * ask the saleitem to apply the adjust item
	 * check it was modified
	 */
	@Test
	public void testApplyAdjustmentOnSaleitem() 
	{
		SaleItem saleitem = new SaleItem ();
		saleitem.addParameter(ConstSet.PARAM_PRODUCT,"Apple" );
		saleitem.addParameter(ConstSet.PARAM_AMOUNT,"20" );
		saleitem.addParameter(ConstSet.PARAM_QTY,"8" );
		
		AdjustmentItem adjitem = new AdjustmentItem();
		adjitem.addParameter(ConstSet.PARAM_PRODUCT,"Apple");
		adjitem.addParameter(ConstSet.PARAM_AMOUNT,"20");
		adjitem.addParameter(ConstSet.PARAM_ADJUSTMENT,ConstSet.ADJ_ADD);
		
		saleitem.actionAdjustment(adjitem);
		
		assertTrue ("TestSaleItem testApplyAdustmentOnSaleItem 40 vs " + saleitem.getValue(),saleitem.getValue()==40);
		
		adjitem.addParameter(ConstSet.PARAM_AMOUNT,"5");
		adjitem.addParameter(ConstSet.PARAM_ADJUSTMENT,ConstSet.ADJ_SUB);
		saleitem.actionAdjustment(adjitem);
		assertTrue ("TestSaleItem testApplyAdustmentOnSaleItem 35 vs " + saleitem.getValue(),saleitem.getValue()==35);
		
		adjitem.addParameter(ConstSet.PARAM_AMOUNT,"3");
		adjitem.addParameter(ConstSet.PARAM_ADJUSTMENT,ConstSet.ADJ_MUL);
		saleitem.actionAdjustment(adjitem);
		assertTrue ("TestSaleItem testApplyAdustmentOnSaleItem 105 vs " + saleitem.getValue(),saleitem.getValue()==105);
		
		/*
		 * Not checking extreme values as no limits in places
		 */
		
		assertTrue ("TestSaleItem testApplyAdustmentOnSaleItem number of actions " + saleitem.getActions().size(),saleitem.getActions().size()==3); 
		
		
	}

}
