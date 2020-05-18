package jmeter.plugins.functional.samplers.mydemo.bean;

public class LoginBean {
    private String appId;
    private String authParam;
    private String publicKey;
    private String type;
    private String userId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAuthParam() {
        return authParam;
    }

    public void setAuthParam(String authParam) {
        this.authParam = authParam;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
