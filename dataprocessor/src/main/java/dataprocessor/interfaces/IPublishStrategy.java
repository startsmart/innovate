package dataprocessor.interfaces;

import java.util.List;

/**
 * @author Sanjeev S. [527395]
 */
@FunctionalInterface
public interface IPublishStrategy
{
	public boolean canDoPublish(List<IDataModel> dataModels);
}
