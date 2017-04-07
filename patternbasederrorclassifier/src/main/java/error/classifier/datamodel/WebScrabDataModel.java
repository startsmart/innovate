package error.classifier.datamodel;

import java.util.Calendar;

import dataprocessor.datamodel.annotations.Context;
import dataprocessor.datamodel.annotations.ContextAttribute;
import dataprocessor.datamodel.annotations.DataType;
import dataprocessor.datamodel.annotations.InstanceAttribute;
import dataprocessor.datamodel.annotations.InstanceTime;
import dataprocessor.interfaces.IDataModel;

public class WebScrabDataModel implements IDataModel
{
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -2531353848217721628L;

	private Calendar time;
	private String tid;
	private String scriptName;
	private String institutionId;
	private String customerId;
	private String aggregationMode;
	private String operation;
	private String offeringId;
	private String dataMode;
	private int totalAccounts;
	private int totalTransactions;
	private int pageViews;
	private long requestParseTime;
	private long responseGenerateTime;
	private long providerServiceTime;
	private long credentialDecryptionTime;
	private long desTime;
	private long cfpOfxTime;
	private long ocrTime;
	private long partnerAuthTime;
	private long browserTime;
	private long scriptTime;
	private long totalScriptExecutionTime;
	private long totalRequestExecutionTime;
	private String errorCode;
	private String errorMessage;
	private String host;
	private boolean mfaRequest;
	private boolean mfaChallengeReponse;

	/**
	 * @return the time
	 */
	@InstanceTime
	public Calendar getTime()
	{
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Calendar time)
	{
		this.time = time;
	}

	/**
	 * @return the tid
	 */
	@InstanceAttribute(name="tid")
	public String getTid()
	{
		return tid;
	}

	/**
	 * @param tid the tid to set
	 */
	public void setTid(String tid)
	{
		this.tid = tid;
	}

	/**
	 * @return the scriptName
	 */
	@Context(name="scriptName")
	public String getScriptName()
	{
		return scriptName;
	}

	/**
	 * @param scriptName the scriptName to set
	 */
	public void setScriptName(String scriptName)
	{
		this.scriptName = scriptName;
	}

