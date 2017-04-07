package dataprocessor.publish.strategy;

import java.util.List;

import dataprocessor.interfaces.IDataModel;
import dataprocessor.interfaces.IPublishStrategy;

public class LazyPublishStrategy implements IPublishStrategy
{

	private int publishAfter;

	public LazyPublishStrategy(int publishAfterArg)
	{
		this.publishAfter = publishAfterArg;
	}

	@Override
	public boolean canDoPublish(List<IDataModel> dataModels)
	{
		return dataModels != null && dataModels.size() == this.publishAfter;
	}

}
