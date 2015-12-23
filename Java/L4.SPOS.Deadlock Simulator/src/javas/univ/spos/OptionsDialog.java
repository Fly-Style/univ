package javas.univ.spos;

import java.awt.*;

public class OptionsDialog extends Dialog
{
  Button okButton;
  Button cancelButton ;
  Label processLabel ;
  TextField processTextField ;
  Label resourceLabel ;
  TextField resourceTextField ;
  Label sleepTimeLabel ;
  TextField sleepTimeTextField ;
  Panel topPanel ;
  Panel bottomPanel ;
  Panel processPanel ;
  Panel resourcePanel ;
  Panel labelPanel ;
  Panel textFieldPanel ;

  private int processCount = 0 ;
  private int resourceCount = 0 ;
  private int sleepTime = 0 ;

  public OptionsDialog( Frame parent )
  {
    super( parent ) ;
    init() ;
  }

  public OptionsDialog( Frame parent , boolean modal )
  {
    super( parent , modal ) ;
    init() ;
  }

  public OptionsDialog( Frame parent , String title )
  {
    super( parent , title ) ;
    init() ;
  }

  public OptionsDialog( Frame parent , String title , boolean modal )
  {
    super( parent , title , modal ) ;
    init() ;
  }

  public void setProcessCount( int newProcessCount )
  {
    processCount = newProcessCount ;
  }

  public int getProcessCount( )
  {
    return processCount ;
  }

  public void setResourceCount( int newResourceCount )
  {
    resourceCount = newResourceCount ;
  }

  public int getResourceCount( )
  {
    return resourceCount ;
  }

  public void setSleepTime( int newSleepTime )
  {
    sleepTime = newSleepTime ;
  }

  public int getSleepTime( )
  {
    return sleepTime ;
  }

  public void init()
  {
    topPanel = new Panel() ;
    bottomPanel = new Panel() ;

    processLabel = new Label( "Number of processes:" , Label.RIGHT ) ;
    processTextField = new TextField( Integer.toString(processCount) ) ;
    resourceLabel = new Label( "Number of resources:" , Label.RIGHT ) ;
    resourceTextField = new TextField( Integer.toString(resourceCount) ) ;
    sleepTimeLabel = new Label( "Milliseconds per step:" , Label.RIGHT ) ;
    sleepTimeTextField = new TextField( Integer.toString(sleepTime) , 6 ) ;

    GridBagLayout gbl = new GridBagLayout() ;
    GridBagConstraints gbc = new GridBagConstraints() ;

    gbc.gridx = 1 ;
    gbc.gridy = 1 ;
    gbc.anchor = GridBagConstraints.EAST ;
    gbl.setConstraints( processLabel , gbc ) ;

    gbc.gridx = 2 ;
    gbc.gridy = 1 ;
    gbc.anchor = GridBagConstraints.WEST ;
    gbl.setConstraints( processTextField , gbc ) ;

    gbc.gridx = 1 ;
    gbc.gridy = 2 ;
    gbc.anchor = GridBagConstraints.EAST ;
    gbl.setConstraints( resourceLabel , gbc ) ;

    gbc.gridx = 2 ;
    gbc.gridy = 2 ;
    gbc.anchor = GridBagConstraints.WEST ;
    gbl.setConstraints( resourceTextField , gbc ) ;

    gbc.gridx = 1 ;
    gbc.gridy = 3 ;
    gbc.anchor = GridBagConstraints.EAST ;
    gbl.setConstraints( sleepTimeLabel , gbc ) ;

    gbc.gridx = 2 ;
    gbc.gridy = 3 ;
    gbc.anchor = GridBagConstraints.WEST ;
    gbl.setConstraints( sleepTimeTextField , gbc ) ;

    topPanel.setLayout( gbl );

    topPanel.add( processLabel );
    topPanel.add( processTextField );
    topPanel.add( resourceLabel );
    topPanel.add( resourceTextField );
    topPanel.add( sleepTimeLabel ) ;
    topPanel.add( sleepTimeTextField ) ;

    okButton = new Button( "ok" ) ;
    cancelButton = new Button( "cancel" ) ;
    bottomPanel.add(okButton);
    bottomPanel.add(cancelButton);

    setLayout( new BorderLayout() ) ;
    add("North", topPanel ) ;
    add("South", bottomPanel);

    pack();
  }

  public void show()
  {
    processTextField.setText( Integer.toString( processCount ) ) ;
    resourceTextField.setText( Integer.toString( resourceCount ) ) ;
    sleepTimeTextField.setText( Integer.toString( sleepTime ) ) ;
    pack();
    super.show();
  }

  public boolean action( Event e, Object arg )
  {
    if ( e.target == okButton )
    {
      try
      {
        setProcessCount( Integer.valueOf(processTextField.getText().trim()).intValue() ) ;
        try
        {
          setResourceCount( Integer.valueOf(resourceTextField.getText().trim()).intValue() ) ;
          try
          {
            setSleepTime( Integer.valueOf(sleepTimeTextField.getText().trim()).intValue() ) ;
            this.hide();
          }
          catch (NumberFormatException exception)
          {
            System.out.println("invalid number value entered for sleep time");
            sleepTimeTextField.requestFocus();
          }
        }
        catch (NumberFormatException exception)
        {
          System.out.println("invalid number value entered for resource count");
          resourceTextField.requestFocus();
        }
      }
      catch (NumberFormatException exception)
      {
        System.out.println("invalid number value entered for process count");
        processTextField.requestFocus();
      }
      return true ;
    }
    else if ( e.target == cancelButton )
    {
      this.hide() ;
      return true ;
    }
    else if ( e.target == processTextField )
    {
      processTextField.setText( processTextField.getText().trim() ) ;
      return true ;
    }
    else if ( e.target == resourceTextField )
    {
      resourceTextField.setText( resourceTextField.getText().trim() ) ;
      return true ;
    }
    else
      return false ;
  }

}
