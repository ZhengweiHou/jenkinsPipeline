def send_email_results(param,from_email,to_emails) {
	def subject = "Jenkins Job : " + param.JOB_NAME + "/" + param.BUILD_ID + " has " +  param.currentBuildResult
	
	def mail_param_str = param.toString()
	
	println("邮件参数：" + mail_param_str)
 
	def text = """
		<!DOCTYPE html>
		<html>
		<head>
		<meta charset="UTF-8">
		<title>${param.JOB_NAME}-第${param.BUILD_ID}次构建日志</title>
		</head>
		 
		<body leftmargin="8" marginwidth="0" topmargin="8" marginheight="4"
		    offset="0">
		    <table width="95%" cellpadding="0" cellspacing="0"
		        style="font-size: 11pt; font-family: Tahoma, Arial, Helvetica, sans-serif">
		        <tr>
		            <td>(本邮件是Jenkins程序自动下发的，请勿回复！)</td>
		        </tr>
		        <tr>
		            <td><h2><font color="#0000FF">构建结果 - ${param.currentBuildResult}</font></h2></td>
		        </tr>
		        <tr>
		            <td><br />
		            <b><font color="#0B610B">构建信息：</font></b><hr size="2" width="100%" align="center" /></td>
		        </tr>
		        <tr>
		            <td>
		                <ul>
		                    <li>项目名称：${param.JOB_NAME}</li>
		                    <li>构建编号：第${param.BUILD_ID}次构建</li>
		                    <li>触发原因：${param.CASE}</li>
		                    <li>项目地址：<a href="${param.JOB_URL}">${param.JOB_URL}</a></li>
		                    <li>构建日志：<a href="${param.BUILD_URL}console">${param.BUILD_URL}console</a></li>
		                    <li>构建地址：<a href="${param.BUILD_URL}">${param.BUILD_URL}</a></li>
		                </ul>
		            </td>
		        </tr>
		        <tr>
		            <td><b><font color="#0B610B">Changes Since Last Successful Build:</font></b><hr size="2" width="100%" align="center" /></td>
		        </tr>
		        <tr>
		            <td>
		                <ul>
		                    <li>历史变更记录 : <a href="${param.JOB_URL}changes">${param.JOB_URL}changes</a></li>
		                </ul>
		            </td>
		        </tr>
		    </table>
		</body>
		</html>
		"""
	
//	def text = """
//		<!DOCTYPE html>
//        <html>    
//        <head>    
//        <meta charset="UTF-8">    
//        <title>${mail_param.jobName}-第${mail_param.buildId}次构建日志</title>    
//        </head>    
//
//        <body leftmargin="8" marginwidth="0" topmargin="8" marginheight="4"    
//            offset="0">    
//            <table width="95%" cellpadding="0" cellspacing="0"  style="font-size: 11pt; font-family: Tahoma, Arial, Helvetica, sans-serif">    
//                <tr>    
//					============================<br/>
//					${mail_param_str}<br/>
//					============================<br/>
//                    本邮件由系统自动发出，无需回复！<br/>       
//                    
//					<p>SUCCESSFUL: Job '${mail_param.jobName} [${mail_param.buildId}]':</p>
//					<p>Check console output at "<a href="${mail_param.buildUrl}">${mail_param.jobName} [${mail_param.buildId}]</a>"</p>
//                </tr>    
//            </table>    
//        </body>    
//        </html>
//	"""
 
	emailext(subject:subject, body:text, from:from_email, to:to_emails)
	
//	mail body: text, subject: subject,  mimeType: 'text/html', to: to_email_address_list
}
// model结尾必须有这句，否则内容load不了
return this;
		
		
	