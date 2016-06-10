/**
 * @Author is flystyle
 * Created on 06.06.16.
 */
import java.util.*;

public class TuringMachine
{
    private Set<String> StateSpace;
    private Set<Transition> TransitionSpace;
    private String StartState;
    private String AcceptState;
    private String RejectState;

    private String Tape;
    private String CurrentState;
    private int CurrentSymbol;

    class Transition {
        String readState;
        char readSymbol;
        String writeState;
        char writeSymbol;
        boolean moveDirection;	//true is right, false is left

        boolean isConflicting(String state, char symbol)
        {
            if (state.equals(readState) && symbol == readSymbol)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }


    public TuringMachine() {
        StateSpace = new HashSet<String>();
        TransitionSpace = new HashSet<Transition>();
        StartState = new String("");
        AcceptState = new String("");
        RejectState = new String("");
        Tape = new String("");
        CurrentState = new String("");
        CurrentSymbol = 0;

    }

    public boolean Run(String input, boolean silentmode) {
        CurrentState = StartState;
        Tape = input;

        while(!CurrentState.equals(AcceptState) && !CurrentState.equals(RejectState))
        {
            boolean foundTransition = false;
            Transition CurrentTransition = null;

            if (!silentmode)
            {
                if (CurrentSymbol>0)
                {
                    System.out.println(Tape.substring(0, CurrentSymbol) + " " + CurrentState + " " + Tape.substring(CurrentSymbol));
                }
                else
                {
                    System.out.println(" " + CurrentState + " " + Tape.substring(CurrentSymbol));
                }
            }





            Iterator<Transition> TransitionsIterator = TransitionSpace.iterator();
            while ( TransitionsIterator.hasNext() && !foundTransition)
            {
                Transition nextTransition = TransitionsIterator.next();
                if (nextTransition.readState.equals(CurrentState) && nextTransition.readSymbol == Tape.charAt(CurrentSymbol))
                {
                    foundTransition = true;
                    CurrentTransition = nextTransition;
                }
            }

            if (!foundTransition)
            {
                System.err.println ("There is no valid transition for this phase! (state=" + CurrentState + ", symbol=" + Tape.charAt(CurrentSymbol) + ")");
                return false;
            }
            else
            {
                CurrentState = CurrentTransition.writeState;
                char[] tempTape = Tape.toCharArray();
                tempTape[CurrentSymbol] = CurrentTransition.writeSymbol;
                Tape =  new String(tempTape);
                if(CurrentTransition.moveDirection)
                {
                    CurrentSymbol++;
                }
                else
                {
                    CurrentSymbol--;
                }

                if (CurrentSymbol < 0)
                    CurrentSymbol = 0;

                while (Tape.length() <= CurrentSymbol)
                {
                    Tape = Tape.concat("_");
                }


            }


        }

        if (CurrentState.equals(AcceptState))
        {
            return true;
        }
        else
        {
            return false;
        }


    }

    public boolean addState(String newState) {
        if (StateSpace.contains(newState))
        {
            return false;
        }
        else
        {
            StateSpace.add(newState);
            return true;
        }
    }

    public boolean setStartState(String newStartState) {
        if (StateSpace.contains(newStartState))
        {
            StartState = newStartState;
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean setAcceptState(String newAcceptState) {
        if (StateSpace.contains(newAcceptState) && !RejectState.equals(newAcceptState))
        {
            AcceptState = newAcceptState;
            return true;
        }
        else
        {
            return false;
        }

    }

    public boolean setRejectState(String newRejectState) {
        if (StateSpace.contains(newRejectState) && !AcceptState.equals(newRejectState))
        {
            RejectState = newRejectState;
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean addTransition(String rState, char rSymbol, String wState, char wSymbol, boolean mDirection) {
        if(!StateSpace.contains(rState) || !StateSpace.contains(wState))
        {
            return false;
        }

        boolean conflict = false;
        Iterator<Transition> TransitionsIterator = TransitionSpace.iterator();
        while ( TransitionsIterator.hasNext() && conflict == false)
        {
            Transition nextTransition = TransitionsIterator.next();
            if (nextTransition.isConflicting(rState, rSymbol))
            {
                conflict = true;
            }

        }
        if (conflict == true)
        {
            return false;
        }
        else
        {
            Transition newTransition = new Transition();
            newTransition.readState = rState;
            newTransition.readSymbol = rSymbol;
            newTransition.writeState = wState;
            newTransition.writeSymbol = wSymbol;
            newTransition.moveDirection = mDirection;
            TransitionSpace.add(newTransition);
            return true;
        }
    }
}
