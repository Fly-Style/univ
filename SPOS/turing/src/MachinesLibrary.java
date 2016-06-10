/**
 * @Author is flystyle
 * Created on 06.06.16.
 */
public final class MachinesLibrary
{
    private MachinesLibrary() {}

    public static TuringMachine EqualBinaryWords() {
        TuringMachine newTM = new TuringMachine();
        newTM.addState("q1");
        newTM.addState("q2");
        newTM.addState("q3");
        newTM.addState("q4");
        newTM.addState("q5");
        newTM.addState("q6");
        newTM.addState("q7");
        newTM.addState("q8");
        newTM.addState("qa");
        newTM.addState("qr");
        newTM.setStartState("q1");
        newTM.setAcceptState("qa");
        newTM.setRejectState("qr");
        newTM.addTransition("q1", '1', "q3", 'x', true);
        newTM.addTransition("q1", '0', "q2", 'x', true);
        newTM.addTransition("q1", '#', "q8", '#', true);
        newTM.addTransition("q2", '0', "q2", '0', true);
        newTM.addTransition("q2", '1', "q2", '1', true);
        newTM.addTransition("q2", '#', "q4", '#', true);
        newTM.addTransition("q3", '0', "q3", '0', true);
        newTM.addTransition("q3", '1', "q3", '1', true);
        newTM.addTransition("q3", '#', "q5", '#', true);
        newTM.addTransition("q4", 'x', "q4", 'x', true);
        newTM.addTransition("q4", '0', "q6", 'x', false);
        newTM.addTransition("q5", 'x', "q5", 'x', true);
        newTM.addTransition("q5", '1', "q6", 'x', false);
        newTM.addTransition("q6", '0', "q6", '0', false);
        newTM.addTransition("q6", '1', "q6", '1', false);
        newTM.addTransition("q6", 'x', "q6", 'x', false);
        newTM.addTransition("q6", '#', "q7", '#', false);
        newTM.addTransition("q7", '0', "q7", '0', false);
        newTM.addTransition("q7", '1', "q7", '1', false);
        newTM.addTransition("q7", 'x', "q1", 'x', true);
        newTM.addTransition("q8", 'x', "q8", 'x', true);
        newTM.addTransition("q8", '_', "qa", '_', true);
        return newTM;
    }

    public static TuringMachine Additional() {
        TuringMachine newTM = new TuringMachine();
        newTM.addState("q0");
        newTM.addState("q1");
        newTM.addState("q2");
        newTM.setStartState("q1");
        newTM.setAcceptState("q0");
        newTM.addTransition("q1", '1', "q2", '0', true);
        newTM.addTransition("q2", '1', "q2", '1', true);
        newTM.addTransition("q2", '0', "q0", '1', true);
        return newTM;
    }

}
