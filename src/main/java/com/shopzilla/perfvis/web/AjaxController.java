package com.shopzilla.perfvis.web;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopzilla.perfvis.data.CompositePerfData;
import com.shopzilla.perfvis.data.WebappData;

@RequestMapping("/ajax/")
@Controller
public class AjaxController {

	@RequestMapping(value = "webapp", method = RequestMethod.GET)
	public @ResponseBody String showWebapp(Model uiModel) {
		List <WebappData> webappList = WebappData.findAllWebappDatas();
		StringBuffer buf = new StringBuffer();
		for (WebappData webappData : webappList)
		{
			buf.append(webappData.getId()).append("|").append(webappData.getWebappName()).append("\n");
		}
		return buf.toString();
	}
	
	@RequestMapping(value = "method/{webappId}", method = RequestMethod.GET)
	public @ResponseBody String showMethod(@PathVariable("webappId") Long webappId, Model uiModel) {
		List <CompositePerfData> compositePerfDataList = CompositePerfData.getCompositePerfDataUniqueMethods(webappId);
		StringBuffer buf = new StringBuffer();
		for (CompositePerfData compositePerfData : compositePerfDataList)
		{
			buf.append(compositePerfData.getMethodName()).append("|").append(compositePerfData.getMethodName()).append("\n");
		}
		return buf.toString();
	}
	
    @RequestMapping(value = "stats/aggmethod/{webappId}", method = RequestMethod.GET)
	public @ResponseBody String showAvg(@PathVariable("webappId") Long webappId, Model uiModel) {
    	
    	StringBuffer buf = new StringBuffer();
    	List <Object[]> objList = CompositePerfData.getCompositePerfDataMeanTimes(webappId);
    	for (Object[] obj : objList) {	
			buf.append(obj[0]).append("|").append(obj[1]).append("\n");
    	}
    	
		return buf.toString();
	}

    @RequestMapping(value = "stats/method", method = RequestMethod.POST)
	public @ResponseBody String showMethodStats(@RequestParam("webappId") Long webappId, @RequestParam("methodName") String methodName, Model uiModel) {
    	
    	methodName.replace("%20", " ");
    	
    	StringBuffer buf = new StringBuffer();
    	List <CompositePerfData> cpdList = CompositePerfData.getCompositePerfDataMethodExecTimes(webappId, methodName);
    	for (CompositePerfData cpd : cpdList) {
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			buf.append(formatter.format(cpd.getInvokeTime())).append("|").append(cpd.getExecTime()).append("\n");
    	}
    	
		return buf.toString();
	}

    
}
