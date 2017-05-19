package sales;

import java.util.LinkedList;

import product.ProductList;

import errors.ErrorRet;
import message.Transaction;

/**
 * So this is simply a productid and a sale value
 * in addition there will be
 * and history record and a transaction tracker with the history record
 * 
 */
public class SaleRecord
{
    int    productid;
    double value;
    int    index;
    int    error;
    
    public SaleRecord(int productid, double value)
    {
        super();
        error=setProductid(productid);
        if (error==ErrorRet.OK) setValue(value);
    }

    public SaleRecord(int productid, double value, int index)
    {
        super();
        this.index = index;
        error=setProductid(productid);
        if (error==ErrorRet.OK) setValue(value);
    }
    /**
     * @return the index
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * @param index
     *            the index to set
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    protected SaleModifier           action;  // This is for historical records. When a historical record is created
                                              // due to the adjustment record, this is a pointer to the adjustment record
                                              // as applied to this record
    protected LinkedList<SaleRecord> history; // This is kept as a list of the copies of previous records before an adjustment is made
                                              // NB History will be with the main sale, with old objects copied onto the list
                                              // Action will be attached with old objects, explaining what was requested

    public SaleRecord()
    {
        super();
        // TODO Auto-generated constructor stub
        action = null;
        history = null;
        value=0;
        productid=0;
        index = 0;
        error = 0;
    }

    /**
     * @return the action
     */
    public SaleModifier getAction()
    {
        return action;
    }

    /**
     * @return the history
     */
    public LinkedList<SaleRecord> getHistory()
    {
        return history;
    }

    public void addAction(SaleModifier transaction)
    {
        action = transaction;
    }

    /*
     * Create a copy of this object only copying over the sale information.
     */
    public SaleRecord createCopy()
    {
        SaleRecord trans = new SaleRecord();
        trans.setProductid(this.getProductid());
        trans.setValue(this.getValue());
        trans.history = null;

        return trans;
    }

    /*
     * When a change is made, then initialise the history list and add the original onto the end of list.
     */
    public int addHistory(SaleRecord trans)
    {
        if (history == null)
        {
            history = new LinkedList<SaleRecord>();
        }
        if (history != null)
        {
            history.add(trans);
        }
        return ErrorRet.OK;
    }

    /**
     * @return the productid
     */
    public int getProductid()
    {
        return productid;
    }

    /**
     * @param productid
     *        the productid to set
     *        limit is checked
     */
    public int setProductid(int productid)
    {
        int retval = ErrorRet.TransGeneric;
        
        this.productid = productid;
        if (ProductList.isValidId(productid))
        {
            this.productid = productid;
            retval = ErrorRet.OK;
        } 
        else 
            retval = ErrorRet.TransValueOutOfRange;
        
        return retval;
    }

    /**
     * @return the value
     */
    public double getValue()
    {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public int setValue(double value2)
    {
        int retval=ErrorRet.OK;
        if ((value2 >= Transaction.valueMin) && (value2 <= Transaction.valueMax))
            this.value = value2;
        else
            retval = ErrorRet.TransValueOutOfRange;
        return retval;
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
        retval = setValue(temp);
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
        retval = this.setValue(temp);
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
        retval = this.setValue(temp);
        return retval;

    }

    /*
     * Select the adjustment to be made
     * Parameters passed are the adjustmentid, the value and the adjustment transaction
     * the adjustment transaction is passed to add to the history
     */
    public int adjustmentAction(SaleModifier trans)
    {
        int retval = ErrorRet.TransGeneric;
        SaleRecord old = this.createCopy();
        switch (trans.getModifierType())
        {
            case Transaction.adjadd:
                retval = this.add(trans.getValue());
                break;
            case Transaction.adjsub:
                retval = this.sub(trans.getValue());
                break;
            case Transaction.adjmult:
                retval = this.mult(trans.getValue());
                break;
            default:
                // currently no other type of valid operation
                retval = ErrorRet.TransAdjTypeOutOfRange;
        }

        // If okay add the adjustment record to the old sale record, and add to the history of
        // the current record
        if (retval == ErrorRet.OK) if (old != null)
        {

            old.addAction(trans);
            addHistory(old);
        }

        return retval;
    }

}
