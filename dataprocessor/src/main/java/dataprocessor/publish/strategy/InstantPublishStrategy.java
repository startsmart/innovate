package dataprocessor.publish.strategy;

import java.util.List;

import dataprocessor.interfaces.IDataModel;
import dataprocessor.interfaces.IPublishStrategy;

/**
 * @author Sanjeev S. [527395]
 */
public class InstantPublishStrategy implements IPublishStrategy
{
	@Override
	public boolean canDoPublish(List<IDataModel> dataModels)
	{
		return dataModels != null && !dataModels.isEmpty();
	}

}
