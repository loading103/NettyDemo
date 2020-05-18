package jmeter.plugins.functional.samplers.mydemo.bean;


import com.alibaba.fastjson.JSON;

public class UserMessage {
    /**
     * 秘钥
     */
    private String publicKey;
    private String aesKey;
    private String aesIv;
    /**
     * token
     */
    private String authParam;
    /**
     * 用户信息类别
     * 1=交换公钥
     * 2=交换aesKey
     * 3=心跳
     * 4=用户消息
     * 5=token相关
     * 6=用户秘钥信息完善
     */
    private String type;
    /**
     * 应用id
     */
    private String appId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 消息内容
     */
    private String data;
    /**
     * 响应状态
     */
    private String response;
    
    public enum Type{
        REGISTER("1","register"),
        PUBLIC_KEY("2","public key"),
        AES_KEY("3","aes key"),
        HEARTBEAT("4","heartbeat"),
        USER_MESSAGE("5","user message");


        String code;
        String desc;
        
        Type(String code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
        
    }

    public enum Response{
        SUCCESS("1","success"),
        FAIL("2","fail");
        
        String code;
        String desc;

        Response(String code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getAesIv() {
        return aesIv;
    }

    public void setAesIv(String aesIv) {
        this.aesIv = aesIv;
    }

    public String getAuthParam() {
        return authParam;
    }

    public void setAuthParam(String authParam) {
        this.authParam = authParam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
