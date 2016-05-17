package com.twoforboth.draft.event;

import java.util.EventObject;
import com.twoforboth.draft.misc.DraftInfo;
/**
 * Title:        Draft
 * Description:  Full fledged draft applet that communicates with php pages acting as servlets.
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class DraftChangedEvent extends EventObject
{

  public static final int DRAFT_CHANGED_EVENT = java.awt.AWTEvent.RESERVED_ID_MAX + 1000;
  private DraftInfo di_ = null;

  public DraftChangedEvent(Object source, DraftInfo di)
  {
    super(source);
    di_ = di;
  }
  public DraftInfo getDraftInfo()
  {
    return di_;
  }
}