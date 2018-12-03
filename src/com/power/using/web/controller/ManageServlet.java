package com.power.using.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.power.using.domain.Book;
import com.power.using.domain.Category;
import com.power.using.service.BusinessService;
import com.power.using.service.commons.Page;
import com.power.using.service.impl.BusinessServiceImpl;
import com.power.using.util.IdGenertor;
import com.power.using.util.WebUtil;

public class ManageServlet extends HttpServlet {

	private BusinessService s=new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = request.getParameter("op");
		if("addCategory".equals(op)){
			addCategory(request,response);
		}else if("showAllCategory".equals(op)){
			showAllCategory(request,response);
		}else if("addBookUI".equals(op)){
			addBookUI(request,response);
		}else if("addBook".equals(op)){
			addBook(request,response);
		}else if("showPageBooks".equals(op)){
			showPageBooks(request,response);
		}
		
		
	}

	/**
	 * 查询书籍分页
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void showPageBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//用户要看的页码
		String num = request.getParameter("num");
		Page page = s.findBookPageRecords(num);
		page.setUrl("/manage/ManageServlet?op=showPageBooks");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manage/listBooks.jsp").forward(request, response);
		
		
	
	
	}

	/**
	 * 添加图书
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//此时webUtil已经失效,文件上传,需要先判断
		//表单是否是multupart/form-data类型
		boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
		if(!isMultiPart){
			throw new RuntimeException("This form is not multipart/form-data");
		}
		//解析请求内容	
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		List<FileItem> items=new ArrayList<FileItem>();
		try {
			items=sfu.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Book book = new Book();
		for (FileItem item : items) {
			//普通字段:封装到book中
			if(item.isFormField()){
				processFormFiled(item,book);
			}else{
				//上传字段:上传
				processUploadFiled(item,book);
			}
			
		}
		
		//数据的分类  此处添加错误
//		Category category = s.findCategoryById(request.getParameter("categoryId"));
//		book.setCategory(category);
		
		//把信息保存到数据库中
		s.addBook(book);
		response.setHeader("Refresh", "2;URL="+request.getContextPath());
		response.sendRedirect(request.getContextPath()+"/common/message.jsp");
		
	}

	/**
	 * 处理文件上传
	 * @param item
	 */
	private void processUploadFiled(FileItem item, Book book) {
		//存放路径:因为存储的是图片,不能放在WEB-INF中,在读取时需要用到servlet.jar;应该放到之外,这样读取时,使用一个链接就可以访问到
		String storeDirectory=getServletContext().getRealPath("/images");
		File rootDirectory = new File(storeDirectory);
		if(!rootDirectory.exists()){
			rootDirectory.mkdirs();
		}
		
		//文件名
		String fileName = item.getName();
		if(fileName!=null){//a.jpg
			//拓展名  a.jpg=====>sdfafafs.jpg
			fileName=IdGenertor.genGUID()+"."+FilenameUtils.getExtension(fileName);
			book.setFilename(fileName);
			
		}
		
		//计算子目录
		String path = genChildDirectory(storeDirectory, fileName);
		book.setPath(path);
		
		
		//文件上传
		try {
			item.write(new File(rootDirectory,path+"/"+fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String genChildDirectory(String realPath,String fileName){
		int hashCode=fileName.hashCode();
		int dir1=hashCode&0xf;
		int dir2=(hashCode&0xf0)>>4;
		
		String str=dir1+File.separator+dir2;
		File file = new File(realPath,str);
		if(!file.exists()){
			file.mkdirs();
		}
		return str;
	}

	/**
	 * 把FileItem中的数据封装到Book中
	 * @param item
	 * @param book
	 */
	private void processFormFiled(FileItem item, Book book) {
		try {
			String fieldName = item.getFieldName(); 
			String filedValue = item.getString("UTF-8");
			BeanUtils.setProperty(book, fieldName, filedValue);
			
			//单独处理书籍所属Category,因为表单是multipart的,提交后request.getparamater()失效
			//此处来处理
			if("categoryId".equals(fieldName)){
				Category c=s.findCategoryById(filedValue);
				book.setCategory(c);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 转向添加书籍的页面,查询所有分类
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void addBookUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/addBook.jsp").forward(request, response);
	}

	/**
	 * 查询所有分类
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void showAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		//另一个页面要想显示,则要使用getRequestDispatcher
		//   '/'==>所有的标签都是服务器地址,加有'/'的都是服务器地址,在服务器内访问的都是服务器地址
		//例如,此处,转发是服务器行为,则要加'/'
		request.getRequestDispatcher("/manage/listCategory.jsp").forward(request, response);
		
		//重定向,是浏览器的行为,如下
		/**
		 * //处理完后要页面转向
		 *response.sendRedirect(request.getContextPath()+"/common/message.jsp");
		*/
	}

	/**
	 * 添加
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Category c=WebUtil.fillBean(request,Category.class);
		s.addCategory(c);
		//处理完后要页面转向
		response.sendRedirect(request.getContextPath()+"/common/message.jsp");
		
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
