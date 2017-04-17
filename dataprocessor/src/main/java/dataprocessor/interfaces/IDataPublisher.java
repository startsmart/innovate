package dataprocessor.interfaces;

import java.util.List;

/**
 * @author Sanjeev S. [527395]
 */
@FunctionalInterface
public interface IDataPublisher
{
	void publishData(List<IDataModel> dataModels);
}
