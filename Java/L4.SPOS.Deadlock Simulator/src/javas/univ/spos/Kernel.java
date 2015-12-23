package javas.univ.spos;

import java.io.IOException;
import java.util.Vector;

public class Kernel extends Thread 
{
  private int time = 0 ; 
  private int sleepTime = 1000 ;
  private String processFilenamePrefix = "process" ;
  private String processFilenameSuffix = ".dat" ;
  private int processCount ;
  private int resourceCount ;
  private Vector processes = new Vector() ;
  private Vector resources = new Vector() ;
  private ControlPanel controlPanel ;
  private boolean stepping = false ;
  private int haltedCount = 0 ;
  private int blockedCount = 0 ;

  public void processHalted()
  {
    haltedCount ++ ;
  }

  public void processBlocked()
  {
    blockedCount ++ ;
  }

  public void processUnblocked()
  {
    blockedCount -- ;
  }

  public void setProcessFilenamePrefix( String newProcessFilenamePrefix )
  {
    processFilenamePrefix = newProcessFilenamePrefix ;
  }

  public String getProcessFilenamePrefix( )
  {
    return processFilenamePrefix ;
  }

  public void setProcessFilenameSuffix( String newProcessFilenameSuffix )
  {
    processFilenameSuffix = newProcessFilenameSuffix ;
  }

  public String getProcessFilenameSuffix( )
  {
    return processFilenameSuffix ;
  }

  public boolean getStepping()
  {
    return stepping ;
  }

  public void setStepping( boolean newStepping )
  {
    stepping = newStepping ;
  }

  public int getTime( ) 
  {
    return time ;
  }

  public void setTime( int newTime )
  {
    time = newTime ;
  }

  public int getSleepTime()
  {
    return sleepTime ;
  }

  public void setSleepTime( int newSleepTime )
  {
    sleepTime = newSleepTime ;
  }

  public void setControlPanel( ControlPanel newControlPanel )
  {
    controlPanel = newControlPanel ;
  }

  public void setProcessCount( int newProcessCount ) 
  { 
    if ( newProcessCount > processCount )
    {
      for ( int i = processCount ; i < newProcessCount ; i ++ )
        processes.addElement( new Process( i , processFilenamePrefix +
          Integer.toString(i) + processFilenameSuffix ) ) ;
    }
    else if ( newProcessCount < processCount )
    {
      processes.setSize( newProcessCount ) ;
      processes.trimToSize( ) ;
    }
    processCount = newProcessCount ;
  }

  public int getProcessCount( )
  {
    return processCount ;
  }

  public void setResourceCount( int newResourceCount )
  {
    if ( newResourceCount > resourceCount )
    {
      for ( int i = resourceCount ; i < newResourceCount ; i ++ )
        resources.addElement( new Resource( i ) ) ;
    }
    else if ( newResourceCount < resourceCount )
    {
      resources.setSize( newResourceCount ) ;
      resources.trimToSize( ) ;
    }
    resourceCount = newResourceCount ;
  }

  public int getResourceCount( )
  {
    return resourceCount ;
  }

  public void setResourceInitialAvailable( int resource , int newInitialAvailable )
  {
    ((Resource)(resources.elementAt( resource ))).setInitialAvailable( newInitialAvailable ) ;
  }

  public int getResourceInitialAvailable( int resource )
  {
    return ((Resource)(resources.elementAt( resource ))).getInitialAvailable() ;
  }

  public Vector getResources()
  {
    return resources ;
  }

  public Vector getProcesses()
  {
    return processes ;
  }

  public void reset()
  {
    haltedCount = 0 ;
    blockedCount = 0 ;
    // reset all the resources
    for ( int i = 0 ; i < resourceCount ; i ++ )
    {
      Resource resource = (Resource)resources.elementAt(i) ;
      resource.setCurrentAvailable( resource.getInitialAvailable() ) ;
    }

    // reset all the processes
    for ( int i = 0 ; i < processCount ; i ++ )
    {
      try
      {
        ((Process)processes.elementAt(i)).reset() ;
      }
      catch (IOException e)
      {
        // the reset should fail, and an error message should be displayed.
        System.out.println( "unable to open \"" +
          ((Process)processes.elementAt(i)).getFilename() + "\" for input" ) ;
      }
    }
    time = 0 ;
    updateControlPanel();
  }

