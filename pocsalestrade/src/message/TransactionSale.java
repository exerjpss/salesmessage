/**
 * 
 */
package message;

import java.util.LinkedList;

import product.ProductList;
import errors.ErrorRet;

/**
 *  
 * This class holds the sale data for a transaction
 * this is expecting the product id, the value and the qty
 * It is also a parent class for a single sale and adjustment message
 *
 */
public class TransactionSale extends Transaction
{

        
    
    public final static int params = 4;             // expected parameters
    protected int productType;                      // product type is a set of products
    protected int qty;                              // how many were sold in this transaction
    protected double value;                         // what was the value of the transaction per item
    protected Transaction action;                   // This is for historical records. When a historical record is created
                                                    // due to the adjustment record, this is a pointer to the adjustment record
                                                    // as applied to this record
    protected LinkedList <TransactionSale> history; // This is kept as a list of the copies of previous records before an adjustment is made
    
    // NB History will be with the main sale, with old objects copied onto the list
    //    Action will be attached with old objects, explaining what was requested
    
    /**
     * @return the action
     */
    public Transaction getAction()
    {
        return action;
    }
    /**
     * @return the history
     */
    public LinkedList<TransactionSale> getHistory()
    {
        return history;
    }
    public void addAction (Transaction transaction)
    {
        action = transaction;
    }

    /*
     * Create a copy of this object only copying over the sale information.
     */
    public TransactionSale createCopy ()
    {
        TransactionSale trans = new TransactionSale();
        
        trans.setProductType(this.getProductType());
        trans.setQty(this.getQty());
        trans.setValue(this.getValue());
        trans.history=null;
        
        return trans;
    }
    
    /*
     * When a change is made, then initialise the history list and add the original onto the end of list.
     */
    public int addHistory (TransactionSale trans)
    {
        if (history==null)
        {
            history = new LinkedList<TransactionSale>();
        }
        if (history!=null)
        {
            history.add (trans);
        }
        return ErrorRet.OK;
    }
    
    /*
     * Initialise the key fields. History and action are not used at the moment.
     */
    public TransactionSale()
    {
        super();
        history=null;
        action=null;
        // TODO Auto-generated constructor stub
    }
    
    
    /**
     * @return the params
     */
    public int getParams()
    {
        return params;
    }
    
    /*
     * With a message object, create a sale transaction with the required
     * checks and information
     * 
     * Any errors are returned as part of the transaction object
     */
    public TransactionSale createTransaction (Message msg)
    {
        int retval = ErrorRet.OK;
        TransactionSale trans = new TransactionSale();
        if (msg.getParams() != this.getParams())
            retval = ErrorRet.TransInvalidParams;
        
        if (retval == ErrorRet.OK)
        {
            retval = trans.setProductType(msg.getProductid());
        }
        if (retval == ErrorRet.OK)
        {
            retval = trans.setValue(msg.getSalevalue());
        }
        
        
            if (retval == ErrorRet.OK)
            {
                retval = trans.setQty(msg.getSalesqty());
            }
        
        trans.setError(retval);
        return trans;
                
    }
    
    /*
     * Check and Set the sale value (per item)
     * limits have been arbitrarily chosen
     */
    protected int setValue(String data)
    {
        int retval = ErrorRet.TransGeneric;
        try 
        {
            double temp1 = Double.parseDouble(data);
            
            if ((temp1 >= valueMin) && (temp1 <= valueMax)) 
            {
                setValue(temp1);
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
     * Check and set the qty sold
     * where a qty has not been passed, it will assumed to be 1
     */
    protected int setQty(String data)
    {
        int retval = ErrorRet.TransGeneric;
        if (data.equals("")) 
        {
           setQty(1);
           retval = ErrorRet.OK;
        }
        else
        try {
            int temp1 = Integer.parseInt(data);
            if ((temp1 >= qtyMin) && (temp1 <= qtyMax)) 
            {
                setQty(temp1);
                retval = ErrorRet.OK;
            }
            else retval = ErrorRet.TransQtyOutOfRange;
        } catch (Exception e)

        {
            retval = ErrorRet.TransInvalidParams;
        }
        return retval;
    }
    /* 
     * Take a string, convert it to an integer
     * check the integer is a valid product type
     * add if so,
     * return error if not
     * 
     */
    protected int setProductType(String data)
    {
        int retval = ErrorRet.TransGeneric;
        try {
            int temp1 = Integer.parseInt(data);
            if (ProductList.isValidId(temp1))
            {
                setProductType(temp1);
                retval = ErrorRet.OK;
            }
        } catch (Exception e)

        {
            retval = ErrorRet.TransInvalidParams;
        }
        return retval;
    }
    /**
     * @return the productType
     */
    public int getProductType()
    {
        return productType;
    }
    /**
     * @param productType the productType to set
     */
    public void setProductType(int productType)
    {
        this.productType = productType;
    }
    /**
     * @return the qty
     */
    public int getQty()
    {
        return qty;
    }
    /**
     * @param qty the qty to set
     */
    public void setQty(int qty)
    {
        this.qty = qty;
    }
    /**
     * @return the value
     */
    public double getValue()
    {
        return value;
    }
    /**
     * @param value the value to set
     */
    private void setValue(double value)
    {
        this.value = value;
    }
    
    /*
     * The following procedures apply an adjustment request to the current value
     * If the result is within the limits it is updated, else an error is returned
     * and the value is not updated
     */
    
    /*
     * add an amount to the sale amount
     */
    public int add(double value2)
    {
        double temp;
        int retval = ErrorRet.OK;
        temp = value + value2;
        if ((temp >= Transaction.valueMin ) && (temp <= Transaction.valueMax ))
          value = temp;
        else retval = ErrorRet.TransValueOutOfRange;
        return retval;
    }
    /*
     * subtract an amount from the sale amount
     */
    public int sub(double value2)
    {
        double temp;
        int retval = ErrorRet.OK;
        temp = value - value2;
        if ((temp >= Transaction.valueMin ) && (temp <= Transaction.valueMax ))
          value = temp;
        else retval = ErrorRet.TransValueOutOfRange;
        return retval;
    }
    /*
     * Multiply the value with the sale value
     */
    public int mult(double value2)
    {
        double temp;
        int retval = ErrorRet.OK;
        temp = value2 * value;
        if ((temp >= Transaction.valueMin ) && (temp <= Transaction.valueMax ))
          value = temp;
        else retval = ErrorRet.TransValueOutOfRange;
        return retval;

    }
    
    /*
     * Select the adjustment to be made
     * Parameters passed are the adjustmentid, the value and the adjustment transaction
     * the adjustment transaction is passed to add to the history
     */
    public int adjustmentAction(int adjustmenttype, double value2, TransactionSale transaction)
    {
        int retval=ErrorRet.TransGeneric;
        TransactionSale old = this.createCopy ();
        switch (adjustmenttype)
        {
            case Transaction.adjadd:
                retval = this.add(value2);
                break;
            case Transaction.adjsub:
                retval = this.sub(value2);
                break;
            case Transaction.adjmult:
                retval = this.mult(value2);
                break;
            default:
                // currently no other type of valid operation
                retval = ErrorRet.TransAdjTypeOutOfRange;
        }
        
        // If okay add the adjustment record to the old sale record, and add to the history of 
        // the current record
        if (retval==ErrorRet.OK)
            if (old!=null)
            {
                old.addAction (transaction);
                addHistory(old);
            }
            
        
        return retval;
    }
}
