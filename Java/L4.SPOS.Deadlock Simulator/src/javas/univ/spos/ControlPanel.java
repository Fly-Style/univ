package javas.univ.spos;

import java.awt.*;

public class ControlPanel extends Frame
{
  boolean running = false ;
  boolean hasBeenReset = false ;

  Kernel kernel ;

  Panel buttonPanel ;
  Button runButton ;
  Button stopButton ;
  Button stepButton ;
  Button resetButton ;
  Button optionsButton ;
  Button processesButton ;
  Button resourcesButton ;
  Button exitButton ;
  Panel timePanel ;
  Label timeLabel ;
  Label timeValueLabel ;
  TextField timeTextField ;

  Panel topPanel ;

  ProcessesPanel processesPanel ;
  ResourcesPanel resourcesPanel ;

  OptionsDialog optionsDialog ;
  ProcessesDialog processesDialog ;
  ResourcesDialog resourcesDialog ;

  public ControlPanel()
  {
    super() ;
  }

  public ControlPanel(String title)
  {
    super( title ) ;
  }

  public void init(Kernel useKernel)
  {
    kernel = useKernel ;
    kernel.setControlPanel( this ) ;

    runButton = new Button( "run" ) ;
    stopButton = new Button( "stop" ) ;
    stepButton = new Button( "step" ) ;
    resetButton = new Button( "reset" ) ;
    optionsButton = new Button( "options" ) ;
    processesButton = new Button( "processes" ) ;
    resourcesButton = new Button( "resources" ) ;
    exitButton = new Button( "exit" ) ;

    buttonPanel = new Panel( ) ;
    buttonPanel.add( runButton ) ;
    buttonPanel.add( stopButton ) ;
    buttonPanel.add( stepButton ) ;
    buttonPanel.add( resetButton ) ;
    buttonPanel.add( optionsButton ) ;
    buttonPanel.add( processesButton ) ;
    buttonPanel.add( resourcesButton ) ;
    buttonPanel.add( exitButton ) ;

    timeLabel = new Label( "Time: " , Label.RIGHT ) ;
    timeValueLabel = new Label( Integer.toString( kernel.getTime() ) , Label.LEFT ) ;
    timeValueLabel.setForeground( Color.white ) ;
    timeValueLabel.setBackground( Color.black ) ;
    // timeTextField = new TextField( Integer.toString( kernel.getTime() ), 6) ;
    // timeTextField.setEditable(false) ;
    // timeTextField.enable(false);
    // timeTextField.setForeground( Color.white ) ;
    // timeTextField.setBackground( Color.black ) ;

    timePanel = new Panel( ) ;
    timePanel.add( timeLabel ) ;
    timePanel.add( timeValueLabel ) ; 
    // timePanel.add( timeTextField ) ;

    processesPanel = new ProcessesPanel(kernel.getProcessCount());
    processesPanel.setProcesses( kernel.getProcesses() ) ;
    processesPanel.init();

    resourcesPanel = new ResourcesPanel(kernel.getResourceCount());
    resourcesPanel.setResources( kernel.getResources() ) ;
    resourcesPanel.init();

optionsDialog = new OptionsDialog( this , "optionsDialog" , true ) ;
processesDialog = new ProcessesDialog( this , "processesDialog" , true ) ;
processesDialog.setProcesses(kernel.getProcesses());
resourcesDialog = new ResourcesDialog( this , "resourcesDialog" , true ) ;
resourcesDialog.setResources(kernel.getResources());

    stopButton.disable();
    runButton.requestFocus() ;

    topPanel = new Panel() ;
    topPanel.setLayout( new BorderLayout() ) ;
    topPanel.add( "North" , buttonPanel ) ;
    topPanel.add( "South" , timePanel ) ;

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints() ;
    gbc.gridx = 1 ;
    gbc.gridy = 1 ;
    gbc.gridwidth = 2 ;
    gbc.gridheight = 1 ;
    gbl.setConstraints( topPanel , gbc ) ;
    gbc.gridx = 1 ;
    gbc.gridy = 2 ;
    gbc.gridwidth = 1 ;
    gbc.gridheight = 1 ;
    gbc.anchor = GridBagConstraints.NORTH ;
    gbl.setConstraints( processesPanel , gbc ) ;
    gbc.gridx = 2 ;
    gbc.gridy = 2 ;
    gbc.gridwidth = 1 ;
    gbc.gridheight = 1 ;
    gbc.anchor = GridBagConstraints.NORTH ;
    gbl.setConstraints( resourcesPanel , gbc ) ;
    add( topPanel ) ;
    add( processesPanel );
    add( resourcesPanel );
    setLayout( gbl ) ;

    kernel.reset();
    hasBeenReset = true ;
    pack() ;

    Dimension screenSize = getToolkit().getScreenSize() ;
    Dimension size = getSize() ;
    setLocation( 
      ( screenSize.width - size.width + 1 ) / 2 ,
      ( screenSize.height - size.height + 1 ) / 2 ) ;

    show() ;
    requestFocus() ;
  }

