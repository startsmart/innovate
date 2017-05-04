package error.classifier.data.impl;

import dataprocessor.interfaces.IDataInstance;

/**
 * @author Sanjeev S.
 */
public class WebScrabDataInstance implements IDataInstance
{
	private String data;

	public WebScrabDataInstance(String data)
	{
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public String getData()
	{
		return data;
	}



	@Override
	public String toString()
	{
		return data;
	}
}
