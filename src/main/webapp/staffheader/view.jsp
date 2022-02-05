<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/bbNG" prefix="bbNG" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="outcomes" class="uk.ac.leedsbeckett.digles.b2unified.PersonalDataOutcomes" scope="request"/>

<c:choose>
  <c:when test="${outcomes.hasFailed()}">
      <p>${outcomes.getErrorMessage()}</p>
  </c:when>
  <c:otherwise>
    <p style="margin-left: 20px; margin-top: 30px! important; margin-bottom: 0; text-transform: uppercase; color: #000; font-size: 30px; font-family: 'AvenyTMedium';">
      ${outcomes.getStaffTitle()}
    </p>
    <p style="margin-left: 20px; margin-top: 2px; margin-bottom: 30px; color: #270758; font-size: 50px; font-family: 'AvenyTMedium';">
      ${outcomes.getStaffSubTitle()}
    </p>
  </c:otherwise>
</c:choose>