	/**
	 * @return the institutionId
	 */
	@Context(name="institutionId")
	public String getInstitutionId()
	{
		return institutionId;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(String institutionId)
	{
		this.institutionId = institutionId;
	}

	/**
	 * @return the customerId
	 */
	@Context(name="customerId")
	public String getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	/**
	 * @return the aggregationMode
	 */
	@ContextAttribute(name="aggregationMode")
	public String getAggregationMode()
	{
		return aggregationMode;
	}

	/**
	 * @param aggregationMode the aggregationMode to set
	 */
	public void setAggregationMode(String aggregationMode)
	{
		this.aggregationMode = aggregationMode;
	}

	/**
	 * @return the operation
	 */
	@ContextAttribute(name="operation")
	public String getOperation()
	{
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
	 * @return the offeringId
	 */
	@ContextAttribute(name="offeringId")
	public String getOfferingId()
	{
		return offeringId;
	}

	/**
	 * @param offeringId the offeringId to set
	 */
	public void setOfferingId(String offeringId)
	{
		this.offeringId = offeringId;
	}

	/**
	 * @return the dataMode
	 */
	@ContextAttribute(name="dataMode")
	public String getDataMode()
	{
		return dataMode;
	}

	/**
	 * @param dataMode the dataMode to set
	 */
	public void setDataMode(String dataMode)
	{
		this.dataMode = dataMode;
	}

	/**
	 * @return the totalAccounts
	 */
	@ContextAttribute(name="totalAccounts", type=DataType.INT)
	public int getTotalAccounts()
	{
		return totalAccounts;
	}

	/**
	 * @param totalAccounts the totalAccounts to set
	 */
	public void setTotalAccounts(int totalAccounts)
	{
		this.totalAccounts = totalAccounts;
	}

	/**
	 * @return the totalTransactions
	 */
	@ContextAttribute(name="totalTransactions", type=DataType.INT)
	public int getTotalTransactions()
	{
		return totalTransactions;
	}

	/**
	 * @param totalTransactions the totalTransactions to set
	 */
	public void setTotalTransactions(int totalTransactions)
	{
		this.totalTransactions = totalTransactions;
	}

	/**
	 * @return the pageViews
	 */
	@InstanceAttribute(name="pageViews", type=DataType.INT)
	public int getPageViews()
	{
		return pageViews;
	}

	/**
	 * @param pageViews the pageViews to set
	 */
	public void setPageViews(int pageViews)
	{
		this.pageViews = pageViews;
	}

	/**
	 * @return the requestParseTime
	 */
	@InstanceAttribute(name="requestParseTime", type=DataType.LONG)
	public long getRequestParseTime()
	{
		return requestParseTime;
	}

	/**
	 * @param requestParseTime the requestParseTime to set
	 */
	public void setRequestParseTime(long requestParseTime)
	{
		this.requestParseTime = requestParseTime;
	}

	/**
	 * @return the responseGenerateTime
	 */
	@InstanceAttribute(name="responseGenerateTime", type=DataType.LONG)
	public long getResponseGenerateTime()
	{
		return responseGenerateTime;
	}

	/**
	 * @param responseGenerateTime the responseGenerateTime to set
	 */
	public void setResponseGenerateTime(long responseGenerateTime)
	{
		this.responseGenerateTime = responseGenerateTime;
	}

	/**
	 * @return the providerServiceTime
	 */
	@InstanceAttribute(name="providerServiceTime", type=DataType.LONG)
	public long getProviderServiceTime()
	{
		return providerServiceTime;
	}

	/**
	 * @param providerServiceTime the providerServiceTime to set
	 */
	public void setProviderServiceTime(long providerServiceTime)
	{
		this.providerServiceTime = providerServiceTime;
	}

	/**
	 * @return the credentialDecryptionTime
	 */
	@InstanceAttribute(name="credentialDecryptionTime", type=DataType.LONG)
	public long getCredentialDecryptionTime()
	{
		return credentialDecryptionTime;
	}

	/**
	 * @param credentialDecryptionTime the credentialDecryptionTime to set
	 */
	public void setCredentialDecryptionTime(long credentialDecryptionTime)
	{
		this.credentialDecryptionTime = credentialDecryptionTime;
	}

	/**
	 * @return the desTime
	 */
	@InstanceAttribute(name="desTime", type=DataType.LONG)
	public long getDesTime()
	{
		return desTime;
	}

	/**
	 * @param desTime the desTime to set
	 */
	public void setDesTime(long desTime)
	{
		this.desTime = desTime;
	}

	/**
	 * @return the cfpOfxTime
	 */
	@InstanceAttribute(name="cfpOfxTime", type=DataType.LONG)
	public long getCfpOfxTime()
	{
		return cfpOfxTime;
	}

	/**
	 * @param cfpOfxTime the cfpOfxTime to set
	 */
	public void setCfpOfxTime(long cfpOfxTime)
	{
		this.cfpOfxTime = cfpOfxTime;
	}

	/**
	 * @return the ocrTime
	 */
	@InstanceAttribute(name="ocrTime", type=DataType.LONG)
	public long getOcrTime()
	{
		return ocrTime;
	}

	/**
	 * @param ocrTime the ocrTime to set
	 */
	public void setOcrTime(long ocrTime)
	{
		this.ocrTime = ocrTime;
	}

	/**
	 * @return the partnerAuthTime
	 */
	@InstanceAttribute(name="partnerAuthTime", type=DataType.LONG)
	public long getPartnerAuthTime()
	{
		return partnerAuthTime;
	}

	/**
	 * @param partnerAuthTime the partnerAuthTime to set
	 */
	public void setPartnerAuthTime(long partnerAuthTime)
	{
		this.partnerAuthTime = partnerAuthTime;
	}

	/**
	 * @return the browserTime
	 */
	@InstanceAttribute(name="browserTime", type=DataType.LONG)
	public long getBrowserTime()
	{
		return browserTime;
	}

	/**
	 * @param browserTime the browserTime to set
	 */
	public void setBrowserTime(long browserTime)
	{
		this.browserTime = browserTime;
	}

	/**
	 * @return the scriptTime
	 */
	@InstanceAttribute(name="scriptTime", type=DataType.LONG)
	public long getScriptTime()
	{
		return scriptTime;
	}

	/**
	 * @param scriptTime the scriptTime to set
	 */
	public void setScriptTime(long scriptTime)
	{
		this.scriptTime = scriptTime;
	}

	/**
	 * @return the totalScriptExecutionTime
	 */
	@InstanceAttribute(name="totalScriptExecutionTime", type=DataType.LONG)
	public long getTotalScriptExecutionTime()
	{
		return totalScriptExecutionTime;
	}

	/**
	 * @param totalScriptExecutionTime the totalScriptExecutionTime to set
	 */
	public void setTotalScriptExecutionTime(long totalScriptExecutionTime)
	{
		this.totalScriptExecutionTime = totalScriptExecutionTime;
	}

	/**
	 * @return the totalRequestExecutionTime
	 */
	@InstanceAttribute(name="totalRequestExecutionTime", type=DataType.LONG)
	public long getTotalRequestExecutionTime()
	{
		return totalRequestExecutionTime;
	}

	/**
	 * @param totalRequestExecutionTime the totalRequestExecutionTime to set
	 */
	public void setTotalRequestExecutionTime(long totalRequestExecutionTime)
	{
		this.totalRequestExecutionTime = totalRequestExecutionTime;
	}

	/**
	 * @return the errorCode
	 */
	@ContextAttribute(name="errorCode")
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	@ContextAttribute(name="errorMessage")
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the host
	 */
	@InstanceAttribute(name="host")
	public String getHost()
	{
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * @return the mfaRequest
	 */
	@InstanceAttribute(name="mfaRequest", type=DataType.BOOLEAN)
	public boolean isMfaRequest()
	{
		return mfaRequest;
	}

	/**
	 * @param mfaRequest the mfaRequest to set
	 */
	public void setMfaRequest(boolean mfaRequest)
	{
		this.mfaRequest = mfaRequest;
	}

	/**
	 * @return the mfaChallengeReponse
	 */
	@InstanceAttribute(name="mfaChallengeReponse", type=DataType.BOOLEAN)
	public boolean isMfaChallengeReponse()
	{
		return mfaChallengeReponse;
	}

	/**
	 * @param mfaChallengeReponse the mfaChallengeReponse to set
	 */
	public void setMfaChallengeReponse(boolean mfaChallengeReponse)
	{
		this.mfaChallengeReponse = mfaChallengeReponse;
	}
}
