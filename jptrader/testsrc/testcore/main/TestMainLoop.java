package testcore.main;

import static org.junit.Assert.*;
import input.HardCodedData;
import input.IDataInput;

import org.junit.Test;

import output.IOutput;
import output.SalesConsoleOut;

import core.main.CentralProcessing;

public class TestMainLoop 
{

	@Test
	public void testMainLoop() 
	{
		CentralProcessing cp = new CentralProcessing();
		HardCodedData hd = new HardCodedData();
		IDataInput id = (IDataInput)hd;
		IOutput io = new SalesConsoleOut();
		
		cp.mainloop(id, io);
		
	}

}
