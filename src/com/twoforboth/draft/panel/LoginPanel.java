package com.twoforboth.draft.panel;

import java.awt.Panel;
import java.awt.Event;
import java.applet.Applet;

import java.awt.TextField;
import java.awt.Label;
import java.awt.Button;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import java.util.Vector;

import com.twoforboth.draft.Draft;
import com.twoforboth.communication.ServletInfo;
import com.twoforboth.communication.Servlet;
import com.twoforboth.communication.CantConnectException;

/**
 * Title:        Draft
 * Description:  Full fledged draft applet that communicates with php pages acting as servlets.
 * Copyright:    Copyright (c) 2001
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class LoginPanel extends Panel implements ActionListener
{
  private static final String LOGIN_OK = "Y";

  private Draft draftApplet_;
  private Servlet loginServlet_ = null;

  private GridBagLayout gridBagLayoutLoginTop_ = new GridBagLayout();
  private Label lblLeagueYear_ = new Label();
  private Label lblLeagueName_ = new Label();
  private Label lblTeamName_ = new Label();
  private Label lblPassword_ = new Label();
  private TextField txtLeagueYear_ = new TextField();
  private TextField txtPassword_ = new TextField();
  private TextField txtTeamName_ = new TextField();
  private TextField txtLeagueName_ = new TextField();
  private Button btnLogin_ = new Button();
  private Label lblInvalidLogin_ = new Label();
  private StringBuffer sb_ = new StringBuffer();

  /**
   * Constructor that draws the login panel
   * @param draftApplet Applet container for this panel
   * @param demo If true then fill in login information
   */

  public LoginPanel(Draft draftApplet,
		    boolean demo)
  {
    draftApplet_ = draftApplet;

    loginServlet_ = new Servlet(ServletInfo.DO_LOGIN_SERVLET_NAME,
                                ServletInfo.DO_LOGIN_SERVLET_PARAMETERS);

    lblLeagueYear_.setText("League Year:");
    setLayout(gridBagLayoutLoginTop_);
    lblLeagueName_.setText("League Name:");
    lblTeamName_.setText("Team Name:");
    lblPassword_.setText("Password:");
    if (demo)
    {
      //Eventually make this smarter so that it can determine the next team to log in as
      txtLeagueYear_.setText("2002");
      txtLeagueName_.setText("Demo");
      txtPassword_.setText("Team1");
      txtTeamName_.setText("Team1");
    }
    else if (draftApplet_.getTeamName().length() > 0)
    {
      txtLeagueYear_.setText(draftApplet_.getLeagueYear());
      txtLeagueName_.setText(draftApplet_.getLeagueName());
      txtPassword_.setText(draftApplet_.getPassword());
      txtTeamName_.setText(draftApplet_.getTeamName());
    }
    else
    {
      txtLeagueYear_.setColumns(10);
      txtLeagueName_.setColumns(10);
      txtPassword_.setColumns(10);
      txtTeamName_.setColumns(10);
    }
    txtPassword_.setEchoChar('*');
    btnLogin_.setActionCommand("");
    btnLogin_.setLabel("Log In");
    lblInvalidLogin_.setText("Invalid Login");
    lblInvalidLogin_.setVisible(false);

    add(lblPassword_,
             new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
             GridBagConstraints.NORTHWEST,
             GridBagConstraints.NONE,
             new Insets(0, 0, 0, 0), 0, 0));
    add(lblLeagueName_,
             new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
             GridBagConstraints.NORTHWEST,
             GridBagConstraints.NONE,
             new Insets(0, 0, 0, 0), 0, 0));
    add(lblLeagueYear_,
             new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
             GridBagConstraints.NORTHWEST,
             GridBagConstraints.NONE,
             new Insets(0, 0, 0, 0), 0, 0));
    add(txtLeagueYear_,
             new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0,
             GridBagConstraints.NORTHWEST,
             GridBagConstraints.HORIZONTAL,
             new Insets(0, 0, 0, 0), 0, 0));
    add(txtLeagueName_,
             new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
             GridBagConstraints.NORTHWEST,
             GridBagConstraints.HORIZONTAL,
             new Insets(0, 0, 0, 0), 0, 0));
    add(txtTeamName_,
             new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
             GridBagConstraints.NORTHWEST,
             GridBagConstraints.HORIZONTAL,
             new Insets(0, 0, 0, 0), 0, 0));
    add(txtPassword_,
             new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
             GridBagConstraints.NORTHWEST,
             GridBagConstraints.HORIZONTAL,
             new Insets(0, 0, 0, 0), 0, 0));
    add(lblTeamName_,
             new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
             GridBagConstraints.NORTHWEST,
             GridBagConstraints.NONE,
             new Insets(0, 0, 0, 0), 0, 0));
    add(btnLogin_,
             new GridBagConstraints(1, 4, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER,
            GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));
    add(lblInvalidLogin_,
             new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
             GridBagConstraints.CENTER,
             GridBagConstraints.NONE,
             new Insets(0, 0, 0, 0), 0, 0));

    btnLogin_.addActionListener(this);


  }

  /**
   * Event handler for login button
   * @param ae ActionEvent
   */

  public void actionPerformed(ActionEvent ae)
  {
    Object target = ae.getSource();
    if (target == btnLogin_)
    {
      doLogin();
    }
  }

  /**
   * Attempts to log the user in and displays an error message
   * if an error exists
   */

  private void doLogin()
  {
    String loginResult = "";

    sb_.setLength(0);
    sb_.append(txtLeagueYear_.getText());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(txtLeagueName_.getText());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(txtTeamName_.getText());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(txtPassword_.getText());

    try
    {
       loginResult = loginServlet_.execute(sb_.toString());
    }
    catch (CantConnectException cce)
    {
      loginResult = "";
    }

    if (loginResult.compareToIgnoreCase(LOGIN_OK) == 0)
    {
        draftApplet_.setLeagueYear(txtLeagueYear_.getText());
        draftApplet_.setLeagueName(txtLeagueName_.getText());
        draftApplet_.setTeamName(txtTeamName_.getText());
        draftApplet_.showDraftPanel();
    }
    else
    {
      lblInvalidLogin_.setVisible(true);    //Display the error message
      validate();
    }
  }
}