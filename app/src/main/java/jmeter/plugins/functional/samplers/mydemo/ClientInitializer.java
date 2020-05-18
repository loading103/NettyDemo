package jmeter.plugins.functional.samplers.mydemo;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        //设置心跳
        pipeline.addLast(new IdleStateHandler(5, 5, 6, TimeUnit.SECONDS));
        pipeline.addLast("handler",new ClientHandler());

    }
}