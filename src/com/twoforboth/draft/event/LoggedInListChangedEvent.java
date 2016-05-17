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

public class LoggedInListChangedEvent extends EventObject
{
  public static final int LOGGED_IN_LIST_CHANGED_EVENT = java.awt.AWTEvent.RESERVED_ID_MAX + 1001;
  private Vector v_ = null;

  public LoggedInListChangedEvent(Object source, Vector v)
  {
    super(source);
    v_ = v;
  }
  public Vector getLoggedInList()
  {
    return v_;
  }
}