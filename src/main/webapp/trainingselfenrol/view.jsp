<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/bbNG" prefix="bbNG" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="outcomes" class="uk.ac.leedsbeckett.digles.b2unified.TrainingSelfEnrolOutcomes" scope="request"/>

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
  function trainingselfenrol_submitform()
  {
    var url = "${outcomes.endpointURL}";
    var cid = document.forms.trainingselfenrol_request_form.elements[0].value;
    if ( 'none' === cid )
    {
      alert( "Select a course before you click on the enrol button." );
      return;
    }
    
    $('trainingselfenrolWait').show();
    $('trainingselfenrolError').hide();
    $('trainingselfenrolSuccess').hide();
    $('trainingselfenrolDetail').show();
    
    new Ajax.Request( url, 
      {
        method: 'post',
        parameters: 'courseid=' + cid,
        onFailure: function( transport ) { $('trainingselfenrolWait').hide(); $('trainingselfenrolError').show(); },
        onSuccess: function( transport )
        {
          var obj = JSON.parse( transport.responseText );
          if ( obj.error )
            $('trainingselfenrolSuccess').innerHTML = obj.errormessage;
          else
            $('trainingselfenrolSuccess').innerHTML = "Enrolment was completed and system administrators have been notified.";
          $('trainingselfenrolWait').hide(); 
          $('trainingselfenrolSuccess').show();
        }
      }
            );
  }
</script>

<style>
  #trainingSession { 
    width: 255px;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  #trainingSession option {
    white-space: nowrap;
    width: 100%
  }
</style>

   <div id="trainingselfenrolDetail"
     style="display:none; position: absolute; box-shadow: 0px 0px 10px #b7b3b3; border-radius: 5px; width: 305px; border-width: 5px; border-style: solid; border-color: rgb(171, 74, 156); background-color: rgb(255, 255, 255); padding: 5px;z-index: 101;">
    <a onclick="$('trainingselfenrolDetail').hide();"('trainingselfenrolDetail').hide() title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">&lt; Back</a>
    <p id="trainingselfenrolWait" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        Please wait for enrolment to complete...</p>
    <p id="trainingselfenrolError" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        There was a technical problem trying to request enrolment from Blackboard Learn.</p>
    <p id="trainingselfenrolSuccess" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        Enrolment was completed and system administrators have been notified. You will find the module listed on the modules tab now.</p>
   </div>

<div class="bkt_channel_div">
  <div class="bkt_capability bkt_capability_${outcomes.getStyleColour()}">
    <br/><!-- SPACE -->
    <!-- INFO BUTTON -->
    <div class="bkt_info_button bkt_info_button_${outcomes.getStyleColour()}" id="more_info_trainingselfenrol" onclick="$('bkt_service_info_trainingselfenrol').hide(); $('bkt_further_help_trainingselfenrol').hide();$('bkt_further_info_trainingselfenrol').show();$('more_info_trainingselfenrol').hide();$('less_info_trainingselfenrol').show(); $('more_help_trainingselfenrol').show();$('less_help_trainingselfenrol').hide();" title="Information" style=""><span class="bkt_info_button_text">i</span></div>
    <!-- LESS INFO BUTTON -->
    <div class="bkt_info_button bkt_info_button_${outcomes.getStyleColour()}" id="less_info_trainingselfenrol" onclick="$('bkt_service_info_trainingselfenrol').show(); $('bkt_further_help_trainingselfenrol').hide(); $('bkt_further_info_trainingselfenrol').hide(); $('more_info_trainingselfenrol').show();$('less_info_trainingselfenrol').hide(); $('more_help_trainingselfenrol').show();$('less_help_trainingselfenrol').hide();" title="Close Information" style="display: none;"><span class="bkt_info_button_text">i</span></div>
    <br/><!-- SPACE -->
    <!-- HELP BUTTON -->
    <div class="bkt_help_button bkt_help_button_${outcomes.getStyleColour()}" id="more_help_trainingselfenrol" onclick="$('bkt_service_info_trainingselfenrol').hide();$('bkt_further_help_trainingselfenrol').show();$('more_help_trainingselfenrol').hide();$('less_help_trainingselfenrol').show();$('bkt_further_info_trainingselfenrol').hide();$('more_info_trainingselfenrol').show();$('less_info_trainingselfenrol').hide();" title="Help" style=""><span class="bkt_help_button_text">?</span></div>
    <!-- LESS HELP BUTTON -->
    <div class="bkt_help_button bkt_help_button_${outcomes.getStyleColour()}" id="less_help_trainingselfenrol" onclick="$('bkt_service_info_trainingselfenrol').show(); $('bkt_further_help_trainingselfenrol').hide(); $('more_help_trainingselfenrol').show(); $('less_help_trainingselfenrol').hide(); $('bkt_further_info_trainingselfenrol').hide(); $('more_info_trainingselfenrol').show(); $('less_info_trainingselfenrol').hide();" title="Close Help" style="display: none;"><span class="bkt_help_button_text">?</span></div>
  </div>

  <!-- The info section which shows when info is clicked -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_further_info_trainingselfenrol" style="display: none;">
    <span class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()} ">Information</span><br/><br/><!-- SPACES -->
    <p class="bkt_service_text">${outcomes.infoText}</p><br/>
    <a onclick="$('bkt_service_info_trainingselfenrol').show(); $('bkt_further_info_trainingselfenrol').hide(); $('more_info_trainingselfenrol').show();$('less_info_trainingselfenrol').hide();" title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">< Back</a>
  </div>

  <!-- The help section which shows when help is clicked -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_further_help_trainingselfenrol" style="display: none;">
    <span class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()}">Help</span><br/><br/><!-- SPACES -->
    <p class="bkt_service_text ">${outcomes.helpText}</p><br/>
    <a onclick="$('bkt_service_info_trainingselfenrol').show(); $('bkt_further_help_trainingselfenrol').hide(); $('more_help_trainingselfenrol').show();$('less_help_trainingselfenrol').hide();" title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">< Back</a>
  </div>

  <!-- The main body of the channel -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_service_info_trainingselfenrol"><span
      class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()}">${outcomes.title}</span>
    <br/> <br/>
    
    <form name="trainingselfenrol_request_form" action="view.jsp" method="POST">
      <select name="trainingSession" id="trainingSession" size="1"
              style="font-size: 16px; color: #666; border: medium #ee312a solid; margin-top: 3px;">
        <c:choose>
          <c:when test="${outcomes.noCoursesFound()}">
            <option value="none" disabled="disabled" selected="true">No Sessions Available</option>        
          </c:when>
          <c:otherwise>
            <option value="none" disabled="disabled" selected="true">No Session Selected</option>
            <c:forEach begin="0" end="${outcomes.courseCount - 1}" var="n">
              <option value="${outcomes.getCourseID(n)}" ${outcomes.getCourseDisabledOption(n)}>${outcomes.getCourseTitle(n)}</option>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </select>
    </form>
      
    <span class="bkt_action_button bkt_action_button_${outcomes.getStyleColour()}" 
          id="trainingselfenrol_Action" 
          onclick="trainingselfenrol_submitform()">${outcomes.buttonLabel}<i class="action_arrow_icon"></i></span>        

  </div>
</div>

  </c:otherwise>
</c:choose>
