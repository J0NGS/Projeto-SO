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
        PCB procTop = TabelaProcessos.getProcTop();
        if(!procTop.getEstadoProcesso().equals("Executando")){
            procTop.setEstadoProcesso("Executando");
            procTop.PCBRegistradores();
        }else{
            procTop.registradoresPCB();
            Escalonador.escalonar();
        }
    }
}
