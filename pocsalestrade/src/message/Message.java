/**
 * 
 */
package message;


import errors.ErrorRet;

/**
 *
 *         Take a string and break it into its component values
 *         Assuming key=value split by HASH # 
 *         This was changed to remove the mistake of assuming values would 
 *         be in their correct position
 *         and should allow more flexible message processing
 *         
 *         Key properties are stored as strings. These will be processed by the appropriate class
 */
public class Message
{
    private static final int maxlength = 1024;   // random limit for preventing data overload
    private String           messageid;
    private String           productid;
    private String           salesqty;
    private String           salevalue;
    private String           adjustmentid;
    private String           adjustmentvalue;
    private int              params;
    private int              retval;

    public Message(String str)
    {
        super();
        messageid = "";
        productid = "";
        salesqty = "";
        salevalue = "";
        adjustmentid = "";
        adjustmentvalue = "";
        params = 0;
        retval = ErrorRet.OK;

        if (str.length() <= maxlength)         // limit the string to value defaulted above
        {
            String[] data = str.split("#");    // try splitting the string
            for (int i = 0; i < data.length; i++)
            {
                String substr = data[i];
                String[] keyval = substr.trim().split("=");
                if (keyval.length == 2)
                {
                    switch (keyval[0].trim().toUpperCase())
                    {
                        case "MESSAGEID":
                            messageid = keyval[1].trim();
                            if (!messageid.equals("")) params++;
                            break;
                        case "PRODUCTID":
                            productid = keyval[1].trim();
                            if (!productid.equals("")) params++;
                            break;
                        case "SALEQTY":
                            salesqty = keyval[1].trim();
                            if (!salesqty.equals("")) params++;
                            break;
                        case "SALEVALUE":
                            salevalue = keyval[1].trim();
                            if (!salevalue.equals("")) params++;
                            break;
                        case "ADJUSTID":
                            adjustmentid = keyval[1].trim();
                            if (!adjustmentid.equals("")) params++;
                            break;
                        case "ADJUSTVALUE":
                            adjustmentvalue = keyval[1].trim();
                            if (!adjustmentvalue.equals("")) params++;
                            break;
                        default:
                            break;

                    }
                } else
                {
                }
            }
        } else retval=ErrorRet.TransDataTooLong;
        //LocalLog.debug("error in construction adjs = " + retval);

    }

    /**
     * @return the retval
     */
    public int getRetval()
    {
        return retval;
    }

    /**
     * @param retval the retval to set
     */
    public void setRetval(int retval)
    {
        this.retval = retval;
    }

    public boolean hasMessageid()
    {
        if (messageid.equals(""))
            return false;
        else
            return true;
    }

    /**
     * @param params
     *            the params to set
     */
    public void setParams(int params)
    {
        this.params = params;
    }

    /**
     * @return the messageid
     */
    public String getMessageid()
    {
        return messageid;
    }

    /**
     * @param messageid
     *            the messageid to set
     */
    public void setMessageid(String messageid)
    {
        this.messageid = messageid;
    }

    /**
     * @return the productid
     */
    public String getProductid()
    {
        return productid;
    }

    /**
     * @param productid
     *            the productid to set
     */
    public void setProductid(String productid)
    {
        this.productid = productid;
    }

    /**
     * @return the salesqty
     */
    public String getSalesqty()
    {
        return salesqty;
    }

    /**
     * @param salesqty
     *            the salesqty to set
     */
    public void setSalesqty(String salesqty)
    {
        this.salesqty = salesqty;
    }

    /**
     * @return the salevalue
     */
    public String getSalevalue()
    {
        return salevalue;
    }

    /**
     * @param salevalue
     *            the salevalue to set
     */
    public void setSalevalue(String salevalue)
    {
        this.salevalue = salevalue;
    }

    /**
     * @return the adjustmentid
     */
    public String getAdjustmentid()
    {
        return adjustmentid;
    }

    /**
     * @param adjustmentid
     *            the adjustmentid to set
     */
    public void setAdjustmentid(String adjustmentid)
    {
        this.adjustmentid = adjustmentid;
    }

    /**
     * @return the adjustmentvalue
     */
    public String getAdjustmentvalue()
    {
        return adjustmentvalue;
    }

    /**
     * @param adjustmentvalue
     *            the adjustmentvalue to set
     */
    public void setAdjustmentvalue(String adjustmentvalue)
    {
        this.adjustmentvalue = adjustmentvalue;
    }

    /**
     * @return the params
     */
    public int getParams()
    {
        return params;
    }

    /**
     * @return the maxlength
     */
    public static int getMaxlength()
    {
        return maxlength;
    }

}
