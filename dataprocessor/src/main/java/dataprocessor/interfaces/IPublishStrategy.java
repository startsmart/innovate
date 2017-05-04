package dataprocessor.interfaces;

import java.util.List;

/**
 * @author Sanjeev S.
 */
@FunctionalInterface
public interface IPublishStrategy
{
	public boolean canDoPublish(List<IDataModel> dataModels);
}
