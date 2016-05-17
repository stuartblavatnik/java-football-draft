package com.twoforboth.draft.panel;

import java.lang.NumberFormatException;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Frame;
import java.awt.Point;
import java.awt.TextField;
import java.awt.Dialog;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.border.*;
import javax.swing.JTabbedPane;

import java.util.Vector;
import java.util.HashMap;

import com.twoforboth.draft.SwingDraft;
import com.twoforboth.draft.misc.DraftInfo;
import com.twoforboth.controls.Swing.JListPlus;

import com.twoforboth.draft.misc.ButtonAdapter;
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

import com.twoforboth.util.Misc;
import com.twoforboth.controls.Swing.StandardTable;
import com.twoforboth.controls.Swing.JButtonSelect;

import com.twoforboth.draft.misc.JButtonEmotion;
import com.twoforboth.draft.panel.DraftConstants;

import com.twoforboth.communication.CantConnectException;
import com.twoforboth.communication.Servlet;
import com.twoforboth.communication.ServletInfo;

import com.twoforboth.draft.NFLPlayer;

/**
 * Title:        Draft
 * Description:  Scrambled Eggs Football Draft Application
 * Copyright:    Copyright (c) 2002
 * Company:      Two For Both Inc.
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class SwingDraftPanel extends JPanel implements DraftChangedListenerI,
                                                       LoggedInListChangedI,
                                                       NewMessagesArrivedI,
						       MouseListener,
						       ActionListener
{
  private SwingDraft draftApplet_ = null;

  private JTabbedPane jTabbedPane_ = new JTabbedPane();

  private JPanel topPanel_ = new JPanel(new GridBagLayout());
  //Begin
  private JPanel roundInfoPanel_ = null;
  private JPanel titlePanel_ = new JPanel(new GridBagLayout());
  private JPanel leagueInfoPanel_ = null;
  //End
  private JPanel middlePanel_ = new JPanel();
  private JPanel draftTotalPanel_= new JPanel(new GridBagLayout());
  private JPanel autoDraftPanel_ = new JPanel(new GridBagLayout());
  private JPanel rostersPanel_ = new JPanel(new GridBagLayout());
  //Begin
  private JPanel draftPanel_ = null;
  private JPanel nflTeamsPanel_ = null;
  private JPanel availablePanel_ = null;
  private JPanel positionsPanel_ = null;
  private JPanel byeWeeksPanel_ = null;
  private JPanel draftPanel2_ = null;
  private JPanel preRankPanel_ = null;
  //End

  private JPanel bottomPanel_ = new JPanel(new GridBagLayout());
  //Begin
  private JPanel emotionButtonPanel_ = null;
  //End

  private GridBagLayout gbl_ = new GridBagLayout();
  private JLabel lblRoundDesc_ = new JLabel(DraftConstants.LABEL_ROUND_DESC);
  private JLabel lblPickDesc_ = new JLabel(DraftConstants.LABEL_PICK_DESC);
  private JLabel lblTeamDesc_ = new JLabel(DraftConstants.LABEL_TEAM_DESC);
  private JLabel lblDraftDesc_ = new JLabel(DraftConstants.LABEL_DRAFT_DESC);
  private JLabel lblTimeRemainingDesc_ = new JLabel(DraftConstants.LABEL_TIME_REMAINING_DESC);
  private JLabel lblDraftDesc2_ = new JLabel(DraftConstants.LABEL_DRAFT_DESC);

  private JLabel lblAvailableDesc_ =
      new JLabel(DraftConstants.LABEL_AVAILABLE_DESC);

  private JLabel lblPreRankDesc_ =
    new JLabel(DraftConstants.LABEL_PRERANK_DESC);

  private JLabel lblNFLTeamsDesc_ =
      new JLabel(DraftConstants.LABEL_NFLTEAMS_DESC);
  private JLabel lblPositionsDesc_ =
      new JLabel(DraftConstants.LABEL_POSITIONS_DESC);
  private JLabel lblLoginTeamNameDesc_ =
      new JLabel(DraftConstants.LABEL_LOGIN_TEAM_NAME_DESC);
  private JLabel lblLeagueNameDesc_ =
      new JLabel(DraftConstants.LABEL_LEAGUE_NAME_DESC);
  private JLabel lblLeagueYearDesc_ =
      new JLabel(DraftConstants.LABEL_LEAGUE_YEAR_DESC);
  private JLabel lblByeWeeks_ =
      new JLabel(DraftConstants.LABEL_EXCLUDE_BYE_WEEKS_DESC);
  private JLabel lblNextPickDesc_=
      new JLabel(DraftConstants.LABEL_NEXT_PICK_DESC);

  private JLabel lblTitle_ =
      new JLabel(DraftConstants.APPLICATION_TITLE);

  private JLabel lblRound_ = new JLabel();
  private JLabel lblPick_ = new JLabel();
  private JLabel lblTeam_ = new JLabel();
  private JLabel lblTimeRemaining_ = new JLabel();

  private JLabel lblLoginTeamName_ = new JLabel();
  private JLabel lblLeagueName_ = new JLabel();
  private JLabel lblLeagueYear_ = new JLabel();
  private JLabel lblNextPick_ = new JLabel();

  private JListPlus lstNFLTeams_ = new JListPlus();
  private JListPlus lstPositions_ = new JListPlus();
  private JListPlus lstByeWeeks_ = new JListPlus();
  private JScrollPane nflTeamsScrollPane_ = new JScrollPane(lstNFLTeams_);
  private JScrollPane positionsScrollPane_ = new JScrollPane(lstPositions_);
  private JScrollPane byeWeeksScrollPane_ = new JScrollPane(lstByeWeeks_);

  //Chat lists
  private JList lstUsers_ = new JList();
  private JList lstChat_ = new JList();
  private JScrollPane usersScrollPane_ = new JScrollPane(lstUsers_);
  private JScrollPane chatScrollPane_ = new JScrollPane(lstChat_);
  private DefaultListModel usersModel_ = new DefaultListModel();
  private DefaultListModel messagesModel_ = new DefaultListModel();

  //Chat entry
  private TextField txtMessage_ = new TextField();

  private JButton btnDraft_ = new JButton(DraftConstants.BUTTON_DRAFT_DESC);
  private JButton btnSearch_ = new JButton(DraftConstants.BUTTON_SEARCH_DESC);
  private JButton btnDraftPreRank_ = new JButton(DraftConstants.BUTTON_DRAFT_DESC);
  private JButtonSelect btnSelectAllTeams_ =
      new JButtonSelect(DraftConstants.BUTTON_ALL_DESC, lstNFLTeams_);
  private JButtonSelect btnDeselectAllTeams_ =
      new JButtonSelect(DraftConstants.BUTTON_NONE_DESC, lstNFLTeams_);
  private JButtonSelect btnSelectAllPositions_ =
      new JButtonSelect(DraftConstants.BUTTON_ALL_DESC, lstPositions_);
  private JButtonSelect btnDeselectAllPositions_ =
      new JButtonSelect(DraftConstants.BUTTON_NONE_DESC, lstPositions_);
  private JButton btnPauseDraft_ =
      new JButton(DraftConstants.BUTTON_PAUSE_DRAFT_DESC);
  private JButton btnDeletePick_ =
      new JButton(DraftConstants.BUTTON_DELETE_PICK_DESC);
  private JButtonSelect btnSelectAllByeWeeks_ =
      new JButtonSelect(DraftConstants.BUTTON_ALL_DESC, lstByeWeeks_);
  private JButtonSelect btnDeselectAllByeWeeks_ =
      new JButtonSelect(DraftConstants.BUTTON_NONE_DESC, lstByeWeeks_);
  private JButton btnHelp_ = new JButton(DraftConstants.BUTTON_HELP_DESC);
  //Emotion Buttons
  private JButtonEmotion btnHappy_ =
      new JButtonEmotion(DraftConstants.BUTTON_HAPPY_DESC,
      DraftConstants.HAPPY_MESSAGE);
  private JButtonEmotion btnSad_ =
      new JButtonEmotion(DraftConstants.BUTTON_SAD_DESC,
      DraftConstants.SAD_MESSAGE);
  private JButtonEmotion btnCurse_ =
      new JButtonEmotion(DraftConstants.BUTTON_CURSE_DESC,
      DraftConstants.CURSE_MESSAGE);

  protected final static String DRAFT_TABLE_NAME = "draft";
  protected final static String AVAILABLE_TABLE_NAME = "available";
  protected final static String PRERANK_TABLE_NAME = "prerank";

  private final String DRAFT_TABLE_PICK_COLUMN_NAME = "Pick";
  private final String DRAFT_TABLE_PLAYER_NAME_COLUMN_NAME = "Pl Name";
  private final String DRAFT_TABLE_NFL_TEAM_COLUMN_NAME = "Team";
  private final String DRAFT_TABLE_POSITION_COLUMN_NAME = "Pos";
  private final String DRAFT_TABLE_FANTASY_COLUMN_NAME = "Fant Team";
  private final String DRAFT_TABLE_BYE_COLUMN_NAME = "Bye";
  private String[] StandardTableDraftColumnHeaders_ =
                                         { DRAFT_TABLE_PICK_COLUMN_NAME,
					   DRAFT_TABLE_PLAYER_NAME_COLUMN_NAME,
					   DRAFT_TABLE_POSITION_COLUMN_NAME,
                                           DRAFT_TABLE_NFL_TEAM_COLUMN_NAME,
					   DRAFT_TABLE_FANTASY_COLUMN_NAME,
					   DRAFT_TABLE_BYE_COLUMN_NAME
					 };

   private final int DRAFT_TABLE_PICK_COLUMN_WIDTH = 15;
   private final int DRAFT_TABLE_PLAYER_NAME_COLUMN_WIDTH = 55;
   private final int DRAFT_TABLE_NFL_TEAM_COLUMN_WIDTH = 15;
   private final int DRAFT_TABLE_POSITION_COLUMN_WIDTH = 10;
   private final int DRAFT_TABLE_FANTASY_COLUMN_WIDTH = 45;
   private final int DRAFT_TABLE_BYE_COLUMN_WIDTH = 10;

  private int[] StandardTableDraftColumnWidths_ =
                                         { DRAFT_TABLE_PICK_COLUMN_WIDTH,
					   DRAFT_TABLE_PLAYER_NAME_COLUMN_WIDTH,
					   DRAFT_TABLE_NFL_TEAM_COLUMN_WIDTH,
					   DRAFT_TABLE_POSITION_COLUMN_WIDTH,
					   DRAFT_TABLE_FANTASY_COLUMN_WIDTH,
					   DRAFT_TABLE_BYE_COLUMN_WIDTH
					 };

  private StandardTable playerDraftTable_ =
      new StandardTable(DRAFT_TABLE_NAME,
                        new Dimension(150, 125),
                        StandardTableDraftColumnHeaders_,
                        StandardTableDraftColumnWidths_);
  private StandardTable playerDraftTable2_ =
      new StandardTable(DRAFT_TABLE_NAME,
                        new Dimension(150, 125),
                        StandardTableDraftColumnHeaders_,
                        StandardTableDraftColumnWidths_);
  private JScrollPane draftPane_ = new JScrollPane(playerDraftTable_);
  private JScrollPane draftPane2_ = new JScrollPane(playerDraftTable2_);

  private final String AVAILABLE_TABLE_PLAYER_NAME_COLUMN_NAME = "Name";
  private final String AVAILABLE_TABLE_POSITION_COLUMN_NAME = "Pos";
  private final String AVAILABLE_TABLE_NFL_TEAM_COLUMN_NAME = "Team";
  private final String AVAILABLE_TABLE_BYE_COLUMN_NAME = "Bye";
  private final String AVAILABLE_TABLE_ID_COLUMN_NAME = "ID";

  private String[] StandardTableAvailableColumnHeaders_ =
                                    { AVAILABLE_TABLE_PLAYER_NAME_COLUMN_NAME,
				      AVAILABLE_TABLE_POSITION_COLUMN_NAME,
				      AVAILABLE_TABLE_NFL_TEAM_COLUMN_NAME,
				      AVAILABLE_TABLE_BYE_COLUMN_NAME,
				      AVAILABLE_TABLE_ID_COLUMN_NAME
                                    };

  private final int AVAILABLE_TABLE_PLAYER_NAME_COLUMN_WIDTH = 70;
  private final int AVAILABLE_TABLE_POSITION_COLUMN_WIDTH = 10;
  private final int AVAILABLE_TABLE_NFL_TEAM_COLUMN_WIDTH = 10;
  private final int AVAILABLE_TABLE_BYE_COLUMN_WIDTH = 10;
  private final int AVAILABLE_TABLE_ID_COLUMN_WIDTH = 0;

  private int[] StandardTableAvailableColumnWidths_ =
      { AVAILABLE_TABLE_PLAYER_NAME_COLUMN_WIDTH,
        AVAILABLE_TABLE_POSITION_COLUMN_WIDTH,
        AVAILABLE_TABLE_NFL_TEAM_COLUMN_WIDTH,
        AVAILABLE_TABLE_BYE_COLUMN_WIDTH,
        AVAILABLE_TABLE_ID_COLUMN_WIDTH };
  private StandardTable playerAvailableTable_ =
      new StandardTable(AVAILABLE_TABLE_NAME,
                        new Dimension(90, 125),
                        StandardTableAvailableColumnHeaders_,
                        StandardTableAvailableColumnWidths_);
  private JScrollPane availablePane_ = new JScrollPane(playerAvailableTable_);

////
  private final String PRERANK_TABLE_PLAYER_NAME_COLUMN_NAME = "Name";
  private final String PRERANK_TABLE_POSITION_COLUMN_NAME = "Pos";
  private final String PRERANK_TABLE_NFL_TEAM_COLUMN_NAME = "Team";
  private final String PRERANK_TABLE_BYE_COLUMN_NAME = "Bye";
  private final String PRERANK_TABLE_ID_COLUMN_NAME = "ID";

  private String[] StandardTablePreRankColumnHeaders_ =
                                    { PRERANK_TABLE_PLAYER_NAME_COLUMN_NAME,
                                      PRERANK_TABLE_POSITION_COLUMN_NAME,
                                      PRERANK_TABLE_NFL_TEAM_COLUMN_NAME,
                                      PRERANK_TABLE_BYE_COLUMN_NAME,
                                      PRERANK_TABLE_ID_COLUMN_NAME
                                    };

  private final int PRERANK_TABLE_PLAYER_NAME_COLUMN_WIDTH = 70;
  private final int PRERANK_TABLE_POSITION_COLUMN_WIDTH = 10;
  private final int PRERANK_TABLE_NFL_TEAM_COLUMN_WIDTH = 10;
  private final int PRERANK_TABLE_BYE_COLUMN_WIDTH = 10;
  private final int PRERANK_TABLE_ID_COLUMN_WIDTH = 0;

  private int[] StandardTablePreRankColumnWidths_ =
      { PRERANK_TABLE_PLAYER_NAME_COLUMN_WIDTH,
        PRERANK_TABLE_POSITION_COLUMN_WIDTH,
        PRERANK_TABLE_NFL_TEAM_COLUMN_WIDTH,
        PRERANK_TABLE_BYE_COLUMN_WIDTH,
        PRERANK_TABLE_ID_COLUMN_WIDTH };
  private StandardTable playerPreRankTable_ =
      new StandardTable(PRERANK_TABLE_NAME,
                        new Dimension(90, 125),
                        StandardTablePreRankColumnHeaders_,
                        StandardTablePreRankColumnWidths_);
  private JScrollPane preRankPane_ = new JScrollPane(playerPreRankTable_);

////

  private JButton btnPlayerTakenOK_ = null;

  //Draft Dialog Confirm
  protected Dialog dlgConfirmDraft_ = null;
  protected JButton btnDraftYes_ = null;
  protected JButton btnDraftNo_ = null;

  protected Dialog dlgPlayerTaken_  = null;

  protected DraftInfo draftInfo_ = null;

  //League Info Thread
  protected Thread lit_ = null;
  protected LeagueInfoThread leagueInfoThread_ = null;
  //Heartbeat Thread
  protected Thread heartbeat_ = null;
  protected HeartBeatThread heartbeatThread_ = null;
  //Logged in teams thread
  protected Thread loggedInBaseThread_ = null;
  protected LoggedInThread loggedInThread_ = null;
  //New messages thread
  protected Thread newMessagesBaseThread_ = null;
  protected NewMessagesThread newMessagesThread_ = null;
  //Message cleanup thread
  protected Thread messageCleanupBaseThread_ = null;
  protected MessageCleanupThread messageCleanupThread_ = null;

  //Currently logged in fantasy teams vector
  protected Vector loggedInTeams_ = new Vector();

  //Servlets
  protected String servletParameters_ = "";
  protected Servlet createDraftMessageServlet_ = null;
  protected Servlet doDraftServlet_ = null;
  protected Servlet getDraftInfoServlet_ = null;
  protected Servlet getAliveTeamsServlet_ = null;
  protected Servlet getNFLTeamsServlet_ = null;
  protected Servlet heartbeatServlet_ = null;
  protected Servlet getDraftRecordsServlet_ = null;
  protected Servlet getNewMessagesServlet_ = null;
  protected Servlet searchServlet_ = null;
  protected Servlet toggleDraftServlet_ = null;
  protected Servlet deletePickServlet_ = null;
  protected Servlet messageCleanupServlet_ = null;
  protected Servlet logoutServlet_ = null;
  protected Servlet getAllPlayersServlet_ = null;
  protected Servlet getNumberOfPlayersServlet_ = null;
  protected Servlet getPreRankedPlayersServlet_ = null;


  protected StringBuffer sb_ = new StringBuffer();
  //Servlet Parameters
  protected StringBuffer yearLeagueName_ = new StringBuffer();
  protected StringBuffer yearLeagueNameTeamName_ = new StringBuffer();


  //Get these using the information from the database -- get real positions
  protected String[] positions_ = { "QB", "RB", "WR", "TE", "PK", "SP", "DE" };


  //If true, then can draft for any team and can stop the draft
  protected boolean admin_ = false;
  //If true, then no one can draft (except for the administrator)
  protected boolean draftLocked_ = false;

  protected ButtonAdapter buttonAdapter = new ButtonAdapter(this);

  //Change this to get from the player database
  String[] byeWeeks_ = { "3", "4", "5", "6", "7", "8", "9", "10" };

  protected final static int PLAYER_NAME_COLUMN = 0;
  protected final static int PLAYER_POSITION_COLUMN = 1;
  protected final static int PLAYER_TEAM_COLUMN = 2;
  protected final static int PLAYER_ID_COLUMN = 4;

  protected HashMap nflPlayers_ = null;

  protected String leagueYear_ = "";
  protected String leagueName_ = "";
  protected String teamName_ = "";
  protected int teamNumber_ = -1;

  /**
   * Constructor that call methods that draws controls and fills the information
   * @param draftApplet Container
   * @param admin boolean true if administrator
   */

  public SwingDraftPanel(SwingDraft draftApplet,
                         boolean admin)
  {
    draftApplet_ = draftApplet;
    admin_ = admin;

    leagueYear_ = draftApplet_.getLeagueYear();
    leagueName_ = draftApplet_.getLeagueName();
    teamName_   = draftApplet_.getTeamName();
    teamNumber_ = draftApplet_.getTeamNumber();

    initServletParameters();
    createServlets();

    drawEmptyControls();

    fillLoggedInControls();

    int numberOfPlayers = 0;

    sb_.setLength(0);      //Clear the buffer
    sb_.append(leagueYear_);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName_);

    try
    {
      numberOfPlayers = Integer.parseInt(getNumberOfPlayersServlet_.execute(sb_.toString()));
    }
    catch (CantConnectException e)
    {
      showError();
    }
    catch (NumberFormatException nfe)
    {
      numberOfPlayers = 0;
    }

    if (numberOfPlayers != 0)
    {
      //nflPlayers_ = new HashMap(numberOfPlayers);
    }

    fillPositionList();
    try
    {
      lstNFLTeams_.fillList(getNFLTeamsServlet_.execute(), ",");
    }
    catch (CantConnectException e)
    {
      showError();
    }

    lstNFLTeams_.invalidate();
    fillDraftList();
    fillPreRankList();

    lstByeWeeks_.fillFromArray(byeWeeks_);
