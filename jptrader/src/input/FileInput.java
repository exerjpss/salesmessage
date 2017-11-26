package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import core.constants.ErrorCodes;

public class FileInput implements IDataInput 
{

	private FileReader fr;
	private BufferedReader br;
	private String filename;
	
	public FileInput (String filename)
	{
		this.filename = filename;
	}
	
	public int init ()
	{
		int err = ErrorCodes.ERR_OK;
		try 
		{
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
		} catch (Exception e)
		{
			err = ErrorCodes.FILE_ERR;
			System.err.println (e.getMessage());
		}
		
		return err;
	}
	@Override
	public String readln() 
	{
		String data = "";
		try {
			data= br.readLine();
		} catch (IOException e) {
			
			System.err.println (e.getMessage());
			data = null;
		}
		return data;
		
	}

}
