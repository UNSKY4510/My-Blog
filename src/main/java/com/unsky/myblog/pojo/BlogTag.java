package com.unsky.myblog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author UNSKY
 * @date 2022年4月20日 21:55
 */
public class BlogTag {
    private int tagId;
    private String tagName;
    private Byte isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public BlogTag() {
    }

    public BlogTag(int tagId, String tagName, Byte isDeleted, Date createTime) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.isDeleted = isDeleted;
        this.createTime = createTime;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BlogTag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                '}';
    }
}
