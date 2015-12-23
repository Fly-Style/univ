package javas.univ.spos;

import java.io.*;

public class CommandParser
{
  private StreamTokenizer in ;
  private InputStream inputStream ;

  public CommandParser( InputStream newInputStream )
  {
    super() ;
    inputStream = newInputStream ;
    in = new StreamTokenizer( inputStream ) ;
    in.eolIsSignificant( true ) ;
    in.ordinaryChar('/');
    in.slashSlashComments( true ) ;
    in.slashStarComments( true ) ;
  }

  public void close() throws IOException
  {
    inputStream.close() ;
  }

  public Command getCommand()
  {
    String commandLetter = null ;
    int commandNumber = 0 ;
    try
    {
      int t ;
      int state = 0 ;
      boolean looping = true ;
      while ( looping ) 
      {
        t = in.nextToken() ;
        if ( t == StreamTokenizer.TT_EOF )
        {
          if ( state != 0 )
            throw new Exception( "unexpected text on line " + in.lineno() ) ;
          else
            return null ;
        }
        switch ( state )
        {
          case 0:// expect letter
            if ( t == StreamTokenizer.TT_WORD )
            {
              if ( in.sval.equals( "C" ) ||
                   in.sval.equals( "R" ) ||
                   in.sval.equals( "F" ) )
              {
                commandLetter = in.sval ;
                state = 1 ;
              }
            else if ( in.sval.equals( "H" ) )
              {
                commandLetter = in.sval ;
                state = 2 ;
              }
            else
              throw new Exception( "C,R,F, or H expected at start of line " + in.lineno() ) ; 
            }
          else if ( t == StreamTokenizer.TT_EOL )
            ; // do nothing
          else
            throw new Exception( "C,R,F, or H expected at start of line " + in.lineno() ) ; 
          break ;
        case 1: // expect parameter
          if ( t == StreamTokenizer.TT_NUMBER )
            {
            commandNumber = (int)in.nval ;
            state = 2 ;
            }
          else
            throw new Exception( "missing numeric value after command on line " + in.lineno());
          break ;
        case 2: // expect EOL
          if ( t == StreamTokenizer.TT_EOL )
            {
            state = 0 ;
            looping = false ;
            }
          else
            throw new Exception( "unexpected text after command online " + in.lineno());
          break ;
        }
//        System.out.println( "t " + t + " state " + state + " sval " + in.sval + " nval " + in.nval ) ;
      }
    }
    catch( IOException e )
    {
      System.out.println( "IOException " + e ) ;
    }
    catch ( Exception e )
    {
      System.out.println( e.toString() ) ;
    }
    return new Command( commandLetter, commandNumber ) ;
  }

  public static void main( String args[] ) 
  {
    String f = args[0] ;

    System.out.println( "filename " + f ) ;
    try
    {
      CommandParser cp = new CommandParser( new BufferedInputStream(new FileInputStream( f )) );
      while ( true ) 
      {
        Command command = cp.getCommand() ;
        if ( command == null )
          break ;
      }
     cp.close() ;
    }
    catch ( IOException e ) 
    {
      System.out.println( "IOException " + e ) ;
    }
  }
}
