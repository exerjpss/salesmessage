package producttest;

import static org.junit.Assert.*;

import org.junit.Test;

import product.ProductList;

public class ProductListTest
{

    @Test
    public void test()
    {
     
       if (ProductList.disableCheck==false)
       {
       assertFalse(ProductList.isValidId(12));   // out of range
       assertFalse(ProductList.isValidId(-2));   // out of range
       assertFalse(ProductList.isValidId(9299212));   // out of range
       }
       else
       {
           assertTrue(ProductList.isValidId(12));   // out of range
           assertTrue(ProductList.isValidId(-2));   // out of range
           assertTrue(ProductList.isValidId(9299212));   // out of range
           
       }
       assertTrue(ProductList.isValidId(2));   // in range
       
    }

}
