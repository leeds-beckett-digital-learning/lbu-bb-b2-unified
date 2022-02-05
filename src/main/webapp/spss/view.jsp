<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/bbNG" prefix="bbNG" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="outcomes" class="uk.ac.leedsbeckett.digles.b2unified.SpssOutcomes" scope="request"/>


<!-- 
     GOTCHA - SCRIPT elements are stripped out and executed by the 
     'module' loading javascript in the host page. This makes it very hard
     to debug.
-->
<script type="text/javascript">
  function spss_showform()
  {
    // these variables are available for the insert string template
    let name       = "${outcomes.name}";
    let id         = "${outcomes.userId}";
    let username   = "${outcomes.userName}";
    let email      = "${outcomes.userEmail}";
    let coursecode = "${outcomes.userCourseCode}";
    let course     = "${outcomes.userCourse}";
    let school     = "${outcomes.userSchool}";
    let level      = "${outcomes.userLevel}";
    let role       = "${outcomes.portalRoleId}";
    
    let fullterms  = ${outcomes.termsAsJavascriptLiteral};
    let termslines = fullterms.split( /[\r\n]+/ );
    let terms      = "";
    for ( var i=0; i<termslines.length; i++ )
      terms += '<p style="margin: 0 0 0 0;"><code>' + termslines[i] + "</code></p>\n";
    let insert     = ${outcomes.formTextAsJavascriptLiteral};
    
    $('spssFormContent').innerHTML = insert;
    $('spssForm').show();
  }
  
  function spss_submitform()
  {
    var url = "${outcomes.endpointURL}";
    
    var agreeinput   = $('spss_field_agree').checked;
    var packageinput = $('spss_field_package').value;
    var nopackage = packageinput !== 'spsswin' && packageinput !== 'spssmac' && packageinput !== 'spssamoswin';
        
    if ( !agreeinput && nopackage )
    {
      alert( "You cannot request the software unless you select a package to download and agree to the stated conditions.");
      return;
    }
    if ( !agreeinput )
    {
      alert( "You cannot request the software unless you agree to the stated conditions.");
      return;
    }
    if ( nopackage )
    {
      alert( "You cannot request the software unless you select a package to download.");
      return;
    }
    
    $('spssForm').hide();
    $('spssDetail').show();
    $('spssWait').show();
    
    new Ajax.Request( url, 
      {
        method: 'post',
        parameters: 'package=' + packageinput,
        onFailure: function( transport ) { $('spssWait').hide(); $('spssSuccess').hide(); $('spssError').show(); },
        onSuccess: function( transport )
        {
          var obj = JSON.parse( transport.responseText );
          if ( obj.error )
            $('spssSuccess').innerHTML = "<p>" + obj.errormessage + "</p>";
          else
            $('spssSuccess').innerHTML = 
                  "<p>Here is a link to the file:</p>\n" + 
                  "<p><strong><a target=\"_blank\" href=\"" +
                  obj.downloadurl +
                  "\">Download</a></strong></p>\n" +
                  "<p>Your request has been logged and an email has been sent to " +
                  "you with information you need to install the software including " +
                  "your license code.</p>\n";
          $('spssWait').hide(); 
          $('spssError').hide();
          $('spssSuccess').show();
        }
      }
            );
  }
</script>


   <div id="spssForm"
     style="display:none; position: fixed; background-color: rgba(64, 64, 64, 0.8 ); top: 0; left: 0; width: 100%; height: 100%; padding: 0 0 0 0; margin: 0 0 0 0; z-index: 100000000; box-shadow: 0px 0px 10px #b7b3b3; border-radius: 5px; border-style: solid; border-color: rgb(171, 74, 156);">
     <div style="padding: 1% 1% 1% 1%; margin: 5% 5% 5% 5%; background-color: white; height: 80%;">
       <div style="height: 8%; padding: 1% 2em 1% 2em;">
         <h1>${outcomes.title}</h1>
       </div>
       <div id="spssFormContent" class="bkt_service_text" style="height: 78%; overflow-y: scroll; padding: 1% 4em 1% 4em;"></div>
       <div style="height: 7%; padding: 1% 2em 1% 2em;">
    <a onclick="$('spssForm').hide();" title="Cancel" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">&lt; Cancel</a>
    <a onclick="spss_submitform();" title="Submit" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">Submit &gt;</a>
       </div>
     </div>
   </div>
    
   <div id="spssDetail"
     style="display:none; position: absolute; box-shadow: 0px 0px 10px #b7b3b3; border-radius: 5px; max-width: 30em; border-width: 5px; border-style: solid; border-color: rgb(171, 74, 156); background-color: rgb(255, 255, 255); padding: 5px;z-index: 101;">
    <a onclick="$('spssDetail').hide();" title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">&lt; Back</a>
    
    <p id="spssWait" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        Please wait for enrolment to complete...</p>
    <p id="spssError" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        There was a technical problem trying to request enrolment from Blackboard Learn.</p>
    <div id="spssSuccess" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        Enrolment was completed and system administrators have been notified. You will find the module listed on the modules tab now.</div>
   </div>

