package message;

/**
 * 
 * This class is specifically for accepting single sale data, where no qty was specified. It calls the 
 * parent class but sets the parameters to check for to 3
 * 
 */


public class TransactionSaleSingle extends TransactionSale
{
    public final static int params = 3;
    
    /**
     * @return the params
     */
    public int getParams()
    {
        return params;
    }
 
}
