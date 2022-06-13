package com.cfive.classroom.student;

import com.cfive.classroom.library.net.util.MessageObject;

public interface ChatReceiveListener {
    void onReceive(MessageObject messageObject);
}
