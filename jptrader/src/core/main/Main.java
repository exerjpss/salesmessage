package core.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import output.IOutput;
import output.SalesConsoleOut;
import input.FileInput;
import input.HardCodedData;
import input.IDataInput;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String filename = "";
		if (args[0].startsWith("FILE=" ))
				{
					filename = args[0].substring("FILE=".length());
				}
		System.out.println ("Running Test program");
		System.out.println();
		
		CentralProcessing cp = new CentralProcessing();
		IDataInput id=null;;
		if (filename.equals(""))
		{
			HardCodedData hd = new HardCodedData();
			id = (IDataInput)hd;
		}
		else
		{
			FileInput fi = new FileInput (filename);
			fi.init();
			id = (FileInput)fi;
			
			
		}
		IOutput io = new SalesConsoleOut();
		cp.mainloop(id, io);
	}

}
