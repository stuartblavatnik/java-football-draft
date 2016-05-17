package com.twoforboth.draft.thread;

import java.lang.Runnable;
import java.util.Vector;
import java.util.Enumeration;
import com.twoforboth.draft.misc.DraftInfo;
import com.twoforboth.draft.event.DraftChangedEvent;
import com.twoforboth.draft.panel.DraftPanel;
import com.twoforboth.draft.panel.SwingDraftPanel;
import com.twoforboth.communication.ServletInfo;
import com.twoforboth.communication.Servlet;
import com.twoforboth.communication.CantConnectException;

/**
 * Title:        LeagueInfoThread
 * Description:  Thread that retrieves the data from the leagueInfo table and
 *               fires events if the information has changed
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class LeagueInfoThread implements Runnable
{
    private long sleepTime_ = 0;
    private String leagueName_ = "";
    private String leagueYear_ = "";
    private Vector listenerList_ = null;
    private DraftInfo draftInfo_ = new DraftInfo();
    private boolean keepAlive_ = true;
    private String result_ = "";
    private Servlet draftInfoServlet_ = null;
    private Vector servletParameters_ = new Vector();

    /**
     * Constructor for league info thread
     * @param sleepTime   -- number of milliseconds between
     * @param leagueName  -- name of league
     * @param leagueYear  -- year of league
     * @param draftInfo   -- current draft info state
     */

    public LeagueInfoThread(Servlet draftInfoServlet,
                            long sleepTime,
                            String leagueName,
                            String leagueYear,
                            DraftInfo draftInfo)
    {
      sleepTime_ = sleepTime;
      leagueName_ = leagueName;
      leagueYear_ = leagueYear;
      draftInfo_ = draftInfo;
      draftInfoServlet_ = draftInfoServlet;
    }

    /**
     * Thread loop
     */

    public void run()
    {
      try
      {
        while(keepAlive_)
        {
          Thread.sleep(sleepTime_);

	  try
	  {
	    result_ = draftInfoServlet_.execute();
	  }
	  catch (CantConnectException c)
	  {
	    try
	    {
	      Thread.sleep(sleepTime_);
	    }
	    catch (InterruptedException ie)
	    {
	      ie.printStackTrace();
	    }
	  }
          if (draftPositionChanged(result_))
          {
            DraftInfo di = new DraftInfo(result_);
            fireDraftChanged(di);
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }

    /**
     * Turn off the thread
     */

    public void kill()
    {
      keepAlive_ = false;
    }

    /**
     * Determines if anything within the draft info object change
     * @param string Value retrived from servlet
     * @returns True if any of the values changed
     */

    private boolean draftPositionChanged(String string)
    {
      boolean retval = false;
      DraftInfo di = new DraftInfo(string);

      //If turn has changed
      if ((di.getPick().compareTo(draftInfo_.getPick()) != 0) ||
          (di.getRound().compareTo(draftInfo_.getRound()) != 0) ||
          (di.getTeamName().compareTo(draftInfo_.getTeamName()) != 0) ||
          (di.getLocked().compareTo(draftInfo_.getLocked()) != 0))
      {
        retval = true;
        //Update draftInfo_ to new values
        draftInfo_ = di;
      }
      di = null;

      return retval;
    }

/* Register a listener this events */
  public void addDraftChangedListener(Object l)
  {
    if (listenerList_ == null)
    {
      listenerList_ = new Vector();
    }
    listenerList_.addElement(l);
  }

  /** Remove a listener for this Events */
  public void removeDraftChangedListener(Object l)
  {
    if (listenerList_ == null)
    {
      listenerList_ = new Vector();
    }
    listenerList_.removeElement(l);
  }

  /** Fire a draftChanged to all registered listeners */
  protected synchronized void fireDraftChanged(DraftInfo di)
  {
    // if we have no listeners, do nothing...
    if (listenerList_ != null && !listenerList_.isEmpty())
    {
      // create the event object to send
      DraftChangedEvent event = new DraftChangedEvent(this, di);

      // make a copy of the listener list in case
      //   anyone adds/removes listeners
      Vector targets;
      synchronized (this)
      {
        targets = (Vector) listenerList_.clone();
      }

      // walk through the listener list and
      //   call the DraftChanged
      Enumeration e = targets.elements();
      while (e.hasMoreElements())
      {
        Object obj = e.nextElement();

        if (obj.getClass().getName().compareTo("com.twoforboth.draft.panel.DraftPanel") == 0)
        {
          DraftPanel l = (DraftPanel)obj;
          l.DraftChanged(event);
        }
        else if (obj.getClass().getName().compareTo("com.twoforboth.draft.panel.SwingDraftPanel") == 0)
        {
          SwingDraftPanel l = (SwingDraftPanel)obj;
          l.DraftChanged(event);
        }
      }
    }
  }
}