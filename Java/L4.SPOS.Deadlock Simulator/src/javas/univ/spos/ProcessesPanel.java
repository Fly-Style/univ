package javas.univ.spos;

import java.awt.*;
import java.util.Vector;

public class ProcessesPanel extends Panel 
{
Label processesLabel ;
Label idLabel ;
Label stateLabel ;
Label resourceLabel ;
int processCount = 0 ;
Vector processes ;
Vector processIdLabelVector = new Vector();
Vector processStateLabelVector = new Vector() ;
Vector processResourceLabelVector = new Vector() ;

ProcessesPanel()
{
super() ;
}

ProcessesPanel(int newProcessCount )
{
super() ;
processCount = newProcessCount ;
}

public void setProcessCount(int newProcessCount )
{
if ( newProcessCount > processCount )
{
GridBagConstraints gbc ;
GridBagLayout gbl = (GridBagLayout)this.getLayout();
Label idLabel ;
Label availableLabel ;
// add the objects to the vector
// add the objects to the panel
  for ( int i = processCount ; i < newProcessCount ; i ++ )
  {
  idLabel = new Label( ) ;
  stateLabel = new Label( );
  resourceLabel = new Label( ) ;
  // add the objects to the vector
  processIdLabelVector.insertElementAt(idLabel,i) ;
  processStateLabelVector.insertElementAt(stateLabel,i) ;
  processResourceLabelVector.insertElementAt(resourceLabel,i) ;
  // add the constraints to the layout manager
  gbc = new GridBagConstraints() ;
  gbc.gridx = 1 ;
  gbc.gridy = 3 + i ;
  gbl.setConstraints( idLabel , gbc ) ;
  gbc = new GridBagConstraints() ;
  gbc.gridx = 2 ;
  gbc.gridy = 3 + i ;
  gbl.setConstraints( stateLabel , gbc ) ;
  gbc = new GridBagConstraints() ;
  gbc.gridx = 3 ;
  gbc.gridy = 3 + i ;
  gbl.setConstraints( resourceLabel , gbc ) ;
  // add the objects to the panel
  this.add( idLabel );
  this.add( stateLabel ) ;
  this.add( resourceLabel ) ;
  }
// redo the layout of the panel
this.layout();
}
else if ( newProcessCount < processCount )
{
  for ( int i = processCount - 1 ; i >= newProcessCount ; i -- )
  {
  // remove the objects from the panel
  this.remove( (Label)processIdLabelVector.elementAt(i) ) ;
  this.remove( (Label)processStateLabelVector.elementAt(i) ) ;
  this.remove( (Label)processResourceLabelVector.elementAt(i) ) ;
  // remove the objects from the vector
  processIdLabelVector.removeElementAt(i) ;
  processStateLabelVector.removeElementAt(i) ;
  processResourceLabelVector.removeElementAt(i) ;
  }
// redo the layout of the panel
this.layout();
}
processCount = newProcessCount ;
}

public void setProcesses(Vector newProcesses )
{
  processes = newProcesses ;
}

public void setProcessId( int i , int newId )
{
  Label label = (Label)processIdLabelVector.elementAt(i) ;
  Dimension oldSize = label.getSize() ;
  label.setText(Integer.toString(newId)) ;
  Dimension newSize = label.getMinimumSize() ;
  if ( newSize.width > oldSize.width )
    label.invalidate();
}

public void setProcessState( int i , String newState )
{
  Label label = (Label)processStateLabelVector.elementAt(i) ;
  Dimension oldSize = label.getSize() ;
  label.setText(newState) ;
  Dimension newSize = label.getMinimumSize() ;
  if ( newSize.width > oldSize.width )
    label.invalidate();
}

public void setProcessResource( int i , String newResourceName )
{
  Label label = (Label)processResourceLabelVector.elementAt(i) ;
  Dimension oldSize = label.getSize() ;
  label.setText(newResourceName) ;
  Dimension newSize = label.getMinimumSize() ;
  if ( newSize.width > oldSize.width )
    label.invalidate();
}

public void init()
{
GridBagConstraints gbc ;
GridBagLayout gbl = new GridBagLayout() ;

processesLabel = new Label( "Processes" ) ;
gbc = new GridBagConstraints() ;
gbc.gridx = 1 ;
gbc.gridy = 1 ;
gbc.gridwidth = GridBagConstraints.REMAINDER ;
gbl.setConstraints( processesLabel , gbc ) ;

idLabel = new Label( "Id" , Label.RIGHT ) ;
gbc = new GridBagConstraints() ;
gbc.gridx = 1 ;
gbc.gridy = 2 ;
gbl.setConstraints( idLabel , gbc ) ;

stateLabel = new Label( "State" , Label.RIGHT ) ;
gbc = new GridBagConstraints() ;
gbc.gridx = 2 ;
gbc.gridy = 2 ;
gbl.setConstraints( stateLabel , gbc ) ;

resourceLabel = new Label( "Resource" , Label.RIGHT ) ;
gbc = new GridBagConstraints() ;
gbc.gridx = 3 ;
gbc.gridy = 2 ;
gbl.setConstraints( resourceLabel , gbc ) ;

for( int i = 0 ; i < processCount ; i ++ )
{
  Label idLabel ;
  Label stateLabel ;
  Label resourceLabel ;
  // create the labels
  // add labels to the vectors
  // set constraints
  idLabel = new Label( Integer.toString(i) , Label.RIGHT ) ;
  processIdLabelVector.insertElementAt( idLabel , i ) ;
  gbc = new GridBagConstraints() ;
  gbc.gridx = 1 ;
  gbc.gridy = 3 + i ;
  gbl.setConstraints( idLabel , gbc ) ;

  stateLabel = new Label( ) ;
  stateLabel.setAlignment( Label.RIGHT ) ;
  processStateLabelVector.insertElementAt( stateLabel , i ) ;
  gbc = new GridBagConstraints() ;
  gbc.gridx = 2 ;
  gbc.gridy = 3 + i ;
  gbl.setConstraints( stateLabel , gbc ) ;

  resourceLabel = new Label( ) ;
  resourceLabel.setAlignment( Label.RIGHT ) ;
  processResourceLabelVector.insertElementAt( resourceLabel , i ) ;
  gbc = new GridBagConstraints() ;
  gbc.gridx = 3 ;
  gbc.gridy = 3 + i ;
  gbl.setConstraints( resourceLabel , gbc ) ;
}

setLayout( gbl ) ;

add( processesLabel ) ;
add( idLabel ) ;
add( stateLabel ) ;
add( resourceLabel ) ;
for( int i = 0 ; i < processCount ; i ++ )
  {
  Label idLabel ;
  Label availableLabel;
  idLabel = (Label)processIdLabelVector.elementAt(i);
  stateLabel = (Label)processStateLabelVector.elementAt(i) ;
  resourceLabel = (Label)processResourceLabelVector.elementAt(i) ;
  add( idLabel ) ;
  add( stateLabel ) ;
  add( resourceLabel ) ;
  }
}

public void show()
{
  for( int i = 0 ; i < processCount ; i ++ )
  {
    Process process = (Process)processes.elementAt(i) ;
    ((Label)processStateLabelVector.elementAt(i)).setText(process.getState()) ;
    Resource resource = process.getResourceAwaiting() ;
    String resourceText = "" ;
    if ( resource != null )
       resourceText = Integer.toString(resource.getId()) ;
    ((Label)processResourceLabelVector.elementAt(i)).setText(resourceText);
  }
  super.show();  
}

}
