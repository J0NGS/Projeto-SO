package mars.mips.instructions.syscalls;

import mars.ProgramStatement;
import mars.mips.SO.ProcessManager.SemaforoList;
import mars.mips.hardware.AddressErrorException;
import mars.mips.hardware.Memory;
import mars.mips.hardware.RegisterFile;

public class SyscallCreateSemaphore extends AbstractSyscall{
    public SyscallCreateSemaphore(){
        super(63, "SyscallCreateSemaphore");
    }

    @Override
    public void simulate(ProgramStatement statement){
        int adress = RegisterFile.getValue(4);
        try {
            int valor = Memory.getInstance().get(adress, 4);
            SemaforoList.create(valor, adress);
        } catch (AddressErrorException e) {
            e.getMessage();
        }
    }
}
