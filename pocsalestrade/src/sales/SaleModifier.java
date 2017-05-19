/**
 * 
 */
package sales;

import errors.ErrorRet;
import message.Transaction;

/**
 * This is the adjustment transaction
 * The data held is only the modifier type and the value
 * As this is only attached to the record that was modified
 * productid is not held here
 *
 */
public class SaleModifier
{
    int modifierType;
    double value;
    int error;
    
    public SaleModifier(int argmodifierType, double argvalue)
    {
        super();
        this.modifierType=0;
        this.error=0;
        this.value=0;
        
        this.error=setModifierType(argmodifierType);
        if (this.error==ErrorRet.OK)
            this.error=setValue(argvalue);
        
    }

    /**
     * @return the modifierType
     */
    public int getModifierType()
    {
        return modifierType;
    }

    /**
     * @param modifierType the modifierType to set
     */
    public int setModifierType(int modifierType)
    {
        int retval = ErrorRet.OK;
        if ((modifierType >= Transaction.adjMin) && (modifierType <= Transaction.adjMax)) 
        {
            this.modifierType = modifierType;
            retval = ErrorRet.OK;
        }
        else retval = ErrorRet.TransAdjTypeOutOfRange;
        
        
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
     * @param value the value to set
     */
    public int setValue(double value)
    {
        
        
        int retval = ErrorRet.OK;
        if ((value >= Transaction.valueMin) && (value <= Transaction.valueMax)) 
        {
            this.value = value;
            retval = ErrorRet.OK;
        }
        else retval = ErrorRet.TransValueOutOfRange;
        
        return retval;
    }
    public String getModifierTypeName()
    {
        String name="";
        switch (getModifierType())
        {
            case Transaction.adjadd : name=Transaction.adjaddName; break;
            case Transaction.adjsub : name=Transaction.adjsubName; break;
            case Transaction.adjmult: name=Transaction.adjmultName; break;
        
        }
        return name;
    }

    
    
}
