package javas.univ.spos;

import java.awt.*;
import java.util.Vector;


public class ResourcesDialog extends Dialog
{
  Button okButton;
  Button cancelButton;
  Label resourceLabel ;
  Label resourceCountLabel ;
  Panel topPanel ;
  Panel resourcePanel ;
  Panel bottomPanel ;

  int resourceCount ;
  Vector resources ;

  Vector initials = new Vector() ;
  Vector currents = new Vector() ;


  public ResourcesDialog( Frame parent )
  {
    super( parent ) ;
    init() ;
  }

  public ResourcesDialog( Frame parent , boolean modal )
  {
    super( parent , modal ) ;
    init() ;
  }

  public ResourcesDialog( Frame parent , String title )
  {
    super( parent , title ) ;
    init() ;
  }

  public ResourcesDialog( Frame parent , String title , boolean modal )
  {
    super( parent , title , modal ) ;
    init() ;
  }

  public void setResourceCount( int newResourceCount )
  {
    resourceCount = newResourceCount ;
    initials.setSize(newResourceCount);
    initials.trimToSize();
    currents.setSize(newResourceCount);
    currents.trimToSize();
  }

  public void setResources( Vector newResources ) 
  {
    resources = newResources ;
  }

  public void init()
  {
    topPanel = new Panel() ;
    resourcePanel = new Panel() ;
    bottomPanel = new Panel() ;

    resourceLabel = new Label( "Number of resources:" , Label.RIGHT ) ;
    resourceCountLabel = new Label( Integer.toString(resourceCount) , Label.LEFT ) ;
    topPanel.add( resourceLabel );
    topPanel.add( resourceCountLabel ) ;

    okButton = new Button( "ok" ) ;
    bottomPanel.add(okButton);
    cancelButton = new Button( "cancel" ) ;
    bottomPanel.add(cancelButton) ;

    setLayout( new BorderLayout() ) ;
    add("North", topPanel ) ;
    add("Center", resourcePanel ) ;
    add("South", bottomPanel);
  }

  public void show()
  {
    GridBagLayout gbl = new GridBagLayout() ;
    GridBagConstraints gbc = new GridBagConstraints();

    resourceCountLabel.setText( Integer.toString( resourceCount ) ) ;

    // we need to add the correct number of lines to the panel
    resourcePanel.removeAll();

    Label idLabel = new Label("Id", Label.RIGHT);
    resourcePanel.add(idLabel);
    gbc.gridx = 1 ;
    gbc.gridy = 1 ;
    gbc.fill = GridBagConstraints.NONE ;
    gbl.setConstraints( idLabel , gbc ) ;

    Label availableLabel = new Label("Initial", Label.RIGHT ) ;
    resourcePanel.add(availableLabel);
    gbc.gridx = 2 ;
    gbc.gridy = 1 ;
    gbl.setConstraints( availableLabel , gbc ) ;

    availableLabel = new Label("Current", Label.RIGHT ) ;
    resourcePanel.add(availableLabel);
    gbc.gridx = 3 ;
    gbc.gridy = 1 ;
    gbl.setConstraints( availableLabel , gbc ) ;

    for( int i = 0 ; i < resourceCount ; i ++ )
    {
      Resource resource = (Resource)resources.elementAt(i) ;
      // add the vector of labels and fields here
      idLabel = new Label( Integer.toString(i) , Label.RIGHT ) ;
      resourcePanel.add(idLabel);
      gbc.gridx = 1 ;
      gbc.gridy = 2 + i ;
      gbl.setConstraints( idLabel , gbc ) ;

      TextField availableTextField = new TextField( 
        Integer.toString(resource.getInitialAvailable()) ) ;
      initials.insertElementAt(availableTextField,i);
      resourcePanel.add(availableTextField);
      gbc.gridx = 2 ;
      gbc.gridy = 2 + i ;
      gbl.setConstraints( availableTextField , gbc ) ;

      availableTextField = new TextField( 
        Integer.toString(resource.getCurrentAvailable()) ) ;
      currents.insertElementAt(availableTextField,i);
      resourcePanel.add(availableTextField);
      gbc.gridx = 3 ;
      gbc.gridy = 2 + i ;
      gbl.setConstraints( availableTextField , gbc ) ;

      if ( i == 0 )
      {
        availableTextField.requestFocus() ;
      }
    }
    if ( resourceCount == 0 )
    {
      cancelButton.requestFocus() ;
    }

    resourcePanel.setLayout( gbl );
    pack() ;
    super.show();
  }

  public boolean action( Event e, Object arg )
  {
    if ( e.target == okButton )
    {
      // before we set the resources, we need to validate the values ???
      for( int i = 0 ; i < resourceCount ; i ++ )
      {
        Resource resource;
        resource = (Resource)resources.elementAt(i) ;
        try
        {
          resource.setInitialAvailable(
            Integer.parseInt(((TextField)initials.elementAt(i)).getText()));
        }
        catch (NumberFormatException exception)
        {
          System.out.println("invalid number value entered for resource initial available");
          ((TextField)initials.elementAt(i)).requestFocus();
          return true ;
        }
        try
        {
          resource.setCurrentAvailable(
            Integer.parseInt(((TextField)currents.elementAt(i)).getText()));
        }
        catch (NumberFormatException exception)
        {
          System.out.println("invalid number value entered for resource current available");
          ((TextField)currents.elementAt(i)).requestFocus();
          return true ;
        }
      }
      this.hide() ;
      return true ;
    }
    else if ( e.id == Event.ACTION_EVENT && e.target instanceof TextField )
    {
      try
      {
        int i = Integer.valueOf(((TextField)e.target).getText().trim()).intValue() ;
        this.hide();
      }
      catch (NumberFormatException exception)
      {
        System.out.println("invalid number value entered for resource available");
        ((TextField)e.target).requestFocus();
      }
      return true ;
    }
    else if ( e.target == cancelButton )
    {
      this.hide() ;
      return true ;
    }
    else
      return false ;
  }

}
