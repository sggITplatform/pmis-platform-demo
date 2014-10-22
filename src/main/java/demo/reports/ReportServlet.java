package demo.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import pmis.commons.reports.ReportUtils;
import pmis.commons.reports.ReportUtils.DocType;

@SuppressWarnings("serial")
public class ReportServlet extends HttpServlet
{
	public static DocType getEnumDocType(String docType)
	{
		DocType type = DocType.PDF;
		docType = docType.toUpperCase();
		if (docType.equals("PDF"))
		{
			type = DocType.PDF;
		}
		else if (docType.equals("DOC"))
		{
			type = DocType.DOC;
		}
		return type;
	}

	/*** 数据对象 ****/
	@SuppressWarnings("unchecked")
	public static List getTestRecords()
	{
		List<User> sourceList = new ArrayList<User>();// 测试数据源
		for (int i = 0; i < 15; i++)
		{
			User user = new User();
			user.setId(1000 + i);
			user.setName("李" + i);
			if (i % 2 == 0)
				user.setSex("男");
			else
				user.setSex("女");

			user.setAge("27");
			sourceList.add(user);
		}
		return sourceList;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		String docType = request.getParameter("type");

		// 取得jrxml文件路径
		ReportUtils jasperReport = new ReportUtils(request, response);
		Map params = new HashMap();
		try
		{
			String jaseperPath = this.getServletContext().getRealPath("/WEB-INF/jrxml/report1.jasper");
			jasperReport
					.servletExportDocument(getEnumDocType(docType), jaseperPath, params, getTestRecords(), "员工信息报表");
		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
	}
}
