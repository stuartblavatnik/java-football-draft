package com.twoforboth.draft.panel;

import java.lang.NumberFormatException;

import javax.swing.JPanel;

import java.awt.Event;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JApplet;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import java.util.Vector;

import com.twoforboth.draft.SwingDraft;

import com.twoforboth.communication.ServletInfo;
import com.twoforboth.communication.Servlet;
import com.twoforboth.communication.CantConnectException;

/**
 * Title:        Draft
 * Description:  Scrambled Eggs Football Draft Application
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class SwingLoginPanel extends JPanel implements ActionListener
{

  private static final String LOGIN_OK = "Y";

  private SwingDraft draftApplet_;
  private Servlet loginServlet_ = null;
  private Servlet teamNumberFromNameServlet_ = null;

  private GridBagLayout gridBagLayoutLoginTop_ = new GridBagLayout();
  private JLabel lblLeagueYear_ = new JLabel();
  private JLabel lblLeagueName_ = new JLabel();
  private JLabel lblTeamName_ = new JLabel();
  private JLabel lblPassword_ = new JLabel();
  private JTextField txtLeagueYear_ = new JTextField();
  private JPasswordField pwdPassword_ = new JPasswordField();
  private JTextField txtTeamName_ = new JTextField();
  private JTextField txtLeagueName_ = new JTextField();
  private JButton btnLogin_ = new JButton("Log in");
  private JLabel lblInvalidLogin_ = new JLabel();

  private StringBuffer sb_ = new StringBuffer();

  /**
   * Constructor that draws the login panel
   * @param draftApplet Applet container for this panel
   * @param demo Fills in login information if true
   */

  public SwingLoginPanel(SwingDraft draftApplet,
			 boolean demo)
  {
    draftApplet_ = draftApplet;

    this.setLayout(gridBagLayoutLoginTop_);

    loginServlet_ = new Servlet(ServletInfo.DO_LOGIN_SERVLET_NAME,
                                ServletInfo.DO_LOGIN_SERVLET_PARAMETERS);

    teamNumberFromNameServlet_ = new Servlet(ServletInfo.GET_FANTASY_TEAM_NUMBER_FROM_NAME_SERVLET,
                                             ServletInfo.GET_FANTASY_TEAM_NUMBER_FROM_NAME_SERVLET_PARAMETERS);


    lblLeagueYear_.setText("League Year:");
    lblLeagueName_.setText("League Name:");
    lblTeamName_.setText("Team Name:");
    lblPassword_.setText("Password:");
    txtLeagueYear_.setColumns(4);
    txtLeagueYear_.setToolTipText("Enter the year of the league here");
    pwdPassword_.setColumns(15);
    pwdPassword_.setEchoChar('*');

    pwdPassword_.setToolTipText("Enter your password here");
    txtTeamName_.setColumns(15);
    txtTeamName_.setToolTipText("Enter your team name here");
    txtLeagueName_.setColumns(15);
    txtLeagueName_.setToolTipText("Enter your league name here");
    btnLogin_.setActionCommand("");

    if (demo)
    {
      txtLeagueYear_.setText("2003");
      pwdPassword_.setText("Team1");
      txtTeamName_.setText("Team1");
      txtLeagueName_.setText("Test");
    }
    else if (draftApplet_.getTeamName().length() > 0)
    {
      txtLeagueYear_.setText(draftApplet_.getLeagueYear());
      txtLeagueName_.setText(draftApplet_.getLeagueName());
      pwdPassword_.setText(draftApplet_.getPassword());
      txtTeamName_.setText(draftApplet_.getTeamName());
    }


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
    add(pwdPassword_,
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

    btnLogin_.setToolTipText("Click here to log in");
    btnLogin_.addActionListener(this);

    this.setBackground(new Color(255, 255, 204));
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
    sb_.append(pwdPassword_.getPassword()); //getText());

    try
    {
      loginResult = loginServlet_.execute(sb_.toString());
    }
    catch (CantConnectException e)
    {
      loginResult = "";
    }

    if (loginResult.compareToIgnoreCase(LOGIN_OK) == 0)
    {
        draftApplet_.setLeagueYear(txtLeagueYear_.getText());
        draftApplet_.setLeagueName(txtLeagueName_.getText());
        draftApplet_.setTeamName(txtTeamName_.getText());

        //Get the team number from team name and store here
        //
        sb_.setLength(0);
        sb_.append(txtLeagueYear_.getText());
        sb_.append(ServletInfo.DELIMITER);
        sb_.append(txtLeagueName_.getText());
        sb_.append(ServletInfo.DELIMITER);
        sb_.append(txtTeamName_.getText());

        int teamNumber = -1;

        try
        {
          teamNumber = Integer.parseInt(teamNumberFromNameServlet_.execute(sb_.toString()));
        }
        catch (CantConnectException e)
        {
        }
        catch (NumberFormatException nfe)
        {
        }

        draftApplet_.setTeamNumber(teamNumber);
        draftApplet_.showDraftPanel();
    }
    else
    {
      lblInvalidLogin_.setVisible(true);    //Display the error message
      validate();
    }
  }
}