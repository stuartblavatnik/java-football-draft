package com.twoforboth.draft;

import java.applet.Applet;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Component;

import netscape.javascript.JSObject;

import com.twoforboth.draft.panel.LoginPanel;
import com.twoforboth.draft.panel.DraftPanel;

/**
 * Title:        Draft
 * Description:  Full fledged draft applet that communicates with php pages
 *               acting as servlets.
 * Copyright:    Copyright (c) 2001
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class Draft extends Applet
{
  private final String ADMIN_PARAM = "Admin";
  private final String VALID_ADMIN_PARAM = "1";
  private final String DEMO_PARAM = "Demo";
  private final String VALID_DEMO_PARAM = "1";

  private final String LEAGUE_NAME_PARAM = "LeagueName";
  private final String LEAGUE_YEAR_PARAM = "LeagueYear";
  private final String TEAM_NAME_PARAM = "TeamName";
  private final String PASSWORD_PARAM = "Password";

  private String leagueYear_ = "";
  private String leagueName_ = "";
  private String teamName_ = "";
  private String password_ = "";
  private Frame parentFrame_ = null;
  private boolean draftLocked_ = false;
  private boolean draftComplete_ = false;
  private boolean admin_ = false;
  private boolean demo_ = false;

  private DraftPanel draftPanel_ = null;
  private LoginPanel loginPanel_ = null;

  public void setLeagueYear(String leagueYear) { leagueYear_ = leagueYear; }
  public String getLeagueYear() { return leagueYear_; }
  public void setLeagueName(String leagueName) { leagueName_ = leagueName; }
  public String getLeagueName() { return leagueName_; }
  public void setTeamName(String teamName) { teamName_ = teamName; }
  public String getTeamName() { return teamName_; }
  public Frame getParentFrame() { return parentFrame_; }
  public void setDraftLocked(boolean draftLocked) { draftLocked_ = draftLocked; }
  public boolean getDraftLocked() { return draftLocked_; }
  public void setDraftComplete(boolean draftComplete) { draftComplete_ = draftComplete; }
  public boolean getDraftComplete() { return draftComplete_; }
  public String getPassword() { return password_; }

  /**
   * Entry point for applet
   */

  public void init()
  {
    if (getParameter(ADMIN_PARAM) != null)
    {
      if (getParameter(ADMIN_PARAM).equalsIgnoreCase(VALID_ADMIN_PARAM))
      {
        admin_ = true;
      }
    }

    if (getParameter(DEMO_PARAM) != null)
    {
      if (getParameter(DEMO_PARAM).equalsIgnoreCase(VALID_DEMO_PARAM))
      {
	demo_ = true;
      }
    }

    if (getParameter(LEAGUE_NAME_PARAM) != null)
    {
      leagueName_ = getParameter(LEAGUE_NAME_PARAM);
    }
    if (getParameter(LEAGUE_YEAR_PARAM) != null)
    {
      leagueYear_ = getParameter(LEAGUE_YEAR_PARAM);
    }
    if (getParameter(TEAM_NAME_PARAM) != null)
    {
      teamName_ = getParameter(TEAM_NAME_PARAM);
    }
    if (getParameter(PASSWORD_PARAM) != null)
    {
      password_ = getParameter(PASSWORD_PARAM);
    }

    createParentFrame();
    showLoginPanel();
  }

  public void destroy()
  {
    if (draftPanel_ != null)
    {
      draftPanel_.doLogout();
    }
  }

  /**
   * Displays the draft panel
   */

  public void showDraftPanel()
  {
    //Create draftPanel
    draftPanel_ = new DraftPanel(this, admin_);
    removeAll();
    setLayout(new BorderLayout());

    add(draftPanel_, BorderLayout.NORTH);
    doLayout();
    validate();
  }

  private void createParentFrame()
  {
    parentFrame_ = new Frame();
    parentFrame_.setSize(this.getSize());
    parentFrame_.setVisible(false);
  }

  /**
   * Displays the login panel
   */

  private void showLoginPanel()
  {
    removeAll();    //Remove all objects from Applet (which is a container)
    loginPanel_ = new LoginPanel(this, demo_);
    setLayout(new GridLayout(1, 1));
    add(loginPanel_);
    doLayout();
    validate();
  }
}