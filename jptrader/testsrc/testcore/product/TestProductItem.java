package testcore.product;

import static org.junit.Assert.*;

import org.junit.Test;

import core.product.ProductItem;
import core.product.ProductItemList;

public class TestProductItem {

	@Test
		public void testAddTwoItemsToListMethod2() 
		{
			ProductItemList.Reset();
			ProductItem product = new ProductItem();
			
			product.setProductInfo("Apple");
			assertTrue ("testing ProductItem additem should = 1 vs " + product.getProduct() , product.getProduct()==1 );
			
			product.setProductInfo("Orange");
			assertTrue ("testing ProductItem checking second additem should = 2 vs " + product.getProduct() , product.getProduct()==2 );

			product.setProductInfo("APPle");
			assertTrue ("testing ProductItem checking second additem should = 1 vs " + product.getProduct() , product.getProduct()==1 );

			product.setProductInfo("pineAPPle");
			assertTrue ("testing ProductItem checking second additem should = 3 vs " + product.getProduct() , product.getProduct()==3 );
		}
	

}
