package com.twoforboth.draft.misc;

import java.util.StringTokenizer;

/**
 * Title:        DraftInfo
 * Description:  Full fledged draft applet that communicates with php pages acting as servlets.
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class DraftInfo
{
  private String round_ = "";
  private String pick_ = "";
  private String teamName_ = "";
  private String locked_ = "";
  private StringTokenizer st_ = null;

  public final static String DRAFT_INFO_DELIMITER = ",";

  public DraftInfo() { }

  public DraftInfo(String string)
  {
    parseAll(string);
  }

  public void parseAll(String string)
  {
    try
    {
      st_ = new StringTokenizer(string, DRAFT_INFO_DELIMITER, false);
      int i = 0;

      while (st_.hasMoreElements())
      {
        switch (i)
        {
          case 0:
            round_ = st_.nextToken();
            break;
          case 1:
            pick_ = st_.nextToken();
            break;
          case 2:
            teamName_ = st_.nextToken();
            break;
          case 3:
            locked_ = st_.nextToken();
            break;
        }
        i++;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public String getRound() { return round_; }
  public String getPick() { return pick_; }
  public String getTeamName() { return teamName_; }
  public String getLocked() { return locked_; }

  public void setRound(String round) { round_ = round; }
  public void setPick(String pick) { pick_ = pick; }
  public void setTeamName(String teamName) { teamName_ = teamName; }
  public void setLocked(String locked) { locked_ = locked; }
  public void setAll(String round, String pick, String teamName, String locked)
  {
    round_ = round;
    pick_ = pick;
    teamName_ = teamName;
    locked_ = locked;
  }
}