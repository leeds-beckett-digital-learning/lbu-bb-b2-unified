<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib uri="/bbNG" prefix="bbNG" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="outcomes" class="uk.ac.leedsbeckett.digles.b2unified.SandboxOutcomes" scope="request"/>
<c:set var="id" scope="request" value="${outcomes.getId()}"/>  
<c:set var="csshref" scope="request" value="${outcomes.getStyleSheetHref()}"/>  

<html>
  <head>
    <title>HTML Fragment for Insertion</title>
  </head>
  <body>
    
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
  function ${id}submitform()
  {
    var url = "${outcomes.endpointURL}";

    $('${id}dialogboxwait').show();
    $('${id}dialogboxerror').hide();
    $('${id}dialogboxsuccess').hide();
    $('${id}dialogbox').show();
    
    new Ajax.Request( url, 
      {
        method: 'post',
        parameters: '',
        onFailure: function( transport ) { $('${id}dialogboxwait').hide(); $('${id}dialogboxerror').show(); },
        onSuccess: function( transport )
        {
          var obj = JSON.parse( transport.responseText );
          $('sandboxWait').hide(); 
          if ( obj.error )
            $('${id}dialogboxerror').show();
          else
            $('${id}dialogboxsuccess').show();
        }
      }
            );
  }

  function ${id}hidehelpinfo()
  {
    $(${outcomes.toIdA("moreinfobutton")}).show();
    $(${outcomes.toIdA("lessinfobutton")}).hide();
    $(${outcomes.toIdA("morehelpbutton")}).show();
    $(${outcomes.toIdA("lesshelpbutton")}).hide();

    $(${outcomes.toIdA("infopanel")}).hide();
    $(${outcomes.toIdA("helppanel")}).hide();
    $(${outcomes.toIdA("mainpanel")}).show();
  }
  
  function ${id}showhelp()
  {
    $(${outcomes.toIdA("moreinfobutton")}).show();
    $(${outcomes.toIdA("lessinfobutton")}).hide();
    $(${outcomes.toIdA("morehelpbutton")}).hide();
    $(${outcomes.toIdA("lesshelpbutton")}).show();

    $(${outcomes.toIdA("infopanel")}).hide();
    $(${outcomes.toIdA("helppanel")}).show();
    $(${outcomes.toIdA("mainpanel")}).hide();
  }
  
  function ${id}showinfo()
  {
    $(${outcomes.toIdA("moreinfobutton")}).hide();
    $(${outcomes.toIdA("lessinfobutton")}).show();
    $(${outcomes.toIdA("morehelpbutton")}).show();
    $(${outcomes.toIdA("lesshelpbutton")}).hide();

    $(${outcomes.toIdA("infopanel")}).show();
    $(${outcomes.toIdA("helppanel")}).hide();
    $(${outcomes.toIdA("mainpanel")}).hide();
  }

  function ${id}addstyletohead()
  {
    var link = $('lbu_unified_bb_style');
    if ( link === null )
    {
      link      = document.createElement( "link" );
      link.id   = 'lbu_unified_bb_style';
      link.rel  = 'stylesheet';
      link.type = 'text/css';
      link.href = ${csshref};
      document.head.appendChild( link );
    }
    
    var main = $(${outcomes.toIdA("main")});
    if ( !main ) return;
    var container = main.parentElement.parentElement;
    container.style.padding = '0px 0px 0px 0px';    
  }
  
  ${id}addstyletohead();

</script>

   <div ${outcomes.toIdA("dialogbox")} class="lbu_unified_bb_dialogbox" style="display:none; ">
     
    <a onclick="$( ${outcomes.toId('dialogbox')} ).hide();" 
       title="Back" 
       class="lbu_unified_bb_back_button"
       style="background-color: ${outcomes.getStyleColour('bg',0)}; color: ${outcomes.getStyleColour('fg',0)}">&lt; Back</a>
    <p ${outcomes.toIdA("dialogboxwait")}    
      class="lbu_unified_bb_dialogcontent" 
      style="display: none; background-color: ${outcomes.getStyleColour('bg',3)};">
        Please wait for enrolment to complete...</p>
    <p ${outcomes.toIdA("dialogboxerror")}   
      class="lbu_unified_bb_dialogcontent" 
      style="display: none; background-color: ${outcomes.getStyleColour('bg',3)};">
        There was a technical problem trying to request the sandbox module from Blackboard Learn.</p>
    <p ${outcomes.toIdA("dialogboxsuccess")} 
      class="lbu_unified_bb_dialogcontent" 
      style="display: none; background-color: ${outcomes.getStyleColour('bg',3)};">
        Processing was completed and system administrators have been notified. You will find the module listed on the modules tab now.
    </p>
   </div>


