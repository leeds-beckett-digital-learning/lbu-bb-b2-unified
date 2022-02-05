<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/bbNG" prefix="bbNG" %>

<%--  
    This useBean tag does all the heavy lifting for the page.
    The constructor of ConfigOutcomes can access form input, can use the
    Blackboard context object and also has access to the web application's
    servlet context.
--%>
<jsp:useBean id="outcomes" class="uk.ac.leedsbeckett.digles.b2unified.ConfigOutcomes" scope="request"/>


<%--  The rest puts HTML on the page according to the outcomes of the processing. --%>
<bbNG:learningSystemPage ctxId="context" title="Configure Unified Building Block">

  <bbNG:pageHeader>
    <bbNG:breadcrumbBar>
      <bbNG:breadcrumb>Configure Unified Building Block</bbNG:breadcrumb>
    </bbNG:breadcrumbBar>
    <bbNG:pageTitleBar title="Configure Unified Building Block"/>
  </bbNG:pageHeader>

  <%-- Different HTML output depending on outcomes. --%>
  <c:choose>
    <c:when test="${outcomes.isDisplayForm()}">
      <p>These are the parameters of the building block that can be
        configured.</p>
      <form action="config.jsp" method="POST">
        <bbNG:dataCollection>
          <bbNG:step title="General Settings">
            <input type="hidden" name="action" value="config"/>
            <bbNG:dataElement label="Log Level" isRequired="true" id="loglevel">
              <bbNG:selectElement title="Log Level" helpText="Log Level for whole building block" name="loglevel" isRequired="true">
                <bbNG:selectOptionElement value="off"   optionLabel="Off"   isSelected="${outcomes.isLogLevelSelected('off')   }"/>
                <bbNG:selectOptionElement value="error" optionLabel="Error" isSelected="${outcomes.isLogLevelSelected('error') }"/>
                <bbNG:selectOptionElement value="warn"  optionLabel="Warn"  isSelected="${outcomes.isLogLevelSelected('warn')  }"/>
                <bbNG:selectOptionElement value="info"  optionLabel="Info"  isSelected="${outcomes.isLogLevelSelected('info')  }"/>
                <bbNG:selectOptionElement value="debug" optionLabel="Debug" isSelected="${outcomes.isLogLevelSelected('debug') }"/>
              </bbNG:selectElement>
            </bbNG:dataElement>
            <bbNG:dataElement label="Email" isRequired="true">
              <bbNG:textElement title="Email" helpText="Email Address" maxlength="100" size="50" name="email" value="${outcomes.settings.email}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Support Website" isRequired="true">
              <bbNG:textElement title="Support Website" helpText="Support Website" maxlength="100" size="50" name="helpwebsite" value="${outcomes.settings.helpwebsite}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Institution Name" isRequired="true">
              <bbNG:textElement title="Institution Name" helpText="Institution Name" maxlength="100" size="50" name="institutionname" value="${outcomes.settings.institutionname}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Student Role Id" isRequired="true">
              <bbNG:textElement title="Student Role Id" helpText="This needs to be the ID in the format [underscore][decimalnumber][underscore][decimalnumber] of the primary institution role used for students" maxlength="100" size="50" name="studentroleid" value="${outcomes.settings.studentroleid}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Staff Role Id" isRequired="true">
              <bbNG:textElement title="Staff Role Id" helpText="This needs to be the ID in the format [underscore][decimalnumber][underscore][decimalnumber] of the primary institution role used for staff" maxlength="100" size="50" name="staffroleid" value="${outcomes.settings.staffroleid}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Prospectus Service URL" isRequired="true">
              <bbNG:textElement title="Prospectus Service URL" helpText="Used to connect to the Prospectus service" maxlength="100" size="50" name="prospectusurl" value="${outcomes.settings.prospectusurl}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Prospectus Service Security Token" isRequired="true">
              <bbNG:textElement title="Prospectus Service Security Token" helpText="Used to authenticate to the Prospectus service" maxlength="100" size="50" name="prospectussecuritytoken" value="${outcomes.settings.prospectussecuritytoken}" isRequired="true" />
            </bbNG:dataElement>
          </bbNG:step>
          <bbNG:step title="Staff Training Self Enrol">
            <bbNG:dataElement label="Course Prefix" isRequired="true">
              <bbNG:textElement title="Course Prefix" helpText="Course Prefix" maxlength="100" size="50" name="trainingcourseprefix" value="${outcomes.settings.trainingcourseprefix}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Title" isRequired="true">
              <bbNG:textElement title="Title" helpText="Title at top of module" maxlength="100" size="50" name="trainingtitle" value="${outcomes.settings.trainingtitle}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Colour Scheme" isRequired="true" id="trainingcolourscheme">
              <bbNG:selectElement title="Colour Scheme" helpText="Colour scheme for training module." name="trainingcolourscheme" isRequired="true">
                <bbNG:selectOptionElement value="teal"   optionLabel="Teal"      isSelected="${outcomes.isTrainingColourSchemeSelected( 'teal'   )}"/>
                <bbNG:selectOptionElement value="violet" optionLabel="Violet"    isSelected="${outcomes.isTrainingColourSchemeSelected( 'violet' )}"/>
                <bbNG:selectOptionElement value="turq"   optionLabel="Turquoise" isSelected="${outcomes.isTrainingColourSchemeSelected( 'turq'   )}"/>
                <bbNG:selectOptionElement value="lime"   optionLabel="Lime"      isSelected="${outcomes.isTrainingColourSchemeSelected( 'lime'   )}"/>
                <bbNG:selectOptionElement value="green"  optionLabel="Green"     isSelected="${outcomes.isTrainingColourSchemeSelected( 'green'  )}"/>
                <bbNG:selectOptionElement value="pink"   optionLabel="Pink"      isSelected="${outcomes.isTrainingColourSchemeSelected( 'pink'   )}"/>
                <bbNG:selectOptionElement value="grey"   optionLabel="Grey"      isSelected="${outcomes.isTrainingColourSchemeSelected( 'grey'   )}"/>
                <bbNG:selectOptionElement value="red"    optionLabel="Red"       isSelected="${outcomes.isTrainingColourSchemeSelected( 'red'    )}"/>
                <bbNG:selectOptionElement value="blue"   optionLabel="Blue"      isSelected="${outcomes.isTrainingColourSchemeSelected( 'blue'   )}"/>
                <bbNG:selectOptionElement value="purple" optionLabel="Purple"    isSelected="${outcomes.isTrainingColourSchemeSelected( 'purple' )}"/>
                <bbNG:selectOptionElement value="yellow" optionLabel="Yellow"    isSelected="${outcomes.isTrainingColourSchemeSelected( 'yellow' )}"/>
                <bbNG:selectOptionElement value="orange" optionLabel="Orange"    isSelected="${outcomes.isTrainingColourSchemeSelected( 'orange' )}"/>
              </bbNG:selectElement>
            </bbNG:dataElement>
            <bbNG:dataElement label="Button Label" isRequired="true">
              <bbNG:textElement title="Button Label" helpText="Text that should appear on the main button." maxlength="100" size="50" name="trainingbuttonlabel" value="${outcomes.settings.trainingbuttonlabel}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Info Text" isRequired="true">
              <bbNG:textbox helpText="Text for 'info' panel of the training self enrol module." rows="5" name="traininginfohtml" ftext="${outcomes.settings.getTrainingInfoHtml()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Help Text" isRequired="true">
              <bbNG:textbox helpText="Text for 'help' panel of the training self enrol module." rows="5" name="traininghelphtml" ftext="${outcomes.settings.getTrainingHelpHtml()}"/>
            </bbNG:dataElement>
          </bbNG:step>

          <bbNG:step title="Staff Training Sandbox Creation">
            <bbNG:dataElement label="Title" isRequired="true">
              <bbNG:textElement title="Title" helpText="Title at top of module" maxlength="100" size="50" name="sandboxtitle" value="${outcomes.settings.sandboxtitle}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Colour Scheme" isRequired="true" id="sandboxcolourscheme">
              <bbNG:selectElement title="Colour Scheme" helpText="Colour scheme for sandbox creation module." name="sandboxcolourscheme" isRequired="true">
                <bbNG:selectOptionElement value="teal"   optionLabel="Teal"      isSelected="${outcomes.isSandboxColourSchemeSelected( 'teal'   )}"/>
                <bbNG:selectOptionElement value="violet" optionLabel="Violet"    isSelected="${outcomes.isSandboxColourSchemeSelected( 'violet' )}"/>
                <bbNG:selectOptionElement value="turq"   optionLabel="Turquoise" isSelected="${outcomes.isSandboxColourSchemeSelected( 'turq'   )}"/>
                <bbNG:selectOptionElement value="lime"   optionLabel="Lime"      isSelected="${outcomes.isSandboxColourSchemeSelected( 'lime'   )}"/>
                <bbNG:selectOptionElement value="green"  optionLabel="Green"     isSelected="${outcomes.isSandboxColourSchemeSelected( 'green'  )}"/>
                <bbNG:selectOptionElement value="pink"   optionLabel="Pink"      isSelected="${outcomes.isSandboxColourSchemeSelected( 'pink'   )}"/>
                <bbNG:selectOptionElement value="grey"   optionLabel="Grey"      isSelected="${outcomes.isSandboxColourSchemeSelected( 'grey'   )}"/>
                <bbNG:selectOptionElement value="red"    optionLabel="Red"       isSelected="${outcomes.isSandboxColourSchemeSelected( 'red'    )}"/>
                <bbNG:selectOptionElement value="blue"   optionLabel="Blue"      isSelected="${outcomes.isSandboxColourSchemeSelected( 'blue'   )}"/>
                <bbNG:selectOptionElement value="purple" optionLabel="Purple"    isSelected="${outcomes.isSandboxColourSchemeSelected( 'purple' )}"/>
                <bbNG:selectOptionElement value="yellow" optionLabel="Yellow"    isSelected="${outcomes.isSandboxColourSchemeSelected( 'yellow' )}"/>
                <bbNG:selectOptionElement value="orange" optionLabel="Orange"    isSelected="${outcomes.isSandboxColourSchemeSelected( 'orange' )}"/>
              </bbNG:selectElement>
            </bbNG:dataElement>
            <bbNG:dataElement label="Button Label" isRequired="true">
              <bbNG:textElement title="Button Label" helpText="Text that should appear on the main button." maxlength="100" size="50" name="sandboxbuttonlabel" value="${outcomes.settings.sandboxbuttonlabel}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Info Text" isRequired="true">
              <bbNG:textbox helpText="Text for 'info' panel of the sandbox self enrol module." rows="5" name="sandboxinfohtml" ftext="${outcomes.settings.getSandboxInfoHtml()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Help Text" isRequired="true">
              <bbNG:textbox helpText="Text for 'help' panel of the sandbox self enrol module." rows="5" name="sandboxhelphtml" ftext="${outcomes.settings.getSandboxHelpHtml()}"/>
            </bbNG:dataElement>
          </bbNG:step>
            

          <bbNG:step title="Module Self Enrol">
            <bbNG:dataElement label="Title" isRequired="true">
              <bbNG:textElement title="Title" helpText="Title at top of module" maxlength="100" size="50" name="courseselfenroltitle" value="${outcomes.settings.courseselfenroltitle}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Colour Scheme" isRequired="true" id="courseselfenrolcolourscheme">
              <bbNG:selectElement title="Colour Scheme" helpText="Colour scheme for plugin module." name="courseselfenrolcolourscheme" isRequired="true">
                <bbNG:selectOptionElement value="teal"   optionLabel="Teal"      isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'teal'   )}"/>
                <bbNG:selectOptionElement value="violet" optionLabel="Violet"    isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'violet' )}"/>
                <bbNG:selectOptionElement value="turq"   optionLabel="Turquoise" isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'turq'   )}"/>
                <bbNG:selectOptionElement value="lime"   optionLabel="Lime"      isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'lime'   )}"/>
                <bbNG:selectOptionElement value="green"  optionLabel="Green"     isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'green'  )}"/>
                <bbNG:selectOptionElement value="pink"   optionLabel="Pink"      isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'pink'   )}"/>
                <bbNG:selectOptionElement value="grey"   optionLabel="Grey"      isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'grey'   )}"/>
                <bbNG:selectOptionElement value="red"    optionLabel="Red"       isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'red'    )}"/>
                <bbNG:selectOptionElement value="blue"   optionLabel="Blue"      isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'blue'   )}"/>
                <bbNG:selectOptionElement value="purple" optionLabel="Purple"    isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'purple' )}"/>
                <bbNG:selectOptionElement value="yellow" optionLabel="Yellow"    isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'yellow' )}"/>
                <bbNG:selectOptionElement value="orange" optionLabel="Orange"    isSelected="${outcomes.isCourseSelfEnrolColourSchemeSelected( 'orange' )}"/>
              </bbNG:selectElement>
            </bbNG:dataElement>
            <bbNG:dataElement label="Button Label" isRequired="true">
              <bbNG:textElement title="Button Label" helpText="Text that should appear on the main button." maxlength="100" size="50" name="courseselfenrolbuttonlabel" value="${outcomes.settings.courseselfenrolbuttonlabel}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Info Text" isRequired="true">
              <bbNG:textbox helpText="Text for 'info' panel of the courseselfenrol self enrol module." rows="5" name="courseselfenrolinfohtml" ftext="${outcomes.settings.getCourseSelfEnrolInfoHtml()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Help Text" isRequired="true">
              <bbNG:textbox helpText="Text for 'help' panel of the courseselfenrol self enrol module." rows="5" name="courseselfenrolhelphtml" ftext="${outcomes.settings.getCourseSelfEnrolHelpHtml()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Email to user" isRequired="true">
              <bbNG:textbox customConfig="{block_formats: 'Preformatted=pre'}" isFormattedText="false" isSpellcheckOnly="false" helpText="Plain unformatted text of email to send user/admin. Uses placeholders to add specific data." rows="10" name="courseselfenrolemailtext" text="${outcomes.settings.getCourseSelfEnrolReceiptEmail()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Search Term Validation" isRequired="true">
              <bbNG:textElement title="Search Term Validation" helpText="A Java regular expression which user input must match." maxlength="100" size="50" name="courseselfenrolvalidation" value="${outcomes.settings.getCourseSelfEnrolValidation()}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Module ID Filter" isRequired="true">
              <bbNG:textElement title="Module ID Filter" helpText="A Java regular expression which module IDs must match to appear in results." maxlength="100" size="50" name="courseselfenrolfilter" value="${outcomes.settings.getCourseSelfEnrolFilter()}" isRequired="true" />
            </bbNG:dataElement>
          </bbNG:step>

          <bbNG:step title="Course Group Self Enrol">
            <bbNG:dataElement label="Title" isRequired="true">
              <bbNG:textElement title="Title" helpText="Title at top of module" maxlength="100" size="50" name="organizationselfenroltitle" value="${outcomes.settings.organizationselfenroltitle}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Colour Scheme" isRequired="true" id="organizationselfenrolcolourscheme">
              <bbNG:selectElement title="Colour Scheme" helpText="Colour scheme for plugin module." name="organizationselfenrolcolourscheme" isRequired="true">
                <bbNG:selectOptionElement value="teal"   optionLabel="Teal"      isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'teal'   )}"/>
                <bbNG:selectOptionElement value="violet" optionLabel="Violet"    isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'violet' )}"/>
                <bbNG:selectOptionElement value="turq"   optionLabel="Turquoise" isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'turq'   )}"/>
                <bbNG:selectOptionElement value="lime"   optionLabel="Lime"      isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'lime'   )}"/>
                <bbNG:selectOptionElement value="green"  optionLabel="Green"     isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'green'  )}"/>
                <bbNG:selectOptionElement value="pink"   optionLabel="Pink"      isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'pink'   )}"/>
                <bbNG:selectOptionElement value="grey"   optionLabel="Grey"      isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'grey'   )}"/>
                <bbNG:selectOptionElement value="red"    optionLabel="Red"       isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'red'    )}"/>
                <bbNG:selectOptionElement value="blue"   optionLabel="Blue"      isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'blue'   )}"/>
                <bbNG:selectOptionElement value="purple" optionLabel="Purple"    isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'purple' )}"/>
                <bbNG:selectOptionElement value="yellow" optionLabel="Yellow"    isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'yellow' )}"/>
                <bbNG:selectOptionElement value="orange" optionLabel="Orange"    isSelected="${outcomes.isOrganizationSelfEnrolColourSchemeSelected( 'orange' )}"/>
              </bbNG:selectElement>
            </bbNG:dataElement>
            <bbNG:dataElement label="Button Label" isRequired="true">
              <bbNG:textElement title="Button Label" helpText="Text that should appear on the main button." maxlength="100" size="50" name="organizationselfenrolbuttonlabel" value="${outcomes.settings.organizationselfenrolbuttonlabel}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Info Text" isRequired="true">
              <bbNG:textbox helpText="Text for 'info' panel of the organizationselfenrol self enrol module." rows="5" name="organizationselfenrolinfohtml" ftext="${outcomes.settings.getOrganizationSelfEnrolInfoHtml()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Help Text" isRequired="true">
              <bbNG:textbox helpText="Text for 'help' panel of the organizationselfenrol self enrol module." rows="5" name="organizationselfenrolhelphtml" ftext="${outcomes.settings.getOrganizationSelfEnrolHelpHtml()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Email to user" isRequired="true">
              <bbNG:textbox customConfig="{block_formats: 'Preformatted=pre'}" isFormattedText="false" isSpellcheckOnly="false" helpText="Plain unformatted text of email to send user/admin. Uses placeholders to add specific data." rows="10" name="organizationselfenrolemailtext" text="${outcomes.settings.getOrganizationSelfEnrolReceiptEmail()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Search Term Validation" isRequired="true">
              <bbNG:textElement title="Search Term Validation" helpText="A Java regular expression which user input must match." maxlength="100" size="50" name="organizationselfenrolvalidation" value="${outcomes.settings.getOrganizationSelfEnrolValidation()}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Course Group ID Filter" isRequired="true">
              <bbNG:textElement title="Course Group ID Filter" helpText="A Java regular expression which course group IDs must match to appear in results." maxlength="100" size="50" name="organizationselfenrolfilter" value="${outcomes.settings.getOrganizationSelfEnrolFilter()}" isRequired="true" />
            </bbNG:dataElement>
          </bbNG:step>

          <bbNG:step title="SPSS Request">
            <bbNG:dataElement label="SPSS License Code" isRequired="true">
              <bbNG:textElement title="SPSS License Code" helpText="Current license code for SPSS windows and mac." maxlength="100" size="50" name="spsslicensecode" value="${outcomes.settings.spsslicensecode}" isRequired="true" />
            </bbNG:dataElement>
            
            <bbNG:dataElement label="Amos License Code" isRequired="true">
              <bbNG:textElement title="Amos License Code" helpText="Current license code for Amos." maxlength="100" size="50" name="spsslicensecodeamos" value="${outcomes.settings.spsslicensecodeamos}" isRequired="true" />
            </bbNG:dataElement>
            
            <bbNG:dataElement label="Download SPSS for Windows URL" isRequired="true">
              <bbNG:textElement title="Download SPSS for Windows URL" helpText="Location of SPSS for Windows package." maxlength="100" size="50" name="spssdownloadurlwindows" value="${outcomes.settings.spssdownloadurlwindows}" isRequired="true" />
            </bbNG:dataElement>
            
            <bbNG:dataElement label="Download SPSS for Mac URL" isRequired="true">
              <bbNG:textElement title="Download SPSS for Mac URL" helpText="Location of SPSS for Mac package." maxlength="100" size="50" name="spssdownloadurlmac" value="${outcomes.settings.spssdownloadurlmac}" isRequired="true" />
            </bbNG:dataElement>
            
            <bbNG:dataElement label="Download SPSS Amos for Windows URL" isRequired="true">
              <bbNG:textElement title="Download SPSS Amos for Windows URL" helpText="Location of Amos for Windows package." maxlength="100" size="50" name="spssdownloadurlamos" value="${outcomes.settings.spssdownloadurlamos}" isRequired="true" />
            </bbNG:dataElement>
            
            <bbNG:dataElement label="SPSS for Windows Instructions URL" isRequired="true">
              <bbNG:textElement title="SPSS for Windows Instructions URL" helpText="SPSS for Windows Instructions URL." maxlength="100" size="50" name="spssinstructionsurlwindows" value="${outcomes.settings.spssinstructionsurlwindows}" isRequired="true" />
            </bbNG:dataElement>
            
            <bbNG:dataElement label="SPSS for Mac Instructions URL" isRequired="true">
              <bbNG:textElement title="SPSS for Mac Instructions URL" helpText="SPSS for Mac Instructions URL." maxlength="100" size="50" name="spssinstructionsurlmac" value="${outcomes.settings.spssinstructionsurlmac}" isRequired="true" />
            </bbNG:dataElement>
            
            <bbNG:dataElement label="Amos for Windows Instructions URL" isRequired="true">
              <bbNG:textElement title="Amos for Windows Instructions URL" helpText="AMOS for Windows Instructions URL." maxlength="100" size="50" name="spssinstructionsurlamos" value="${outcomes.settings.spssinstructionsurlamos}" isRequired="true" />
            </bbNG:dataElement>
            
            <bbNG:dataElement label="Email address for record keeping." isRequired="true">
              <bbNG:textElement title="Email address for record keeping." helpText="Email address for record keeping." maxlength="100" size="50" name="spssreceiptemail" value="${outcomes.settings.spssreceiptemail}" isRequired="true" />
            </bbNG:dataElement>
            
            <bbNG:dataElement label="Title" isRequired="true">
              <bbNG:textElement title="Title" helpText="Title at top of module" maxlength="100" size="50" name="spsstitle" value="${outcomes.settings.spsstitle}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Colour Scheme" isRequired="true" id="spsscolourscheme">
              <bbNG:selectElement title="Colour Scheme" helpText="Colour scheme for plugin module." name="spsscolourscheme" isRequired="true">
                <bbNG:selectOptionElement value="teal"   optionLabel="Teal"      isSelected="${outcomes.isSpssColourSchemeSelected( 'teal'   )}"/>
                <bbNG:selectOptionElement value="violet" optionLabel="Violet"    isSelected="${outcomes.isSpssColourSchemeSelected( 'violet' )}"/>
                <bbNG:selectOptionElement value="turq"   optionLabel="Turquoise" isSelected="${outcomes.isSpssColourSchemeSelected( 'turq'   )}"/>
                <bbNG:selectOptionElement value="lime"   optionLabel="Lime"      isSelected="${outcomes.isSpssColourSchemeSelected( 'lime'   )}"/>
                <bbNG:selectOptionElement value="green"  optionLabel="Green"     isSelected="${outcomes.isSpssColourSchemeSelected( 'green'  )}"/>
                <bbNG:selectOptionElement value="pink"   optionLabel="Pink"      isSelected="${outcomes.isSpssColourSchemeSelected( 'pink'   )}"/>
                <bbNG:selectOptionElement value="grey"   optionLabel="Grey"      isSelected="${outcomes.isSpssColourSchemeSelected( 'grey'   )}"/>
                <bbNG:selectOptionElement value="red"    optionLabel="Red"       isSelected="${outcomes.isSpssColourSchemeSelected( 'red'    )}"/>
                <bbNG:selectOptionElement value="blue"   optionLabel="Blue"      isSelected="${outcomes.isSpssColourSchemeSelected( 'blue'   )}"/>
                <bbNG:selectOptionElement value="purple" optionLabel="Purple"    isSelected="${outcomes.isSpssColourSchemeSelected( 'purple' )}"/>
                <bbNG:selectOptionElement value="yellow" optionLabel="Yellow"    isSelected="${outcomes.isSpssColourSchemeSelected( 'yellow' )}"/>
                <bbNG:selectOptionElement value="orange" optionLabel="Orange"    isSelected="${outcomes.isSpssColourSchemeSelected( 'orange' )}"/>
              </bbNG:selectElement>
            </bbNG:dataElement>
            <bbNG:dataElement label="Button Label" isRequired="true">
              <bbNG:textElement title="Button Label" helpText="Text that should appear on the main button." maxlength="100" size="50" name="spssbuttonlabel" value="${outcomes.settings.spssbuttonlabel}" isRequired="true" />
            </bbNG:dataElement>
            <bbNG:dataElement label="Info Text" isRequired="true">
              <bbNG:textbox helpText="Text for 'info' panel of the spss self enrol module." rows="5" name="spssinfohtml" ftext="${outcomes.settings.getSpssInfoHtml()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Help Text" isRequired="true">
              <bbNG:textbox helpText="Text for 'help' panel of the spss self enrol module." rows="5" name="spsshelphtml" ftext="${outcomes.settings.getSpssHelpHtml()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Terms of Download" isRequired="true">
              <bbNG:textbox customConfig="{block_formats: 'Preformatted=pre'}" isFormattedText="false" isSpellcheckOnly="false" helpText="Plain unformatted text containing terms of use that user must agree to." rows="10" name="spssterms" text="<pre>${outcomes.settings.getSpssTerms()}</pre>"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Email to user" isRequired="true">
              <bbNG:textbox customConfig="{block_formats: 'Preformatted=pre'}" isFormattedText="false" isSpellcheckOnly="false" helpText="Plain unformatted text of email to send user/admin. Uses placeholders to add specific data." rows="10" name="spssemailtext" text="${outcomes.settings.getSpssEmailText()}"/>
            </bbNG:dataElement>
            <bbNG:dataElement label="Form Text" isRequired="true">
              <bbNG:textbox helpText="Text for form that will be presented to users. You need to enter certain placeholders to indicate where form elements will go." rows="5" name="spssformhtml" ftext="${outcomes.settings.getSpssFormHtml()}"/>
            </bbNG:dataElement>
          </bbNG:step>
            
          <bbNG:stepSubmit instructions="Click <b>Submit</b> to save or update the settings. "/>
        </bbNG:dataCollection>
      </form>
    </c:when>
    <c:when test="${outcomes.isDisplayConfirm()}">
      <h3>Success</h3>
      <p>Setting have been saved.</p>
      <form action="admin.jsp" method="GET">
        <input type="submit" value="Close"/>
      </form>
    </c:when>
    <c:when test="${outcomes.isDisplayError()}">
      <h3>Error</h3>
      <p>${outcomes.getErrorMessage()}</p>
      <form action="admin.jsp" method="GET">
        <input type="submit" value="Close"/>
      </form>
    </c:when>
    <c:otherwise>
      <p>Programmer error - this text should not appear.</p>
      <form action="admin.jsp" method="GET">
        <input type="submit" value="Close"/>
      </form>
    </c:otherwise>      
  </c:choose>
</bbNG:learningSystemPage>