
package dataprocessor.interfaces;

import java.util.List;

/**
 * @author Sanjeev S.
 */
@FunctionalInterface
public interface IDataExtractor
{
	List<IDataModel> extractData(IDataInstance... dataInstance);
}
