package com.ai.cloud.client.pojo;

/**
 * @author xiaoniu 2018/3/27.
 */
public class MsgReqParam {

    /**
     * 业务类别编码
     */
    private String bizCode;
    /**
     * 业务名称
     */
    private String bizName;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 内容
     */
    private String content;
    /**
     * 手机号码
     */
    private  String target;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "MsgReqParam{" +
                "bizCode='" + bizCode + '\'' +
                ", bizName='" + bizName + '\'' +
                ", taskName='" + taskName + '\'' +
                ", content='" + content + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
