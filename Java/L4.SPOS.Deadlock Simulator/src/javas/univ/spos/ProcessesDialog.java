package javas.univ.spos;

import java.awt.*;
import java.io.File;
import java.util.Vector;


public class ProcessesDialog extends Dialog
{
  Button okButton;
  Button cancelButton;
  Label processLabel ;
  Label processCountLabel ;
  Panel topPanel ;
  Panel processPanel ;
  Panel bottomPanel ;

  FileDialog fileDialog = null ;

  int processCount ;
  Vector processes ;

  Vector filenames = new Vector() ;
  Vector chooseButtons = new Vector() ;

  public ProcessesDialog( Frame parent )
  {
    super( parent ) ;
    init() ;
  }

  public ProcessesDialog( Frame parent , boolean modal )
  {
    super( parent , modal ) ;
    init() ;
  }

  public ProcessesDialog( Frame parent , String title )
  {
    super( parent , title ) ;
    init() ;
  }

  public ProcessesDialog( Frame parent , String title , boolean modal )
  {
    super( parent , title , modal ) ;
    init() ;
  }

  public void setProcessCount( int newProcessCount )
  {
    processCount = newProcessCount ;
    filenames.setSize(newProcessCount);
    filenames.trimToSize();
  }

  public void setProcesses( Vector newProcesses ) 
  {
    processes = newProcesses ;
  }

  public void init()
  {
    topPanel = new Panel() ;
    processPanel = new Panel() ;
    bottomPanel = new Panel() ;

    processLabel = new Label( "Number of processes:" , Label.RIGHT ) ;
    processCountLabel = new Label( Integer.toString(processCount) , Label.LEFT ) ;
    topPanel.add( processLabel );
    topPanel.add( processCountLabel ) ;

    okButton = new Button( "ok" ) ;
    cancelButton = new Button( "cancel" ) ;
    bottomPanel.add(okButton);
    bottomPanel.add(cancelButton) ;
    setLayout( new BorderLayout() ) ;
    add("North", topPanel ) ;
    add("Center", processPanel ) ;
    add("South", bottomPanel);

  }

  public void show()
  {
    GridBagLayout gbl = new GridBagLayout() ;
    GridBagConstraints gbc = new GridBagConstraints();

    processCountLabel.setText( Integer.toString( processCount ) ) ;

    // we need to add the correct number of lines to the panel
    processPanel.removeAll();

    Label idLabel = new Label("Id", Label.RIGHT);
    processPanel.add(idLabel);
    gbc.gridx = 1 ;
    gbc.gridy = 1 ;
    gbc.fill = GridBagConstraints.NONE ;
    gbl.setConstraints( idLabel , gbc ) ;

    Label filenameLabel = new Label("File Name", Label.LEFT ) ;
    processPanel.add(filenameLabel);
    gbc.gridx = 2 ;
    gbc.gridy = 1 ;
    gbl.setConstraints( filenameLabel , gbc ) ;

    for( int i = 0 ; i < processCount ; i ++ )
    {
      Process process = (Process)processes.elementAt(i) ;
      // add the vector of labels and fields here
      idLabel = new Label( Integer.toString(i) , Label.RIGHT ) ;
      processPanel.add(idLabel);
      gbc.gridx = 1 ;
      gbc.gridy = 2 + i ;
      gbl.setConstraints( idLabel , gbc ) ;

      TextField filenameTextField = new TextField( process.getFilename() , 32 ) ;
      filenames.insertElementAt(filenameTextField,i);
      processPanel.add(filenameTextField);
      gbc.gridx = 2 ;
      gbc.gridy = 2 + i ;
      gbl.setConstraints( filenameTextField , gbc ) ;

      Button chooseButton = new Button( "choose" ) ;
      chooseButtons.insertElementAt(chooseButton,i);
      processPanel.add(chooseButton);
      gbc.gridx = 3 ;
      gbc.gridy = 2 + i ;
      gbl.setConstraints( chooseButton , gbc ) ;

      if ( i == 0 )
      {
        filenameTextField.requestFocus() ;
      }
    }
    if ( processCount == 0 )
    {
      cancelButton.requestFocus() ;
    }

    processPanel.setLayout( gbl );
    pack() ;
    super.show();
  }

  public boolean action( Event e, Object arg )
  {
    if ( e.target == okButton )
    {
      // before we set the processes, we need to validate the values ???
      for( int i = 0 ; i < processCount ; i ++ )
      {
        Process process;
        process = (Process)processes.elementAt(i) ;
        process.setFilename(((TextField)filenames.elementAt(i)).getText());
      }
      this.hide() ;
      return true ;
    }
    else if ( e.target == cancelButton )
    {
      this.hide() ;
      return true ;
    }
    else if ( ((String)e.arg).equals("choose") )
    {
      // find the TextField corresponding to the button and update its value
      // if a value returned by the dialog.

      int i = chooseButtons.indexOf(e.target) ;
      File f = new File( ((TextField)filenames.elementAt(i)).getText() ) ;
      if ( fileDialog == null )
      {
        fileDialog = new FileDialog( (Frame)this.getParent() , 
          "Open file" , FileDialog.LOAD ) ;
      }
      fileDialog.setDirectory( f.getParent() ) ;
      fileDialog.setFile( f.getName() ) ;
      // filename filtering may not be implemented on all platforms.
      // fileDialog.setFilenameFilter( new DatFilenameFilter() ) ;
      fileDialog.show();
      if( fileDialog.getFile() != null )
      { 
        TextField filenameTextField = ((TextField)filenames.elementAt(i)); 
        String filename = fileDialog.getDirectory() + fileDialog.getFile() ;
        filenameTextField.setText( filename ) ;
      }
      return true ;
    }
    else
      return false ;
  }

}
