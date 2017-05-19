/**
 * 
 */
package message;


import errors.ErrorRet;

/**
 * 
 * Adjustment Transaction
 * Based on TransactionSingleSale
 * Additional parameters are the adjustment value and the adjustment type
 * 
 *
 */
public class TransactionAdj extends TransactionSaleSingle
{
    public final static int params = 5;
    
    
    protected double adjvalue;
    protected int adjustmenttype;
    
    public int getParams()
    {
        return params;
    }
    
    
    /*
     * Creates a clone with the basic information only
     * the parent class copies over the other basic data required
     * 
     */
    public TransactionAdj createCopy ()
    {
        TransactionAdj trans = new TransactionAdj();
        super.createCopy();
        
        
        trans.setAdjvalue(this.getAdjvalue());
        trans.setAdjustmenttype(this.getAdjustmenttype());
               
        return trans;
    }
    
    /*
     * Create a transactionAdj from the message parameters
     * Validate the parameters
     * If an error, return the error as part of the transaction object
     * the calling procedure should check the error flag
     * 
     */
    public TransactionAdj createTransaction (Message msg)
    {
        
        int retval = ErrorRet.OK;
        TransactionAdj trans = new TransactionAdj ();
        
        //Confirm the expected number of parameters are present 
         
        if (msg.getParams() != this.getParams())
            retval = ErrorRet.TransInvalidParams;
        
        // Set the key data now, if there is an error that is
        // returned to the caller
        

        if (retval == ErrorRet.OK)
        {
            retval = trans.setProductType(msg.getProductid());
        }
        if (retval == ErrorRet.OK)
        {
            retval = trans.setValue(msg.getSalevalue());
        }

        // NB setQty is passed a "" will fill with a 1
        if (retval == ErrorRet.OK)
        {
           retval = trans.setQty(msg.getSalesqty());
        }
        if (retval == ErrorRet.OK)
        {
            retval = trans.setAdjustmentValue(msg.getAdjustmentvalue());
        }
        if (retval == ErrorRet.OK)
        {
            retval = trans.setAdjustmentType(msg.getAdjustmentid());
        }
        trans.setError(retval);
        return trans;
                
    }
    
    
    /**
     * @return the adjvalue
     */
    public double getAdjvalue()
    {
        return adjvalue;
    }


    /**
     * @param adjvalue the adjvalue to set
     */
    private void setAdjvalue(double adjvalue)
    {
        this.adjvalue = adjvalue;
    }

    /*
     * Main way to set the adjustment value. Send in a string and
     * check if double, check if in range and set or return an
     * error
     */
    protected int setAdjustmentValue(String data)
    {
        int retval = ErrorRet.TransGeneric;
        try 
        {
            double temp1 = Double.parseDouble(data);
            
            if ((temp1 >= valueMin) && (temp1 <= valueMax)) 
            {
                setAdjvalue(temp1);
                retval = ErrorRet.OK;
            }
            else retval = ErrorRet.TransValueOutOfRange;
        } catch (Exception e)

        {
            retval = ErrorRet.TransInvalidParams;
        }
        return retval;
    }
    
    /*
     * Main way to set the adjustment type. Send in a string and
     * check if double, check if in range and set or return an
     * error
     */
    private int setAdjustmentType(String data)
    {
        int retval = ErrorRet.TransGeneric;
        try {
            int temp1 = Integer.parseInt(data);
            if ((temp1 >= adjMin) && (temp1 <= adjMax)) 
            {
                setAdjustmenttype(temp1);
                retval = ErrorRet.OK;
            }
            else retval = ErrorRet.TransAdjTypeOutOfRange;
        } catch (Exception e)

        {
            retval = ErrorRet.TransInvalidParams;
        }
        return retval;
    }
    
    /**
     * @return the adjustmenttype
     */
    public int getAdjustmenttype()
    {
        return adjustmenttype;
    }
    /**
     * @param adjustmenttype the adjustmenttype to set
     */
    private void setAdjustmenttype(int adjustmenttype)
    {
        this.adjustmenttype = adjustmenttype;
    }

    /*
     * This is for reports for this example
     * the transaction adjustment is mapped to the appropriate String
     */
    public String getAdjustmenttypeName()
    {
        String name="";
        switch (getAdjustmenttype())
        {
            case Transaction.adjadd : name=Transaction.adjaddName; break;
            case Transaction.adjsub : name=Transaction.adjsubName; break;
            case Transaction.adjmult: name=Transaction.adjmultName; break;
        
        }
        return name;
    }
}
