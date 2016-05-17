package com.twoforboth.draft.thread;

import java.lang.Runnable;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.Enumeration;
import com.twoforboth.draft.event.LoggedInListChangedEvent;
import com.twoforboth.draft.panel.DraftPanel;
import com.twoforboth.draft.panel.SwingDraftPanel;

//import com.twoforboth.draft.servlet.Servlet;
//import com.twoforboth.draft.servlet.ServletInfo;
//import com.twoforboth.draft.misc.MiscUtils;
//import com.twoforboth.draft.misc.CantConnectException;
import com.twoforboth.communication.Servlet;
import com.twoforboth.communication.ServletInfo;
import com.twoforboth.communication.CantConnectException;
import com.twoforboth.util.Misc;


/**
 * Title:        Draft
 * Description:  Full fledged draft applet that communicates with php pages acting as servlets.
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class LoggedInThread implements Runnable
{
    private long sleepTime_ = 0;
    private Vector listenerList_ = null;
    private boolean keepAlive_ = true;
    private String result_ = "";
    private Vector loggedIn_ = new Vector();
    private Servlet getAliveTeamsServlet_ = null;
    /**
     * Constructor for league info thread
     * @param sleepTime       -- number of milliseconds between
     * @param leagueName      -- name of league
     * @param leagueYear      -- year of league
     */

    public LoggedInThread(Servlet getAliveTeamsServlet,
                          long sleepTime,
                          Vector loggedInTeams)
    {
      sleepTime_ = sleepTime;
      getAliveTeamsServlet_ = getAliveTeamsServlet;
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
	    result_ = getAliveTeamsServlet_.execute();
	  }
	  catch (CantConnectException cce)
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
          if (teamListIsDifferent(result_))
          {
            loggedIn_ = Misc.getVectorFromDelimitedString(result_, ",");
            fireLoggedInListChanged(loggedIn_);
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
     * Determines if the values from result_ are the same as loggedIn_
     * Create a vector from result_ and compare.  Start by comparing
     * the size of each vector. Then do an element check
     * @param string -- comma separated list of currently logged in teams
     * from server
     * @returns true if the team list is different
     */

    private boolean teamListIsDifferent(String string)
    {
      StringTokenizer st = null;
      boolean retval = false;
      Vector v = null;

      try
      {
        st = new StringTokenizer(string, ",", false);
        if (st.countTokens() != loggedIn_.size())
        {
          retval = true;
        }
        else
        {
          v = new Vector();
          while (st.hasMoreElements())
          {
            v.add(st.nextToken());
          }
          for (int i = 0; i < v.size(); i++)
          {
            if (loggedIn_.indexOf(v.elementAt(i)) == -1)
            {
              retval = true;
              break;
            }
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        v = null;
        st = null;
      }
      return retval;
    }

  /**
   * Register a listener for this event
   * @param l any object
   */

  public void addLoggedInChangedListener(Object l)
  {
    if (listenerList_ == null)
    {
      listenerList_ = new Vector();
    }
    listenerList_.addElement(l);
  }

  /**
   * Removes a listener for this event
   * @param l any object
   */

  public void removeLoggedInChangedListener(Object l)
  {
    if (listenerList_ == null)
    {
      listenerList_ = new Vector();
    }
    listenerList_.removeElement(l);
  }

  /** Fire a draftChanged to all registered listeners */
  protected synchronized void fireLoggedInListChanged(Vector loggedIn_)
  {
    // if we have no listeners, do nothing...
    if (listenerList_ != null && !listenerList_.isEmpty())
    {
      // create the event object to send
      LoggedInListChangedEvent event = new LoggedInListChangedEvent(this, loggedIn_);

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
          l.LoggedInListChanged(event);
        }
        else if (obj.getClass().getName().compareTo("com.twoforboth.draft.panel.SwingDraftPanel") == 0)
        {
          SwingDraftPanel l = (SwingDraftPanel)obj;
          l.LoggedInListChanged(event);
        }
      }
    }
  }
}