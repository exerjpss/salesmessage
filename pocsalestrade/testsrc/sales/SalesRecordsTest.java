package sales;

/*
 * We are supplying some data, but to verify the data processing
 * fixed arrays have been created and different procedures written
 * to calculate the results 
 * These should match the main classes calculations.
 * 
 */

import static org.junit.Assert.*;

import log.LocalLog;
import message.Message;
import message.MessageTypes;
import message.Transaction;

import org.junit.Test;



import errors.ErrorRet;

public class SalesRecordsTest
{
    // Used for tracking the testing
    // manually totalling amounts to match records
    // only testing three records
    
    SalesRecords salesrec ;
    double totalsales = 0, totalp1sales = 0, totalp2sales=0, totalp3sales=0;
    int    totalcount = 0, p1count = 0, p2count = 0, p3count = 0;
    double [] p1value    = {0,0,0,0,0,0,0,0}, p2value = {0,0,0,0,0,0,0,0}, p3value = {0,0,0,0,0,0,0,0};
    int    [] p1qty      = {0,0,0,0,0,0,0,0}, p2qty = {0,0,0,0,0,0,0,0}, p3qty = {0,0,0,0,0,0,0,0};
    int    totalqty = 0;

    double testvalue  = 0;
    int    testcount  = 0;
    int    testqty    = 0, counter = 0;
    int    error      = 0;
    int    testadj    = 0;