  public void step()
  {
    for(int i = 0 ; i < processCount ; i ++ )
    {
      Process process = (Process)processes.elementAt(i) ;
      /**
      Perform one millisecond of work for a process.  Note that only
      computable processes consume time.  It does not take time to
      grant a resource,
      nor does it take time to free a resource.  If a process is blocked,
      or if it is halted, the next process is free to execute.
      */
      boolean running = true ;

      while ( running )
      {
        switch( process.state )
        {
        case Process.STATE_UNKNOWN:
          Command command = process.cp.getCommand();
          if ( command == null )
          {
            process.state = Process.STATE_HALT ;
            processHalted() ;
          }
          else
          {
            String keyword = command.getKeyword() ;
            if ( keyword.equals( "C" ) )
            {
              process.timeToCompute = command.getParameter() ;
              process.state = Process.STATE_COMPUTABLE ;
            }
            else if ( keyword.equals( "R" ) )
            {
              // allocate resource command.getParameter()
              // state depends on whether the resource is available
              int r = command.getParameter() ;
              Resource resource = (Resource)resources.elementAt(r);
              if ( DeadlockManager.grantable( i , resource ) )
                DeadlockManager.allocate( i , resource ) ;
              else
              {
                // increment blocked count
                processBlocked();
                process.resourceAwaiting = resource ;
                process.state = Process.STATE_RESOURCE_WAIT ;
                if ( processCount == blockedCount )
                {
                  // deadlocked; have the deadlock manager kill a process.
                  DeadlockManager.deadlocked(); //////////////////////////////////////////////////////////////////Demonstrate Daedlock
                }
              }
              //running = false;//////////////////////////////////////////////////////////////////////////////////////////////DEADLOCK
            }
            else if ( keyword.equals( "F" ) )
            {
              // free resource command.getParameter() (if it was allocated ???)
              // state depends on whether there are other commands
              int r = command.getParameter() ;
              Resource resource = (Resource)resources.elementAt(r);
              DeadlockManager.deallocate( i , resource ) ;
            }
            else if ( keyword.equals( "H" ) )
            {
              process.state = Process.STATE_HALT ;
              processHalted() ;
            }
          }
          break ;
        case Process.STATE_COMPUTABLE:
          if ( process.timeToCompute > 0 )
          {
            process.timeToCompute -- ;
            running = false ;
          }
          else
            process.state = Process.STATE_UNKNOWN ;
          break ;
        case Process.STATE_RESOURCE_WAIT:
          if ( DeadlockManager.available( i , process.resourceAwaiting ) )
            if ( DeadlockManager.grantable( i , process.resourceAwaiting ) )
            {
              DeadlockManager.allocate( i , process.resourceAwaiting ) ;
              process.state = Process.STATE_UNKNOWN ;
              process.resourceAwaiting = null ;
              processUnblocked() ;
            }
            else
              running = false ; // continue to be blocked on this resource
          else
            running = false ; // continue to be blocked on this resource
          break ;
        case Process.STATE_HALT:
          // we're already stopped, no need to do anything
          running = false ;
          break ;
        }
      }
    }
    printStatus() ;
    time ++ ;
    updateControlPanel();
  }

  private void updateControlPanel()
  {
    controlPanel.setTime( time ) ;
    for( int i = 0 ; i < resourceCount ; i ++ )
    {
      Resource resource = (Resource)resources.elementAt(i) ;
      controlPanel.setResourceId(i,resource.getId());
      controlPanel.setResourceAvailable(i,resource.getCurrentAvailable() ) ;
    }
    for( int i = 0 ; i < processCount ; i ++ )
    {
      Process process = (Process)processes.elementAt(i) ;
      controlPanel.setProcessId(i,process.getId());
      controlPanel.setProcessState(i,process.getState() ) ;
      Resource resourceAwaiting = process.getResourceAwaiting() ;
      if ( resourceAwaiting == null )
      {
        controlPanel.setProcessResource(i,"") ;
      }
      else
      {
        controlPanel.setProcessResource(i,Integer.toString(resourceAwaiting.getId())) ;
      }
    }
    controlPanel.validate() ;
  }

  public void printStatus()
  {
    System.out.print( "time = " + time + " available resourse: " ) ;
    for( int i = 0 ; i < resourceCount ; i ++ )
      {
      Resource resource = ((Resource)resources.elementAt(i)) ;
        System.out.print( "id"+resource.getId()) ;
      System.out.print( "-" + resource.getCurrentAvailable()+" " ) ;
      }
    System.out.println( " count blocked process: " + blockedCount ) ;
  }

  public void run()
  {
    DeadlockManager.setProcesses(processes) ;
    DeadlockManager.setResources(resources) ;
    suspend() ;
    while( true )
    {
      step();
      if ( stepping )
      {
        suspend() ;
      }
      else
      {
        try
        {
          sleep( sleepTime ) ;
        }
        catch ( InterruptedException e ) 
        {
          // do nothing
        }
      }
      if ( processCount == haltedCount ||
           processCount == blockedCount )
      {
        controlPanel.stopAction() ;
        suspend() ;
      }
    }
  }

}
