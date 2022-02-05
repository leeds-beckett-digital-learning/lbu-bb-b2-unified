<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/bbNG" prefix="bbNG" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="outcomes" class="uk.ac.leedsbeckett.digles.b2unified.SelfEnrolOutcomes" scope="request"/>

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
  function selfenrol_submitform()
  {
    var url = "${outcomes.searchEndpointURL}";
    var code = document.forms.selfenrol_request_form.elements[0].value;
    if ( code === null || code.trim().length === 0 )
    {
      alert( "Enter a value in the edit box first." );
      return;
    }
    
    $('selfenrolWait').show();
    $('selfenrolError').hide();
    $('selfenrolSuccess').hide();
    $('selfenrolDetail').show();
    $('selfenrolselectionlist').innerHTML = "";
    $('selfenrolList').hide();
    
    new Ajax.Request( url, 
      {
        method: 'post',
        parameters: 'code=' + code,
        onFailure: function( transport ) { $('selfenrolWait').hide(); $('selfenrolError').show(); },
        onSuccess: function( transport )
        {
          $('selfenrolWait').hide(); 
          var obj = JSON.parse( transport.responseText );
          if ( obj.error )
          {
            $('selfenrolSuccess').innerHTML = obj.errormessage;
            $('selfenrolSuccess').show();
          }
          else
          {
            if ( obj.titles.length === 0 )
            {
              $('selfenrolSuccess').innerHTML = "Nothing found matching your search term.";
              $('selfenrolSuccess').show();
            }
            else
            {
              var str="\n<option value=\"none\">Select from this list.</option>\n";
              for ( var i=0; i<obj.courseids.length; i++ )
                str += "<option value=\"" + obj.courseids[i] + "\">" + obj.titles[i] + "</option>\n";
              $('selfenrolselectionlist').innerHTML = str;
              $('selfenrolreasonlist').selectedIndex = 0;
              $('selfenrolList').show();
            }
          }
        }
      }
            );
  }

  function selfenrol_submitselection()
  {
    var sels = $( 'selfenrolselectionlist' );
    var ns   = sels.selectedIndex;
    if ( ns === 0 )
    {
      alert( "Select a course in the drop down list." );
      return;
    }
    var selr = $( 'selfenrolreasonlist' );
    var nr   = selr.selectedIndex;
    if ( nr === 0 )
    {
      alert( "Select a reason for access in the drop down list." );
      return;
    }

    $('selfenrolDetail').show();
    $('selfenrolWait').show();
    $('selfenrolError').hide();
    $('selfenrolSuccess').hide();
    $('selfenrolList').hide();

    var url = "${outcomes.enrolEndpointURL}";
    new Ajax.Request( url, 
      {
        method: 'post',
        parameters: 'courseid=' + sels[ns].value + '&reason=' + selr[nr].value,
        onFailure: function( transport ) { $('selfenrolError').show(); },
        onSuccess: function( transport )
        {
          var obj = JSON.parse( transport.responseText );
          if ( obj.error )
          {
            // successful call to server but returned
            // error message:
            $('selfenrolSuccess').innerHTML = obj.errormessage;
            $('selfenrolSuccess').show();
          }
          else
          {
            $('selfenrolSuccess').innerHTML = obj.successmessage;
            $('selfenrolSuccess').show();
          }
        }
      }
            );
  }
</script>



   <div id="selfenrolDetail"
     style="display:none; position: absolute; box-shadow: 0px 0px 10px #b7b3b3; border-radius: 5px; max-width: 80em; border-width: 5px; border-style: solid; border-color: rgb(171, 74, 156); background-color: rgb(255, 255, 255); padding: 5px;z-index: 101;">
    <a onclick="$('selfenrolDetail').hide();"('selfenrolDetail').hide() title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">&lt; Back</a>
    <p id="selfenrolWait" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        Please wait for enrolment to complete...</p>
    <p id="selfenrolError" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        There was a technical problem trying to request enrolment from Blackboard Learn.</p>
    <p id="selfenrolSuccess" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px"></p>
    <div id="selfenrolList" style="display:none; margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
      <p>Select from the list below.</p>
      <div>
        <select id="selfenrolselectionlist" style="font-family: AvenyTRegular; font-size: 20px; ">
          
        </select>
      </div>
      <p>Please select the reason for requesting access to the module.</p>
      <select id="selfenrolreasonlist" style="font-family: AvenyTRegular; font-size: 20px; ">
        <option value="none">Select from this list.</option>
        <option value="coursedirector">I am the course director responsible.</option>
        <option value="moduleleader">I am the relevant module leader.</option>
        <option value="admin">I am the course administrator responsible.</option>
        <option value="moduleteacher">I am teaching these students.</option>
        <option value="sysadmin">I am a Digital Learning Service system administrator.</option>
        <option value="directorpermit">The relevant course director has given me permission to self enrol.</option>
        <option value="leaderpermit">The relevant module leader has given me permission to self enrol.</option>
      </select>
      <p>
        <span class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}" 
          id="selfenrol_Action" 
          onclick="selfenrol_submitselection()">Enrol</span>            
      </p>
    </div>
   </div>