/*
    sb_.setLength(0);
    sb_.append(leagueYear_);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName_);
*/
//    sb_ = createYearNameStringBuffer();
    try
    {
      draftInfo_ =
	  new DraftInfo(getDraftInfoServlet_.execute(yearLeagueName_.toString()));
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
    sb_.append(leagueYear_);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName_);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(teamName_);
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
    sb_.append(leagueYear_);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName_);
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

    this.setBackground(new Color(255, 255, 204));
  }

  private void showError()
  {
    System.out.println("Can connect error");
  }

  public void mouseExited(MouseEvent me)
  {
  }

  public void mouseEntered(MouseEvent me)
  {
  }

  public void mouseReleased(MouseEvent me)
  {
    handleMouseEvent(me);
  }

  public void mousePressed(MouseEvent me)
  {
  }

  public void mouseClicked(MouseEvent me)
  {
    handleMouseEvent(me);
  }

  /**
   * Deals with mouse events for both released and clicked events
   * @param me MouseEvent object
   */

  private void handleMouseEvent(MouseEvent me)
  {
//    if (me.getSource().getClass().getName().equals("com.twoforboth.draft.table.StandardTable"))
    if (me.getSource().getClass().getName().equals("com.twoforboth.controls.Swing.StandardTable"))
    {
      int nRow = playerAvailableTable_.rowAtPoint( new Point(me.getX(), me.getY()) );
      ((StandardTable)me.getSource()).selectRow(nRow);
      if (((StandardTable)me.getSource()).getName().equals(AVAILABLE_TABLE_NAME))
      {
	updateDraftButtonStatus();
      }
      else if (((StandardTable)me.getSource()).getName().equals(DRAFT_TABLE_NAME))
      {
	updateDeletePickButtonStatus();
      }
      else if (((StandardTable)me.getSource()).getName().equals(PRERANK_TABLE_NAME))
      {
        updatePreRankDraftButtonStatus();
      }
    }
  }

  /**
   * Called when an item is selected on the available player table
   * If after moving the draft along and the new current player has an
   * available player selected, enable the draft button.<BR>
   * Note the admin can always draft, so the button always be enabled
   */
  private void updateDraftButtonStatus()
  {
    if (admin_)
    {
      btnDraft_.setEnabled(true);
    }
    else if (draftLocked_)
    {
      btnDraft_.setEnabled(false);
    }
    else if (draftApplet_.getTeamName().compareTo(draftInfo_.getTeamName())
	     == 0)
    {
      btnDraft_.setEnabled(true);
    }
  }

  private void updatePreRankDraftButtonStatus()
  {
    if (admin_)
    {
      btnDraftPreRank_.setEnabled(true);
    }
    else if (draftLocked_)
    {
      btnDraftPreRank_.setEnabled(false);
    }
    else if (draftApplet_.getTeamName().compareTo(draftInfo_.getTeamName())
             == 0)
    {
      btnDraftPreRank_.setEnabled(true);
    }
  }

  /**
   * Updates the enabled status for the delete button
   */

  private void updateDeletePickButtonStatus()
  {
    if (admin_)
    {
      btnDeletePick_.setEnabled(true);
    }
  }

  /**
   * Event listener for DraftChangedEvents.  Updates the draftInfo_ class
   * updaes the draft list and controls
   * @param dce DraftChangedEvent object
   */

  public void DraftChanged(DraftChangedEvent dce)
  {
    draftInfo_ = dce.getDraftInfo();

    //Can determine from the draftInfo_ object what is different
    //(i.e. check for locked, etc)
    //draftLocked_

    if ((draftInfo_.getLocked().equals("0") && draftLocked_ == true) ||
        (draftInfo_.getLocked().equals("1") && draftLocked_ == false))
    {
      draftLocked_ = !draftLocked_;
    }
    //Change the displayed current draft position values
    updateDraftInfoControls();
    //Update the draft table
    fillDraftList();
    //Update the pre-rankings
    fillPreRankList();
    //Modify the draft buttons
    updateDraftButtonStatus();
    updatePreRankDraftButtonStatus();
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
      messagesModel_.addElement(
                   (String)newMessagesArrivedEvent.getMessages().elementAt(i));
    }
    //Scroll to the newly added element(s)
    lstChat_.ensureIndexIsVisible(messagesModel_.getSize() - 1);
  }

  /**
   * Text box handler
   * @param actionEvent Event triggered by a button
   */
  public void actionPerformed(ActionEvent actionEvent)
  {
    Object target = actionEvent.getSource();

    if (target == txtMessage_)   //Enter key hit in txtMessage_
    {
      createDraftMessage(txtMessage_.getText());
      txtMessage_.setText(DraftConstants.EMPTY_STRING);
    }
  }

  /**
   * Button OK (from draft dialog box) handler
   * @param actionEvent Event triggered by button
   */

  public void handleButtonTakenOK(ActionEvent actionEvent)
  {
    dlgPlayerTaken_.setVisible(false);
  }

  /**
   * Button associated with a JListPlus handler
   * Calls method to select all items in the JListPlus list
   * @param actionEvent Event triggered by button
   */
  public void selectAllListItems(ActionEvent actionEvent)
  {
    JButtonSelect b = (JButtonSelect)actionEvent.getSource();
    b.selectAll();
  }

  /**
   * Button associated with a JListPlus handler
   * Calls method to deselect all items in the JListPlus list
   * @param actionEvent Event triggered by button
   */
  public void deselectAllListItems(ActionEvent actionEvent)
  {
    JButtonSelect b = (JButtonSelect)actionEvent.getSource();
    b.deselectAll();
  }

  /**
   * JButtonEmotion event handler
   * Creates a message based on the value of the emotion
   * in the JButtonEmotion object
   * @param actionEvent Event triggered by button
   */
  public void createDraftMessage(ActionEvent actionEvent)
  {
    JButtonEmotion jbe = (JButtonEmotion)actionEvent.getSource();

    createDraftMessage(jbe.getEmotion());
  }

  /**
   * Creates a text message to send to the createDraftMessage servlet
   * @param message Message to create
   */

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

  /**
   * Creates a text message to send to the createDraftMessage servlet
   * that indicates the draft action
   * @param message Message to create
   */

  private void createDoDraftMessage(String message)
  {
    StringBuffer text = new StringBuffer(draftInfo_.getTeamName());
    text.append("--");
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

  /**
   * Creates an action message to send to createDraftMessage servlet based
   * on message
   * @param message Message to create
   */

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
   * Event handler for delete button
   * Calls the servlet to delete the currenly selected pick from the draft
   * @param actionEvent Event triggered by button
   */

  public void deletePick(ActionEvent actionEvent)
  {
    //Clear the buffer
    sb_.setLength(0);
    //Set up parameters
    sb_.append(draftApplet_.getLeagueYear());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(draftApplet_.getLeagueName());
    sb_.append(ServletInfo.DELIMITER);
    sb_.append((playerDraftTable_.getSelectedRow() + 1));
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
   * Event handler for pause draft button
   * Calls servlet to change state of draft
   * @param actionEvent Event triggered by button
   */

  public void toggleDraft(ActionEvent actionEvent)
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
      if (usersModel_.contains(v.elementAt(i)) == false)
      {
         usersModel_.addElement(v.elementAt(i));
      }
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
   * @param actionEvent Event from button being pressed
   */

  public void doSearch(ActionEvent actionEvent)
  {
    String result = "";

    Object[] nflPlayerDetailKeys = { "PLAYER NAME", "POS", "NFL TEAM", "BYE", "ID"};
    int[] nflPlayerDetailLengths = { StandardTable.LENGTH_DONT_CARE,
                                     StandardTable.LENGTH_DONT_CARE,
				     StandardTable.LENGTH_DONT_CARE,
				     StandardTable.LENGTH_DONT_CARE,
				     StandardTable.LENGTH_DONT_CARE };


    btnDraft_.setEnabled(false);          //Disable the draft button


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


    playerAvailableTable_.clear();
    playerAvailableTable_.fill(result,
			       "|",
			       "`",
			       '=',
			       nflPlayerDetailKeys,
			       nflPlayerDetailLengths);

    validate();
  }

  /**
   * Populates the draft list box
   */
  private void fillDraftList()
  {
    String result = "";
    try
    {
      result = getDraftRecordsServlet_.execute();
    }
    catch (CantConnectException e)
    {
      showError();
    }


    Object[] nflPlayerDetailKeys = { "NUM",
                                     "PLAYER NAME",
				     "POS",
				     "PLAYER TEAM",
				     "FANTASY TEAM",
				     "BYE"};
    int[] nflPlayerDetailLengths = { 3, 15, 4, 4, 8, 5 };
    playerDraftTable_.clear();
    playerDraftTable2_.clear();
    playerDraftTable_.fill(result,
			       "|",
			       "`",
			       '=',
			       nflPlayerDetailKeys,
			       nflPlayerDetailLengths);
    playerDraftTable2_.fill(result,
                               "|",
                               "`",
                               '=',
                               nflPlayerDetailKeys,
                               nflPlayerDetailLengths);
    //Scroll to bottom
    playerDraftTable_.scrollRectToVisible(playerDraftTable_.getCellRect(playerDraftTable_.getRowCount() -1, -1, true));
    playerDraftTable2_.scrollRectToVisible(playerDraftTable2_.getCellRect(playerDraftTable2_.getRowCount() -1, -1, true));
    validate();
  }

  /**
   * Populates the draft list box
   */
  private void fillPreRankList()
  {
    String result = "";
    try
    {
      result = getPreRankedPlayersServlet_.execute();
    }
    catch (CantConnectException e)
    {
      showError();
    }


    Object[] nflPlayerDetailKeys = { "PLAYER NAME",
                                     "POS",
                                     "NFL TEAM",
                                     "BYE",
                                     "ID"};
    int[] nflPlayerDetailLengths = { 15, 4, 4, 5, StandardTable.LENGTH_DONT_CARE };
    playerPreRankTable_.clear();
    playerPreRankTable_.fill(result,
                               "|",
                               "`",
                               '=',
                               nflPlayerDetailKeys,
                               nflPlayerDetailLengths);
    validate();
  }


  /**
   * Updates the controls displaying the current draft position
   */

  private void updateDraftInfoControls()
  {
    if (draftInfo_.getRound().equals(DraftConstants.END_OF_DRAFT_ROUND) == true)
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
   * @param actionEvent Event to associate with dialog
  */

  public void createConfirmationDraftDialog(ActionEvent actionEvent)
  {
    dlgConfirmDraft_ = new Dialog(draftApplet_.getParentFrame(), false);
    dlgConfirmDraft_.setTitle(DraftConstants.DRAFT_CONFIRMATION_TITLE);
    dlgConfirmDraft_.setResizable(false);
    btnDraftYes_ = new JButton(DraftConstants.BUTTON_YES_DESC);
    btnDraftNo_ = new JButton(DraftConstants.BUTTON_NO_DESC);
    JLabel lblQuestion = new JLabel(DraftConstants.DRAFT_QUESTION);

    int col =
	playerAvailableTable_.getColumn(AVAILABLE_TABLE_PLAYER_NAME_COLUMN_NAME).getModelIndex();
    String playerName =
          (String)playerAvailableTable_.getValueAt(playerAvailableTable_.getSelectedRow(), col);
    col =
	playerAvailableTable_.getColumn(AVAILABLE_TABLE_POSITION_COLUMN_NAME).getModelIndex();
    String position =
	(String)playerAvailableTable_.getValueAt(playerAvailableTable_.getSelectedRow(), col);
    col =
	playerAvailableTable_.getColumn(AVAILABLE_TABLE_NFL_TEAM_COLUMN_NAME).getModelIndex();
    String team =
	(String)playerAvailableTable_.getValueAt(playerAvailableTable_.getSelectedRow(), col);
    StringBuffer sb = new StringBuffer(playerName);
    sb.append(' ');
    sb.append(position);
    sb.append(' ');
    sb.append(team);

    JLabel lblPlayer = new JLabel(sb.toString());
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

    try
    {
      buttonAdapter.registerActionEventHandler(btnDraftYes_,
					       DraftConstants.DO_DRAFT_CONFIRMED_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnDraftNo_,
					       DraftConstants.DO_DRAFT_NEGATED_CALLBACK);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    dlgConfirmDraft_.setSize(300, 200);
    dlgConfirmDraft_.setLocation((draftApplet_.getX() + draftApplet_.getWidth()) / 2 -
                                 dlgConfirmDraft_.getWidth() / 2,
                                 (draftApplet_.getY() + draftApplet_.getHeight()) / 2);

    dlgConfirmDraft_.show();
  }

  public void doDraftConfirmed(ActionEvent actionEvent)
  {
    dlgConfirmDraft_.setVisible(false);
    doDraft();
  }

  public void doDraftNegated(ActionEvent actionEvent)
  {
    dlgConfirmDraft_.setVisible(false);
  }

  /**
   * Calls a servlet to draft a player
   */

  private void doDraft()
  {
    String result = "";
    int row = playerAvailableTable_.getSelectedRow();
    String playerName =
	(String)playerAvailableTable_.getValueAt(row, PLAYER_NAME_COLUMN);
    String playerTeam =
	(String)playerAvailableTable_.getValueAt(row, PLAYER_TEAM_COLUMN);
    String playerPosition =
	(String)playerAvailableTable_.getValueAt(row, PLAYER_POSITION_COLUMN);

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
    sb_.append(playerAvailableTable_.getValueAt(row, PLAYER_ID_COLUMN));
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(draftInfo_.getPick());

    //System.out.println(sb_.toString());
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
        buildPlayerTakenDialog(DraftConstants.PLAYER_TAKEN_STRING);
      }
      else
      {
        //Remove the item just drafted
        //lstAvailable_.remove(lstAvailable_.getSelectedIndex());
	sb_.setLength(0);
	sb_.append("Drafts ");
	sb_.append(playerName);
	sb_.append(' ');
	sb_.append(playerTeam);
	sb_.append(' ');
	sb_.append(playerPosition);
	createDoDraftMessage(sb_.toString());
	playerAvailableTable_.removeRow(row);
        btnDraft_.setEnabled(false);
        btnDraftPreRank_.setEnabled(false);
      }
  }


  /**
   * Builds the draft confirmation dialog
   * @param actionEvent Event to associate with dialog
  */

  public void createConfirmationPreRankDraftDialog(ActionEvent actionEvent)
  {
    dlgConfirmDraft_ = new Dialog(draftApplet_.getParentFrame(), false);
    dlgConfirmDraft_.setTitle(DraftConstants.DRAFT_CONFIRMATION_TITLE);
    dlgConfirmDraft_.setResizable(false);
    btnDraftYes_ = new JButton(DraftConstants.BUTTON_YES_DESC);
    btnDraftNo_ = new JButton(DraftConstants.BUTTON_NO_DESC);
    JLabel lblQuestion = new JLabel(DraftConstants.DRAFT_QUESTION);

    int col =
        playerPreRankTable_.getColumn(PRERANK_TABLE_PLAYER_NAME_COLUMN_NAME).getModelIndex();
    String playerName =
          (String)playerPreRankTable_.getValueAt(playerPreRankTable_.getSelectedRow(), col);
    col =
        playerPreRankTable_.getColumn(PRERANK_TABLE_POSITION_COLUMN_NAME).getModelIndex();
    String position =
        (String)playerPreRankTable_.getValueAt(playerPreRankTable_.getSelectedRow(), col);
    col =
        playerPreRankTable_.getColumn(PRERANK_TABLE_NFL_TEAM_COLUMN_NAME).getModelIndex();
    String team =
        (String)playerPreRankTable_.getValueAt(playerPreRankTable_.getSelectedRow(), col);
    StringBuffer sb = new StringBuffer(playerName);
    sb.append(' ');
    sb.append(position);
    sb.append(' ');
    sb.append(team);

    JLabel lblPlayer = new JLabel(sb.toString());
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

    try
    {
      buttonAdapter.registerActionEventHandler(btnDraftYes_,
                                               DraftConstants.DO_PRERANK_DRAFT_CONFIRMED_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnDraftNo_,
                                               DraftConstants.DO_PRERANK_DRAFT_NEGATED_CALLBACK);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    dlgConfirmDraft_.setSize(300, 200);
    dlgConfirmDraft_.setLocation((draftApplet_.getX() + draftApplet_.getWidth()) / 2 -
                                 dlgConfirmDraft_.getWidth() / 2,
                                 (draftApplet_.getY() + draftApplet_.getHeight()) / 2);

    dlgConfirmDraft_.show();
  }

  public void doPreRankDraftConfirmed(ActionEvent actionEvent)
  {
    dlgConfirmDraft_.setVisible(false);
    doPreRankDraft();
  }

  public void doPreRankDraftNegated(ActionEvent actionEvent)
  {
    dlgConfirmDraft_.setVisible(false);
  }

  /**
   * Calls a servlet to draft a player
   */

  private void doPreRankDraft()
  {
    String result = "";
    int row = playerPreRankTable_.getSelectedRow();
    String playerName =
        (String)playerPreRankTable_.getValueAt(row, PLAYER_NAME_COLUMN);
    String playerTeam =
        (String)playerPreRankTable_.getValueAt(row, PLAYER_TEAM_COLUMN);
    String playerPosition =
        (String)playerPreRankTable_.getValueAt(row, PLAYER_POSITION_COLUMN);

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
    sb_.append(playerPreRankTable_.getValueAt(row, PLAYER_ID_COLUMN));
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(draftInfo_.getPick());

    //System.out.println(sb_.toString());
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
        buildPlayerTakenDialog(DraftConstants.PLAYER_TAKEN_STRING);
      }
      else
      {
        //Remove the item just drafted
        //lstPreRank_.remove(lstPreRank_.getSelectedIndex());
        sb_.setLength(0);
        sb_.append("Drafts ");
        sb_.append(playerName);
        sb_.append(' ');
        sb_.append(playerTeam);
        sb_.append(' ');
        sb_.append(playerPosition);
        createDoDraftMessage(sb_.toString());
        playerPreRankTable_.removeRow(row);
        btnDraft_.setEnabled(false);
        btnDraftPreRank_.setEnabled(false);
      }
  }




  /**
   * Creates a dialog that indicates that the current player was taken
   * @param message Message for dialog
   */

  private void buildPlayerTakenDialog(String message)
  {
    dlgPlayerTaken_ = new Dialog(draftApplet_.getParentFrame(), false);
    dlgPlayerTaken_.setTitle(DraftConstants.PLAYER_TAKEN_STRING);
    dlgPlayerTaken_.setResizable(false);
    btnPlayerTakenOK_ = new JButton(DraftConstants.BUTTON_OK_DESC);
    JLabel lblMessage = new JLabel(message);

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

    try
    {
      buttonAdapter.registerActionEventHandler(btnPlayerTakenOK_,
					       DraftConstants.PLAYER_TAKEN_OK_BUTTON_CALLBACK);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    dlgPlayerTaken_.setSize(300, 200);

    dlgPlayerTaken_.setLocation((draftApplet_.getX() + draftApplet_.getWidth()) / 2 -
                                dlgPlayerTaken_.getWidth() / 2,
                                (draftApplet_.getY() + draftApplet_.getHeight()) / 2);


    dlgPlayerTaken_.show();
  }

  /**
   * Populates the position list
   */
  private void fillPositionList()
  {
    lstPositions_.fillFromArray(positions_);
  }

  /**
   * Instantiates all of the servlets
   */
  private void createServlets()
  {
    createDraftMessageServlet_ =
              new Servlet(ServletInfo.CREATE_DRAFT_MESSAGE_SERVLET_NAME,
                          ServletInfo.CREATE_DRAFT_MESSAGE_SERVLET_PARAMETERS);

    doDraftServlet_ =
              new Servlet(ServletInfo.DO_DRAFT_SERVLET_NAME,
                          ServletInfo.DO_DRAFT_SERVLET_PARAMETERS);

    getDraftInfoServlet_ =
              new Servlet(ServletInfo.GET_DRAFT_INFO_SERVLET_NAME,
                          ServletInfo.GET_ALIVE_TEAMS_SERVLET_PARAMETERS,
                          yearLeagueName_.toString());

    getDraftRecordsServlet_ =
              new Servlet(ServletInfo.GET_DRAFT_RECORDS_SERVLET_NAME,
                          ServletInfo.GET_DRAFT_RECORDS_SERVLET_PARAMETERS,
                          yearLeagueName_.toString());


    getNewMessagesServlet_ =
              new Servlet(ServletInfo.GET_NEW_MESSAGES_SERVLET_NAME,
                          ServletInfo.GET_NEW_MESSAGES_SERVLET_PARAMETERS);

    getNFLTeamsServlet_ =
              new Servlet(ServletInfo.GET_NFL_TEAMS_SERVLET_NAME,
                          ServletInfo.GET_NFL_TEAMS_SERVLET_PARAMETERS,
                          leagueYear_);

    searchServlet_ =
              new Servlet(ServletInfo.SEARCH_SERVLET_NAME,
                          ServletInfo.SEARCH_SERVLET_PARAMETERS);

    toggleDraftServlet_ =
              new Servlet(ServletInfo.TOGGLE_DRAFT_SERVLET_NAME,
                          ServletInfo.TOGGLE_DRAFT_SERVLET_PARAMETERS,
                          yearLeagueName_.toString());

    getAliveTeamsServlet_ =
              new Servlet(ServletInfo.GET_ALIVE_TEAMS_SERVLET_NAME,
                          ServletInfo.GET_ALIVE_TEAMS_SERVLET_PARAMETERS,
                          yearLeagueName_.toString());

    heartbeatServlet_ =
              new Servlet(ServletInfo.HEARTBEAT_SERVLET_NAME,
                          ServletInfo.HEARTBEAT_SERVLET_PARAMETERS,
                          yearLeagueNameTeamName_.toString());

    deletePickServlet_ =
              new Servlet(ServletInfo.DELETE_PICK_SERVLET_NAME,
                          ServletInfo.DELETE_PICK_SERVLET_PARAMETERS);

    messageCleanupServlet_ =
              new Servlet(ServletInfo.MESSAGE_CLEANUP_SERVLET_NAME,
                          ServletInfo.MESSAGE_CLEANUP_SERVLET_PARAMETERS);

    logoutServlet_ =
              new Servlet(ServletInfo.LOGOUT_SERVLET_NAME,
                          ServletInfo.LOGOUT_SERVLET_PARAMETERS,
                          yearLeagueNameTeamName_.toString());

    getAllPlayersServlet_ =
              new Servlet(ServletInfo.GET_ALL_NFL_PLAYERS,
                          ServletInfo.GET_ALL_NFL_PLAYERS_SERVLET_PARAMETERS,
                          yearLeagueName_.toString());

    getNumberOfPlayersServlet_ =
              new Servlet(ServletInfo.GET_NUMBER_OF_PLAYERS,
                          ServletInfo.GET_NUMBER_OF_PLAYERS_SERVLET_PARAMETERS,
                          yearLeagueName_.toString());

    getPreRankedPlayersServlet_ =
              new Servlet(ServletInfo.GET_PRERANK,
                          ServletInfo.GET_PRERANK_SERVLET_PARAMETERS);


    sb_.setLength(0);
    sb_.append(leagueYear_);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName_);
    sb_.append(ServletInfo.DELIMITER);
    //Convert to seconds
    sb_.append((DraftConstants.MESSAGE_CLEANUP_INTERVAL / 1000));
    messageCleanupServlet_.setStaticValues(sb_.toString());


    sb_.setLength(0);
    sb_.append(leagueYear_);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(leagueName_);
    sb_.append(ServletInfo.DELIMITER);
    sb_.append(teamNumber_);

    getPreRankedPlayersServlet_.setStaticValues(sb_.toString());
  }

  private void initServletParameters()
  {
    yearLeagueName_.setLength(0);
    yearLeagueName_.append(leagueYear_);
    yearLeagueName_.append(ServletInfo.DELIMITER);
    yearLeagueName_.append(leagueName_);

    yearLeagueNameTeamName_.setLength(0);
    yearLeagueNameTeamName_.append(leagueYear_);
    yearLeagueNameTeamName_.append(ServletInfo.DELIMITER);
    yearLeagueNameTeamName_.append(leagueName_);
    yearLeagueNameTeamName_.append(ServletInfo.DELIMITER);
    yearLeagueNameTeamName_.append(teamName_);
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
    topPanel_.setBackground(new Color(255, 255, 204));
    middlePanel_.setBackground(new Color(255, 255, 204));
    bottomPanel_.setBackground(new Color(255, 255, 204));

    setLayout(gbl_);    //Set the layout for the entire panel

    //6/15/03
    add(topPanel_, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                          GridBagConstraints.NORTHWEST,
                                          GridBagConstraints.BOTH,
                                          new Insets(0, 0, 0, 0), 0, 0));

    add(middlePanel_, new GridBagConstraints(0, 1, 1, 3, 0.0, 0.0,
                                          GridBagConstraints.NORTHWEST,
                                          GridBagConstraints.BOTH,
                                          new Insets(0, 0, 0, 0), 0, 115));
    add(bottomPanel_, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                                          GridBagConstraints.NORTHWEST,
                                          GridBagConstraints.BOTH,
                                          new Insets(0, 0, 0, 0), 0, 0));

    drawEmptyRoundInfoControls();

    topPanel_.add(roundInfoPanel_,
                  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                         GridBagConstraints.NORTHWEST,
                                         GridBagConstraints.VERTICAL,
                                         new Insets(0, 0, 0, 0), 0, 20));
    roundInfoPanel_.setBorder(new EtchedBorder(EtchedBorder.RAISED));


    drawTitleRoundControls();
    topPanel_.add(titlePanel_,
        new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                               GridBagConstraints.CENTER,
                               GridBagConstraints.VERTICAL,
                               new Insets(0, 0, 0, 0), 80, 20));


    drawEmptyDraftDescriptionControls();
    leagueInfoPanel_.setBorder(new EtchedBorder(EtchedBorder.RAISED));
    topPanel_.add(leagueInfoPanel_,
        new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                               GridBagConstraints.NORTHEAST,
                               GridBagConstraints.VERTICAL,
                               new Insets(0, 0, 0, 0), 0, 20));

    jTabbedPane_.add("Main", draftTotalPanel_);
    jTabbedPane_.add("Auto", autoDraftPanel_);
    jTabbedPane_.add("Rosters", rostersPanel_);
    middlePanel_.add(jTabbedPane_,
        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                               GridBagConstraints.CENTER,
                               GridBagConstraints.HORIZONTAL,
                               new Insets(0, 0, 0, 0), 0, 0));

    draftTotalPanel_.setBackground(new Color(255, 255, 204));
    autoDraftPanel_.setBackground(new Color(255, 255, 204));
    rostersPanel_.setBackground(new Color(255, 255, 204));
    titlePanel_.setBackground(new Color(255, 255, 204));
    roundInfoPanel_.setBackground(new Color(255, 255, 204));
    leagueInfoPanel_.setBackground(new Color(255, 255, 204));
