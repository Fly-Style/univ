import mpi.*;

public class MPJTest {
	
	
	
	public static void main(String[] args) {		
		
		MPI.Init(args);
        
        int rank = MPI.COMM_WORLD.Rank(); //get num of current thread
        int size = MPI.COMM_WORLD.Size();
        String str = "" + rank;
        
        do {
	        Object[] send_obj = new Object[1];
	        send_obj[0] = (Object) str;
	        if (rank == 0)
	        	MPI.COMM_WORLD.Isend(send_obj, 0, 1, MPI.OBJECT, size - 1, 0);
	        else 
	        	MPI.COMM_WORLD.Isend(send_obj, 0, 1, MPI.OBJECT, rank - 1, 0);
	        
	        System.out.println("Thread " + rank + " send "  + str);
	        
	        Object[] rec_obj = new Object[1];
	        if (rank == size - 1)
	        	MPI.COMM_WORLD.Recv(rec_obj, 0, 1, MPI.OBJECT, 0, 0);
	        else
	        	MPI.COMM_WORLD.Recv(rec_obj, 0, 1, MPI.OBJECT, rank + 1, 0);
	        
	        str = (String) rec_obj[0];
	        
	        System.out.println("Thread " + rank + " recv "  + str);
	        
	        
        } while (!str.equals("" + rank));
        System.out.println("Thread " + rank + " exit....");
        MPI.Finalize();
	}

}
