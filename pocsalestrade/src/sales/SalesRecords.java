
package sales;

/**
 *  Sales Records:
 *  
 *  Keep a list of sales 
 *  Add sales to the list
 *  Apply any relevant adjustments to the sales transactions in the list
 *  Produce the reports required - for now
 *  Sum up the totals, and sub totals for products
 *  
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import errors.ErrorRet;

import log.LocalLog;
import message.Transaction;
import message.TransactionAdj;
import message.TransactionSale;



public class SalesRecords
{
    
    List<SaleRecord>      listOfSales = null;
    List<TransactionSale> listOfTransactions = null;    // A basic list is used at this point
    private int           transRecordCount;             // internal count for how many sales transactions have been processed
    private int           saleRecordCount;
    
    /*
     * Initialise the sales record
     */
    public SalesRecords()
    {
        transRecordCount = 0;
        saleRecordCount=0;
        listOfTransactions = new LinkedList<TransactionSale>();
        listOfSales = new LinkedList<SaleRecord> ();
    }

    /*
     * public int processTransaction (Transaction trans)
     * 
     * Given a transaction from a message has been generated process the
     * transaction this might be a new sale, which is simply added to the list
     * or with an adjustment transaction which is applied first 
     * Assumption: Sale given does not need adjustment applied
     *             If incorrect, simply rearrange order
     * 
     */
    public int processTransaction(Transaction trans)
    {
        int retval = -1;
        if (trans != null)
        {
            
            if (trans.getError() == ErrorRet.OK) // check transaction was error free
            {
                retval = trans.getError();
                
                // If this is an adjustment type, apply the adjustment
                // before adding the sale, as the sale is not to be adjusted
                
                if (trans instanceof TransactionAdj)
                {
                    if (retval == ErrorRet.OK)
                    {
                        
                        retval = processAdjustment((TransactionAdj) trans);
                    }
                }
                
                // if a sales type, add it to the list
                if ((trans instanceof TransactionSale))
                {
                    if (retval == ErrorRet.OK)
                    {
                        
                        trans.setIndex(transRecordCount);                    // adding a record count to keep different transactions identifiable
                        listOfTransactions.add((TransactionSale) trans);     // add the record to the collection
                        
                        for (int n=0;n<((TransactionSale) trans).getQty();n++)
                        {
                            SaleRecord salehold = new SaleRecord(((TransactionSale) trans).getProductType(), ((TransactionSale) trans).getValue(),++saleRecordCount);
                            if (salehold!=null)
                                listOfSales.add(salehold);
                            
                        }
                        transRecordCount++;                                  // increment the record count
                    }  
                    else LocalLog.debug("Processtransaction A error ="+retval);
                }
            } else LocalLog.debug("Processtransaction B error "+trans.getError());
        }

        return retval;
    }

    /*
     * private void processAdjustment(TransactionAdj trans)
     * 
     * Iterate through the sales list apply the adjustment message to the sale
     * list where an adjustment affects the value 
     * Where an error occurs, such as exceed expected results, log an error
     * but continue
     */
    private int processAdjustment(TransactionAdj trans)
    {

        SaleRecord sale = null;
        int retval = ErrorRet.OK;

        Iterator<SaleRecord> it = listOfSales.iterator();    // Iterate through the list of sales

        while (it.hasNext())
        {
            sale = it.next();
            if (sale != null) 
                if (trans.getProductType() == sale.getProductid())
                {
                    retval = ErrorRet.TransGeneric;
                    SaleModifier salemod = new SaleModifier (trans.getAdjustmenttype(), trans.getAdjvalue());
                    retval = sale.adjustmentAction(salemod);    // So the sales record will apply the modification
                                                                                                              // and record a history
                    if (retval != ErrorRet.OK)
                    {
                        LocalLog.debug("Error in processAdjustment = " + retval);
                    }
                }

        }
        return retval;

    }
    
    /* 
     * Given a transaction, create a sale and add it to the list of sales
     */
    private int addSales (TransactionSale trans)
    {
        int retval= ErrorRet.OK;
        
        for (int n=1;n<=trans.getQty();n++)
        {
            SaleRecord salesrec = new SaleRecord(trans.getProductType(),trans.getValue(),++saleRecordCount);
            if (salesrec != null)
                listOfSales.add (salesrec);
        }
        
        return retval;
    }

  
    public int getCount()
    {
        return transRecordCount;
    }

    /*
     * Get total numbers, but use the same function as the product specific ones
     */
    public double getTotalSales()
    {
        return getTotalSales(-1);
    }

    public int getTotalSalesCount()
    {
        return getTotalSalesCount(-1);
    }

    public int getTotalQtyCount()

    {
        return getTotalQtyCount(-1);
    }
    
    /*
     * NB Sales records could be stored as per messages arriving, such as
     * 20 items sold @ 10p
     * But following the spec, it could be suggested that sales are stored as
     * single items
     * 
     * In the later case getTotalSalesCount and getTotalQtyCount will have the same
     * value.
     * 
     */
    
    /*
     * Scan through the sales list, and calculate on current values the total sales
     */
    public double getTotalSales(int productType)
    {
        SaleRecord sale = null;
        double total = 0;
        if (listOfTransactions != null)
        {
            Iterator<SaleRecord> it = listOfSales.iterator();    // Iterate through the list
            while (it.hasNext())
            {
                sale = it.next();
                if (sale != null) if ((sale.getProductid() == productType) || (productType == -1)) total = total +  sale.getValue();
            }
        }
        return total;
    }

    /*
     * Scan through the sales list, and add up the sales entries (not qty)
     */
    public int getTotalSalesCount(int productType)
    {
        SaleRecord sale = null;
        int total = 0;
        if (listOfTransactions != null)
        {
            Iterator<SaleRecord> it = listOfSales.iterator();    // Iterate through the list
            while (it.hasNext())
            {
                sale = it.next();
                if (sale != null) if ((sale.getProductid() == productType) || (productType == -1)) total++;
            }
        }
        return total;
    }

    /*
     * Scan through the sales list, and add up the sales entries using qty
     */
    public int getTotalQtyCount(int productType)
    {
        SaleRecord sale = null;
        int total = 0;
        if (listOfTransactions != null)
        {
            Iterator<SaleRecord> it = listOfSales.iterator();    // Iterate through the list
            while (it.hasNext())
            {
                sale = it.next();
                if (sale != null) if ((sale.getProductid() == productType) || (productType == -1)) total ++;
            }
        }
        return total;
    }

    
    
    /*
     * Ideally these reports should be moved out into separate classes
     * Temporarily here for the limited purposes of this exercise
     * 
     */

    /*
     * Report to output every 10 records
     * 
     * Assuming : List of each sale is not required. (Commented out)
     */
    public void reportSummary(int report)
    {
        SaleRecord sale = null;
        String productlist = "";
        LocalLog.output("Report after " + report + " sales");
        if (listOfTransactions != null)
        {
            Iterator<SaleRecord> it = listOfSales.iterator();    // Iterate through the list

            while (it.hasNext())
            {
                sale = it.next();
                if (sale != null)
                {
                    // LocalLog.output("Product = [" + sale.getProductType() + "] : " + "Qty sold = [" + sale.getQty() + "] : " + "Unit Value= [" + sale.getValue() + "]");
                    // Assumption is no list of all transactions to date is required

                    // quick way to get a list of products with sales against them
                    // so we can iterate the list and produce totals for each product
                    String s = "#" + sale.getProductid() + "#";
                    if (!productlist.contains(s))
                    {
                        productlist += s;
                    }
                }
            }
        }

        // Output the summary of the total sales and sale items

        LocalLog.output("");
        LocalLog.output("Total sale entries = [" + getTotalSalesCount() + "]");
        LocalLog.output("Total sale amount  = [" + getTotalSales() + "]");
        LocalLog.output("");

        // run through the products with sales, unsorted and output the sales for each product
        
        String productlistarray[] = productlist.split("#");
        for (int n = 0; n < productlistarray.length; n++)
        {
            if (!productlistarray[n].equals(""))
            {
                int index = Integer.parseInt(productlistarray[n]);
                if (index > 0)
                {
                    LocalLog.output("Total sale entries for product [" + index + "] = [" + getTotalSalesCount(index) + "]");
                    LocalLog.output("Total sale amount for product  [" + index + "] = [" + getTotalSales(index) + "]");
                    LocalLog.output("Total sale qty for product     [" + index + "[ = [" + getTotalQtyCount(index) + "]");
                }
            }

        }

    }

    /*
     * Report to output at 50 records
     * 
     * Assuming : List of each sale is not required.
     * This is a historical report of any data that was adjusted, showing value, 
     * change requested and latest value.
     */
    public void reportUnitHistory(int report)
    {
        SaleRecord sale = null;
        LocalLog.output("Report after " + report + " sales");
        if (listOfTransactions != null)
        {
            Iterator<SaleRecord> it = listOfSales.iterator();    // Iterate through the sales list

            while (it.hasNext())
            {
                sale = it.next();
                if (sale != null)
                {
                    if (sale.getHistory() != null)              // If history is set, then changes are available to track
                    {
                        LocalLog.output("History : Record = [" + sale.getIndex() + "] Product = [" + sale.getProductid() + "] :");

                        // Start iteration through historical data

                        Iterator<SaleRecord> history = sale.getHistory().iterator();
                        SaleRecord histsale;
                        while (history.hasNext())
                        {
                            histsale = history.next();
                            if (histsale != null)
                            {
                                LocalLog.output("  : Unit Value= [" + histsale.getValue() + "]");

                                // Where history is present, check to see if adjustment action was stored and output the action required

                                SaleModifier salemod = histsale.getAction();
                                if (salemod != null) LocalLog.output("       : Adjustment " + salemod.getModifierTypeName() + " = [" + salemod.getValue() + "]");

                            }
                        }

                        // Output the current (final) data
                        LocalLog.output("Current : "  + "Unit Value= [" + sale.getValue() + "]");

                        LocalLog.output("");
                    }
                }
            }
        }
    }
    
    /*
     * Get a report for the Junit test. Report is here, not for general use. 
     * Only producing a report for a limited number of products
     * For testing purposes only
     */
    public void testlogRecords()
    {
        TransactionSale sale = null;

        LocalLog.debug("------");
        if (listOfTransactions != null)
        {
            Iterator<TransactionSale> it = listOfTransactions.iterator();    // Iterate through the list

            while (it.hasNext())
            {
                sale = it.next();
                if (sale != null) LocalLog.debug("Product = [" + sale.getProductType() + "] : Qty = [" + sale.getQty() + "] : Value=[" + sale.getValue() + "]");
            }
        }

        if (listOfTransactions != null)
        {
            LocalLog.debug("Total sale entries = " + getTotalSalesCount());
            LocalLog.debug("Total sale amount = " + getTotalSales());

            LocalLog.debug("Total sale entries for 1 = " + getTotalSalesCount(1));
            LocalLog.debug("Total sale amount for 1  = " + getTotalSales(1));

            LocalLog.debug("Total sale entries for 2 = " + getTotalSalesCount(2));
            LocalLog.debug("Total sale amount for 2 = " + getTotalSales(2));

            LocalLog.debug("Total sale entries for 3 = " + getTotalSalesCount(3));
            LocalLog.debug("Total sale amount for 3  = " + getTotalSales(3));

        }
    }
}
