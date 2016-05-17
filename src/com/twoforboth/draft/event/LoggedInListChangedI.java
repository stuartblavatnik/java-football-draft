package com.twoforboth.draft.event;

import java.util.EventListener;
/**
 * Title:        Draft
 * Description:  Full fledged draft applet that communicates with php pages acting as servlets.
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public interface LoggedInListChangedI extends EventListener
{
  public void LoggedInListChanged(LoggedInListChangedEvent loggedInListChangedChangedEvent);
}