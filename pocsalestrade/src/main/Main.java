/**
 * 
 */
package main;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import sales.SalesRecords;

import errors.ErrorRet;

import log.LocalLog;
import message.MessageProcess;
import message.Transaction;

/**
 * 
 * 
 */
public class Main
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {

        SalesRecords sales = new SalesRecords();
        MessageProcess messages = new MessageProcess();

        // for this purpose this is the external interface
        // loop until 50 messages are received
        try
        {
            InputStreamReader inr = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(inr);
            String data;
            LocalLog.status("Sales tester v1");

            data = "";
            int reported = 0;
            while (data != null)
            {
                
                data = in.readLine();
                LocalLog.debug("INPUT [" + data + "]");
                
                Transaction trans = messages.processMessage(data);
                if (trans.getError() == ErrorRet.OK) 
                    sales.processTransaction(trans);
                else 
                    LocalLog.error("ERROR: Input data [" + data + "] error [" + trans.getError() + "]");

                if (sales.getCount() % 10 == 0)
                {
                    if (sales.getCount() > 0)
                    {
                        if (reported == 0)
                        {
                            reported = 1;
                            sales.Report10Records(sales.getCount());
                        }
                    }
                } else
                {
                    reported = 0;
                }
                if (sales.getCount() == 50)
                {
                    LocalLog.output("50 sales recorded. System is taking a break for now. Thanks");
                    
                    sales.Report50Records(sales.getCount());
                    data = null;

                }

            }

        } catch (Exception e)
        {
            LocalLog.error("Error in data reading");
            LocalLog.error(e.getMessage());
        }

    }

}
