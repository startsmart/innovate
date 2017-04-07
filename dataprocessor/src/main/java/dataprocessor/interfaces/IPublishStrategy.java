/**
 *
 */
package dataprocessor.interfaces;

import java.util.List;

/**
 * @author 527395
 */
@FunctionalInterface
public interface IPublishStrategy
{
	public boolean canDoPublish(List<IDataModel> dataModels);
}
