package com.twoforboth.draft.panel;

/**
 * <p>Title: Draft</p>
 * <p>Description: Scrambled Eggs Football Draft Application</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Two For Both Inc.</p>
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class DraftConstants
{
  protected final static long HEARTBEAT_TIME_INTERVAL  = 30000;
  protected final static long LEAGUE_INFO_INTERVAL     = 5000;
  protected final static long LOGGED_IN_INTERVAL       = 5000;
  protected final static long NEW_MESSAGES_INTERVAL    = 3500;
  protected final static long MESSAGE_CLEANUP_INTERVAL = 120000;

  protected final static String APPLICATION_TITLE = "Scrambled Eggs Fantasy Football Draft";
  protected final static String LABEL_ROUND_DESC = "Round:";
  protected final static String LABEL_PICK_DESC = "Pick:";
  protected final static String LABEL_TEAM_DESC = "Team:";
  protected final static String LABEL_DRAFT_DESC = "Draft";
  protected final static String LABEL_AVAILABLE_DESC = "Available";
  protected final static String LABEL_PRERANK_DESC = "Your Rankings";
  protected final static String LABEL_NFLTEAMS_DESC = "NFL Teams";
  protected final static String LABEL_POSITIONS_DESC = "Positions";
  protected final static String LABEL_EXCLUDE_BYE_WEEKS_DESC = "Exclude Bye Wk";
  protected final static String LABEL_TIME_REMAINING_DESC = "Pick Time Remaining";

  protected final static String LABEL_LOGIN_TEAM_NAME_DESC = "Team Name:";
  protected final static String LABEL_LEAGUE_NAME_DESC = "League Name:";
  protected final static String LABEL_LEAGUE_YEAR_DESC = "League Year:";
  protected final static String LABEL_NEXT_PICK_DESC = "Next Autopick:";

  protected final static String BUTTON_DRAFT_DESC = "Draft Player";
  protected final static String BUTTON_SEARCH_DESC = "Search";
  protected final static String BUTTON_ALL_DESC = "All";
  protected final static String BUTTON_NONE_DESC = "None";
  protected final static String BUTTON_PAUSE_DRAFT_DESC = "Pause Draft";
  protected final static String BUTTON_DELETE_PICK_DESC = "Delete Pick";
  protected final static String BUTTON_HAPPY_DESC = ":)";
  protected final static String BUTTON_SAD_DESC = ":(";
  protected final static String BUTTON_CURSE_DESC = "!";
  protected final static String BUTTON_HELP_DESC = "Help";
  protected final static String BUTTON_OK_DESC = "OK";
  protected final static String BUTTON_YES_DESC = "OK";
  protected final static String BUTTON_NO_DESC = "Cancel";

  protected final static String ALREADY_DRAFTED = "Player already drafted";

  protected final static String HAPPY_MESSAGE = "I am very happy.";
  protected final static String SAD_MESSAGE = "I am very sad.";
  protected final static String CURSE_MESSAGE = "Darn!";
  protected final static String LOGGED_OUT_MESSAGE = " has left the draft.";
  protected final static String LOGGED_IN_MESSAGE = " has entered the draft.";

  protected final static String CONFIRM_DRAFT_CALLBACK = "createConfirmationDraftDialog";
  protected final static String CONFIRM_DRAFT_PRERANK_CALLBACK = "createConfirmationPreRankDraftDialog";
  protected final static String DO_SEARCH_CALLBACK = "doSearch";
  protected final static String SELECT_ALL_LIST_ITEMS_CALLBACK = "selectAllListItems";
  protected final static String DESELECT_ALL_LIST_ITEMS_CALLBACK = "deselectAllListItems";
  protected final static String TOGGLE_DRAFT_CALLBACK = "toggleDraft";
  protected final static String DELETE_PICK_CALLBACK = "deletePick";
  protected final static String CREATE_DRAFT_MESSAGE_CALLBACK = "createDraftMessage";
  protected final static String PLAYER_TAKEN_OK_BUTTON_CALLBACK = "handleButtonTakenOK";
  protected final static String DO_DRAFT_CONFIRMED_CALLBACK = "doDraftConfirmed";
  protected final static String DO_DRAFT_NEGATED_CALLBACK = "doDraftNegated";
  protected final static String DO_PRERANK_DRAFT_CONFIRMED_CALLBACK = "doPreRankDraftConfirmed";
  protected final static String DO_PRERANK_DRAFT_NEGATED_CALLBACK = "doPreRankDraftNegated";

  protected final static String EMPTY_STRING = "";
  protected final static String DRAFT_LOCKED_STRING = "Draft is locked";
  protected final static String DRAFT_COMPLETE_STRING = "Draft is complete";
  protected final static String END_OF_DRAFT_ROUND = "-1";
  protected final static String PLAYER_TAKEN_STRING = "Player Already Taken";
  protected final static String DRAFT_CONFIRMATION_TITLE = "Confirm Selection";
  protected final static String DRAFT_QUESTION = "Are you sure you want to draft";
}