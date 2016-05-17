package com.twoforboth.draft.thread;

import java.lang.Runnable;
import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

import com.twoforboth.draft.misc.DraftInfo;
import com.twoforboth.draft.event.NewMessagesArrivedEvent;
import com.twoforboth.draft.panel.DraftPanel;
import com.twoforboth.draft.panel.SwingDraftPanel;
import com.twoforboth.communication.CantConnectException;
import com.twoforboth.communication.Servlet;
import com.twoforboth.communication.ServletInfo;

/**
 * Title:        Draft
 * Description:  Full fledged draft applet that communicates with php pages acting as servlets.
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class NewMessagesThread implements Runnable
{
    private final String delimeter_ = "`";
    private long sleepTime_ = 0;
    private String leagueName_ = "";
    private String leagueYear_ = "";
    private Vector listenerList_ = null;
    private DraftInfo draftInfo_ = new DraftInfo();
    private boolean keepAlive_ = true;
    private String result_ = "";
    private Vector v_ = new Vector();
    private String lastTime_ = "0";
    private Servlet newMessagesServlet_ = null;
    private StringBuffer sb_ = new StringBuffer();

    /**
     * Constructor for league info thread
     * @param sleepTime   -- number of milliseconds between
     * @param leagueName  -- name of league
     * @param leagueYear  -- year of league
     */

    public NewMessagesThread(Servlet newMessagesServlet,
                             long sleepTime,
                             String leagueName,
                             String leagueYear)
    {
      newMessagesServlet_ = newMessagesServlet;
      sleepTime_ = sleepTime;
      leagueName_ = leagueName;
      leagueYear_ = leagueYear;
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

          sb_.setLength(0);
          sb_.append(leagueYear_);
          sb_.append(ServletInfo.DELIMITER);
          sb_.append(leagueName_);
          sb_.append(ServletInfo.DELIMITER);
          sb_.append(lastTime_);
	  try
	  {
	    result_ = newMessagesServlet_.execute(sb_.toString());
	  }
	  catch (CantConnectException cce)
	  {
	    Thread.sleep(sleepTime_);
	  }
          if (result_.length() > 0)
          {
            if (result_.compareTo(lastTime_) != 0)
            {
              updateMessageVector(result_);
              fireNewMessagesArrived(v_);
            }
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

    //Put results into vector
    // NOTE -- TOKENS SEPARATED BY delimeter_
    //First Item will be time of last message
    //Make sure to update lastTime_;

    private void updateMessageVector(String result)
    {
      v_.clear(); //clear message vector
      StringTokenizer st = new StringTokenizer(result, delimeter_, false);
      int i = 0;
      while (st.hasMoreTokens())
      {
        if (i == 0)
        {
          lastTime_ = st.nextToken();
        }
        else
        {
          v_.add(st.nextToken());
        }
        i++;
      }
    }

/* Register a listener this events */
    public void addNewMessagesArrivedListener(Object l)
    {
      if (listenerList_ == null)
      {
        listenerList_ = new Vector();
      }
      listenerList_.addElement(l);
    }

  /** Remove a listener for this Events */
  public void removeNewMessagesArrivedListener(Object l)
  {
    if (listenerList_ == null)
    {
      listenerList_ = new Vector();
    }
    listenerList_.removeElement(l);
  }

  /** Fire a draftChanged to all registered listeners */
  protected synchronized void fireNewMessagesArrived(Vector v)
  {
    // if we have no listeners, do nothing...
    if (listenerList_ != null && !listenerList_.isEmpty())
    {
      // create the event object to send
      NewMessagesArrivedEvent event = new NewMessagesArrivedEvent(this, v);

      // make a copy of the listener list in case
      //   anyone adds/removes listeners
      Vector targets;
      synchronized (this)
      {
        targets = (Vector) listenerList_.clone();
      }

      // walk through the listener list and
      //   call NewMessagesArrived
      Enumeration e = targets.elements();
      while (e.hasMoreElements())
      {
        Object obj = e.nextElement();
        if (obj.getClass().getName().compareTo("com.twoforboth.draft.panel.DraftPanel") == 0)
        {
          DraftPanel l = (DraftPanel)obj;
          l.NewMessagesArrived(event);
        }
        else if (obj.getClass().getName().compareTo("com.twoforboth.draft.panel.SwingDraftPanel") == 0)
        {
          SwingDraftPanel l = (SwingDraftPanel)obj;
          l.NewMessagesArrived(event);
        }
      }
    }
  }
}