package jmeter.plugins.functional.samplers.mydemo;


import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import jmeter.plugins.functional.samplers.mydemo.bean.UserMessage;

public class Client extends Thread {
    public static String host = "192.168.2.192";
    public static int port = 1112;
    public boolean isfirst=false;

    public void run() {
        super.run();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());
            Channel channel = bootstrap.connect(host, port).sync().channel();
            while (true) {
                if (channel != null) {
                    Log.e("-----","连接成功");
                    if(!isfirst){
                        setRegister(channel);
                        isfirst=true;
                    }
                    setHeart(channel);
                }else {
                    Log.e("-----","连接失败");
                }
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }
    /**
     * 发送心跳
     */
    public void setHeart(Channel channel){
        Map<String,String> map = new HashMap<>();
        map.put("type", UserMessage.Type.HEARTBEAT.getCode());
        ChannelFuture channelFuture1 = channel.writeAndFlush(JSON.toJSONString(map));
    }
    /**
     * 发送注册
     */
    public void setRegister(Channel channel){
        UserMessage userMessage = new UserMessage();
        userMessage.setAppId("5d9013d9baa48711ec03276f");
        userMessage.setType(UserMessage.Type.REGISTER.getCode());
        userMessage.setAuthParam("b2188cffcc120b8850509412c67e18b802e634256f86df405cd1b877a4cba3006857b6515d4eb983b5cffd453b42e970ea72939bfe20b8b478aa7dcdce3ea2126985450df98c79534c24b34f94cf08a928aca86fa30f654dd19defc3c19996f854d4e83429c8b7ade52157553333061101447ecd251019ff4df6f2184e936973ac92c0aea3ba0814df4b4591cc7e38c836fc8f6c0a28df37134c374726018aadd096a0c1659ed92f71a83590a774dd43");
        channel.writeAndFlush(userMessage.toString());
    }
}