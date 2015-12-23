package javas.univ.spos;

import java.io.File;
import java.io.FilenameFilter;

public class DatFilenameFilter implements FilenameFilter
{
  public DatFilenameFilter()
  {
    super() ;
  }

  public boolean accept( File dir , String name ) 
  {
    return name.endsWith(".dat");
  }
}