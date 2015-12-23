package javas.univ.spos;

import java.util.Vector;


public class DeadlockManager
{

  private static Vector resources ;
  private static Vector processes ;

  public static void setResources( Vector newResources )
  {
    resources = newResources ;
  }

  public static void setProcesses( Vector newProcesses )
  {
    processes = newProcesses ;
  }

  /**
  Can the process be granted the resource?
  */
  public static boolean grantable( int id , Resource resource )
  {
    return available( id , resource ) ;
  }

  public static boolean available( int id , Resource resource )
  {
    return ( resource.getCurrentAvailable() > 0 ) ;
  }

  public static void allocate( int id , Resource resource )
  {
    resource.setCurrentAvailable( resource.getCurrentAvailable() - 1 ) ;
    // we also need to note that the process has the resource allocated to it
    Process p = (Process)processes.elementAt(id);
    p.addAllocatedResource( resource ) ;
  }

  public static void deallocate( int id , Resource resource )
  {
    resource.setCurrentAvailable( resource.getCurrentAvailable() + 1 ) ;
    // we also need to note that this process no longer has the resource allocated
    Process p = (Process)processes.elementAt(id);
    p.removeAllocatedResource( resource ) ;
  }

  /**
  all processes are blocked.  One of them should be killed 
  and its resources deallocated so that the others can continue.
  */
  public static void deadlocked()
  {
    System.out.println("Deadlock!");
  }

}