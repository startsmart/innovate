package error.classifier.data.impl;

import dataprocessor.interfaces.IPattern;

public class WebScrabPattern implements IPattern
{
	/**
	 * Generated Serial version UID
	 */
	private static final long serialVersionUID = -1696795860892876455L;
	private String data;

	public WebScrabPattern(String data)
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
