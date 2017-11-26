package testcore.product;

import static org.junit.Assert.*;

import org.junit.Test;

import core.product.ProductItemList;

public class TestProductItemList {

	@Test
	public void testAddSingleItemToList() 
	{
		ProductItemList productitemlist = ProductItemList.getInstance();
		int retval = productitemlist.getOrAdd ("Apple");
		assertTrue ("testing ProductItemList additem should = 1 vs " + retval , retval==1 );
		
		/* NB should be case insensitive so try that

		 */
		retval = productitemlist.getOrAdd ("ApPle");
		assertTrue ("testing ProductItemList checking second time additem should = 1 vs " + retval , retval==1 );
	}
	@Test
	public void testAddTwoItemsToList() 
	{
		ProductItemList productitemlist = ProductItemList.getInstance();
		int retval = productitemlist.getOrAdd ("Apple");
		assertTrue ("testing ProductItemList additem should = 1 vs " + retval , retval==1 );
		
		retval = productitemlist.getOrAdd ("Orange");
		assertTrue ("testing ProductItemList checking second additem should = 2 vs " + retval , retval==2 );
	}
	
	//use the static method for same test
	@Test
	public void testAddTwoItemsToListMethod2() 
	{
		int retval = ProductItemList.getProductKey("Apple");
		assertTrue ("testing ProductItemList additem should = 1 vs " + retval , retval==1 );
		
		retval = ProductItemList.getProductKey("Orange");
		assertTrue ("testing ProductItemList checking second additem should = 2 vs " + retval , retval==2 );
	}

	//use the static method for getting name
	@Test
	public void testAddTwoItemsToListRetrieveNameUsingValue() 
	{
		int retval = ProductItemList.getProductKey("Apple");
		assertTrue ("testing ProductItemList additem should = Apple vs " + ProductItemList.getProductName(retval) , ProductItemList.getProductName(retval).equalsIgnoreCase("APPLE"));
		
		retval = ProductItemList.getProductKey("Orange");
		assertTrue ("testing ProductItemList checking second Orange " +  ProductItemList.getProductName(retval) , ProductItemList.getProductName(retval).equalsIgnoreCase("ORANGE") );
		
		assertTrue ("testing ProductItemList checking second Orange " +  ProductItemList.getProductName(7) , ProductItemList.getProductName(7).equalsIgnoreCase("") );
	}
}
