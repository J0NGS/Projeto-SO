package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.SO.ProcessManager.Escalonador;
import mars.mips.SO.ProcessManager.PCB;
import mars.mips.SO.ProcessManager.TabelaProcessos;

public class SyscallProcessChange extends AbstractSyscall{
    public SyscallProcessChange() {
        super(61, "SyscallProcessChange");
    }

    public void simulate(ProgramStatement statement) throws ProcessingException {
        PCB procExec = TabelaProcessos.getProcTop();
        procExec.registradoresPCB();
        Escalonador.escalonar();
        PCB novoProcExec = TabelaProcessos.getProcTop();
        novoProcExec.PCBRegistradores();
    }
}