    public void adjustment (int group, double value, int type)
    {
        for (int n=0;n<8;n++)
        {
            switch (group)
            {
                case 1 :
                    if (p1value[n]!=0)
                    {
                        if (type==Transaction.adjadd)
                            p1value[n]+=value;
                        if (type==Transaction.adjsub)
                            p1value[n]-=value;
                        if (type==Transaction.adjmult)
                            p1value[n]=p1value[n]*value;
                    }
                    break;
                case 2 : 
                    if (p2value[n]!=0)
                    {
                        if (type==Transaction.adjadd)
                            p2value[n]+=value;
                        if (type==Transaction.adjsub)
                            p2value[n]-=value;
                        if (type==Transaction.adjmult)
                            p2value[n]=p2value[n]*value;
                    }
                    break;
                case 3 : 
                    if (p3value[n]!=0)
                    {
                        if (type==Transaction.adjadd)
                            p3value[n]+=value;
                        if (type==Transaction.adjsub)
                            p3value[n]-=value;
                        if (type==Transaction.adjmult)
                            p3value[n]=p3value[n]*value;
                    }

                    break;
                
            }
        }
    }
    
    
    // grouped the calculations and assertions to test nothing corrupted and entered in correct part
    public void assertioncheck ()
    {
        totalsales = 0;
        totalp1sales = 0;
        totalp2sales=0;
        totalp3sales=0;
        totalqty=0;
        for (int n=0;n<8;n++)
        {
            totalsales += p1value[n] * p1qty[n] + p2value[n] * p2qty[n] + p3value[n] * p3qty[n];
            totalp1sales += p1value[n] * p1qty[n] ;
            totalp2sales += p2value[n] * p2qty[n];
            totalp3sales += p3value[n] * p3qty[n];
            totalqty += p1qty[n] + p2qty[n] + p3qty[n];
            
        }
        totalcount = p1count + p2count + p3count;
        counter++;
        
        assertTrue("Sale no." + counter + " value " + totalsales, salesrec.getTotalSales() == totalsales);
        assertTrue("Sale no." + counter + " count " + totalcount, salesrec.getTotalSalesCount() == totalcount);
        assertTrue("Sale no." + counter + " qty " + totalqty, salesrec.getTotalQtyCount() == totalqty);
        assertTrue("Sale no." + counter + " value for [1] ", salesrec.getTotalSales(1) == totalp1sales);
        assertTrue("Sale no." + counter + " count for [1] ", salesrec.getTotalSalesCount(1) == p1count);
        assertTrue("Sale no." + counter + " value for [2] ", salesrec.getTotalSales(2) == totalp2sales);
        assertTrue("Sale no." + counter + " count for [2] ", salesrec.getTotalSalesCount(2) == p2count);
        assertTrue("Sale no." + counter + " value for [3] ", salesrec.getTotalSales(3) == totalp3sales);
        assertTrue("Sale no." + counter + " count for [3] ", salesrec.getTotalSalesCount(3) == p3count);

    }
    @Test
    public void test()
    {
        salesrec = new SalesRecords();

        Transaction action;
        salesrec.testlogRecords();

        testvalue = 3;
        testqty = 2;
        Message msg = new Message("MESSAGEID="+Transaction.sale+"#PRODUCTID=1#SALEVALUE="+testvalue+"#SALEQTY="+testqty);
        action = MessageTypes.isValidMessage(Transaction.sale);
        action = action.createTransaction(msg);
        salesrec.processTransaction(action);
        salesrec.testlogRecords();
        p1value[p1count] = testvalue;
        p1qty [p1count] = testqty;
        p1count++;
        assertioncheck();

        testvalue = 3;
        testqty = 3;
        msg = new Message("MESSAGEID="+Transaction.sale+"#PRODUCTID=2#SALEVALUE="+testvalue+"#SALEQTY="+testqty);
        //data = new String[] { "" + Transaction.sale, "2", "" + testvalue, "" + testqty };
        action = MessageTypes.isValidMessage(Transaction.sale);
        action = action.createTransaction(msg);
        salesrec.processTransaction(action);
        salesrec.testlogRecords();
        p2value[p2count] = testvalue;
        p2qty [p2count] = testqty;
        p2count++;
        assertioncheck();
        
        testvalue = 5;
        testqty = 1;
        //data = new String[] { "" + Transaction.salesingle, "3", "" + testvalue };
        msg = new Message("MESSAGEID="+Transaction.salesingle+"#PRODUCTID=3#SALEVALUE="+testvalue);
        action = MessageTypes.isValidMessage(Transaction.salesingle);
        action = action.createTransaction(msg);
        salesrec.processTransaction(action);
        salesrec.testlogRecords();
        p3value[p3count] = testvalue;
        p3qty [p3count] = testqty;

        p3count++;
        assertioncheck();

        
        testvalue = 8;
        testqty = 1;
        msg = new Message("MESSAGEID="+Transaction.salesingle+"#PRODUCTID=3#SALEVALUE="+testvalue);
        action = MessageTypes.isValidMessage(Transaction.salesingle);
        action = action.createTransaction(msg);
        salesrec.processTransaction(action);
        salesrec.testlogRecords();
        p3value[p3count] = testvalue;
        p3qty [p3count] = testqty;
        p3count++;
        assertioncheck();


        testvalue = 400;
        testadj   = 200;
        testqty=1;
        //data = new String[] { "" + Transaction.adjustment, "2", ""+ testvalue, "" + Transaction.adjadd , "" + testadj};
        msg = new Message("MESSAGEID="+Transaction.adjustment+"#PRODUCTID=2#SALEVALUE="+testvalue+"#ADJUSTID="+ Transaction.adjadd + "#ADJUSTVALUE="+testadj);
        action = MessageTypes.isValidMessage(Transaction.adjustment);
        action = action.createTransaction(msg);
        salesrec.processTransaction(action);
        adjustment (2,testadj,Transaction.adjadd);
        salesrec.testlogRecords();
        p2value[p2count] = testvalue;
        p2qty [p2count] = testqty;  
        p2count++;
        assertioncheck();

        
        testvalue = 7;
        testqty=1;
        testadj= 2000000;
        //data = new String[] { "" + Transaction.adjustment, "2", ""+ testvalue, "" + Transaction.adjmult };
        msg = new Message("MESSAGEID="+Transaction.adjustment+"#PRODUCTID=2#SALEVALUE="+testvalue+"#ADJUSTID="+ Transaction.adjmult + "#ADJUSTVALUE="+testadj);
        action = MessageTypes.isValidMessage(Transaction.adjustment);
        action = action.createTransaction(msg);
        error = salesrec.processTransaction(action);
        salesrec.testlogRecords();
        if (error != ErrorRet.OK)
        {
            LocalLog.error  ("Not adjusted");
        } else
        {
            adjustment (2,testadj,Transaction.adjmult);
            
            p2value[p2count] = testvalue;
            p2qty [p2count] = testqty;  
            p2count++;
            
        }
        assertioncheck();
        
        
        testvalue = 11;
        testqty=1;
        testadj = 1;
        //data = new String[] { "" + Transaction.adjustment, "2", "" + testvalue, "" + Transaction.adjsub };
        msg = new Message("MESSAGEID="+Transaction.adjustment+"#PRODUCTID=2#SALEVALUE="+testvalue+"#ADJUSTID="+ Transaction.adjsub + "#ADJUSTVALUE="+testadj);

        action = MessageTypes.isValidMessage(Transaction.adjustment);
        action = action.createTransaction(msg);
        salesrec.processTransaction(action);
        salesrec.testlogRecords();
        adjustment (2,testadj,Transaction.adjsub);
        p2value[p2count] = testvalue;
        p2qty [p2count] = testqty;
        p2count++;
        assertioncheck();

        testvalue = 3;
        testqty=1;
        testadj = 8;
        //data = new String[] { "" + Transaction.adjustment, "1", "" + testvalue, "" + Transaction.adjmult };
        msg = new Message("MESSAGEID="+Transaction.adjustment+"#PRODUCTID=1#SALEVALUE="+testvalue+"#ADJUSTID="+ Transaction.adjmult + "#ADJUSTVALUE="+testadj);
        action = MessageTypes.isValidMessage(Transaction.adjustment);
        action = action.createTransaction(msg);
        salesrec.processTransaction(action);
        salesrec.testlogRecords();
        adjustment (1,testadj,Transaction.adjmult);
        p1value[p1count] = testvalue;
        p1qty [p1count] = testqty;
        p1count++;
        assertioncheck();
        
        
        
        

    }

}
