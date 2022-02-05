<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/bbNG" prefix="bbNG" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="outcomes" class="uk.ac.leedsbeckett.digles.b2unified.SandboxOutcomes" scope="request"/>

<c:choose>
  <c:when test="${outcomes.hasFailed()}">
      <p>${outcomes.getErrorMessage()}</p>
  </c:when>
  <c:otherwise>

<!-- 
     GOTCHA - SCRIPT elements are stripped out and executed by the 
     'module' loading javascript in the host page. This makes it very hard
     to debug.
-->
<script type="text/javascript">
  function sandbox_submitform()
  {
    var url = "${outcomes.endpointURL}";

    $('sandboxWait').show();
    $('sandboxError').hide();
    $('sandboxSuccess').hide();
    $('sandboxDetail').show();
    
    new Ajax.Request( url, 
      {
        method: 'post',
        parameters: '',
        onFailure: function( transport ) { $('sandboxWait').hide(); $('sandboxError').show(); },
        onSuccess: function( transport )
        {
          var obj = JSON.parse( transport.responseText );
          if ( obj.error )
            $('sandboxSuccess').innerHTML = obj.errormessage;
          else
            $('sandboxSuccess').innerHTML = "The sandbox module was created. You will find it on the modules tab.";
          $('sandboxWait').hide(); 
          $('sandboxSuccess').show();
        }
      }
            );
  }
</script>

<style>
  #sandboxSession { 
    width: 255px;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  #sandboxSession option {
    white-space: nowrap;
    width: 100%
  }
</style>

   <div id="sandboxDetail"
     style="display:none; position: absolute; box-shadow: 0px 0px 10px #b7b3b3; border-radius: 5px; width: 305px; border-width: 5px; border-style: solid; border-color: rgb(171, 74, 156); background-color: rgb(255, 255, 255); padding: 5px;z-index: 101;">
    <a onclick="$('sandboxDetail').hide();"('sandboxDetail').hide() title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">&lt; Back</a>
    <p id="sandboxWait" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        Please wait for processing to complete...</p>
    <p id="sandboxError" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        There was a technical problem trying to request the sandbox module from Blackboard Learn.</p>
    <p id="sandboxSuccess" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        Processing was completed and system administrators have been notified. You will find the module listed on the modules tab now.</p>
   </div>

<div class="bkt_channel_div">
  <div class="bkt_capability bkt_capability_${outcomes.getStyleColour()}">
    <br/><!-- SPACE -->
    <!-- INFO BUTTON -->
    <div class="bkt_info_button bkt_info_button_${outcomes.getStyleColour()}" id="more_info_sandbox" onclick="$('bkt_service_info_sandbox').hide(); $('bkt_further_help_sandbox').hide();$('bkt_further_info_sandbox').show();$('more_info_sandbox').hide();$('less_info_sandbox').show(); $('more_help_sandbox').show();$('less_help_sandbox').hide();" title="Information" style=""><span class="bkt_info_button_text">i</span></div>
    <!-- LESS INFO BUTTON -->
    <div class="bkt_info_button bkt_info_button_${outcomes.getStyleColour()}" id="less_info_sandbox" onclick="$('bkt_service_info_sandbox').show(); $('bkt_further_help_sandbox').hide(); $('bkt_further_info_sandbox').hide(); $('more_info_sandbox').show();$('less_info_sandbox').hide(); $('more_help_sandbox').show();$('less_help_sandbox').hide();" title="Close Information" style="display: none;"><span class="bkt_info_button_text">i</span></div>
    <br/><!-- SPACE -->
    <!-- HELP BUTTON -->
    <div class="bkt_help_button bkt_help_button_${outcomes.getStyleColour()}" id="more_help_sandbox" onclick="$('bkt_service_info_sandbox').hide();$('bkt_further_help_sandbox').show();$('more_help_sandbox').hide();$('less_help_sandbox').show();$('bkt_further_info_sandbox').hide();$('more_info_sandbox').show();$('less_info_sandbox').hide();" title="Help" style=""><span class="bkt_help_button_text">?</span></div>
    <!-- LESS HELP BUTTON -->
    <div class="bkt_help_button bkt_help_button_${outcomes.getStyleColour()}" id="less_help_sandbox" onclick="$('bkt_service_info_sandbox').show(); $('bkt_further_help_sandbox').hide(); $('more_help_sandbox').show(); $('less_help_sandbox').hide(); $('bkt_further_info_sandbox').hide(); $('more_info_sandbox').show(); $('less_info_sandbox').hide();" title="Close Help" style="display: none;"><span class="bkt_help_button_text">?</span></div>
  </div>

  <!-- The info section which shows when info is clicked -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_further_info_sandbox" style="display: none;">
    <span class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()} ">Information</span><br/><br/><!-- SPACES -->
    <p class="bkt_service_text">${outcomes.infoText}</p><br/>
    <a onclick="$('bkt_service_info_sandbox').show(); $('bkt_further_info_sandbox').hide(); $('more_info_sandbox').show();$('less_info_sandbox').hide();" title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">&lt; Back</a>
  </div>

  <!-- The help section which shows when help is clicked -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_further_help_sandbox" style="display: none;">
    <span class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()}">Help</span><br/><br/><!-- SPACES -->
    <p class="bkt_service_text ">${outcomes.helpText}</p><br/>
    <a onclick="$('bkt_service_info_sandbox').show(); $('bkt_further_help_sandbox').hide(); $('more_help_sandbox').show();$('less_help_sandbox').hide();" title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">&lt; Back</a>
  </div>

  <!-- The main body of the channel -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_service_info_sandbox"><span
      class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()}">${outcomes.title}</span>
    <br/> <br/>
    
    <c:choose>
      <c:when test="${outcomes.courseExistsAlready()}">
        <p>Your sandbox module with ID, ${outcomes.getCourseID()} already exists.</p>        
      </c:when>
      <c:otherwise>
        <p>You can create a module with ID, ${outcomes.getCourseID()}.</p>        
        <span class="bkt_action_button bkt_action_button_${outcomes.getStyleColour()}" 
          id="sandbox_Action" 
          onclick="sandbox_submitform()">${outcomes.buttonLabel}<i class="action_arrow_icon"></i></span>        
      </c:otherwise>
    </c:choose>
      

  </div>
</div>

  </c:otherwise>
</c:choose>