  public boolean action( Event e, Object arg )
  {
    if ( e.target == runButton )
    {
      if ( ! hasBeenReset )
        {
        kernel.reset() ;
        hasBeenReset = true ;
        }
      runButton.disable() ;
      stopButton.enable() ;
      stepButton.disable() ;
      resetButton.disable() ;
      optionsButton.disable() ;
      processesButton.disable() ;
      resourcesButton.disable() ;
      stopButton.requestFocus() ;
      kernel.setStepping(false);
      kernel.resume() ;
      running = true ;
      return true ;
    }
    else if ( e.target == stopButton )
    {
      stopAction() ;
      kernel.suspend();
      return true ;
    }
    else if ( e.target == stepButton )
    {
      if ( ! hasBeenReset )
        {
        kernel.reset() ;
        hasBeenReset = true ;
        }
      kernel.setStepping(true);
      kernel.resume() ;
      return true ;
    }
    else if ( e.target == resetButton )
    {
      kernel.reset();
      hasBeenReset = true ;
      return true ;
    }
    else if ( e.target == optionsButton ) 
    {
      optionsDialog.setProcessCount( kernel.getProcessCount() ) ;
      optionsDialog.setResourceCount( kernel.getResourceCount() ) ;
      optionsDialog.setSleepTime( kernel.getSleepTime() ) ;
      optionsDialog.show();
      kernel.setProcessCount( optionsDialog.getProcessCount( ) ) ;
      kernel.setResourceCount( optionsDialog.getResourceCount() ) ;
      kernel.setSleepTime( optionsDialog.getSleepTime() ) ;
      processesPanel.setProcessCount( optionsDialog.getProcessCount() ) ;
      resourcesPanel.setResourceCount( optionsDialog.getResourceCount() ) ;
      hasBeenReset = false ;
      return true ;
    }
    else if ( e.target == processesButton )
    {
      processesDialog.setProcessCount( kernel.getProcessCount() ) ;
      processesDialog.show() ;
      processesPanel.show() ;
      hasBeenReset = false ;
      return true ;
    }
    else if ( e.target == resourcesButton )
    {
      resourcesDialog.setResourceCount( kernel.getResourceCount() ) ;
      resourcesDialog.show() ;
      resourcesPanel.show() ;
      hasBeenReset = false ;
      return true ;
    }
    else if ( e.target == exitButton )
    {
      kernel.stop();
      System.exit(0);
      return true ;
    }
    else
    {
      System.out.println( e.toString() ) ;
      return false ;
    }
  }

  public void stopAction() 
  {
      runButton.enable() ;
      stopButton.disable() ;
      stepButton.enable() ;
      resetButton.enable() ;
      optionsButton.enable() ;
      processesButton.enable() ;
      resourcesButton.enable() ;
      runButton.requestFocus() ;
      running = false ;
  }

  public void setTime( int newTime )
  {
    Dimension oldSize = timeValueLabel.getSize() ;
    timeValueLabel.setText( Integer.toString( newTime ) ) ;
    Dimension newSize = timeValueLabel.getMinimumSize() ;
    if ( newSize.width > oldSize.width )
      timeValueLabel.invalidate();
  }

  public void setProcessId( int i , int newId )
  {
    processesPanel.setProcessId( i , newId ) ;
  }

  public void setProcessState( int i , String newState )
  {
    processesPanel.setProcessState( i , newState ) ;
  }

  public void setProcessResource( int i , String newResource )
  {
    processesPanel.setProcessResource( i , newResource ) ;
  }

  public void setResourceId( int i , int newId )
  {
    resourcesPanel.setResourceId( i , newId ) ;
  }

  public void setResourceAvailable( int i , int newAvailable )
  {
    resourcesPanel.setResourceAvailable( i , newAvailable ) ;
  }

}
