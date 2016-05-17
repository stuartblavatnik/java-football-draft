package com.twoforboth.draft.panel;

import java.applet.Applet;

import java.awt.Panel;
import java.awt.Label;
import java.awt.Button;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.TextField;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Point;

import java.util.Vector;

import com.twoforboth.draft.Draft;
import com.twoforboth.draft.misc.DraftInfo;
import com.twoforboth.controls.awt.ListPlus;
import com.twoforboth.util.Misc;

import com.twoforboth.draft.thread.HeartBeatThread;
import com.twoforboth.draft.thread.LeagueInfoThread;
import com.twoforboth.draft.thread.LoggedInThread;
import com.twoforboth.draft.thread.MessageCleanupThread;
import com.twoforboth.draft.thread.NewMessagesThread;
import com.twoforboth.draft.event.DraftChangedEvent;
import com.twoforboth.draft.event.DraftChangedListener;
import com.twoforboth.draft.event.DraftChangedListenerI;
import com.twoforboth.draft.event.LoggedInListChangedEvent;
import com.twoforboth.draft.event.LoggedInListChangedI;
import com.twoforboth.draft.event.NewMessagesArrivedEvent;
import com.twoforboth.draft.event.NewMessagesArrivedI;
import com.twoforboth.communication.Servlet;
import com.twoforboth.communication.ServletInfo;
import com.twoforboth.draft.panel.DraftConstants;
import com.twoforboth.communication.CantConnectException;

