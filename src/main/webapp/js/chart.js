google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(initWebappNames);

function prepareAggChart(webappId) {
	//alert(webappId);
	ProcessXML("http://127.0.0.1:9090/perfvis/ajax/stats/aggmethod/" + webappId,drawAggStatsChart);
}

function drawAggStatsChart() {
	
	// 4 means the response has been returned and ready to be processed
    if (obj.readyState == 4) {
        // 200 means "OK"
        if (obj.status == 200) {
        	//alert(obj.responseText);
        	
            var data = new google.visualization.DataTable();
            var lines = new Array();
        	lines = obj.responseText.split("\n"); 
            
            data.addColumn('string', 'Method Name');
            data.addColumn('number', 'Execution Time');
            data.addRows(lines.length - 1);
           
        	
        	for (var i=0;i<lines.length - 1;i++)
        	{
        		var elems = new Array();
        		elems = lines[i].split("|");
        		//selbox.options[selbox.options.length] = new Option(elems[1],elems[0]);
        		data.setValue(i, 0, elems[0]);
        		data.setValue(i, 1, parseFloat(elems[1]));
        		//alert(elems[1]);
        	}
        
         //alert("uuuo");   
  
    	var chart = new google.visualization.ColumnChart(document.getElementById('aggstats_div'));
    	chart.draw(data, {width: 640, height: 480, title: 'Aggregate Performance by Method',
                      hAxis: {title: 'Method', titleTextStyle: {color: 'red'}}
                         });
    	
    	//prepare method chart (1st time)
    	prepareMethodChart();
    	
        // anything else means a problem
        } else {
            alert("drawAggStatsChart: There was a problem in the returned data:\n");
        }
    }

}

function prepareMethodChart() {
	//alert(webappId);
	methodname = document.form1.methodList.options[document.form1.methodList.selectedIndex].value;
	methodname.replace(" ", "%20");
	webappId = document.form1.webappList.options[document.form1.webappList.selectedIndex].value;
	params = "webappId=" + webappId + "&methodName=" + methodname; 
	//alert(params);
	ProcessXMLPost("http://127.0.0.1:9090/perfvis/ajax/stats/method/", params ,drawMethodChart);
}



function drawMethodChart() {
	
	// 4 means the response has been returned and ready to be processed
    if (obj.readyState == 4) {
        // 200 means "OK"
        if (obj.status == 200) {
        	//alert(obj.responseText);
        	
            var data = new google.visualization.DataTable();
            var lines = new Array();
        	lines = obj.responseText.split("\n"); 
            
            data.addColumn('string', 'Invoke Time');
            data.addColumn('number', 'Execution Time');
            data.addRows(lines.length - 1);
           
        	
        	for (var i=0;i<lines.length - 1;i++)
        	{
        		var elems = new Array();
        		elems = lines[i].split("|");
        		//selbox.options[selbox.options.length] = new Option(elems[1],elems[0]);
        		data.setValue(i, 0, elems[0]);
        		data.setValue(i, 1, parseFloat(elems[1]));
        		//alert(elems[1]);
        	}
        
         //alert("uuuo");   
  
    	var chart = new google.visualization.LineChart(document.getElementById('methodstats_div'));
    	chart.draw(data, {width: 640, height: 480, title: 'Method Execution Time',
                      hAxis: {title: 'Timestamp', titleTextStyle: {color: 'red'}}
                         });
    	
    	
        // anything else means a problem
        } else {
            alert("drawMethodChart: There was a problem in the returned data:\n");
        }
    }

}
