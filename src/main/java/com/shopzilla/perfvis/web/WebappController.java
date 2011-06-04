package com.shopzilla.perfvis.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopzilla.perfvis.data.WebappData;

@RequestMapping("/webapp/")
@Controller
public class WebappController {

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String showWebappList(Model uiModel) {
		
		setupModel(uiModel, "", "", "");
		return "views/webapp/list";
	}
	
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String addWebapp(@RequestParam("hidden_webappId") Long webappId, @RequestParam("tb_webappName") String webappName, @RequestParam("tb_webappURI") String webappURI, Model uiModel) {
		
		if(webappId == null) {
			if (!webappName.equals("") && !webappURI.equals("")) {
			
				WebappData webappData = new WebappData();
				webappData.setWebappName(webappName);
				webappData.setWebappURI(webappURI);
				webappData.persist();
			}
		}
		else {
			WebappData webappData = WebappData.findWebappData(webappId);
			webappData.setWebappName(webappName);
			webappData.setWebappURI(webappURI);
			webappData.merge();
		}
		
		setupModel(uiModel, "", "", "");
		return "views/webapp/list";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String editWebapp(@RequestParam("id") Long id, Model uiModel) {
		
		WebappData webappData = WebappData.findWebappData(id);
		
		setupModel(uiModel, webappData.getId(), webappData.getWebappName(), webappData.getWebappURI());		
		return "views/webapp/list";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String deleteWebapp(@RequestParam("id") Long id, Model uiModel) {
		
		WebappData webappData = WebappData.findWebappData(id);
		
		webappData.remove();
		
		setupModel(uiModel, "", "", "");
		return "views/webapp/list";
	}
	
	private void setupModel(Model uiModel, Object webappId, Object webappName, Object webappURI) {
		uiModel.addAttribute("webappList", WebappData.findAllWebappDatas());
		uiModel.addAttribute("inp_webappId", webappId);
		uiModel.addAttribute("inp_webappName", webappName);
		uiModel.addAttribute("inp_webappURI", webappURI);
	}
	
}
