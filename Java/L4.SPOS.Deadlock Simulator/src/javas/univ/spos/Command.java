package javas.univ.spos;
/**
A command for a process.  This is an object representing a command
from a process command file.  It contains the process command letter
(C, E, F, R) and a numeric parameter (if C, F, or R).

@author Ray Ontko (rayo@Ontko.com)
*/
public class Command
{
  private String keyword = null ; 
  private int parameter = 0 ;

  public Command()
  {
    super();
  }

  public Command( String newKeyword , int newParameter )
  {
    super();
    setKeyword( newKeyword ) ;
    setParameter( newParameter ) ;
  }

  public String getKeyword()
  {
    return keyword ;
  }

  public int getParameter()
  {
    return parameter ;
  }

  public void setKeyword( String newKeyword )
  {
    keyword = newKeyword ;
  }

  public void setParameter( int newParameter )
  {
    parameter = newParameter ;
  }

  public String toString( ) 
  {
    return ("Command[keyword="+keyword+",parameter="+parameter+"]" ) ;
  }

}