<div class="bkt_channel_div">
  <div class="bkt_capability bkt_capability_${outcomes.getStyleColour()}">
    <br/><!-- SPACE -->
    <!-- INFO BUTTON -->
    <div class="bkt_info_button bkt_info_button_${outcomes.getStyleColour()}" id="more_info_spss" onclick="$('bkt_service_info_spss').hide(); $('bkt_further_help_spss').hide();$('bkt_further_info_spss').show();$('more_info_spss').hide();$('less_info_spss').show(); $('more_help_spss').show();$('less_help_spss').hide();" title="Information" style=""><span class="bkt_info_button_text">i</span></div>
    <!-- LESS INFO BUTTON -->
    <div class="bkt_info_button bkt_info_button_${outcomes.getStyleColour()}" id="less_info_spss" onclick="$('bkt_service_info_spss').show(); $('bkt_further_help_spss').hide(); $('bkt_further_info_spss').hide(); $('more_info_spss').show();$('less_info_spss').hide(); $('more_help_spss').show();$('less_help_spss').hide();" title="Close Information" style="display: none;"><span class="bkt_info_button_text">i</span></div>
    <br/><!-- SPACE -->
    <!-- HELP BUTTON -->
    <div class="bkt_help_button bkt_help_button_${outcomes.getStyleColour()}" id="more_help_spss" onclick="$('bkt_service_info_spss').hide();$('bkt_further_help_spss').show();$('more_help_spss').hide();$('less_help_spss').show();$('bkt_further_info_spss').hide();$('more_info_spss').show();$('less_info_spss').hide();" title="Help" style=""><span class="bkt_help_button_text">?</span></div>
    <!-- LESS HELP BUTTON -->
    <div class="bkt_help_button bkt_help_button_${outcomes.getStyleColour()}" id="less_help_spss" onclick="$('bkt_service_info_spss').show(); $('bkt_further_help_spss').hide(); $('more_help_spss').show(); $('less_help_spss').hide(); $('bkt_further_info_spss').hide(); $('more_info_spss').show(); $('less_info_spss').hide();" title="Close Help" style="display: none;"><span class="bkt_help_button_text">?</span></div>
  </div>

  <!-- The info section which shows when info is clicked -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_further_info_spss" style="display: none;">
    <span class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()} ">Information</span><br/><br/><!-- SPACES -->
    <p class="bkt_service_text">${outcomes.infoText}</p><br/>
    <a onclick="$('bkt_service_info_spss').show(); $('bkt_further_info_spss').hide(); $('more_info_spss').show();$('less_info_spss').hide();" title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">< Back</a>
  </div>

  <!-- The help section which shows when help is clicked -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_further_help_spss" style="display: none;">
    <span class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()}">Help</span><br/><br/><!-- SPACES -->
    <p class="bkt_service_text ">${outcomes.helpText}</p><br/>
    <a onclick="$('bkt_service_info_spss').show(); $('bkt_further_help_spss').hide(); $('more_help_spss').show();$('less_help_spss').hide();" title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">< Back</a>
  </div>

    <!-- The main body of the channel -->
    <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_service_info_spss"><span
        class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()}">${outcomes.title}</span>
      <br/> <br/>

      <!-- Button or error message -->
<c:choose>
  <c:when test="${outcomes.hasFailed()}">
    <p>${outcomes.getErrorMessage()}</p>
  </c:when>
  <c:otherwise>
      <span class="bkt_action_button bkt_action_button_${outcomes.getStyleColour()}" 
            id="spss_Action" 
            onclick="spss_showform()">${outcomes.buttonLabel}<i class="action_arrow_icon"></i></span>
  </c:otherwise>
</c:choose>
    
    </div>
    
</div>

