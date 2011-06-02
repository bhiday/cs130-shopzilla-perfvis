package com.shopzilla.perfvis.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.shopzilla.perfvis.data.CompositePerfData;
import com.shopzilla.perfvis.data.WebappData;

@Component("syncWorker")
public class PerfDataCollectorImpl implements PerfDataCollector{

	private static final Log LOG = LogFactory.getLog(PerfDataCollectorImpl.class);
	
	public void work() {
		
		LOG.info("Scheduler task has been run!!!");
		
		List <WebappData> webappDataList = WebappData.findAllWebappDatas();
		
		for (WebappData webappData : webappDataList) {
			getPerfDataForWebapp(webappData.getId());
		}
		
		LOG.info("Scheduler task has ended!!!");
		
	}

	public void getPerfDataForWebapp(Long webappId) {
		//get the URI
		String uri = WebappData.findWebappData(webappId).getWebappURI();
		
		//check if there exist records in the database
		List <CompositePerfData> compositePerfDataList = CompositePerfData.getCompositePerfDatabyWebapp(webappId);
		//LOG.info(String.valueOf(compositePerfDataList == null || compositePerfDataList.size() == 0));
		if (compositePerfDataList == null || compositePerfDataList.size() == 0) {
			//first run!
			//get data from last one day!
			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			cal.add(Calendar.DATE, -1);
			Date yesterday = cal.getTime();
			
			uri = uri + "/perf/starttime/" + String.valueOf(yesterday.getTime()) + "/endtime/" + String.valueOf(now.getTime());
		}
		else {
			//some data already exists!
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = new Date();
	    	try {
	    		String str = CompositePerfData.getCompositePerfDataMaxTimestamp(webappId).get(0).toString().substring(0, 19);
				date = formatter.parse(str);
				LOG.info(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}			
			
			uri = uri +  "/perf/starttime/" + String.valueOf(date.getTime());
		}
		LOG.info(uri);
		List <CompositePerfData> cpdList = parseXml(uri);
    	for (CompositePerfData cpd : cpdList)
    	{
    		cpd.setWebappId(webappId);
    		cpd.persist();
    	} 
	}
	
	private List <CompositePerfData> parseXml(String input) {

    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = null;
    	Document doc = null;

    	try {

    		db = dbf.newDocumentBuilder();
    		doc = db.parse(input);

    	} catch (Exception e) {
    		LOG.info("Error parsing return value of URI! Please check the URI : " + input);
    		return new ArrayList <CompositePerfData>();
    	}

    	doc.getDocumentElement().normalize();
    	//System.out.println("Root element " + doc.getDocumentElement().getNodeName());
    	NodeList nodeLst = doc.getElementsByTagName("result");
    	//System.out.println("Information of all employees");
    	List <CompositePerfData> cpdList = new ArrayList<CompositePerfData>();
    	LOG.info("Number of entries returned = " + String.valueOf(nodeLst.getLength()));
    	for (int s = 0; s < nodeLst.getLength(); s++) {

    		Node fstNode = nodeLst.item(s);


    		if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

    			Element fstElmnt = (Element) fstNode;
    			CompositePerfData cpd = new CompositePerfData();

    			NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("methodName");
    			Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
    			NodeList fstNm = fstNmElmnt.getChildNodes();
    			cpd.setMethodName((String) (((Node) fstNm.item(0)).getNodeValue()));

    			NodeList midNmElmntLst = fstElmnt.getElementsByTagName("invokeTime");
    			Element midNmElmnt = (Element) midNmElmntLst.item(0);
    			NodeList midNm = midNmElmnt.getChildNodes();
    			String dateString = (String) (((Node) midNm.item(0)).getNodeValue());
    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			Date date = new Date();
    			try {
    				date = formatter.parse(dateString);
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    			cpd.setInvokeTime(date);

    			NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("execTime");
    			Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
    			NodeList lstNm = lstNmElmnt.getChildNodes();
    			cpd.setExecTime(Long.parseLong(((Node) lstNm.item(0)).getNodeValue()));

    			cpdList.add(cpd);
    		}
    	}
    	return cpdList;
    }
	
}
