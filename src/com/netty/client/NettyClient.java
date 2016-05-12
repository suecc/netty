package com.netty.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.netty.client.callable.MyTask;
import com.netty.client.handler.ClientChannelHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**  
 * 2016年5月9日10:55:50 
 * @author Sue  
 * @version 1.0   
 * @since JDK 1.8.0_60  
 * NettyClient.java
 */
public class NettyClient {

	public final static String msg = "丘以书"+System.getProperty("line.separator");
	
	public void connect(String host, int port) throws Exception{
		
		//线程组
		EventLoopGroup group = new NioEventLoopGroup();
		
		try{
			
			Bootstrap boostrap = new Bootstrap();
						boostrap.group(group)
								.channel(NioSocketChannel.class)
								.option(ChannelOption.TCP_NODELAY, true)
								.handler(new ClientChannelInitializer());
			
						//链接回调
						ChannelFuture future = boostrap.connect(host, port).sync();
						
						//异步关闭
						future.channel().closeFuture().sync();
						System.out.println("关闭");
						
		}catch(Exception e){
			
		}finally{
			
			group.shutdownGracefully();
		}
		
		
	}
	
	
	private class ClientChannelInitializer extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel channel) throws Exception {
			System.out.println("客户端: initChannel");
			
			channel.pipeline().addLast("lineBased", new LineBasedFrameDecoder(1024));
			channel.pipeline().addLast("stringDecode", new StringDecoder());
			channel.pipeline().addLast(new  ClientChannelHandler());
		}
		
	} 
	
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		
		int port = 8089;
		
		if(args!=null && args.length>0){
			
			try{
				port = Integer.valueOf(args[0]);
			}catch(NumberFormatException e){
				//默认处理
			}
		}
		
		try {
			
			//启动连接
			new NettyClient().connect("127.0.0.1", 8089);
			
//			CountDownLatch await = new CountDownLatch(2);
//			await.await();
			
//			ExecutorService pool = Executors.newFixedThreadPool(1);
//			Future<String> f = pool.submit(new MyTask());
//			System.out.println("f.isDone(): "+f.isDone());
//			
//			System.out.println("执行其余线程...");
//			Thread.currentThread().sleep(5000);
//			System.out.println("执行其余线程结束");
//			
//			System.out.println("返回结果:"+f.get());
//			System.out.println("返回结果 Done.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		long end = System.currentTimeMillis();
		
		System.out.println("共耗时: "+(end-start)+" ms");
	}

}
  
