/**
 * 
 */
package message;

import errors.ErrorRet;

/**
 * 
 * Class to hold parent for different types of transactions
 * and the constants for the class and limits 
 * Allows processing of transactions to be more generic
 *
 */
public class Transaction
{
    public static final int sale = 0;
    public static final int adjustment = 1;
    public static final int salesingle = 2;
    
    public static final int productIndex = 1;
    public static final int valueIndex = 2;
    public static final int salesvalueIndex = 2;
    public static final int qtyIndex = 3;
    public static final int adjustmenttypeIndex = 3;
    public static final int adjustmentvalueIndex = 4;
    
    
    public static final double valueMin = 0;            // negative would be buy, so assume 0 as minimum
    public static final double valueMax = 1000000000;   // arbritarily chosen
    public static final int    qtyMin  = 1;
    public static final int    qtyMax  = 1000  ;         // arbritraily limit chosen
    
    
    public static final int    adjMin  = 1  ;
    public static final int    adjadd  = 1  ;
    public static final int    adjsub  = 2  ;
    public static final int    adjmult  =3  ;
    public static final int    adjdiv  =4  ;
    public static final int    adjdisc  =5  ;
    public static final int    adjMax  = 5;
    
    public static final String    adjmultName  = "Multiply"  ;
    public static final String    adjaddName  = "Add"  ;
    public static final String    adjsubName  = "Subtract"  ;
    
    
    public final static int params = 0;
    
    private int error=ErrorRet.TransGeneric;
    private int index=0;
    /**
     * @return the error
     */
    public int getError()
    {
        return error;
    }

    /**
     * @param errorstate the error to set
     */
    
    public void setError(int errorstate)
    {
        this.error = errorstate;
    }
    /*
     * This is a stub only, overridden by child classes
     */
    public int getParams()
    {
        return params;
    }

    /*
     * This is a stub only, overridden by child classes
     */
    public Transaction createTransaction (Message msg)
    {
        Transaction trans = null;
        
        return trans;
                
    }

    /*
     * Add a record index to identify the records
     */
    public void setIndex(int recordCount)
    {
        index = recordCount;
        
    }
    public int getIndex()
    {
        return index;
        
    }
}
