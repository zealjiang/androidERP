package com.jzg.erp.update;

import com.jzg.erp.base.BaseObject;

/**
 * 应用程序更新实体类
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressWarnings("serial")
public class UpdateApp extends BaseObject {


    /**
     * VersionCode : 1
     * VersionName : 1.0.0
     * DownloadUrl : http://www.jingzhengu.com/app/yiqidazhong/JzgDzhg.apk
     * UpdateLog : 更新日志：修复动弹点赞问题修复图片保存图库未刷新BUG安装包大小：5.50MB
     */

    private SourceEntity Source;

    private SourceEntity Data;

    public SourceEntity getData() {
        return Data;
    }

    public void setData(SourceEntity data) {
        Data = data;
    }

    public SourceEntity getSource() {
        return Source;
    }

    public void setSource(SourceEntity Source) {
        this.Source = Source;
    }


    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public static class SourceEntity {
        private int VersionCode;
        private String VersionName;
        private String DownloadUrl;
        private String UpdateLog;
        private String UpdateForce;

        public String getUpdateForce() {
            return UpdateForce;
        }

        public void setUpdateForce(String updateForce) {
            UpdateForce = updateForce;
        }

        public int getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(int VersionCode) {
            this.VersionCode = VersionCode;
        }

        public String getVersionName() {
            return VersionName;
        }

        public void setVersionName(String VersionName) {
            this.VersionName = VersionName;
        }

        public String getDownloadUrl() {
            return DownloadUrl;
        }

        public void setDownloadUrl(String DownloadUrl) {
            this.DownloadUrl = DownloadUrl;
        }

        public String getUpdateLog() {
            return UpdateLog;
        }

        public void setUpdateLog(String UpdateLog) {
            this.UpdateLog = UpdateLog;
        }
    }
}
