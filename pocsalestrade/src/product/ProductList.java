/**
 * 
 */
package product;

/**
 * 
 * It would be expected that the sending and receiving would have the same product list
 * so while this is "fixed" here, it is expandable to any amount as long as each party 
 * assumes the same meanings.
 *   
 * Assumption made : Both sides agree the meanings of product type
 * Assumption made : This would be a list or a database so both sides can use the same definitions
 *                   It could rely on text only, but this would be open to changes in text changing the
 *                   results. IDs are coded rather than generated to maintain consistency across time.
 *
 * Without a limit, we could be processing multiple variations and invalid products
 */
public class ProductList
{
      public static boolean disableCheck = true;
      public static Product [] products = new Product [] 
                  { 
                      new Product(1, "Apple"),
                      new Product(2, "Banana"),
                      new Product(3, "Orange"),
                      new Product(4, "Pear"),
                      new Product(5, "Melon"),
                      new Product(6, "Star Fruit"),
                      new Product(7, "White Grapes"),
                      new Product(8, "Red Grapes"),
                      new Product(9, "Lettuce")
                     
                      
                  };
      
    public static boolean isValidId(int id)
    {
        boolean retval = false;

        // Items could be deleted so have to check individual by ID
        
        if (ProductList.disableCheck)
            retval = true;
        else
            for (int i = 0; i < products.length; i++)
                if (products[i].getProducttype() == id)
                {
                    return true;
                }

        return retval;
    }
}
