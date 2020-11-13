package Dataproviders;


import java.util.ListIterator;

import org.testng.annotations.DataProvider;

import Generic_Library.Utility;

public class dp_login {

	@DataProvider(name = "Valid_data")
	public ListIterator<Object[]> dp_valid() throws Exception
	{
		return Utility.dp_commonlogic("Credentials","validname");
	}
	
}