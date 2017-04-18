package error.classifier.data.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loggerapi.Logger;
import dataprocessor.interfaces.IDataExtractor;
import dataprocessor.interfaces.IDataInstance;
import dataprocessor.interfaces.IDataModel;

/**
 * @author 527395
 */
public class WebScrabDataExtractor implements IDataExtractor
{
	 private static final String _TIME="_time";
	 private static final String TID="tid";
	 private static final String SCRIPT_NAME="script_name";
	 private static final String INSTITUTION_ID="institution_id";
	 private static final String CUSTOMER_ID="customer_id";
	 private static final String AGGREGATION_MODE="aggregation_mode";
	 private static final String OPERATION="operation";
	 private static final String OFFERING_ID="offering_id";
	 private static final String DATA_MODE="data_mode";
	 private static final String TOTAL_ACCOUNTS="total_accounts";
	 private static final String TOTAL_TRANSACTIONS="total_transactions";
	 private static final String PAGE_VIEWS="page_views";
	 private static final String REQUEST_PARSE_TIME="request_parse_time";
	 private static final String RESPONSE_GENERATE_TIME="response_generate_time";
	 private static final String PROVIDER_SERVICE_TIME="provider_service_time";
	 private static final String CREDENTIAL_DECRYPTION_TIME="credential_decryption_time";
	 private static final String DES_TIME="des_time";
	 private static final String CFP_OFX_TIME="cfp_ofx_time";
	 private static final String OCR_TIME="ocr_time";
	 private static final String PARTNER_AUTH_TIME="partner_auth_time";
	 private static final String BROWSER_TIME="browser_time";
	 private static final String SCRIPT_TIME="script_time";
	 private static final String TOTAL_SCRIPT_EXECUTION_TIME="total_script_execution_time";
	 private static final String TOTAL_REQUEST_EXECUTION_TIME="total_request_execution_time";
	 private static final String ERROR_CODE="error_code";
	 private static final String ERROR_MESSAGE="error_message";
	 private static final String HOST="host";
	 private static final String MFA_REQUEST="mfa_request";
	 private static final String MFA_CHALLENGE_REPONSE="mfa_challenge_reponse";

	@Override
	public List<IDataModel> extractData(IDataInstance... dataInstance)
	{
		if (dataInstance.length == 0)
		{
			throw new IllegalArgumentException("No dataInstance provided."); //$NON-NLS-1$
		}

		List<IDataModel> dataModelList = new ArrayList<>();
		for (IDataInstance instance : dataInstance)
		{
			dataModelList.add(scrapeEachInstance(instance));
		}
		return dataModelList;
	}

	private IDataModel scrapeEachInstance(IDataInstance instance)
	{
		String currentLine;
		String[] dataArray;
		Map<String, Integer> csvHeaderMap = null;
		StringReader sr = new StringReader(instance.toString());
		BufferedReader myInput = new BufferedReader(sr);
		IDataModel dataModel = null;
		try
		{
			while ((currentLine = myInput.readLine()) != null)
			{
				dataArray = currentLine.replace("\"", "").split(",");
				if (csvHeaderMap== null || csvHeaderMap.isEmpty())
				{
					csvHeaderMap = getCsvRecordHeader(dataArray);

				}
				else
				{
					dataModel = processEachCsvRecords(dataArray, csvHeaderMap);
				}

			}
		}
		catch (IOException e)
		{
			Logger.getLogger().error("Unable to read instance.", e);
		}
		return dataModel;
	}

