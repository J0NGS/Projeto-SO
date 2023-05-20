package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.SO.ProcessManager.SemaforoList;
import mars.mips.hardware.AddressErrorException;
import mars.mips.hardware.Memory;
import mars.mips.hardware.RegisterFile;

public class SyscallCreateSemaphore extends AbstractSyscall {
    public SyscallCreateSemaphore() {
        super(63, "SyscallCreateSemaphore");
    }

    @Override
    public void simulate(ProgramStatement statement) throws ProcessingException {
        int adress = RegisterFile.getValue(4);
        try {
            int value = Memory.getInstance().get(adress, 4);
            SemaforoList.create(value, adress);
        } catch (AddressErrorException e) {
            e.printStackTrace();
        }
    }
}