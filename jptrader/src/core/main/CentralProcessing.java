package core.main;

import input.IDataInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;

import core.messages.MessageItem;
import core.sales.SaleList;
import core.transactions.ITransaction;

import output.IOutput;

public class CentralProcessing 
{
	
	// take input and readlines
	// after 10 messages : ouput
	// after 50 messages : output : pause
	public void mainloop (IDataInput in, IOutput io)
	{
		int n=1;
		MessageItem msg = new MessageItem();
		SaleList salelist = new SaleList();
		
		
		while (n<=50)
			
		{
			
			String data="";
			data = in.readln();
			if (data!=null)
			{
					
					msg.setMessage(data);
					msg.processMessage();
					LinkedList <ITransaction> transactionlist = msg.getTransactionList();
					salelist.addTransactionsToList(transactionlist);
					
					if ((n % 10) == 0)
					{
						salelist.sortList();
						salelist.outputSales(io);
					}
					if ((n % 50) == 0)
					{
						
						salelist.outputSalesDetail(io);
						
					}
					n++;
			}
			else
			{
				salelist.outputSalesDetail(io);
				n = 100;
			}
			
		}
		
		
		/*............
		 * This is only to satisy the end criteria of waiting after 50
		 */
		InputStreamReader is = new InputStreamReader (System.in);
		
		BufferedReader br = new BufferedReader (is);
		System.out.println ("waiting....");
		try {
			String wait = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
