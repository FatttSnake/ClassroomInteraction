package com.cfive.classroom.library.net.util;

import com.cfive.classroom.library.database.bean.AttStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageObject implements Serializable {
    private String stuNo;
    private String stuName;
    private String code;
    private String count;
    private String message;
    private AttStatus attStatus;
    private LocalDateTime localDateTime;
    private MessageType messageType;


    public MessageObject(String stuNo, String name, String code, String message,String count,AttStatus attStatus,LocalDateTime localDateTime,MessageType messageType) {
        this.stuNo = stuNo;
        this.stuName = name;
        this.code = code;
        this.message = message;
        this.count = count;
        this.attStatus=attStatus;
        this.localDateTime=localDateTime;
        this.messageType=messageType;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public AttStatus getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(AttStatus attStatus) {
        this.attStatus = attStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "MessageObject{" +
                "stuNo='" + stuNo + '\'' +
                ", stuName='" + stuName + '\'' +
                ", code='" + code + '\'' +
                ", count='" + count + '\'' +
                ", message='" + message + '\'' +
                ", attStatus=" + attStatus +
                ", localDateTime=" + localDateTime +
                ", messageType=" + messageType +
                '}';
    }
}
