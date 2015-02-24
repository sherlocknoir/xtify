(function(){
  
  /*---------------
        Variables
        ----------------*/
  
  var app_key = "";
  var api_key = "";
  var xid = "";
  var subject="";
  var rich_subject = "";
  var rich_message = "";
  var message = "";


      
        var your_vmr = document.getElementById('vmr');
        your_vmr.addEventListener('click', f_your_vmr, false);
		
        function f_your_vmr(event)
        {
          if ( event.preventDefault ) event.preventDefault();
          event.returnValue = false;
          vmr();
                
        }
        
        var your_select = document.getElementById('sel1');
        your_select.addEventListener('change', f_your_select, false);
		
        function f_your_select(event)
        {
          if ( event.preventDefault ) event.preventDefault();
          event.returnValue = false;
          json_payload();       
        }

        
        


if (typeof String.prototype.startsWith != 'function') {
  // see below for better implementation!
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}
 

function json_payload(){
	
	var e = document.getElementById("sel1");
	var strUser = e.options[e.selectedIndex].value;
	console.log("TOTO" + strUser);
	
	var editorText = document.getElementById('rich_message');
	if(strUser=="Wire Transfer Validation")
		{
			editorText.value = "{'com.xtify.notification.STYLE':'LinkedIN','com.xtify.notification.ACCOUNT':'Do you accept the money transfer?'}";
			document.getElementById('subject').value = "Mister X";
		}
	if(strUser=="Meeting Confirmation")
	{
		editorText.value = "{'com.xtify.notification.STYLE':'RDV','com.xtify.notification.ACCOUNT':'We have a meeting tomorrow at 11am'}";
		document.getElementById('subject').value = "Mister X";
	}
	if(strUser=="Call your advisor")
	{
		editorText.value = "{'com.xtify.notification.STYLE':'Call','com.xtify.notification.ACCOUNT':'Call me or Send me a SMS: ','com.xtify.notification.CALLNUMBER':'06XXXXXXXX'}";
		document.getElementById('subject').value = "Mister X";
	}
	if(strUser=="Chat Head Watson")
	{
		editorText.value = "{'com.xtify.notification.STYLE':'ChatHead','com.xtify.notification.IMAGE':'watson'}";
		document.getElementById('subject').value = "Watson";
	}
	if(strUser=="Chat Head Bluemix")
	{
		editorText.value = "{'com.xtify.notification.STYLE':'ChatHead','com.xtify.notification.IMAGE':'bluemix'}";
		document.getElementById('subject').value = "Bluemix";
	}
	if(strUser=="Bluemix Ad (Large Text)")
	{
		editorText.value = "{'com.xtify.notification.STYLE':'BIG_TEXT','com.xtify.notification.BIG_TEXT':'IBM Bluemix is a cloud platform as a service (PaaS) developed by IBM. It supports several programming languages and services as well as integrated DevOps to build, run, deploy and manage applications on the cloud. Bluemix is based on Cloud Foundry open technology and runs on SoftLayer infrastructure.','com.xtify.notification.LARGE_ICON':'http:\/\/www.ostiasolutions.com\/images\/bluemix2.png'}";
		document.getElementById('subject').value = "Bluemix Announcement";
	}
	if(strUser=="Bluemix Ad (Large Image)")
	{
		editorText.value = "{'com.xtify.notification.STYLE':'BIG_PICTURE','com.xtify.notification.BIG_PICTURE':'http:\/\/cdn.evbuc.com\/images\/5273211\/19365402509\/1\/logo.png','com.xtify.notification.LARGE_ICON':'http:\/\/www.ostiasolutions.com\/images\/bluemix2.png'}";
		document.getElementById('subject').value = "Bluemix: Docker Support";
	}
}

function vmr() {
  console.log("Click Button");
  subject = document.getElementById('subject').value;
  //message = document.getElementById('message').value;
  xid = document.getElementById('xid').value;
  var editorText = document.getElementById('rich_message').value;
  
  console.log("CK Editor Basic : " + editorText);
  //editorText = editorText.replace("&#39;","'");
  //editorText = editorText.replace(/(\r\n|\n|\r)/gm," ");
  //console.log("After replacing" + editorText);

  app_key = "e9356276-8d91-4dac-8ec6-85f16d0961b7";
  api_key = "9a4db126-6ae4-4f7b-9dc4-4db3e87b9993";
  //xid = "54cfaa371fde007c8073e767";
	   
    message.innerHTML="";
	var json_message="{\"apiKey\":\""+ api_key + "\"," +
          "\"appKey\":\"" + app_key + "\"," + 
          "\"xids\": [\"" + xid + "\"]," + 
          " \"content\": {"+
		  " \"subject\": \"" + subject + "\"," +
          "\"message\": \"" + message + "\"," +
          "\"payload\": \""+ editorText +"\"}}"
          console.log(json_message);
	
	
		url="http://api.xtify.com/2.0/push";
 
        var xhr = new XMLHttpRequest();
        xhr.open('POST',url);
        xhr.setRequestHeader("Content-Type","application/json");
        xhr.send(json_message);
  
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
        if (xhr.status === 200) {
		} else 
         {
           }
    }
};
}

  })();