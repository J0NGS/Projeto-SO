package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.SO.ProcessManager.Semaforo;
import mars.mips.SO.ProcessManager.SemaforoList;
import mars.mips.hardware.RegisterFile;

public class SyscallUpSemaphore extends AbstractSyscall {
    public SyscallUpSemaphore() {
        super(66, "SyscallUpSemaphore");
    }

    @Override
    public void simulate(ProgramStatement statement) throws ProcessingException {
        int adress = RegisterFile.getValue(4);

        try {
            Semaforo semaforo = SemaforoList.getByAdress(adress);
            semaforo.incrementarValor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}