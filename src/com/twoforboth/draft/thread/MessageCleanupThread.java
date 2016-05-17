package com.twoforboth.draft.thread;

import java.lang.Runnable;
import java.util.Vector;

import com.twoforboth.communication.ServletInfo;
import com.twoforboth.communication.Servlet;
import com.twoforboth.communication.CantConnectException;

/**
 * Title:        MessageCleanupThread
 * Description:  Calls servlet to delete draft messages that are n seconds old
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class MessageCleanupThread implements Runnable
{
  private long sleepTime_ = 0;
  private Servlet messageCleanupServlet_ = null;
  private boolean keepAlive_ = true;

  /**
   * Constructor
   * @param messageCleanupServlet Servlet to run
   * @param sleetTime How much to pause between invocation of servlet
   */

  public MessageCleanupThread(Servlet messageCleanupServlet,
                              long sleepTime)
  {
      sleepTime_ = sleepTime;
      messageCleanupServlet_ = messageCleanupServlet;
  }

  public void kill()
  {
    keepAlive_ = false;
  }

  public void run()
  {
    try
    {
      while(keepAlive_)
      {
        Thread.sleep(sleepTime_);
	try
	{
	  messageCleanupServlet_.execute();
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
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}