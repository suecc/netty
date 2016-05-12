package com.netty.client.callable;

import java.util.concurrent.Callable;

/**   
*    
* 项目名称：netty-client   
* 类名称：MyTask   
* 类描述：   
* 创建人：yshqiu   
* 创建时间：2016年5月9日 下午12:23:46   
* @version   
*    
*/
public class MyTask implements Callable<String> {

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public String call() throws Exception {

		System.out.println("休眠5s");
		Thread.currentThread().sleep(5000);
		System.out.println("休眠5s结束");
		return "返回成功了！";
		
	}

}
