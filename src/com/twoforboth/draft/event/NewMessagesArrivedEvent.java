package com.twoforboth.draft.event;

import java.util.EventObject;
import java.util.Vector;

/**
 * Title:        Draft
 * Description:  Full fledged draft applet that communicates with php pages acting as servlets.
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class NewMessagesArrivedEvent extends EventObject
{
  public static final int NEW_MESSAGES_ARRIVED_EVENT = java.awt.AWTEvent.RESERVED_ID_MAX + 1002;
  private Vector v_ = new Vector();

  public NewMessagesArrivedEvent(Object source, Vector v)
  {
    super(source);
    v_ = v;
  }
  public Vector getMessages()
  {
    return v_;
  }
}