<div class="bkt_channel_div">
  <div class="bkt_capability bkt_capability_${outcomes.getStyleColour()}">
    <br/><!-- SPACE -->
    <!-- INFO BUTTON -->
    <div class="bkt_info_button bkt_info_button_${outcomes.getStyleColour()}" id="more_info_selfenrol" onclick="$('bkt_service_info_selfenrol').hide(); $('bkt_further_help_selfenrol').hide();$('bkt_further_info_selfenrol').show();$('more_info_selfenrol').hide();$('less_info_selfenrol').show(); $('more_help_selfenrol').show();$('less_help_selfenrol').hide();" title="Information" style=""><span class="bkt_info_button_text">i</span></div>
    <!-- LESS INFO BUTTON -->
    <div class="bkt_info_button bkt_info_button_${outcomes.getStyleColour()}" id="less_info_selfenrol" onclick="$('bkt_service_info_selfenrol').show(); $('bkt_further_help_selfenrol').hide(); $('bkt_further_info_selfenrol').hide(); $('more_info_selfenrol').show();$('less_info_selfenrol').hide(); $('more_help_selfenrol').show();$('less_help_selfenrol').hide();" title="Close Information" style="display: none;"><span class="bkt_info_button_text">i</span></div>
    <br/><!-- SPACE -->
    <!-- HELP BUTTON -->
    <div class="bkt_help_button bkt_help_button_${outcomes.getStyleColour()}" id="more_help_selfenrol" onclick="$('bkt_service_info_selfenrol').hide();$('bkt_further_help_selfenrol').show();$('more_help_selfenrol').hide();$('less_help_selfenrol').show();$('bkt_further_info_selfenrol').hide();$('more_info_selfenrol').show();$('less_info_selfenrol').hide();" title="Help" style=""><span class="bkt_help_button_text">?</span></div>
    <!-- LESS HELP BUTTON -->
    <div class="bkt_help_button bkt_help_button_${outcomes.getStyleColour()}" id="less_help_selfenrol" onclick="$('bkt_service_info_selfenrol').show(); $('bkt_further_help_selfenrol').hide(); $('more_help_selfenrol').show(); $('less_help_selfenrol').hide(); $('bkt_further_info_selfenrol').hide(); $('more_info_selfenrol').show(); $('less_info_selfenrol').hide();" title="Close Help" style="display: none;"><span class="bkt_help_button_text">?</span></div>
  </div>

  <!-- The info section which shows when info is clicked -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_further_info_selfenrol" style="display: none;">
    <span class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()} ">Information</span><br/><br/><!-- SPACES -->
    <p class="bkt_service_text">${outcomes.infoText}</p><br/>
    <a onclick="$('bkt_service_info_selfenrol').show(); $('bkt_further_info_selfenrol').hide(); $('more_info_selfenrol').show();$('less_info_selfenrol').hide();" title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">< Back</a>
  </div>

  <!-- The help section which shows when help is clicked -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_further_help_selfenrol" style="display: none;">
    <span class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()}">Help</span><br/><br/><!-- SPACES -->
    <p class="bkt_service_text ">${outcomes.helpText}</p><br/>
    <a onclick="$('bkt_service_info_selfenrol').show(); $('bkt_further_help_selfenrol').hide(); $('more_help_selfenrol').show();$('less_help_selfenrol').hide();" title="Back" class="bkt_back_button bkt_back_button_${outcomes.getStyleColour()}">< Back</a>
  </div>

  <!-- The main body of the channel -->
  <div class="bkt_service_info bkt_service_info_${outcomes.getStyleColour()}" id="bkt_service_info_selfenrol"><span
      class="bkt_service_name bkt_service_name_${outcomes.getStyleColour()}">${outcomes.title}</span>
    <br/> <br/>
    
    <form name="selfenrol_request_form" action="javascript:void(0);">
      <c:choose>
        <c:when test="${outcomes.isCoursemode()}">
          <p><label>Enter Course Reference Number, e.g. 12345 <input name="course_org_code"/></label></p>
        </c:when>
        <c:otherwise>
          <p><label>Enter Award Code, e.g. BSLTH <input name="course_org_code"/></label></p>        
        </c:otherwise>
      </c:choose>
    </form>
      
    <span class="bkt_action_button bkt_action_button_${outcomes.getStyleColour()}" 
          id="selfenrol_Action" 
          onclick="selfenrol_submitform()">${outcomes.buttonLabel}<i class="action_arrow_icon"></i></span>        

  </div>
</div>

  </c:otherwise>
</c:choose>
