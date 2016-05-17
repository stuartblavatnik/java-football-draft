package com.twoforboth.draft.event;

import java.util.EventListener;

/**
 * Title:        DraftChangedListenerI
 * Description:  Interface for Draft Changed Events
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public interface DraftChangedListenerI extends EventListener
{
  public void DraftChanged(DraftChangedEvent draftChangedEvent);
}