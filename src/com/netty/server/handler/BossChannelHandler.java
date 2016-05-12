package com.netty.server.handler;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**   
*    
* 项目名称：netty-server   
* 类名称：BossChannelHandler   
* 类描述：   
* 创建人：yshqiu   
* 创建时间：2016年5月9日 下午7:07:36   
* @version   
*    
*/
public class BossChannelHandler extends ChannelHandlerAdapter {

	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception{
		
		
		System.out.println("bind to "+promise.channel().id());
	} 
	
}
