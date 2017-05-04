package dataprocessor.interfaces;

import java.util.List;

/**
 * @author Sanjeev S.
 */
@FunctionalInterface
public interface IDataPublisher
{
	void publishData(List<IDataModel> dataModels);
}
