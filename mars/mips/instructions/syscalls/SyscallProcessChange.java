package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.SO.ProcessManager.Escalonador;
import mars.tools.Timer;

public class SyscallProcessChange extends AbstractSyscall{
    public SyscallProcessChange() {
        super(61, "SyscallProcessChange");
    }

    public void simulate(ProgramStatement statement) throws ProcessingException {
         Escalonador escalonador = new Escalonador(Timer.getAlgoritmoSelecionado());
        escalonador.escalonar(false);
    }
}
