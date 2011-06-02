package com.shopzilla.perfvis.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shopzilla.perfvis.data.CompositePerfData;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RequestMapping("/")
@Controller
public class HomeController {

    /*@RequestMapping
    public void get(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping
    public String index() {
        return "home/index";
    }*/
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showHomepage(Model uiModel)
	{
		return "views/home/index";
	}
	
    @Transactional
    @RequestMapping(value = "test/store", method = RequestMethod.GET)
    public @ResponseBody String storetest(Model uiModel) {

        CompositePerfData cpd = new CompositePerfData();
        cpd.setWebappId(1234L);
        cpd.setExecTime(1000L);
        cpd.setInvokeTime(new Date());
        cpd.setMethodName("method");
        cpd.persist();

        return "Success!";
    }
    
    @RequestMapping(value = "test/xml", method = RequestMethod.GET)
    public String xmltest(Model uiModel) {

        //String test = 
        //return CompositePerfData.getCompositePerfDatabyWebapp("webapp").toString();

        return "yoyo";
    }
    
    @Transactional
    @RequestMapping(value = "test/xmlparse", method = RequestMethod.GET)
    public @ResponseBody String parsetest(Model uiModel) {
		
    	List <CompositePerfData> cpdList = parseXml("http://127.0.0.1:9090/perfvis/test/xml");
    	for (CompositePerfData cpd : cpdList)
    	{
    		cpd.setWebappId(1337L);
    		//CompositePerfData.storeCompositePerfData(cpd);
    		cpd.persist();
    	}
    	
    	return "Inserted into database!";
    	
    }
    
    public String doHttpGet(String address) {
    	HttpClient client = new HttpClient();
    	HttpMethod method = new GetMethod(address);
    	String response = "";
    	
    	try {
    		client.executeMethod(method);
    	 
    		if (method.getStatusCode() == HttpStatus.SC_OK) {
    			response = method.getResponseBodyAsString();
    			//System.out.println("Response = " + response);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		method.releaseConnection();
    	}
    	return response;
    }
    
    public List <CompositePerfData> parseXml(String input) {

    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = null;
    	Document doc = null;

    	try {

    		db = dbf.newDocumentBuilder();
    		doc = db.parse(input);

    	} catch (Exception e) {
    		e.printStackTrace();

    	}

    	doc.getDocumentElement().normalize();
    	//System.out.println("Root element " + doc.getDocumentElement().getNodeName());
    	NodeList nodeLst = doc.getElementsByTagName("result");
    	//System.out.println("Information of all employees");
    	List <CompositePerfData> cpdList = new ArrayList<CompositePerfData>();

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