<div ${outcomes.toIdA("main")}>
  <div class="lbu_unified_bb_buttonpanel" style="background-color: ${outcomes.getStyleColour('bg',1)}; border-right-color: ${outcomes.getStyleColour('bor',0)};">
    <br/><!-- SPACE -->
    <!-- INFO BUTTON -->
    <div class="lbu_unified_bb_info_button" 
         style="background-color: ${outcomes.getStyleColour('bg',0)}; color: ${outcomes.getStyleColour('fg',0)}" 
         ${outcomes.toIdA("moreinfobutton")} 
         onclick="${id}showinfo()"><span class="lbu_unified_bb_info_button_text">i</span></div>
    <!-- LESS INFO BUTTON -->
    <div class="lbu_unified_bb_info_button" 
         style="background-color: ${outcomes.getStyleColour('bg',0)}; color: ${outcomes.getStyleColour('fg',0)}; display: none;" 
         ${outcomes.toIdA("lessinfobutton")}  
         onclick="${id}hidehelpinfo()"><span class="lbu_unified_bb_info_button_text">i</span></div>
    <br/><!-- SPACE -->
    <!-- HELP BUTTON -->
    <div class="lbu_unified_bb_help_button" 
         style="background-color: ${outcomes.getStyleColour('bg',0)}; color: ${outcomes.getStyleColour('fg',0)}" 
         ${outcomes.toIdA("morehelpbutton")} 
         onclick="${id}showhelp()"><span class="lbu_unified_bb_help_button_text">?</span></div>
    <!-- LESS HELP BUTTON -->
    <div class="lbu_unified_bb_help_button" 
         style="background-color: ${outcomes.getStyleColour('bg',0)}; color: ${outcomes.getStyleColour('fg',0)}; display: none;" 
         ${outcomes.toIdA("lesshelpbutton")} 
         onclick="${id}hidehelpinfo()"><span class="lbu_unified_bb_help_button_text">?</span></div>
  </div>

  <!-- The info section which shows when info is clicked -->
  <div class="lbu_unified_bb_centralpanel" ${outcomes.toIdA("infopanel")} 
       style="display: none; background-color: ${outcomes.getStyleColour('bg',2)}">
    <span class="lbu_unified_bb_centraltitle" style="color: ${outcomes.getStyleColour('fg',1)}">Information</span><br/><br/><!-- SPACES -->
    <p class="lbu_unified_bb_centraltext">${outcomes.infoText}</p><br/>
    <a onclick="${id}hidehelpinfo()" title="Back" 
       class="lbu_unified_bb_back_button"
       style="background-color: ${outcomes.getStyleColour('bg',0)}; color: ${outcomes.getStyleColour('fg',0)}">< Back</a>
  </div>

  <!-- The help section which shows when help is clicked -->
  <div class="lbu_unified_bb_centralpanel" ${outcomes.toIdA("helppanel")} 
       style="display: none; background-color: ${outcomes.getStyleColour('bg',2)}">
    <span class="lbu_unified_bb_centraltitle" style="color: ${outcomes.getStyleColour('fg',1)}">Help</span><br/><br/><!-- SPACES -->
    <p class="lbu_unified_bb_centraltext ">${outcomes.helpText}</p><br/>
.    <a onclick="${id}hidehelpinfo()" title="Back" 
        class="lbu_unified_bb_back_button" 
        style="background-color: ${outcomes.getStyleColour('bg',0)}; color: ${outcomes.getStyleColour('fg',0)}">< Back</a>
  </div>

  <!-- The main body of the channel -->
  <div class="lbu_unified_bb_centralpanel" ${outcomes.toIdA("mainpanel")} style="background-color: ${outcomes.getStyleColour('bg',2)}">
    <span class="lbu_unified_bb_centraltitle" style="color: ${outcomes.getStyleColour('fg',1)}">${outcomes.title}</span>
    <br/> <br/>
    
    <c:choose>
      <c:when test="${outcomes.courseExistsAlready()}">
        <p>Your sandbox module with ID, ${outcomes.getCourseID()} already exists.</p>        
      </c:when>
      <c:otherwise>
        <p>You can create a module with ID, ${outcomes.getCourseID()}.</p>        
          <span class="lbu_unified_bb_action_button"
          style="background-color: ${outcomes.getStyleColour('bg',0)}; color: ${outcomes.getStyleColour('fg',0)}"
          onclick="${id}submitform()">${outcomes.buttonLabel} <span class="lbu_unified_bb_action_button_arrow">&#x203a;</span></span>     
      </c:otherwise>
    </c:choose>
      

  </div>
</div>

  </c:otherwise>
</c:choose>

  </body>
</html>
