package com.twoforboth.draft.thread;

import java.lang.Runnable;
import java.util.Vector;

import com.twoforboth.communication.Servlet;
import com.twoforboth.communication.ServletInfo;
import com.twoforboth.communication.CantConnectException;

/**
 * Title:        HeartBeatThread
 * Description:  Heartbeat for individual teams to indicate that they are still
 *               logged into the draft system
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

 /*
    Indicate to the server that the user is still around
 */

public class HeartBeatThread implements Runnable
{
  private long sleepTime_ = 0;
  private Servlet heartbeatServlet_ = null;
  private boolean keepAlive_ = true;

  public HeartBeatThread(Servlet heartbeatServlet,
                         long sleepTime)
  {
    sleepTime_ = sleepTime;
    heartbeatServlet_ = heartbeatServlet;
  }

  public void kill()
  {
    keepAlive_ = false;
  }

  public void resume()
  {
    keepAlive_ = true;
    run();
  }

  public void run()
  {
    while(keepAlive_)
    {
      try
      {
	Thread.sleep(sleepTime_);
      }
      catch (InterruptedException e)
      {
      }
      try
      {
	heartbeatServlet_.execute();
      }
      catch (CantConnectException e)
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
}