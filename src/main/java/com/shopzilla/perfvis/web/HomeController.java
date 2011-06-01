package com.shopzilla.perfvis.web;

import java.util.Date;

import com.shopzilla.perfvis.data.CompositePerfData;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Transactional
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayHomepage(Model uiModel) {

        CompositePerfData cpd = new CompositePerfData();
        cpd.setWebappName("webapp");
        cpd.setExecTime(1000L);
        cpd.setInvokeTime(new Date());
        cpd.setMethodName("method");
        CompositePerfData.storeCompositePerfData(cpd);

        return "yoyo";
    }
    
    @RequestMapping(value = "/webapp", method = RequestMethod.GET)
    public @ResponseBody String getByWebapp(Model uiModel) {

        
        return CompositePerfData.getCompositePerfDatabyWebapp("webapp").toString();

        //return "yoyo";
    }
    
}