	private IDataModel processEachCsvRecords(String[] dataArray, Map<String, Integer> csvHeaderMap)
	{
		WebScrabDataModel dataModel = new WebScrabDataModel();
		dataModel.setAggregationMode(optString(AGGREGATION_MODE, dataArray, csvHeaderMap));
		dataModel.setBrowserTime(optLong(optString(BROWSER_TIME, dataArray, csvHeaderMap)));
		dataModel.setCfpOfxTime(optLong(optString(CFP_OFX_TIME, dataArray, csvHeaderMap)));
		dataModel.setCredentialDecryptionTime(optLong(optString(CREDENTIAL_DECRYPTION_TIME, dataArray, csvHeaderMap)));
		dataModel.setCustomerId(optString(CUSTOMER_ID, dataArray, csvHeaderMap));
		dataModel.setDataMode(optString(DATA_MODE, dataArray, csvHeaderMap));
		dataModel.setDesTime(optLong(optString(DES_TIME, dataArray, csvHeaderMap)));
		dataModel.setErrorCode(optString(ERROR_CODE, dataArray, csvHeaderMap));
		dataModel.setErrorMessage(optString(ERROR_MESSAGE, dataArray, csvHeaderMap));
		dataModel.setHost(optString(HOST, dataArray, csvHeaderMap));
		dataModel.setInstitutionId(optString(INSTITUTION_ID, dataArray, csvHeaderMap));
		dataModel.setMfaChallengeReponse(Boolean.parseBoolean(optString(MFA_CHALLENGE_REPONSE, dataArray, csvHeaderMap)));
		dataModel.setMfaRequest(Boolean.parseBoolean(optString(MFA_REQUEST, dataArray, csvHeaderMap)));
		dataModel.setOcrTime(optLong(optString(OCR_TIME, dataArray, csvHeaderMap)));
		dataModel.setOfferingId(optString(OFFERING_ID, dataArray, csvHeaderMap));
		dataModel.setOperation(optString(OPERATION, dataArray, csvHeaderMap));
		dataModel.setPageViews(optInt(optString(PAGE_VIEWS, dataArray, csvHeaderMap)));
		dataModel.setPartnerAuthTime(optLong(optString(PARTNER_AUTH_TIME, dataArray, csvHeaderMap)));
		dataModel.setProviderServiceTime(optLong(optString(PROVIDER_SERVICE_TIME, dataArray, csvHeaderMap)));
		dataModel.setRequestParseTime(optLong(optString(REQUEST_PARSE_TIME, dataArray, csvHeaderMap)));
		dataModel.setResponseGenerateTime(optLong(optString(RESPONSE_GENERATE_TIME, dataArray, csvHeaderMap)));
		dataModel.setScriptName(optString(SCRIPT_NAME, dataArray, csvHeaderMap));
		dataModel.setTid(optString(TID, dataArray, csvHeaderMap));
		dataModel.setTime(optCalendar(optString(_TIME, dataArray, csvHeaderMap)));
		dataModel.setTotalAccounts(optInt(optString(TOTAL_ACCOUNTS, dataArray, csvHeaderMap)));
		dataModel.setTotalRequestExecutionTime(optLong(optString(TOTAL_REQUEST_EXECUTION_TIME, dataArray, csvHeaderMap)));
		dataModel.setTotalScriptExecutionTime(optLong(optString(TOTAL_SCRIPT_EXECUTION_TIME, dataArray, csvHeaderMap)));
		dataModel.setTotalTransactions(optInt(optString(TOTAL_TRANSACTIONS, dataArray, csvHeaderMap)));
		return dataModel;
	}

	private Calendar optCalendar(String string)
	{
		Date d = null;
		try
		{
			String time = string.replace("T", " ");
			time = time.substring(0, time.lastIndexOf('-'));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			d = format.parse(time);
		} catch (Exception e) {
		}
		Calendar  cal = Calendar.getInstance();
		if(d != null)
		{
			cal.setTime(d);
		}
		return cal;
	}

	private int optInt(String string)
	{
		try
		{
			return Integer.parseInt(string);
		}
		catch (Exception e) {
		}
		return 0;
	}


	private long optLong(String string)
	{
		try
		{
			return Long.parseLong(string);
		}
		catch (Exception e) {
		}
		return 0;
	}

	private String optString(String key, String[] dataArray, Map<String, Integer> csvHeaderMap)
	{
		try
		{
			return dataArray[csvHeaderMap.get(key)];
		}
		catch (Exception e) {
		}
		return null;
	}

	private Map<String, Integer> getCsvRecordHeader(String[] dataArray)
	{
		Map<String, Integer> csvHeaderMap = new HashMap<>();
		for(int i = 0; i < dataArray.length; i++)
		{
			csvHeaderMap.put(dataArray[i], i);
		}
		return csvHeaderMap;
	}
}