///////////////////////////////////////////////////////////////////////////////
// Main Draft Tab

    drawDraftPanel();
    draftTotalPanel_.add(draftPanel_,
        new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.HORIZONTAL,
                               new Insets(0, 0, 0, 0), 0, 0));

    drawAvailablePanel();
    draftTotalPanel_.add(availablePanel_,
        new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.HORIZONTAL,
                               new Insets(0, 0, 0, 0), 0, 0));


    drawNFLTeamsPanel();
    draftTotalPanel_.add(nflTeamsPanel_,
        new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.HORIZONTAL,
                               new Insets(0, 0, 0, 0), 0, 0));


    drawPositionsPanel();
    draftTotalPanel_.add(positionsPanel_,
        new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.HORIZONTAL,
                               new Insets(0, 0, 0, 0), 0, 0));



    drawByeWeeksPanel();
    draftTotalPanel_.add(byeWeeksPanel_,
        new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.HORIZONTAL,
                               new Insets(0, 0, 0, 0), 0, 0));


///////////////////////////////////////////////////////////////////////////////
// AUTO TAB PAGE

    drawDraftPanel2();
    autoDraftPanel_.add(draftPanel2_,
        new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.HORIZONTAL,
                               new Insets(0, 0, 0, 0), 0, 0));

    drawPreRankPanel();
    autoDraftPanel_.add(preRankPanel_,
        new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.HORIZONTAL,
                               new Insets(0, 0, 0, 0), 0, 0));


