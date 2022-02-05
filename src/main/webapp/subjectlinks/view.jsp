<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/bbNG" prefix="bbNG" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="outcomes" class="uk.ac.leedsbeckett.digles.b2unified.SubjectLinkOutcomes" scope="request"/>


<c:choose>
  <c:when test="${outcomes.hasFailed()}">
      <p>${outcomes.getErrorMessage()}</p>
  </c:when>
  <c:otherwise>
   <div id="subjectListDetail"
     style="display:none; position: absolute; box-shadow: 0px 0px 10px #b7b3b3; border-radius: 5px; width: 305px; border-width: 5px; border-style: solid; border-color: rgb(171, 74, 156); background-color: rgb(255, 255, 255); padding: 5px;z-index: 101;">
    <a onclick="$('subjectListDetail').hide();" title="Back" class="bkt_back_button bkt_back_button_violet">&lt;
        Back</a>
    <p style="margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        Library Subject Support</p>
        <c:if test="${outcomes.hasSchool()}">
            <p style="padding-top: 10px; padding-bottom:8px; padding-left: 10px; border-style:solid; border-width:0px; border-bottom-width:1px; border-color:#ccc;">
            <span style="font-family: AvenyTRegular; font-size: 18px; color:#2f3233; font-weight:normal; padding-bottom:5px;">
                <a href="${outcomes.getSchoolLink()}" target="_blank">${outcomes.getSchoolName()}</a>
            </span>
            </p>
        </c:if>
        <p style="padding-top: 10px; padding-bottom:8px; padding-left: 10px; border-style:solid; border-width:0px; border-bottom-width:1px; border-color:#ccc;">
        <span style="font-family: Arial,sans-serif; font-size: 14px; color:#8D3E80; font-weight:bold; padding-bottom:5px;">
            <a href="https://libguides.leedsbeckett.ac.uk/subject_support" target="_blank">All Subjects</a>
        </span>
        <br/>
        </p>
    <br/>
   </div>

    <!-- Course Contacts Button -->
    <div id="CC_container" class="square-btn-bigimage channel_btn_colour_purple" onclick="$('subjectListDetail').show();"><img
        src="https://my-staging.leedsbeckett.ac.uk/bbcswebdav/xid-5237817_1"/>
      <div class="btn-text-background">
        <p>Subject Support</p>
      </div>
    </div>
    <div id="CT_CCar_CCTE" class="square-btn-bigimage channel_btn_colour_blue" onclick="ga('send', 'event', 'MyBeckett_Portal', 'Portal-Channel-Link', 'CT_CCar_CCTE');"><a href="https://myhub.leedsbeckett.ac.uk/s/careers" title="Careers info" target="_blank" rel="noopener"><img src="https://my-staging.leedsbeckett.ac.uk/bbcswebdav/xid-6491547_1" /></a>
      <div class="btn-text-background">
        <p>Course Careers</p>
      </div>
    </div>
    
  </c:otherwise>
</c:choose>
