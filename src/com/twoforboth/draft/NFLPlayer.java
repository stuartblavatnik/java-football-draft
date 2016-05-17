package com.twoforboth.draft;

/**
 * <p>Title: Draft</p>
 * <p>Description: Scrambled Eggs Football Draft Application</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Two For Both Inc.</p>
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class NFLPlayer
{
  private String name_ = "";
  private String nflTeamShortName_ = "";
  private String nflTeamLongName_ = "";
  private String position_ = "";
  private String fantasyTeamName_ = "";
  private boolean drafted_ = false;

  public NFLPlayer(String name,
                   String teamShortName,
                   String teamLongName,
                   String position)
  {
    name_ = name;
    nflTeamShortName_ = teamShortName;
    nflTeamLongName_ = teamLongName;
    position_ = position;
  }

  public String getName() { return name_; }
  public String getNFLTeamShortName() { return nflTeamShortName_; }
  public String getNFLTeamLongName() { return nflTeamLongName_; }
  public String getPosition() { return position_; }
  public String getFantasyTeamName() { return fantasyTeamName_; }
  public boolean getDrafted() { return drafted_; }

}