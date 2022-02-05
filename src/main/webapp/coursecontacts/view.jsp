<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/bbNG" prefix="bbNG" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="outcomes" class="uk.ac.leedsbeckett.digles.b2unified.ContactsOutcomes" scope="request"/>


<c:choose>
  <c:when test="${outcomes.hasFailed()}">
      <p>${outcomes.getErrorMessage()}</p>
  </c:when>
  <c:otherwise>
   <div id="courseContactsDetail"
     style="display:none; position: absolute; box-shadow: 0px 0px 10px #b7b3b3; border-radius: 5px; width: 305px; border-width: 5px; border-style: solid; border-color: rgb(171, 74, 156); background-color: rgb(255, 255, 255); padding: 5px;z-index: 101;">
    <a onclick="$('courseContactsDetail').hide();" title="Back" class="bkt_back_button bkt_back_button_violet">&lt;
        Back</a>
    <p style="margin-top: 10px; font-family: AvenyTRegular; font-size: 20px; color:#59194e; font-weight:bold; padding-top:5px; padding-bottom:10px; padding-left:10px">
        Course Contacts</p>
    <c:choose>
        <c:when test="${outcomes.hasCourseContacts()}">
            <c:forEach items="${outcomes.getCourseContacts()}" var="courseContact">
                <p style="padding-top: 10px; padding-bottom:8px; padding-left: 10px; border-style:solid; border-width:0px; border-bottom-width:1px; border-color:#ccc;">
                <span style="font-family: AvenyTRegular; font-size: 18px; color:#2f3233; font-weight:normal; padding-bottom:5px;">
                    ${courseContact.getName()} (${courseContact.getRole()})
                </span>
                <br/>
                <span style="font-family: Arial,sans-serif; font-size: 14px; color:#8D3E80; font-weight:bold; padding-bottom:5px;">
                  ${courseContact.getEmail()}
                </span>
                <br/>
                </p>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p style="padding-top: 10px; padding-bottom:8px; padding-left: 10px; border-style:solid; border-width:0px; border-bottom-width:1px; border-color:#ccc;">
            <span style="font-family: Arial,sans-serif; font-size: 14px; color:#8D3E80; font-weight:bold; padding-bottom:5px;">
                Sorry, no details available.
            </span>
            <br/>
            </p>
        </c:otherwise>
    </c:choose>
    <br/>
   </div>

   <!-- Course Contacts Button -->
   <div id="CC_container" class="square-btn channel_btn_colour_violet" onclick="$('courseContactsDetail').show();"><img
        src="https://my.leedsbeckett.ac.uk/bbcswebdav/xid-6491548_1"/>
       <div class="btn-text-background">
          <p>Course Contacts</p>
       </div>
   </div>

   <!-- Course Handbooks Button -->
   <div id="CH_container" class="square-btn channel_btn_colour_green"><a
        href="http://www.leedsbeckett.ac.uk/student-information/course-information/course-handbooks/" target="_blank"><img
        src="https://my.leedsbeckett.ac.uk/bbcswebdav/xid-6491549_1"/></a>
     <div class="btn-text-background">
        <p>Course Handbooks</p>
     </div>
   </div>
  </c:otherwise>
</c:choose>
