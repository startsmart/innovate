package error.classifier.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataprocessor.interfaces.IDataModel;
import dataprocessor.interfaces.IDataPublisher;
import error.classifier.IDataListener;

/**
 * @author Sanjeev S.
 */
public class WebScrabDataPublisher implements IDataPublisher
{

	private Map<String, IDataListener<List<IDataModel>>> listeners;

	public WebScrabDataPublisher()
	{
		listeners = new HashMap<>();
	}

	@Override
	public void publishData(List<IDataModel> dataModels)
	{
		listeners.values().parallelStream().forEach(listener -> listener.listen(dataModels));
	}

	public void registerListener(String listenerKey, IDataListener<List<IDataModel>> listener)
	{
		listeners.put(listenerKey, listener);
	}

	public void unregisterListener(String listenerKey)
	{
		listeners.remove(listenerKey);
	}
}
