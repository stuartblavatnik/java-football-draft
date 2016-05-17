package com.twoforboth.draft.misc;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;

import javax.swing.JButton;

import java.lang.reflect.Method;

import java.util.Hashtable;

/**
 * <p>Title: Draft</p>
 * <p>Description: Generic Button adapter for Buttons and JButtons</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Two For Both Inc.</p>
 * @author Stuart Blavatnik
 * @version 1.0
 */

public class ButtonAdapter implements ActionListener
{
  //target object
  protected Object target = null;
  //class of the target object
  protected Class targetClass = null;
  //class array for parameters used for target method
  protected final static Class paramClasses[] = { java.awt.event.ActionEvent.class };
  //Map the source objects to callback methods
  protected Hashtable mappingTable = new Hashtable();

  /**
   * @param target event target
   */
  public ButtonAdapter(Object target)
  {
    this.target = target;
    targetClass = target.getClass();
  }

  /**
   * Associates an action object with the method call on the target when the
   * action is triggered (for buttons)
   */
  public void registerActionEventHandler(Button btn,
					 String methodName)
					 throws NoSuchMethodException
  {
    Method mthd = targetClass.getMethod(methodName, paramClasses);
    btn.addActionListener(this);
    mappingTable.put(btn, mthd);
  }

  /**
   * Associates an action object with the method call on the target when the
   * action is triggered (for buttons)
   */
  public void registerActionEventHandler(JButton btn,
					 String methodName)
					 throws NoSuchMethodException
  {
    Method mthd = targetClass.getMethod(methodName, paramClasses);
    btn.addActionListener(this);
    mappingTable.put(btn, mthd);
  }

  /**
   * Listener method
   * @param e Event generated by the button
   */
  public void actionPerformed(ActionEvent actionEvent)
  {
    try
    {
      Method mthd = (Method)mappingTable.get(actionEvent.getSource());
      Object params[] = { actionEvent };
      mthd.invoke(target, params);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}