package jmeter.plugins.functional.samplers.mydemo;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import jmeter.plugins.functional.samplers.mydemo.bean.RsaUtil;
import jmeter.plugins.functional.samplers.mydemo.bean.UserMessage;
import jmeter.plugins.functional.samplers.mydemo.util.AesUtil;

import static jmeter.plugins.functional.samplers.mydemo.util.AesUtil.generateIv;
import static jmeter.plugins.functional.samplers.mydemo.util.AesUtil.generateKey;


public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private String key;     //本地AES秘钥
    private String iv;      //本地AES偏量
    private String serve_key;
    private String serve_iv;


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.err.println("TCPReadHandler channelInactive()");
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.err.println("TCPReadHandler exceptionCaught()");
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }

    }

    /**
     * 收到服务器消息
     *  type=2,注册成功，服务器返回一个公钥， 向服务器发送本地AES秘钥，AES偏量
     *   type=3,发送本地AES秘钥，AES偏量成功，服务器服务器AES秘钥，AES偏量
     *    type=5,正常接收消息，用服务器AES秘钥，AES偏量解密数据data
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Log.e("收到服务器消息------：",msg.toString());
        UserMessage loginBean = new Gson().fromJson(msg.toString(), UserMessage.class);
        if(loginBean.getType().equals("2") && !TextUtils.isEmpty(loginBean.getPublicKey())){
            sendAeskey(ctx,loginBean.getPublicKey());
        }else  if(loginBean.getType().equals("3")){
            serve_iv = AesUtil.decrypt(key,iv, loginBean.getAesIv());
            serve_key = AesUtil.decrypt(key,iv, loginBean.getAesKey());
        }else  if(loginBean.getType().equals("5")){
            String bean = AesUtil.decrypt(serve_key, serve_iv, loginBean.getData());
            Log.e("收到服务器消息解析结果：",bean);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }


    /**
     * 发送key和偏量（本地先生成key和iv，用rsa加密发送给服务器）
     */
    public void sendAeskey(ChannelHandlerContext ctx,String publickey) {
        UserMessage userMessage = new UserMessage();
        userMessage.setType(UserMessage.Type.AES_KEY.getCode());
        key = generateKey();
        iv = generateIv();
        try {
            String encrypts = RsaUtil.encrypts(key, publickey);
            String encryptiv = RsaUtil.encrypts(iv, publickey);
            userMessage.setAesKey(encrypts);
            userMessage.setAesIv(encryptiv);

            Log.e("--key-：",key);
            Log.e("--iv-：",iv);
            Log.e("--key加密后-：",key);
            Log.e("--iv加密后-：",iv);
            Log.e("--publickey-：",publickey);

            ctx.writeAndFlush(userMessage.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}