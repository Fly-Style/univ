package javas.univ.spos;
public class Resource
{
  int id = 0 ;
  private int initialAvailable = 0 ;
  private int currentAvailable = 0 ;

  public Resource( int newId )
  {
    super() ;
    id = newId ;
  }

  public int getId()
  {
    return id ;
  }

  public void setInitialAvailable(int i) 
  {
    initialAvailable = i ;
  }

  public int getInitialAvailable()
  {
    return initialAvailable ;
  }

  public void setCurrentAvailable(int i) 
  {
    currentAvailable = i ;
  }

  public int getCurrentAvailable()
  {
    return currentAvailable ;
  }

  public void reset()
  {
    currentAvailable = initialAvailable ;
  }

  public void allocate()
  {
    currentAvailable -- ;
  }

  public void deallocate()
  {
    currentAvailable ++ ;
  }

}