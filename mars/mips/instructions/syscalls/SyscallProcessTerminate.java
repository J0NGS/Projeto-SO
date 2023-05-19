package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.SO.ProcessManager.TabelaProcessos;
import mars.mips.SO.ProcessManager.PCB;
import mars.mips.SO.ProcessManager.Escalonador;

public class SyscallProcessTerminate extends AbstractSyscall{
    
    public SyscallProcessTerminate() {
        super(62, "Syscall Terminate");
    }

    @Override
    public void simulate(ProgramStatement statement) throws ProcessingException {
        // pegando o processo para ser finalizado, depois escalonando e removendo da lista de procesos prontos.
        PCB processoParaFinalizar = TabelaProcessos.getProcExect();
        Escalonador.escalonar();
        TabelaProcessos.removerProcPronto(processoParaFinalizar);
    }
    
}
