package javas.univ.spos;

import java.awt.*;
import java.util.Vector;

public class ResourcesPanel extends Panel 
{
  Label resourcesLabel ;
  Label idLabel ;
  Label availableLabel ;
  int resourceCount = 0 ;
  Vector resources ;
  Vector resourceIdLabelVector = new Vector();
  Vector resourceAvailableLabelVector = new Vector() ;

  ResourcesPanel()
  {
    super() ;
  }

  ResourcesPanel(int newResourceCount )
  {
    super() ;
    resourceCount = newResourceCount ;
  }

  public void setResourceCount(int newResourceCount )
  {
    if ( newResourceCount > resourceCount )
    {
      GridBagConstraints gbc ;
      GridBagLayout gbl = (GridBagLayout)this.getLayout();
      Label idLabel ;
      Label availableLabel ;
      // add the objects to the vector
      // add the objects to the panel
      for ( int i = resourceCount ; i < newResourceCount ; i ++ )
      {
        idLabel = new Label( ) ;
        availableLabel = new Label( );
        // add the objects to the vector
        resourceIdLabelVector.insertElementAt(idLabel,i) ;
        resourceAvailableLabelVector.insertElementAt(availableLabel,i) ;
        // add the constraints to the layout manager
        gbc = new GridBagConstraints() ;
        gbc.gridx = 1 ;
        gbc.gridy = 3 + i ;
        gbl.setConstraints( idLabel , gbc ) ;
        gbc = new GridBagConstraints() ;
        gbc.gridx = 2 ;
        gbc.gridy = 3 + i ;
        gbl.setConstraints( availableLabel , gbc ) ;
        // add the objects to the panel
        this.add( idLabel );
        this.add( availableLabel ) ;
      }
      // redo the layout of the panel
      this.layout();
    }
    else if ( newResourceCount < resourceCount )
    {
      for ( int i = resourceCount - 1 ; i >= newResourceCount ; i -- )
      {
        // remove the objects from the panel
        this.remove( (Label)resourceIdLabelVector.elementAt(i) ) ;
        this.remove( (Label)resourceAvailableLabelVector.elementAt(i) ) ;
        // remove the objects from the vector
        resourceIdLabelVector.removeElementAt(i) ;
        resourceAvailableLabelVector.removeElementAt(i) ;
      }
    // redo the layout of the panel
    this.layout();
    }
  resourceCount = newResourceCount ;
  }

  public void setResources(Vector newResources )
  {
    resources = newResources ;
  }

  public void setResourceId( int i , int newId )
  {
    Label label = (Label)resourceIdLabelVector.elementAt(i) ;
    Dimension oldSize = label.getSize() ;
    label.setText(Integer.toString(newId)) ;
    Dimension newSize = label.getMinimumSize() ;
    if ( newSize.width > oldSize.width )
      label.invalidate() ;
  }

  public void setResourceAvailable( int i , int newAvailable )
  {
    Label label = (Label)resourceAvailableLabelVector.elementAt(i) ;
    Dimension oldSize = label.getSize() ;
    label.setText(Integer.toString(newAvailable)) ;
    Dimension newSize = label.getMinimumSize() ;
    if ( newSize.width > oldSize.width )
      label.invalidate() ;
  }

  public void init()
  {
    GridBagConstraints gbc ;
    GridBagLayout gbl = new GridBagLayout() ;

    resourcesLabel = new Label( "Resources" ) ;
    gbc = new GridBagConstraints() ;
    gbc.gridx = 1 ;
    gbc.gridy = 1 ;
    gbc.gridwidth = GridBagConstraints.REMAINDER ;
    gbl.setConstraints( resourcesLabel , gbc ) ;

    idLabel = new Label( "Id" , Label.RIGHT ) ;
    gbc = new GridBagConstraints() ;
    gbc.gridx = 1 ;
    gbc.gridy = 2 ;
    gbl.setConstraints( idLabel , gbc ) ;

    availableLabel = new Label( "Available" , Label.RIGHT ) ;
    gbc = new GridBagConstraints() ;
    gbc.gridx = 2 ;
    gbc.gridy = 2 ;
    gbl.setConstraints( availableLabel , gbc ) ;

    for( int i = 0 ; i < resourceCount ; i ++ )
    {
      Label idLabel ;
      Label availableLabel ;
      // create the labels
      // add labels to the vectors
      // set constraints
      idLabel = new Label( ) ;
      idLabel.setAlignment( Label.RIGHT ) ;
      resourceIdLabelVector.insertElementAt( idLabel , i ) ;
      gbc = new GridBagConstraints() ;
      gbc.gridx = 1 ;
      gbc.gridy = 3 + i ;
      gbl.setConstraints( idLabel , gbc ) ;

      availableLabel = new Label( ) ;
      availableLabel.setAlignment( Label.RIGHT ) ;
      resourceAvailableLabelVector.insertElementAt( availableLabel , i ) ;
      gbc = new GridBagConstraints() ;
      gbc.gridx = 2 ;
      gbc.gridy = 3 + i ;
      gbl.setConstraints( availableLabel , gbc ) ;
    }

    setLayout( gbl ) ;
    
    add( resourcesLabel ) ;
    add( idLabel ) ;
    add( availableLabel ) ;
    for( int i = 0 ; i < resourceCount ; i ++ )
    {
      Label idLabel ;
      Label availableLabel;
      idLabel = (Label)resourceIdLabelVector.elementAt(i);
      availableLabel = (Label)resourceAvailableLabelVector.elementAt(i) ;
      add( idLabel ) ;
      add( availableLabel ) ;
    }
  }

  public void show()
  {
    for( int i = 0 ; i < resourceCount ; i ++ )
    {
      Resource resource = (Resource)resources.elementAt(i) ;
      ((Label)resourceIdLabelVector.elementAt(i)).setText(
        Integer.toString(resource.getId())) ;
      ((Label)resourceAvailableLabelVector.elementAt(i)).setText(
        Integer.toString(resource.getCurrentAvailable())) ;
    }
    super.show();  
  }

}
