package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.SO.ProcessManager.TabelaProcessos;
import mars.mips.hardware.RegisterFile;

public class SyscallFork extends AbstractSyscall{
    
    public SyscallFork(){
        super(60, "Syscall Fork");
    }
    
    @Override
    public void simulate(ProgramStatement statement) throws ProcessingException {
        //Criando novo processo, pegando o valor a partir do registrador a0
        TabelaProcessos.novoProcesso(RegisterFile.getValue(4));
    }
}