/**
 * Title:        DraftPanel
 * Description:  Main drafting screen includes lists for NFL teams, positions,
 *               available players and draft picks.
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class DraftPanel extends Panel implements ActionListener,
                                                 ItemListener,
                                                 DraftChangedListenerI,
                                                 LoggedInListChangedI,
                                                 NewMessagesArrivedI
{
  private Draft draftApplet_ = null;
  private DraftInfo draftInfo_ = null;

  private Panel roundInfoPanel_ = null;
  private Panel leagueInfoPanel_ = null;
  private Panel draftPanel_ = null;
  private Panel nflTeamsPanel_ = null;
  private Panel availablePanel_ = null;
  private Panel positionsPanel_ = null;
  private Panel emotionButtonPanel_ = null;

  private GridBagLayout gbl_ = new GridBagLayout();
  private Label lblRoundDesc_ = new Label(DraftConstants.LABEL_ROUND_DESC);
  private Label lblPickDesc_ = new Label(DraftConstants.LABEL_PICK_DESC);
  private Label lblTeamDesc_ = new Label(DraftConstants.LABEL_TEAM_DESC);
  private Label lblDraftDesc_ = new Label(DraftConstants.LABEL_DRAFT_DESC);
  private Label lblAvailableDesc_ =
      new Label(DraftConstants.LABEL_AVAILABLE_DESC);
  private Label lblNFLTeamsDesc_ =
      new Label(DraftConstants.LABEL_NFLTEAMS_DESC);
  private Label lblPositionsDesc_ =
      new Label(DraftConstants.LABEL_POSITIONS_DESC);
  private Label lblLoginTeamNameDesc_ =
      new Label(DraftConstants.LABEL_LOGIN_TEAM_NAME_DESC);
  private Label lblLeagueNameDesc_ =
      new Label(DraftConstants.LABEL_LEAGUE_NAME_DESC);
  private Label lblLeagueYearDesc_ =
      new Label(DraftConstants.LABEL_LEAGUE_YEAR_DESC);
  private Label lblByeWeeks_ =
      new Label(DraftConstants.LABEL_EXCLUDE_BYE_WEEKS_DESC);

  private Label lblRound_ = new Label();
  private Label lblPick_ = new Label();
  private Label lblTeam_ = new Label();

  private Label lblLoginTeamName_ = new Label();
  private Label lblLeagueName_ = new Label();
  private Label lblLeagueYear_ = new Label();

  private ListPlus lstDraft_ = new ListPlus();
  private ListPlus lstAvailable_ = new ListPlus();
  private ListPlus lstNFLTeams_ = new ListPlus();
  private ListPlus lstPositions_ = new ListPlus();
  private ListPlus lstByeWeeks_ = new ListPlus();

  //Placeholder list
  private List lstAvailablePlaceholder_ = new List();
  private List lstDraftPlaceholder_ = new List();

  //Hidden lists
  private ListPlus lstHiddenID_ = new ListPlus();

  //Chat lists
  private List lstUsers_ = new List();
  private List lstChat_ = new List();

  //Chat entry
  private TextField txtMessage_ = new TextField();

  private Button btnDraft_ = new Button(DraftConstants.BUTTON_DRAFT_DESC);
  private Button btnSearch_ = new Button(DraftConstants.BUTTON_SEARCH_DESC);
  private Button btnSelectAllTeams_ =
      new Button(DraftConstants.BUTTON_ALL_DESC);
  private Button btnDeselectAllTeams_ =
      new Button(DraftConstants.BUTTON_NONE_DESC);
  private Button btnSelectAllPositions_ =
      new Button(DraftConstants.BUTTON_ALL_DESC);
  private Button btnDeselectAllPositions_ =
      new Button(DraftConstants.BUTTON_NONE_DESC);
  private Button btnSelectAllByeWeeks_ =
      new Button(DraftConstants.BUTTON_ALL_DESC);
  private Button btnDeselectAllByeWeeks_ =
      new Button(DraftConstants.BUTTON_NONE_DESC);
  private Button btnPauseDraft_ =
      new Button(DraftConstants.BUTTON_PAUSE_DRAFT_DESC);
  private Button btnDeletePick_ =
      new Button(DraftConstants.BUTTON_DELETE_PICK_DESC);
  private Button btnHelp_ =
      new Button(DraftConstants.BUTTON_HELP_DESC);


  //Emotion Buttons
  private Button btnHappy_ = new Button(DraftConstants.BUTTON_HAPPY_DESC);
  private Button btnSad_ = new Button(DraftConstants.BUTTON_SAD_DESC);
  private Button btnCurse_ = new Button(DraftConstants.BUTTON_CURSE_DESC);

  //League Info Thread
  private Thread lit_ = null;
  private LeagueInfoThread leagueInfoThread_ = null;
  //Heartbeat Thread
  private Thread heartbeat_ = null;
  private HeartBeatThread heartbeatThread_ = null;
  //Logged in teams thread
  private Thread loggedInBaseThread_ = null;
  private LoggedInThread loggedInThread_ = null;
  //New messages thread
  private Thread newMessagesBaseThread_ = null;
  private NewMessagesThread newMessagesThread_ = null;
  //Message cleanup thread
  private Thread messageCleanupBaseThread_ = null;
  private MessageCleanupThread messageCleanupThread_ = null;

  //Draft Dialog Confirm
  private Dialog dlgConfirmDraft_ = null;
  private Button btnDraftYes_ = null;
  private Button btnDraftNo_ = null;

  private Vector loggedInTeams_ = new Vector();

  //Servlets
  private String servletParameters_ = "";
  private Servlet createDraftMessageServlet_ = null;
  private Servlet doDraftServlet_ = null;
  private Servlet getDraftInfoServlet_ = null;
  private Servlet getAliveTeamsServlet_ = null;
  private Servlet getNFLTeamsServlet_ = null;
  private Servlet heartbeatServlet_ = null;
  private Servlet getDraftRecordsServlet_ = null;
  private Servlet getNewMessagesServlet_ = null;
  private Servlet searchServlet_ = null;
  private Servlet toggleDraftServlet_ = null;
  private Servlet deletePickServlet_ = null;
  private Servlet messageCleanupServlet_ = null;
  private Servlet logoutServlet_ = null;

  private StringBuffer sb_ = new StringBuffer();

  private String[] positions_ = { "QB", "RB", "WR", "TE", "PK", "SP", "DE" };

  private Dialog dlgPlayerTaken_  = null;
  private Button btnPlayerTakenOK_ = null;

  private boolean admin_ = false;         //If true, then can draft for any team and can stop the draft
  private boolean draftLocked_ = false;

  /**
   * Constructor that call methods that draws controls and fills the information
   * @param draftApplet Container
   * @param admin boolean true if administrator
   */

  public DraftPanel(Draft draftApplet,
                    boolean admin)
  {
    draftApplet_ = draftApplet;
    admin_ = admin;

    String leagueYear = draftApplet_.getLeagueYear();
    String leagueName = draftApplet_.getLeagueName();
    String teamName   = draftApplet_.getTeamName();
    createServlets();

    drawEmptyControls();
    fillLoggedInControls();
    fillPositionList();
    try
    {
      lstNFLTeams_.fillList(getNFLTeamsServlet_.execute(), ",");
    }
    catch (CantConnectException cce)
    {
      showError();
    }
    fillDraftList();

    sb_.setLength(0);
    sb_.append(leagueYear);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName);
    try
    {
      draftInfo_ =
	  new DraftInfo(getDraftInfoServlet_.execute(sb_.toString()));
      if (draftInfo_.getLocked().equals("0"))
      {
	draftLocked_ = false;
      }
      else
      {
	draftLocked_ = true;
      }
    }
    catch (CantConnectException e)
    {
      showError();
    }
    updateDraftInfoControls();

    //New force a heartbeat
    sb_.setLength(0);      //Clear the buffer
    sb_.append(leagueYear);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(teamName);
    try
    {
      heartbeatServlet_.execute(sb_.toString());
    }
    catch (CantConnectException e)
    {
      showError();
    }

    //force a retrieval of who is logged in
    sb_.setLength(0);
    sb_.append(leagueYear);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName);
    String result = "";
    try
    {
      result = getAliveTeamsServlet_.execute(sb_.toString());
    }
    catch (CantConnectException e)
    {
      showError();
    }

    updateLoggedInList(Misc.getVectorFromDelimitedString(result, ","));
    startThreads();

    createActionDraftMessage(DraftConstants.LOGGED_IN_MESSAGE);

    lstByeWeeks_.fillList("3#4#5#6#7#8#9#10", "#");
  }

  private void showError()
  {
    System.out.println("Can't connect");
  }

  /**
   * Event listener for DraftChangedEvents.  Updates the draftInfo_ class
   * updaes the draft list and controls
   * @param dce DraftChangedEvent object
   */

  public void DraftChanged(DraftChangedEvent dce)
  {
    //boolean draftLockedToggled = false;

    draftInfo_ = dce.getDraftInfo();
    //Check for draft is over
    if (draftInfo_.getRound().equals("-1") == true)
    {
      leagueInfoThread_.kill();
    }

    //Can determine from the draftInfo_ object what is different
    //(i.e. check for locked, etc)
    //draftLocked_

    if ((draftInfo_.getLocked().equals("0") && draftLocked_ == true) ||
        (draftInfo_.getLocked().equals("1") && draftLocked_ == false))
    {
      draftLocked_ = !draftLocked_;
      //draftLockedToggled = true;
    }

    updateDraftInfoControls();
    fillDraftList();

    /*
      If after moving the draft along and the new current player has an
      available player selected, enable the draft button.

      Note the admin can always draft, so the button always be enabled
    */

    if (admin_ == true)
    {
      btnDraft_.setEnabled(true);
    }
    else if (draftLocked_ == true)
    {
      btnDraft_.setEnabled(false);
    }
    else if ((draftInfo_.getTeamName().equals(draftApplet_.getTeamName())) &&
        (lstAvailable_.getSelectedIndex() > -1))
    {
      btnDraft_.setEnabled(true);
    }
  }

  /**
   * Event listener for LoggedInListChangedEvent.  Called when the teams logged
   * in changes in any way.
   * @param evt Event
   */

  public void LoggedInListChanged(LoggedInListChangedEvent evt)
  {
    updateLoggedInList(evt.getLoggedInList());
  }

  /**
   * Event listener for when new chat messages are found.  When new messages
   * are found, add them to the chat list.
   * @param newMessagesArrivedEvent Event containing a vector of new messages
   */

  public void NewMessagesArrived(NewMessagesArrivedEvent newMessagesArrivedEvent)
  {
    for (int i = 0; i < newMessagesArrivedEvent.getMessages().size(); i++)
    {
      lstChat_.add((String)newMessagesArrivedEvent.getMessages().elementAt(i));
      lstChat_.makeVisible(lstChat_.getItemCount() - 1);
    }
  }


  /**
   * Button listener
   * @aw Event triggered by a button
   */

  public void actionPerformed(ActionEvent ae)
  {
    Object target = ae.getSource();

    if (target == btnSearch_)
    {
      doSearch();
    }
    else if (target == btnSelectAllTeams_)
    {
      lstNFLTeams_.selectAll();
    }
    else if (target == btnDeselectAllTeams_)
    {
      lstNFLTeams_.deselectAll();
    }
    else if (target == btnSelectAllPositions_)
    {
      lstPositions_.selectAll();
    }
    else if (target == btnDeselectAllPositions_)
    {
      lstPositions_.deselectAll();
    }
    else if (target == btnSelectAllByeWeeks_)
    {
      lstByeWeeks_.selectAll();
    }
    else if (target == btnDeselectAllByeWeeks_)
    {
      lstByeWeeks_.deselectAll();
    }

    else if (target == btnDraft_)
    {
      createConfirmationDraftDialog();
    }
    else if (target == txtMessage_)   //Enter key hit in txtMessage_
    {
      createDraftMessage(txtMessage_.getText());
      txtMessage_.setText("");
    }
    else if (target == btnHappy_)
    {
      createDraftMessage(DraftConstants.HAPPY_MESSAGE);
    }
    else if (target == btnSad_)
    {
      createDraftMessage(DraftConstants.SAD_MESSAGE);
    }
    else if (target == btnCurse_)
    {
      createDraftMessage(DraftConstants.CURSE_MESSAGE);
    }
    else if (target == btnDraftYes_)
    {
      dlgConfirmDraft_.setVisible(false);
      doDraft();
    }
    else if (target == btnDraftNo_)
    {
      dlgConfirmDraft_.setVisible(false);
    }
    else if (target == btnPlayerTakenOK_)
    {
      dlgPlayerTaken_.setVisible(false);
    }
    else if (target == btnPauseDraft_)
    {
      toggleDraft();
    }
    else if (target == btnDeletePick_)
    {
      deletePick();
    }
  }

  private void createDraftMessage(String message)
  {
    StringBuffer text = new StringBuffer(draftApplet_.getTeamName());
    text.append(':');
    text.append(message);

    sb_.setLength(0);
    sb_.append(draftApplet_.getLeagueYear());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(draftApplet_.getLeagueName());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(text);

    try
    {
      createDraftMessageServlet_.execute(sb_.toString());
    }
    catch (CantConnectException e)
    {
      showError();
    }

    text = null;
  }

  private void createActionDraftMessage(String message)
  {
    if (draftApplet_ != null && createDraftMessageServlet_ != null)
    {
      StringBuffer text = new StringBuffer(draftApplet_.getTeamName());
      text.append(message);

      sb_.setLength(0);
      sb_.append(draftApplet_.getLeagueYear());
      sb_.append(ServletInfo.DELIMITER);
      sb_.append(draftApplet_.getLeagueName());
      sb_.append(ServletInfo.DELIMITER);
      sb_.append(text);

      try
      {
	createDraftMessageServlet_.execute(sb_.toString());
      }
      catch (CantConnectException e)
      {
	showError();
      }

      text = null;
    }
  }

  /**
   * Calls the servlet to delete the currenly selected pick from the draft
   */

  private void deletePick()
  {
    //Clear the buffer
    sb_.setLength(0);
    //Set up parameters
    sb_.append(draftApplet_.getLeagueYear());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(draftApplet_.getLeagueName());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append((lstDraft_.getSelectedIndex() + 1));
    //Call the servlet

    try
    {
      deletePickServlet_.execute(sb_.toString());
    }
    catch (CantConnectException e)
    {
      showError();
    }
  }

  /**
   * Event triggered when an item is selected from a list box (used for the
   * available list)
   * @param ie ItemEvent
   */

  public void itemStateChanged(ItemEvent ie)
  {
    if (ie.getStateChange() == ie.DESELECTED)
    {
      btnDraft_.setEnabled(false);
    }
    else
    {
      if (admin_)
      {
        btnDraft_.setEnabled(true);
      }
      else if (draftLocked_)
      {
        btnDraft_.setEnabled(false);
      }
      else if (draftApplet_.getTeamName().compareTo(draftInfo_.getTeamName()) == 0)
      {
        btnDraft_.setEnabled(true);
      }
    }
  }

  /**
   * Calls servlet to change state of draft
   */

  private void toggleDraft()
  {
    try
    {
      toggleDraftServlet_.execute();
    }
    catch (CantConnectException e)
    {
      showError();
    }

  }

  /**
   * Redraws the user list
   * @param v vector of users that are logged in
   */

  private void updateLoggedInList(Vector v)
  {
    loggedInTeams_ = v;

    lstUsers_.removeAll();
    for (int i = 0; i < v.size(); i++)
    {
      lstUsers_.add((String)v.elementAt(i));
    }
  }

  /**
   * Updates the labels with draft info information from the applet
   */

  private void fillLoggedInControls()
  {
    lblLoginTeamName_.setText(draftApplet_.getTeamName());
    lblLeagueName_.setText(draftApplet_.getLeagueName());
    lblLeagueYear_.setText(new Integer(draftApplet_.getLeagueYear()).toString());
  }

  /**
   * Search available player method (input from Position and NFL Team Lists
   */

  private void doSearch()
  {
    String result = "";

    Object[] nflPlayerIDKeys = { "ID" };
    Object[] nflPlayerDetailKeys = { "PLAYER NAME", "POS", "NFL TEAM", "BYE"};
    int[] nflPlayerIDFieldLengths = { 10 };
    int[] nflPlayerDetailLengths = { 18, 5, 5, 5 };

    btnDraft_.setEnabled(false);          //Disable the draft button

    lstAvailablePlaceholder_.setVisible(true);
    lstAvailable_.setVisible(false);
    lstAvailable_.removeAll();
    lstHiddenID_.removeAll();

    sb_.setLength(0);
    sb_.append(draftApplet_.getLeagueYear());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(draftApplet_.getLeagueName());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(lstNFLTeams_.getSelectedDelimeted('#'));
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(lstPositions_.getSelectedDelimeted('#'));
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(lstByeWeeks_.getSelectedDelimeted('#'));
    try
    {
      result = searchServlet_.execute(sb_.toString());
    }
    catch (CantConnectException e)
    {
      showError();
    }


    //Result returned in the following format
    //ID|Name|Pos|ShortName`ID|Name|Pos|ShortName
    //Put ID's in lstHiddenID_ and the rest in lstAvailable_

    lstHiddenID_.fillList(result, "|", "`", '=', nflPlayerIDKeys, nflPlayerIDFieldLengths);
    lstAvailable_.fillList(result, "|", "`", '=', nflPlayerDetailKeys, nflPlayerDetailLengths);


    lstAvailable_.setVisible(true);
    lstAvailablePlaceholder_.setVisible(false);
    validate();
  }

  /**
   * Populates the draft list box
   */
  private void fillDraftList()
  {
    lstDraftPlaceholder_.setVisible(true);
    lstDraft_.setVisible(false);
    lstDraft_.removeAll();

    String result = "";
    try
    {
      result = getDraftRecordsServlet_.execute();
    }
    catch (CantConnectException e)
    {
      showError();
    }

    Object[] nflPlayerDetailKeys = { "NUM", "PLAYER NAME", "POS", "PLAYER TEAM", "FANTASY TEAM", "BYE"};
    int[] nflPlayerDetailLengths = { 3, 15, 4, 4, 8, 5 };
    lstDraft_.fillList(result, "|", "`", '=', nflPlayerDetailKeys, nflPlayerDetailLengths);


    //Scroll to newest draft item
    lstDraft_.makeVisible(lstDraft_.getItemCount() - 1);
    lstDraft_.setVisible(true);
    lstDraftPlaceholder_.setVisible(false);
  }

  /**
   * Updates the controls displaying the current draft position
   */

  private void updateDraftInfoControls()
  {
    //Look for -1,-1,-1
    if (draftInfo_.getRound().equals("-1") == true)
    {
      lblRound_.setText(DraftConstants.DRAFT_COMPLETE_STRING);
      lblPick_.setText(DraftConstants.EMPTY_STRING);
      lblTeam_.setText(DraftConstants.EMPTY_STRING);
    }
    else if (draftLocked_ && !admin_)
    {
      lblRound_.setText(DraftConstants.DRAFT_LOCKED_STRING);
      lblPick_.setText(DraftConstants.EMPTY_STRING);
      lblTeam_.setText(DraftConstants.EMPTY_STRING);
    }
    else
    {
      lblRound_.setText(draftInfo_.getRound());
      lblPick_.setText(draftInfo_.getPick());
      lblTeam_.setText(draftInfo_.getTeamName());
    }
    lblRound_.setSize(lblRound_.getPreferredSize());
    lblPick_.setSize(lblPick_.getPreferredSize());
    lblTeam_.setSize(lblTeam_.getPreferredSize());
    lblRound_.getParent().validate();
    lblRound_.getParent().getParent().validate();
  }

  /**
   * Builds the draft confirmation dialog
   */

  private void createConfirmationDraftDialog()
  {
    dlgConfirmDraft_ = new Dialog(draftApplet_.getParentFrame(), false);
    dlgConfirmDraft_.setTitle(DraftConstants.DRAFT_CONFIRMATION_TITLE);
    dlgConfirmDraft_.setResizable(false);
    btnDraftYes_ = new Button(DraftConstants.BUTTON_YES_DESC);
    btnDraftNo_ = new Button(DraftConstants.BUTTON_NO_DESC);
    Label lblQuestion = new Label(DraftConstants.DRAFT_QUESTION);
    Label lblPlayer = new Label(lstAvailable_.getSelectedItem());
    dlgConfirmDraft_.setLayout(new GridBagLayout());
    dlgConfirmDraft_.add(lblQuestion,
                         new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                                                GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0),
                                                0, 0));
    dlgConfirmDraft_.add(lblPlayer,
                         new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                                                GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0),
                                                0, 0));
    dlgConfirmDraft_.add(btnDraftYes_,
                         new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                                                GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0),
                                                0, 0));
    dlgConfirmDraft_.add(btnDraftNo_,
                         new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                                                GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0),
                                                0, 0));
    btnDraftYes_.addActionListener(this);
    btnDraftNo_.addActionListener(this);
    dlgConfirmDraft_.setSize(300, 200);

    dlgConfirmDraft_.setLocation(draftApplet_.getX() +
                                 draftApplet_.getWidth() / 4,
                                 draftApplet_.getY() +
                                 draftApplet_.getHeight() / 4);

    dlgConfirmDraft_.show();
  }

  /**
   * Calls a servlet to draft a player
   */

  private void doDraft()
  {
    String result = "";

    sb_.setLength(0);
    sb_.append(draftApplet_.getLeagueYear());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(draftApplet_.getLeagueName());
    sb_.append(ServletInfo.DELIMITER);
    //If administrator, then allow for drafting for another team
    if (admin_)
    {
      sb_.append(draftInfo_.getTeamName());
      sb_.append(ServletInfo.DELIMITER);
    }
    else
    {
      sb_.append(draftApplet_.getTeamName());
      sb_.append(ServletInfo.DELIMITER);
    }
    sb_.append(draftInfo_.getRound());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(lstHiddenID_.getItem(lstAvailable_.getSelectedIndex()));
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(draftInfo_.getPick());

    try
    {
      result = doDraftServlet_.execute(sb_.toString());
    }
    catch (CantConnectException e)
    {
      showError();
    }



//NOTE!!! TEST RESULT, because the player may have already been selected
//Returns the following
//echo("$CDraft->round,$CDraft->number,$CDraft->fantasyTeamName,$CDraft->Locked");
//NOTE: If result == -1,-1,-1,-1 then the draft is over

      if (result.compareToIgnoreCase(DraftConstants.ALREADY_DRAFTED) == 0)
      {
        buildPlayerTakenDialog("Player Already Taken");
      }
      else
      {
        //Remove the item just drafted
        lstAvailable_.remove(lstAvailable_.getSelectedIndex());
        btnDraft_.setEnabled(false);
      }
  }

  /**
   * Creates a dialog that indicates that the current player was taken
   * @param message Message for dialog
   */

  private void buildPlayerTakenDialog(String message)
  {
    dlgPlayerTaken_ = new Dialog(draftApplet_.getParentFrame(), false);
    dlgPlayerTaken_.setTitle("Player Already Taken");
    dlgPlayerTaken_.setResizable(false);
    btnPlayerTakenOK_ = new Button("OK");
    Label lblMessage = new Label(message);

    dlgPlayerTaken_.setLayout(new GridBagLayout());
    dlgPlayerTaken_.add(lblMessage,
                         new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                                                GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0),
                                                0, 0));
    dlgPlayerTaken_.add(btnPlayerTakenOK_,
                         new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                                                GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0),
                                                0, 0));
    btnPlayerTakenOK_.addActionListener(this);
    dlgPlayerTaken_.setSize(300, 200);
    dlgPlayerTaken_.setLocation(draftApplet_.getX() +
                                 ((draftApplet_.getWidth() -
                                   dlgPlayerTaken_.getWidth()) / 2),
                                 draftApplet_.getY() +
                                 ((draftApplet_.getHeight() -
                                   dlgPlayerTaken_.getHeight()) / 2));
    dlgPlayerTaken_.show();
  }

  /**
   * Populates the position list
   */
  private void fillPositionList()
  {
    for (int i = 0; i < positions_.length; i++)
    {
      lstPositions_.add(positions_[i]);
    }
  }

  /**
   * Instantiates all of the servlets
   */
  private void createServlets()
  {
    String leagueYear = draftApplet_.getLeagueYear();
    String leagueName = draftApplet_.getLeagueName();
    String teamName   = draftApplet_.getTeamName();

    createDraftMessageServlet_ =
              new Servlet(ServletInfo.CREATE_DRAFT_MESSAGE_SERVLET_NAME,
                          ServletInfo.CREATE_DRAFT_MESSAGE_SERVLET_PARAMETERS);

    doDraftServlet_ =
              new Servlet(ServletInfo.DO_DRAFT_SERVLET_NAME,
                          ServletInfo.DO_DRAFT_SERVLET_PARAMETERS);

    getDraftInfoServlet_ =
              new Servlet(ServletInfo.GET_DRAFT_INFO_SERVLET_NAME,
                          ServletInfo.GET_ALIVE_TEAMS_SERVLET_PARAMETERS);
    sb_.setLength(0);
    sb_.append(leagueYear);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName);
    getDraftInfoServlet_.setStaticValues(sb_.toString());


    getAliveTeamsServlet_ =
              new Servlet(ServletInfo.GET_ALIVE_TEAMS_SERVLET_NAME,
                          ServletInfo.GET_ALIVE_TEAMS_SERVLET_PARAMETERS);
    sb_.setLength(0);
    sb_.append(leagueYear);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName);
    getAliveTeamsServlet_.setStaticValues(sb_.toString());

    heartbeatServlet_ =
              new Servlet(ServletInfo.HEARTBEAT_SERVLET_NAME,
                          ServletInfo.HEARTBEAT_SERVLET_PARAMETERS);
    sb_.setLength(0);
    sb_.append(leagueYear);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(teamName);
    heartbeatServlet_.setStaticValues(sb_.toString());

    getDraftRecordsServlet_ =
              new Servlet(ServletInfo.GET_DRAFT_RECORDS_SERVLET_NAME,
                          ServletInfo.GET_DRAFT_RECORDS_SERVLET_PARAMETERS);

    sb_.setLength(0);
    sb_.append(draftApplet_.getLeagueYear());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(draftApplet_.getLeagueName());
    getDraftRecordsServlet_.setStaticValues(sb_.toString());

    getNewMessagesServlet_ =
              new Servlet(ServletInfo.GET_NEW_MESSAGES_SERVLET_NAME,
                          ServletInfo.GET_NEW_MESSAGES_SERVLET_PARAMETERS);

    getNFLTeamsServlet_ =
              new Servlet(ServletInfo.GET_NFL_TEAMS_SERVLET_NAME,
                          ServletInfo.GET_NFL_TEAMS_SERVLET_PARAMETERS);
    getNFLTeamsServlet_.setStaticValues(leagueYear);

    searchServlet_ =
              new Servlet(ServletInfo.SEARCH_SERVLET_NAME,
                          ServletInfo.SEARCH_SERVLET_PARAMETERS);

    toggleDraftServlet_ =
              new Servlet(ServletInfo.TOGGLE_DRAFT_SERVLET_NAME,
                          ServletInfo.TOGGLE_DRAFT_SERVLET_PARAMETERS);

    sb_.setLength(0);
    sb_.append(leagueYear);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName);
    toggleDraftServlet_.setStaticValues(sb_.toString());

    deletePickServlet_ =
              new Servlet(ServletInfo.DELETE_PICK_SERVLET_NAME,
                          ServletInfo.DELETE_PICK_SERVLET_PARAMETERS);

    messageCleanupServlet_ =
              new Servlet(ServletInfo.MESSAGE_CLEANUP_SERVLET_NAME,
                          ServletInfo.MESSAGE_CLEANUP_SERVLET_PARAMETERS);

    sb_.setLength(0);
    sb_.append(leagueYear);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append((DraftConstants.MESSAGE_CLEANUP_INTERVAL / 1000));

    messageCleanupServlet_.setStaticValues(sb_.toString());

    logoutServlet_ = new Servlet(ServletInfo.LOGOUT_SERVLET_NAME,
                                 ServletInfo.LOGOUT_SERVLET_PARAMETERS);

    sb_.setLength(0);
    sb_.append(leagueYear);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(teamName);
    logoutServlet_.setStaticValues(sb_.toString());

    leagueYear = null;
    leagueName = null;
    teamName   = null;
  }

  /**
   * Method for logging the user out of the system.  This calls the
   * logoutServlet which resets the timestamp to 0 for the user's heartbeat
   */

  public void doLogout()
  {
    if (logoutServlet_ != null)
    {
      createActionDraftMessage(DraftConstants.LOGGED_OUT_MESSAGE);
      try
      {
	logoutServlet_.execute();
      }
      catch (CantConnectException e)
      {
	showError();
      }

    }
    if (heartbeatThread_ != null)
    {
      heartbeatThread_.kill();
    }
    if (leagueInfoThread_ != null)
    {
      leagueInfoThread_.kill();
    }
    if (newMessagesThread_ != null)
    {
      newMessagesThread_.kill();
    }
    if (messageCleanupThread_ != null)
    {
      messageCleanupThread_.kill();
    }
  }

  /**
   * Enables all of the threads
   */

  private void startThreads()
  {
    heartbeatThread_ = new HeartBeatThread(heartbeatServlet_,
                                           DraftConstants.HEARTBEAT_TIME_INTERVAL);
    heartbeat_ = new Thread(heartbeatThread_);
    heartbeat_.start();


    leagueInfoThread_ = new LeagueInfoThread(getDraftInfoServlet_,
                                             DraftConstants.LEAGUE_INFO_INTERVAL,
                                             draftApplet_.getLeagueName(),
                                             new Integer(draftApplet_.getLeagueYear()).toString(),
                                             draftInfo_);
    lit_ = new Thread(leagueInfoThread_);
    leagueInfoThread_.addDraftChangedListener(this);    //Set up listener for the thread's events
    lit_.start();

    loggedInThread_ = new LoggedInThread(getAliveTeamsServlet_,
                                         DraftConstants.LOGGED_IN_INTERVAL,
                                         loggedInTeams_);
    loggedInBaseThread_ = new Thread(loggedInThread_);
    loggedInThread_.addLoggedInChangedListener(this);
    loggedInBaseThread_.start();

    newMessagesThread_ = new NewMessagesThread(getNewMessagesServlet_,
                                               DraftConstants.NEW_MESSAGES_INTERVAL,
                                               draftApplet_.getLeagueName(),
                                               draftApplet_.getLeagueYear());
    newMessagesBaseThread_ = new Thread(newMessagesThread_);
    newMessagesThread_.addNewMessagesArrivedListener(this);
    newMessagesBaseThread_.start();


    messageCleanupThread_ = new MessageCleanupThread(messageCleanupServlet_,
                                                DraftConstants.MESSAGE_CLEANUP_INTERVAL);
    messageCleanupBaseThread_ = new Thread(messageCleanupThread_);
    messageCleanupBaseThread_.start();
  }

  /**
   * Sets up the initial main screen
   */

  private void drawEmptyControls()
  {
    setLayout(gbl_);    //Set the layout for the entire panel

    drawEmptyRoundInfoControls();

    add(roundInfoPanel_,
	new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			       GridBagConstraints.NORTHWEST,
			       GridBagConstraints.NONE,
			       new Insets(0, 5, 0, 0), 0, 0));
    drawEmptyDraftDescriptionControls();
    add(leagueInfoPanel_,
	new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
			       GridBagConstraints.NORTHEAST,
			       GridBagConstraints.NONE,
			       new Insets(0, 0, 0, 5), 0, 0));


    drawDraftPanel();
    add(draftPanel_,
	new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0,
			       GridBagConstraints.CENTER,
			       GridBagConstraints.HORIZONTAL,
			       new Insets(0, 0, 0, 0), 0, 0));

    drawAvailablePanel();
    add(availablePanel_,
	new GridBagConstraints(2, 1, 2, 1, 1.0, 0.0,
			       GridBagConstraints.CENTER,
			       GridBagConstraints.HORIZONTAL,
			       new Insets(0, 0, 0, 0), 0, 0));


    drawNFLTeamsPanel();
    add(nflTeamsPanel_,
	new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
			       GridBagConstraints.WEST,
			       GridBagConstraints.HORIZONTAL,
			       new Insets(0, 0, 0, 0), 0, 0));


    drawPositionsPanel();
    add(positionsPanel_,
	new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
			       GridBagConstraints.CENTER,
			       GridBagConstraints.NONE,
			       new Insets(0, 0, 0, 0), 0, 0));

    txtMessage_.setColumns(128);
    add(txtMessage_,
	new GridBagConstraints(0, 2, 5, 1, 0.0, 0.0,
			       GridBagConstraints.WEST,
			       GridBagConstraints.NONE,
			       new Insets(0, 0, 0, 0), 0, 0));

    drawEmotionPanel();
    add(emotionButtonPanel_,
	new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
			       GridBagConstraints.CENTER,
			       GridBagConstraints.NONE,
			       new Insets(0, 0, 0, 0), 0, 0));


    add(lstChat_,
	new GridBagConstraints(0, 3, 5, 1, 0.0, 0.0,
			       GridBagConstraints.WEST,
			       GridBagConstraints.BOTH,
			       new Insets(0, 0, 0, 0), 0, 100));
    add(lstUsers_,
	new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0,
			       GridBagConstraints.WEST,
			       GridBagConstraints.HORIZONTAL,
			       new Insets(0, 0, 0, 0), 0, 100));


    //allow for multiple selections for position and nfl team dropdown
    lstPositions_.setMultipleMode(true);
    lstNFLTeams_.setMultipleMode(true);
    lstByeWeeks_.setMultipleMode(true);
    //Set up the event listeners for the buttons
    btnDraft_.addActionListener(this);
    btnSearch_.addActionListener(this);
    btnSelectAllTeams_.addActionListener(this);
    btnDeselectAllTeams_.addActionListener(this);
    btnSelectAllPositions_.addActionListener(this);
    btnDeselectAllPositions_.addActionListener(this);
    btnSelectAllByeWeeks_.addActionListener(this);
    btnDeselectAllByeWeeks_.addActionListener(this);
    btnPauseDraft_.addActionListener(this);
    btnDeletePick_.addActionListener(this);

    btnHappy_.addActionListener(this);
    btnSad_.addActionListener(this);
    btnCurse_.addActionListener(this);

    if (admin_)
    {
      btnPauseDraft_.setVisible(true);
      btnDeletePick_.setVisible(true);
    }
    else
    {
      btnHelp_.setVisible(true);
    }

    lstNFLTeams_.setFont( new Font("Monospaced",Font.PLAIN,12) );
    lstAvailable_.setFont( new Font("Monospaced",Font.PLAIN,12) );
    lstDraft_.setFont( new Font("Monospaced",Font.PLAIN,12) );

    //Set up the event listener for the available list
    lstAvailable_.addItemListener(this);

    //Set up the event listener for the txtMessage_
    txtMessage_.addActionListener(this);

    //Add hidden lists
    lstHiddenID_.setVisible(false);
    add(lstHiddenID_);

    //Disable the draft button
    btnDraft_.setEnabled(false);
   }

   private void drawPositionsPanel()
   {
     positionsPanel_ = new Panel(new GridBagLayout());
     Insets insets = new Insets(0, 0, 0, 0);

     positionsPanel_.add(lblPositionsDesc_,
	 new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
				GridBagConstraints.NORTH,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

     positionsPanel_.add(lstPositions_,
	 new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
				GridBagConstraints.NORTH,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 50));

     lstPositions_.setFont( new Font("Monospaced",Font.PLAIN,12) );

     positionsPanel_.add(btnSelectAllPositions_,
	 new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0),
				0, 0));

     positionsPanel_.add(btnDeselectAllPositions_,
	 new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0),
				0, 0));

     positionsPanel_.add(lblByeWeeks_,
	 new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
				GridBagConstraints.SOUTH,
				GridBagConstraints.NONE,
				new Insets(10, 0, 0, 0),
				0, 0));
     positionsPanel_.add(lstByeWeeks_,
	 new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
				GridBagConstraints.SOUTH,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 30));
     positionsPanel_.add(btnSelectAllByeWeeks_,
	 new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0),
				0, 0));

     positionsPanel_.add(btnDeselectAllByeWeeks_,
	 new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0),
				0, 0));

   }

   private void drawEmotionPanel()
   {
     emotionButtonPanel_ = new Panel(new GridBagLayout());
     emotionButtonPanel_.add(btnHappy_,
	new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			       GridBagConstraints.CENTER,
			       GridBagConstraints.NONE,
			       new Insets(0, 0, 0, 0), 0, 0));

     emotionButtonPanel_.add(btnSad_,
	new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
			       GridBagConstraints.CENTER,
			       GridBagConstraints.NONE,
			       new Insets(0, 0, 0, 0), 0, 0));

     emotionButtonPanel_.add(btnCurse_,
	new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
			       GridBagConstraints.CENTER,
			       GridBagConstraints.NONE,
			       new Insets(0, 0, 0, 0), 0, 0));

   }

   private void drawAvailablePanel()
   {
     availablePanel_ = new Panel(new GridBagLayout());
     Insets insets = new Insets(0, 0, 0, 0);

     availablePanel_.add(lblAvailableDesc_,
	 new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				insets, 0, 0));

     availablePanel_.add(lstAvailable_,
	 new GridBagConstraints(0, 1, 3, 1, 1.0, 0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				insets, 0, 200));
     availablePanel_.add(btnDraft_,
	 new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST,
				GridBagConstraints.NONE,
				insets, 0, 0));
     availablePanel_.add(btnSearch_,
	 new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST,
				GridBagConstraints.NONE,
				insets, 0, 0));
   }

   private void drawDraftPanel()
   {
     draftPanel_ = new Panel(new GridBagLayout());

     draftPanel_.add(lblDraftDesc_,
	 new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

     draftPanel_.add(lstDraft_,
	 new GridBagConstraints(0, 1, 3, 1, 1.0, 0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 50, 200));
     if (admin_)
     {
       btnDeletePick_.setVisible(false);
       btnDeletePick_.setEnabled(false);
       draftPanel_.add(btnDeletePick_,
		       new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
		       GridBagConstraints.WEST,
		       GridBagConstraints.NONE,
		       new Insets(0, 0, 0, 0), 0, 0));

       btnPauseDraft_.setVisible(false);
       draftPanel_.add(btnPauseDraft_,
		       new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
		       GridBagConstraints.EAST,
		       GridBagConstraints.NONE,
		       new Insets(0, 0, 0, 0), 0, 0));
     }
     else
     {
       btnHelp_.setVisible(false);
       draftPanel_.add(btnHelp_,
		       new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
		       GridBagConstraints.CENTER,
		       GridBagConstraints.NONE,
		       new Insets(0, 0, 0, 0), 0, 0));
     }
   }
   private void drawNFLTeamsPanel()
   {
     nflTeamsPanel_ = new Panel(new GridBagLayout());
     Insets insets = new Insets(0, 0, 0, 0);


     nflTeamsPanel_.add(lblNFLTeamsDesc_,
	 new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				insets, 0, 0));

     nflTeamsPanel_.add(lstNFLTeams_,
	 new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
				GridBagConstraints.NORTH,
				GridBagConstraints.BOTH,
				insets, 0, 200));

     nflTeamsPanel_.add(btnSelectAllTeams_,
	 new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST,
				GridBagConstraints.NONE,
				insets,
				0, 0));

     nflTeamsPanel_.add(btnDeselectAllTeams_,
	 new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST,
				GridBagConstraints.NONE,
				insets,
				0, 0));

     insets = null;
   }


   private void drawEmptyRoundInfoControls()
   {
     Insets insets = new Insets(0, 0, 0, 0);
     GridBagConstraints gbcDesc = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							 GridBagConstraints.WEST,
							 GridBagConstraints.NONE,
							 insets, 0, 0);
     GridBagConstraints gbcValue =
				  new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
							 GridBagConstraints.EAST,
							 GridBagConstraints.NONE,
							 insets, 0, 0);

     roundInfoPanel_ = new Panel(new GridBagLayout());


     roundInfoPanel_.add(lblRoundDesc_, gbcDesc);
     gbcDesc.gridy = 1;
     roundInfoPanel_.add(lblPickDesc_, gbcDesc);
     gbcDesc.gridy = 2;
     roundInfoPanel_.add(lblTeamDesc_, gbcDesc);

     roundInfoPanel_.add(lblRound_, gbcValue);
     gbcValue.gridy = 1;
     roundInfoPanel_.add(lblPick_, gbcValue);
     gbcValue.gridy = 2;
     roundInfoPanel_.add(lblTeam_, gbcValue);

     gbcValue = null;
     gbcDesc = null;
     insets = null;
   }

   private void drawEmptyDraftDescriptionControls()
   {
     Insets insets = new Insets(0, 0, 0, 0);


     leagueInfoPanel_ = new Panel(new GridBagLayout());
     leagueInfoPanel_.add(lblLoginTeamNameDesc_,
			 new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.WEST,
						GridBagConstraints.NONE,
						insets, 0, 0));
     leagueInfoPanel_.add(lblLoginTeamName_,
			 new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
						GridBagConstraints.EAST,
						GridBagConstraints.NONE,
						insets, 0, 0));
     leagueInfoPanel_.add(lblLeagueNameDesc_,
			 new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.WEST,
						GridBagConstraints.NONE,
						insets, 0, 0));
     leagueInfoPanel_.add(lblLeagueName_,
			 new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
						GridBagConstraints.EAST,
						GridBagConstraints.NONE,
						insets, 0, 0));
     leagueInfoPanel_.add(lblLeagueYearDesc_,
			 new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.WEST,
						GridBagConstraints.NONE,
						insets, 0, 0));
     leagueInfoPanel_.add(lblLeagueYear_,
			 new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
						GridBagConstraints.EAST,
						GridBagConstraints.NONE,
						insets, 0, 0));
   }
}