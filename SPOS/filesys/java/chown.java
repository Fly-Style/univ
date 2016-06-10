/**
 * @Author is flystyle
 * Created on 29.05.16.
 */
public class chown {
    public static final String PROGRAM_NAME = "chown" ;

    public static void main (String [] args) throws Exception{

        /*
         * Example: java chown 2 /root/home
         */
        Kernel.initialize() ;

        if( args.length < 1 )
        {
            System.err.println( PROGRAM_NAME + ": too few arguments" ) ;
            Kernel.exit( 1 ) ;
        }

        short owner = Short.parseShort(args[0]);
        short grp   = 0;
        String name = args[1];

        Kernel.chown(name, owner, grp);

        Kernel.exit( 0 ) ;
    }
}
