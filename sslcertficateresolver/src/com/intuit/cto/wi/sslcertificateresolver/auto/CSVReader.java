package com.intuit.cto.wi.sslcertificateresolver.auto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author Sanjeev S.[527395]
 */
public class CSVReader implements IInputReader
{

    private static final String URL_COL_NAME = "url";
    private static final String FIID_COLNAME = "fiid";
    private static final String SCRIPT_NAME_COLNAME = "scriptname";
    private static final String CAUSE_COL_NAME = "cause";
    private static final String DOUBLE_QUOTES = "\"";

    @Override
    public Set<CertificateFailureVO> getInput(ExecutionContext context)
    {
        context.interactToUser();
        Set<CertificateFailureVO> input = new HashSet<>();
        File inputFile = (File) context.getData("inputFile");
        if(inputFile == null)
        {
            throw new RuntimeException("Invalid context. Insufficient Data");
        }
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));)
        {
            String line = null;
            Map<String, Integer> map = new HashMap<String, Integer>();
            int lineNum = 1;
            while((line = reader.readLine()) != null)
            {
                if(lineNum == 1)
                {
                    String[] ss = line.split(",");
                    for(int i = 0; i < ss.length; i++)
                    {
                        map.put(ss[i], Integer.valueOf(i));
                    }
                }
                else
                {
                    String[] ss = line.split(",");
                    CertificateFailureVO vo = new CertificateFailureVO();
                    Integer index = map.get(URL_COL_NAME);
                    if(index !=null && index > -1)
                    {
                       vo.setUrlFailing(replaceAdditionalChar(ss[index]));
                    }

                    index = map.get(FIID_COLNAME);
                    if(index !=null && index > -1)
                    {
                       vo.setFiId(replaceAdditionalChar(ss[index]));
                    }

                    index = map.get(SCRIPT_NAME_COLNAME);
                    if(index !=null && index > -1)
                    {
                       vo.setScriptName(replaceAdditionalChar(ss[index]));
                    }

                    index = map.get(CAUSE_COL_NAME);
                    if(index !=null && index > -1)
                    {
                       vo.setCause(replaceAdditionalChar(ss[index]));
                    }
                    input.add(vo);
                }
                lineNum++;
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException("Execution Failure. Cause - " + e.getCause() , e);
        }
        return input;
    }

    private String replaceAdditionalChar(String data)
    {
        String tempData = data;
        if(tempData != null)
        {
            if(tempData.startsWith(DOUBLE_QUOTES) && tempData.endsWith(DOUBLE_QUOTES))
            {
                tempData = tempData.substring(1, tempData.lastIndexOf(DOUBLE_QUOTES));
            }
            else if(tempData.startsWith(DOUBLE_QUOTES))
            {
                tempData = tempData.substring(1);
            }
            else if(tempData.endsWith(DOUBLE_QUOTES))
            {
                tempData = tempData.substring(0, tempData.lastIndexOf(DOUBLE_QUOTES));
            }
        }
        return tempData;
    }

    @Override
    public ExecutionContext prepareExecutionContext()
    {
        return new ConsoleUIContext();
    }


}
