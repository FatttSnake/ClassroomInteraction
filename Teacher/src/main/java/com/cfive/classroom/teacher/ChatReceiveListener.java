package com.cfive.classroom.teacher;

import com.cfive.classroom.library.net.util.MessageObject;

public interface ChatReceiveListener {
    void onReceive(MessageObject messageObject);
}
