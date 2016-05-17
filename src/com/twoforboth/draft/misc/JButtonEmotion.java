package com.twoforboth.draft.misc;

import javax.swing.JButton;

/**
 * <p>Title: Draft</p>
 * <p>Description: Scrambled Eggs Football Draft Application</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Two For Both Inc.</p>
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class JButtonEmotion extends JButton
{
  String emotion_ = "";

  public JButtonEmotion(String name, String emotion)
  {
    super(name);
    emotion_ = emotion;
  }

  public String getEmotion() { return emotion_ ; }
}