
package dataprocessor.interfaces;

import java.util.List;

/**
 * @author Sanjeev S. [527395]
 */
@FunctionalInterface
public interface IDataExtractor
{
	List<IDataModel> extractData(IDataInstance... dataInstance);
}
