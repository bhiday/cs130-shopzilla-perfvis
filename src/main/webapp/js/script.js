//window.onload=initWebappNames();

function initWebappNames() {
	ProcessXML("http://127.0.0.1:9090/perfvis/ajax/webapp", buildWebappNames);
}

var obj;

//function copied from http://code.google.com/edu/ajax/tutorials/ajax-tutorial.html

function ProcessXML(url, funcname) {
  // native  object

  if (window.XMLHttpRequest) {
    // obtain new object
    obj = new XMLHttpRequest();
    // set the callback function
    obj.onreadystatechange = funcname;
    // we will do a GET with the url; "true" for asynch
    obj.open("GET", url, true);
    // null for GET with native object
    obj.send(null);
  // IE/Windows ActiveX object
  } else if (window.ActiveXObject) {
    obj = new ActiveXObject("Microsoft.XMLHTTP");
    if (obj) {
      obj.onreadystatechange = funcname;
      obj.open("GET", url, true);
      // don't send null for ActiveX
      obj.send();
    }
  } else {
    alert("Your browser does not support AJAX");
  }
}

function buildWebappNames() {
    // 4 means the response has been returned and ready to be processed
    if (obj.readyState == 4) {
        // 200 means "OK"
        if (obj.status == 200) {
        	setOptions(obj.responseText, document.form1.webappList);
        	initMethodNames(document.form1.webappList.options[0].value);
        // anything else means a problem
        } else {
            alert("buildWebappNames: There was a problem in the returned data:\n");
        }
    }
}

function setOptions(data, element) {
	
	var selbox = element;
	//alert("fef");
	selbox.options.length = 0;
	//alert("yo");
	var lines = new Array();
	lines = data.split("\n");
	for (var i=0;i<lines.length - 1;i++)
	{
		var elems = new Array();
		elems = lines[i].split("|");
		selbox.options[selbox.options.length] = new Option(elems[1],elems[0]);
	}
}


function initMethodNames(webappId) {
	//alert("yo"+webappId);
	ProcessXML("http://127.0.0.1:9090/perfvis/ajax/method/" + webappId, buildMethodNames);
}	

function buildMethodNames() {
    // 4 means the response has been returned and ready to be processed
    if (obj.readyState == 4) {
        // 200 means "OK"
        if (obj.status == 200) {
        	setOptions(obj.responseText, document.form1.methodList);
        	prepareAggChart(document.form1.webappList.options[document.form1.webappList.selectedIndex].value);
        	//alert("yo");
        // anything else means a problem
        } else {
            alert("buildMethodNames: There was a problem in the returned data:\n");
        }
    }
}

function ProcessXMLPost(url, params, funcname) {
	  // native  object

	  if (window.XMLHttpRequest) {
	    // obtain new object
	    obj = new XMLHttpRequest();
	    // set the callback function
	    obj.onreadystatechange = funcname;
	    // we will do a GET with the url; "true" for asynch
	    obj.open("POST", url, true);
	    obj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	    obj.setRequestHeader("Content-length", params.length);
	    obj.setRequestHeader("Connection", "close");
	    // null for GET with native object
	    obj.send(params);
	  // IE/Windows ActiveX object
	  } else if (window.ActiveXObject) {
	    obj = new ActiveXObject("Microsoft.XMLHTTP");
	    if (obj) {
	      obj.onreadystatechange = funcname;
	      obj.open("POST", url, true);
	      obj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		  obj.setRequestHeader("Content-length", params.length);
		  obj.setRequestHeader("Connection", "close");
	      // don't send null for ActiveX
	      obj.send(params);
	    }
	  } else {
	    alert("Your browser does not support AJAX");
	  }
	}