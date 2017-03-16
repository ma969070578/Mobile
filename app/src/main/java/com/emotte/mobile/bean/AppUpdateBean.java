package com.emotte.mobile.bean;

/**
 * Created by maq on 2016/8/15.
 */
public class AppUpdateBean extends Return {

    /**
     * desc : 发现***，版本号4.00,是否立即安装

     更新列表:

     【新增】***模块;
     【优化】程序稳定性功能;
     * forceUpdate : 1
     * type : 1
     * url : http://app.95081.com/gjb.apk
     * isUpdate : 2
     */

    private AppFile data;

    public AppFile getData() {
        return data;
    }

    public void setData(AppFile data) {
        this.data = data;
    }

    public static class AppFile {
        private String desc;
        private int forceUpdate;// 1不强制  2强制
        private int type;// 1：三方更新  2：自己服务器下载
        private String url;
        private int isUpdate; //1：不更新 2：更新

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getForceUpdate() {
            return forceUpdate;
        }

        public void setForceUpdate(int forceUpdate) {
            this.forceUpdate = forceUpdate;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getIsUpdate() {
            return isUpdate;
        }

        public void setIsUpdate(int isUpdate) {
            this.isUpdate = isUpdate;
        }
    }
}