///////////////////////////////////////////////////////////////////////////////


    txtMessage_.setColumns(128);
    bottomPanel_.add(txtMessage_,
        new GridBagConstraints(0, 2, 5, 1, 0.0, 0.0,
                               GridBagConstraints.WEST,
                               GridBagConstraints.NONE,
                               new Insets(0, 0, 0, 0), 0, 0));

    drawEmotionPanel();
    bottomPanel_.add(emotionButtonPanel_,
        new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
                               GridBagConstraints.CENTER,
                               GridBagConstraints.NONE,
                               new Insets(0, 0, 0, 0), 0, 0));


    bottomPanel_.add(chatScrollPane_,
        new GridBagConstraints(0, 3, 5, 1, 0.0, 0.0,
                               GridBagConstraints.WEST,
                               GridBagConstraints.BOTH,
                               new Insets(0, 0, 0, 0), 0, 100));
    bottomPanel_.add(usersScrollPane_,
        new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0,
                               GridBagConstraints.WEST,
                               GridBagConstraints.HORIZONTAL,
                               new Insets(0, 0, 0, 0), 0, 100));

    //Set up the event listeners for the buttons
    try
    {
      buttonAdapter.registerActionEventHandler(btnDraft_,
					       DraftConstants.CONFIRM_DRAFT_CALLBACK);
      //Make sure 6/21/03
      buttonAdapter.registerActionEventHandler(btnDraftPreRank_,
                                               DraftConstants.CONFIRM_DRAFT_PRERANK_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnSearch_,
					       DraftConstants.DO_SEARCH_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnSelectAllTeams_,
					       DraftConstants.SELECT_ALL_LIST_ITEMS_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnDeselectAllTeams_,
					       DraftConstants.DESELECT_ALL_LIST_ITEMS_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnSelectAllPositions_,
					       DraftConstants.SELECT_ALL_LIST_ITEMS_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnDeselectAllPositions_,
					       DraftConstants.DESELECT_ALL_LIST_ITEMS_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnSelectAllByeWeeks_,
					       DraftConstants.SELECT_ALL_LIST_ITEMS_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnDeselectAllByeWeeks_,
					       DraftConstants.DESELECT_ALL_LIST_ITEMS_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnPauseDraft_,
					       DraftConstants.TOGGLE_DRAFT_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnDeletePick_,
					       DraftConstants.DELETE_PICK_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnHappy_,
					       DraftConstants.CREATE_DRAFT_MESSAGE_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnSad_,
					       DraftConstants.CREATE_DRAFT_MESSAGE_CALLBACK);
      buttonAdapter.registerActionEventHandler(btnCurse_,
					       DraftConstants.CREATE_DRAFT_MESSAGE_CALLBACK);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    //Listen for any mouse messages from the available table
    playerAvailableTable_.addMouseListener(this);
    playerDraftTable_.addMouseListener(this);
    playerDraftTable2_.addMouseListener(this);
    playerPreRankTable_.addMouseListener(this);

    if (admin_)
    {
      btnPauseDraft_.setVisible(true);
      btnDeletePick_.setVisible(true);
    }
    else
    {
      btnHelp_.setVisible(true);
      playerDraftTable_.setEnabled(false);
      playerDraftTable2_.setEnabled(false);
    }

    //Set up the event listener for the available list
    //8/24/02 -- Use table anyway
    //lstAvailable_.addItemListener(this);

    //Set up the event listener for the txtMessage_
    txtMessage_.addActionListener(this);

    //Disable the draft button
    btnDraft_.setEnabled(false);
    btnDraftPreRank_.setEnabled(false);

    lstChat_.setModel(messagesModel_);
    lstUsers_.setModel(usersModel_);

   }

  private void drawEmotionPanel()
  {
    emotionButtonPanel_ = new JPanel(new GridBagLayout());
    emotionButtonPanel_.setBackground(new Color(255, 255, 204));
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
    availablePanel_ = new JPanel(new GridBagLayout());
    availablePanel_.setBackground(new Color(255, 255, 204));
    Insets insets = new Insets(0, 0, 0, 0);

    availablePanel_.add(lblAvailableDesc_,
        new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                               GridBagConstraints.CENTER,
                               GridBagConstraints.NONE,
                               insets, 0, 0));

    availablePanel_.add(availablePane_,
        new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.BOTH,
                               insets, 150, 125));
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

  private void drawPreRankPanel()
  {
    preRankPanel_ = new JPanel(new GridBagLayout());
    preRankPanel_.setBackground(new Color(255, 255, 204));
    Insets insets = new Insets(0, 0, 0, 0);

    preRankPanel_.add(lblPreRankDesc_,
        new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                               GridBagConstraints.CENTER,
                               GridBagConstraints.NONE,
                               insets, 0, 0));

    preRankPanel_.add(preRankPane_,
        new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.BOTH,
                               insets, 150, 125));
    preRankPanel_.add(btnDraftPreRank_,
        new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                               GridBagConstraints.CENTER,
                               GridBagConstraints.NONE,
                               insets, 0, 0));
  }

  private void drawDraftPanel()
  {
    draftPanel_ = new JPanel(new GridBagLayout());
    draftPanel_.setBackground(new Color(255, 255, 204));

    draftPanel_.add(lblDraftDesc_,
        new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.NONE,
                               new Insets(0, 0, 0, 0), 0, 0));

    draftPanel_.add(draftPane_,
        new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.BOTH,
                               new Insets(0, 0, 0, 0), 150, 125));
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
		      GridBagConstraints.NORTH,
		      GridBagConstraints.NONE,
		      new Insets(0, 0, 0, 0), 0, 0));
    }
  }

  private void drawDraftPanel2()
  {
    draftPanel2_ = new JPanel(new GridBagLayout());
    draftPanel2_.setBackground(new Color(255, 255, 204));

    draftPanel2_.add(lblDraftDesc2_,
        new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.NONE,
                               new Insets(0, 0, 0, 0), 0, 0));

    draftPanel2_.add(draftPane2_,
        new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.BOTH,
                               new Insets(0, 0, 0, 0), 150, 125));
  }


  private void drawNFLTeamsPanel()
  {
    nflTeamsPanel_ = new JPanel(new GridBagLayout());
    nflTeamsPanel_.setBackground(new Color(255, 255, 204));
    Insets insets = new Insets(0, 0, 0, 0);

    nflTeamsPanel_.add(lblNFLTeamsDesc_,
        new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.NONE,
                               insets, 0, 0));

    nflTeamsPanel_.add(nflTeamsScrollPane_,
        new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.BOTH,
                               insets, 20, 133));

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


  private void drawPositionsPanel()
  {
    positionsPanel_ = new JPanel(new GridBagLayout());
    positionsPanel_.setBackground(new Color(255, 255, 204));
    positionsPanel_.add(lblPositionsDesc_,
        new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.NONE,
                               new Insets(0, 0, 0, 0), 0, 0));

    positionsPanel_.add(positionsScrollPane_,
        new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                               GridBagConstraints.NORTH,
                               GridBagConstraints.NONE,
                               new Insets(0, 0, 0, 0), 20, 60));

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
  }

  private void drawByeWeeksPanel()
  {

    byeWeeksPanel_ = new JPanel(new GridBagLayout());
    byeWeeksPanel_.setBackground(new Color(255, 255, 204));
    byeWeeksPanel_.add(lblByeWeeks_,
                        new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
                                               GridBagConstraints.NORTH,
                                               GridBagConstraints.NONE,
                                               new Insets(0, 0, 0, 0),
                                               0, 0));
    byeWeeksPanel_.add(byeWeeksScrollPane_,
                        new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
                                               GridBagConstraints.NORTH,
                                               GridBagConstraints.NONE,
                                               new Insets(0, 0, 0, 0), 20, 60));
    byeWeeksPanel_.add(btnSelectAllByeWeeks_,
                        new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                                               GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               new Insets(0, 0, 0, 0),
                                               0, 0));

    byeWeeksPanel_.add(btnDeselectAllByeWeeks_,
                        new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                                               GridBagConstraints.EAST,
                                               GridBagConstraints.NONE,
                                               new Insets(0, 0, 0, 0),
                                               0, 0));

  }

  private void drawEmptyRoundInfoControls()
  {
    Insets insets = new Insets(0, 0, 0, 0);
    GridBagConstraints gbcDesc = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                                        GridBagConstraints.WEST,
                                                        GridBagConstraints.NONE,
                                                        insets, 80, 0);
    GridBagConstraints gbcValue =
                                 new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
                                                        GridBagConstraints.EAST,
                                                        GridBagConstraints.NONE,
                                                        insets, 40, 0);

    roundInfoPanel_ = new JPanel(new GridBagLayout());

    roundInfoPanel_.add(lblRoundDesc_, gbcDesc);
    gbcDesc.gridy = 1;
    roundInfoPanel_.add(lblPickDesc_, gbcDesc);
    gbcDesc.gridy = 2;
    roundInfoPanel_.add(lblTeamDesc_, gbcDesc);
    gbcDesc.gridy = 3;
    roundInfoPanel_.add(lblTimeRemainingDesc_, gbcDesc);


    roundInfoPanel_.add(lblRound_, gbcValue);
    gbcValue.gridy = 1;
    roundInfoPanel_.add(lblPick_, gbcValue);
    gbcValue.gridy = 2;
    roundInfoPanel_.add(lblTeam_, gbcValue);
    gbcValue.gridy = 3;
    roundInfoPanel_.add(lblTimeRemaining_, gbcValue);

    gbcValue = null;
    gbcDesc = null;
    insets = null;
  }

  private void drawTitleRoundControls()
  {
    Insets insets = new Insets(0, 0, 0, 0);
    GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                                        GridBagConstraints.CENTER,
                                                        GridBagConstraints.BOTH,
                                                        insets, 0, 0);

    lblTitle_.setFont(new Font("SansSerif", Font.BOLD, 16));
    titlePanel_.add(lblTitle_, gbc);

  }

  private void drawEmptyDraftDescriptionControls()
  {
    Insets insets = new Insets(0, 0, 0, 0);


    leagueInfoPanel_ = new JPanel(new GridBagLayout());
    leagueInfoPanel_.add(lblLoginTeamNameDesc_,
                        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                               GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               insets, 80, 0));
    leagueInfoPanel_.add(lblLoginTeamName_,
                        new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
                                               GridBagConstraints.EAST,
                                               GridBagConstraints.NONE,
                                               insets, 40, 0));
    leagueInfoPanel_.add(lblLeagueNameDesc_,
                        new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                               GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               insets, 80, 0));
    leagueInfoPanel_.add(lblLeagueName_,
                        new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
                                               GridBagConstraints.EAST,
                                               GridBagConstraints.NONE,
                                               insets, 40, 0));
    leagueInfoPanel_.add(lblLeagueYearDesc_,
                        new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                                               GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               insets, 80, 0));
    leagueInfoPanel_.add(lblLeagueYear_,
                        new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
                                               GridBagConstraints.EAST,
                                               GridBagConstraints.NONE,
                                               insets, 40, 0));

   leagueInfoPanel_.add(lblNextPickDesc_,
                       new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                                              GridBagConstraints.WEST,
                                              GridBagConstraints.NONE,
                                              insets, 80, 0));
   leagueInfoPanel_.add(lblNextPick_,
                       new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0,
                                              GridBagConstraints.EAST,
                                              GridBagConstraints.NONE,
                                              insets, 40, 0));
  }
}