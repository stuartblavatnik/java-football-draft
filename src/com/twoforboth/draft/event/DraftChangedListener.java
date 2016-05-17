package com.twoforboth.draft.event;

import com.twoforboth.draft.misc.DraftInfo;
/**
 * Title:        Draft
 * Description:  Listener for draft changed event
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class DraftChangedListener implements DraftChangedListenerI
{
  DraftInfo di_ = new DraftInfo();

  public void DraftChanged(DraftChangedEvent dce)
  {
    di_ = dce.getDraftInfo();
  }

  public DraftInfo getDraftInfo()
  {
    return di_;
  }
}