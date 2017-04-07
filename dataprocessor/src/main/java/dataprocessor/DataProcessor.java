package dataprocessor;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import dataprocessor.interfaces.IDataExtractor;
import dataprocessor.interfaces.IDataInstance;
import dataprocessor.interfaces.IDataModel;
import dataprocessor.interfaces.IDataPublisher;
import dataprocessor.interfaces.IPublishStrategy;
import dataprocessor.publish.strategy.InstantPublishStrategy;

/**
 * @author 527395
 */
public class DataProcessor
{
	private IDataExtractor dataExtractor;
	private IDataPublisher publisher;
	private IPublishStrategy publishStrategy;
	private List<IDataModel> registry;

	public DataProcessor(IDataExtractor dataExtractorArg, IDataPublisher publisherArg)
	{
		this(dataExtractorArg, publisherArg, new InstantPublishStrategy());
	}

	public DataProcessor(IDataExtractor dataExtractorArg, IDataPublisher publisherArg, IPublishStrategy publishStrategyArg)
	{
		if(dataExtractorArg == null)
		{
			throw new InvalidParameterException("DataExtractor cannot be null");
		}
		if(publisherArg == null)
		{
			throw new InvalidParameterException("DataPublisher cannot be null");
		}
		this.dataExtractor = dataExtractorArg;
		this.publisher = publisherArg;
		this.publishStrategy = publishStrategyArg;
		if(this.publishStrategy == null)
		{
			this.publishStrategy = new InstantPublishStrategy();
		}
		this.registry = new ArrayList<>();
	}

	public void processData(IDataInstance... dataInstance)
	{
		List<IDataModel> dataModel = this.dataExtractor.extractData(dataInstance);
		onData(dataModel);
	}

	private void onData(List<IDataModel> dataModels)
	{
		synchronized(this)
		{
			this.registry.addAll(dataModels);
		}
		publish();
	}

	private void publish()
	{
		if(this.publishStrategy.canDoPublish(this.registry))
		{
			synchronized(this)
			{
				this.publisher.publishData(this.registry);
				this.registry = new ArrayList<>();
			}
		}
	}
}
