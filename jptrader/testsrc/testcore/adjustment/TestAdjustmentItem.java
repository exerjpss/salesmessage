package testcore.adjustment;

import static org.junit.Assert.*;

import org.junit.Test;

import core.adjustment.AdjustmentItem;
import core.constants.ConstSet;
import core.product.ProductItemList;

public class TestAdjustmentItem 
{

	/*
	 * Add adjustment value via parameter
	 */
	
	@Test
	public void testAddParameterAdjustmentValue () 
	{
		AdjustmentItem adjitem = new AdjustmentItem();
		assertTrue ("TestAdjustmentItem value test valid=false",adjitem.isValid()==false);
		adjitem.addParameter(ConstSet.PARAM_AMOUNT,"20");
		assertTrue ("TestAdjustmentItem value test valid=false",adjitem.isValid()==false);
		assertTrue ("TestAdjustmentItem value test value=20 vs " + adjitem.getAdjustmentval(), adjitem.getAdjustmentval()==20);
	}

	/*
	 * Add product value via parameter
	 */
	@Test
	public void testAddParameterAdjustmentProduct () 
	{
		ProductItemList.Reset();
		AdjustmentItem adjitem = new AdjustmentItem();
		assertTrue ("TestAdjustmentItem product test valid=false",adjitem.isValid()==false);
		adjitem.addParameter(ConstSet.PARAM_PRODUCT,"Apple");
		assertTrue ("TestAdjustmentItem product test valid=false",adjitem.isValid()==false);
		assertTrue ("TestAdjustmentItem product test value=1 vs " + adjitem.getProduct(), adjitem.getProduct()==1);

		adjitem.addParameter(ConstSet.PARAM_PRODUCT,"PineApple");
		assertTrue ("TestAdjustmentItem product test valid=false",adjitem.isValid()==false);
		assertTrue ("TestAdjustmentItem product test value=2 vs " + adjitem.getProduct(), adjitem.getProduct()==2);

		adjitem.addParameter(ConstSet.PARAM_PRODUCT,"Apple");
		assertTrue ("TestAdjustmentItem product test valid=false",adjitem.isValid()==false);
		assertTrue ("TestAdjustmentItem product test value=1 vs " + adjitem.getProduct(), adjitem.getProduct()==1);

	}

	/*
	 * Add different type value via parameter
	 */
	@Test
	public void testAddParameterAdjustmentType () 
	{
		AdjustmentItem adjitem = new AdjustmentItem();
		assertTrue ("TestAdjustmentItem type test valid=false",adjitem.isValid()==false);
		adjitem.addParameter(ConstSet.PARAM_ADJUSTMENT,ConstSet.ADJ_ADD);
		assertTrue ("TestAdjustmentItem type test valid=false",adjitem.isValid()==false);
		assertTrue ("TestAdjustmentItem type test type = " + adjitem.getAdjustmenttype(), adjitem.getAdjustmenttype()==ConstSet.ADJ_NADD);
		
		adjitem.addParameter(ConstSet.PARAM_ADJUSTMENT.toLowerCase(),ConstSet.ADJ_SUB);
		assertTrue ("TestAdjustmentItem type test valid=false",adjitem.isValid()==false);
		assertTrue ("TestAdjustmentItem type test type = " + adjitem.getAdjustmenttype(), adjitem.getAdjustmenttype()==ConstSet.ADJ_NSUB);

		adjitem.addParameter(ConstSet.PARAM_ADJUSTMENT,ConstSet.ADJ_MUL);
		assertTrue ("TestAdjustmentItem type test valid=false",adjitem.isValid()==false);
		assertTrue ("TestAdjustmentItem type test type = " + adjitem.getAdjustmenttype(), adjitem.getAdjustmenttype()==ConstSet.ADJ_NMUL);

	}

	/*
	 * Add all value via parameters and check it is valid
	 */
	
	@Test
	public void testAddParameterAdjustmentAllParams () 
	{
		AdjustmentItem adjitem = new AdjustmentItem();
		assertTrue ("TestAdjustmentItem type test valid=false",adjitem.isValid()==false);
		adjitem.addParameter(ConstSet.PARAM_ADJUSTMENT,ConstSet.ADJ_ADD);
		assertTrue ("TestAdjustmentItem type test valid=false",adjitem.isValid()==false);
		assertTrue ("TestAdjustmentItem type test type = " + adjitem.getAdjustmenttype(), adjitem.getAdjustmenttype()==ConstSet.ADJ_NADD);
		
		adjitem.addParameter(ConstSet.PARAM_PRODUCT,"Apple");
		assertTrue ("TestAdjustmentItem product test valid=false",adjitem.isValid()==false);
		assertTrue ("TestAdjustmentItem product test value=1 vs " + adjitem.getProduct(), adjitem.getProduct()==1);

		adjitem.addParameter(ConstSet.PARAM_AMOUNT,"20");
		assertTrue ("TestAdjustmentItem value test valid=true",adjitem.isValid()==true);
		assertTrue ("TestAdjustmentItem value test value=20 vs " + adjitem.getAdjustmentval(), adjitem.getAdjustmentval()==20);

	}

}
