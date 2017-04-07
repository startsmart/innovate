package dataprocessor.publish.strategy;

import java.security.InvalidParameterException;
import java.util.List;

import dataprocessor.interfaces.IDataModel;
import dataprocessor.interfaces.IPublishStrategy;

/**
 * Publish extracted data on a call to check ability to publish after specified interval. If the interval is given as one hour the
 * method canDoPublish will return true when it is invoked after one hour of creation, the next success will be one hour from the
 * current success time.
 *
 * Note: This is not a scheduled publisher that publish data on a given interval. Instead it allows to publish data when check is
 * made in a given interval of time.
 * @author Sanjeev S. [527395]
 */
public class TimerPublishStrategy implements IPublishStrategy
{
	private long timeInterval;
	private long lastRun;

	public TimerPublishStrategy(long timeInterValArg)
	{
		if(timeInterValArg < 1)
		{
			throw new InvalidParameterException("Time interval value should be positive");
		}
		lastRun = System.currentTimeMillis();
		this.timeInterval = timeInterValArg;
	}

	@Override
	public boolean canDoPublish(List<IDataModel> dataModels)
	{
		long current = System.currentTimeMillis();
		long interval =  current - this.lastRun;
		if(interval >= this.timeInterval)
		{
			this.lastRun = current;
			return true;
		}
		return false;
	}

}
