package mars.mips.instructions.syscalls;


import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.SO.ProcessManager.Semaforo;
import mars.mips.SO.ProcessManager.SemaforoList;
import mars.mips.hardware.RegisterFile;

public class SyscallDownSemaphore extends AbstractSyscall {
    public SyscallDownSemaphore() {
        super(65, "SyscallDownSemaphore");
    }

    @Override
    public void simulate(ProgramStatement statement) throws ProcessingException {
        int adress = RegisterFile.getValue(4);

        try {
            Semaforo semaforo = SemaforoList.getByAdress(adress);
            semaforo.decrementarValor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}