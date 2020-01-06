package com.enjoy.sevlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(value="/asyncOrder", asyncSupported = true)
public class OrderAsyncServlet extends HttpServlet{
	//支持异步处理asyncSupported = true
	//重写doget方法
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//151个……
		System.out.println("主线程开始……"+Thread.currentThread()+"start....."+System.currentTimeMillis());
		AsyncContext startAsync = req.startAsync();

		startAsync.start(() -> {
				try {
					System.out.println("副线程开始……"+Thread.currentThread()+"start....."+System.currentTimeMillis());

					buyCards();
					startAsync.complete();
//					AsyncContext asyncContext = req.getAsyncContext();
//					ServletResponse response = asyncContext.getResponse();
					resp.getWriter().write("order sucesful....");
					System.out.println("副线程结束……"+Thread.currentThread()+"end....."+System.currentTimeMillis());

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});

		System.out.println("主线程结束……"+Thread.currentThread()+"end....."+System.currentTimeMillis());
		//主线程的资源断开……
	}

	public void buyCards() throws InterruptedException{
		System.out.println(Thread.currentThread()+".............");
		Thread.sleep(5000);//模拟业务操作
	}
}

